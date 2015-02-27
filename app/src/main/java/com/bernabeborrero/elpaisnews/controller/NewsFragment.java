package com.bernabeborrero.elpaisnews.controller;

/**
 * Created by berna on 6/02/15.
 */

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.bernabeborrero.elpaisnews.R;
import com.bernabeborrero.elpaisnews.model.NewsItem;
import com.bernabeborrero.elpaisnews.model.RssReader;
import com.bernabeborrero.elpaisnews.model.dao.NewsItemConversor;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class NewsFragment extends Fragment {
    private int sectionNumber;
    private ListView listItems;
    private TextView noItems;
    private NewsItemConversor dataManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static NewsFragment newInstance(int sectionNumber) {
        NewsFragment fragment = new NewsFragment();
        fragment.setSectionNumber(sectionNumber);
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_headlines, container, false);

        listItems = (ListView) rootView.findViewById(R.id.listHeadlines);
        noItems = (TextView) rootView.findViewById(R.id.noItemsText);

        // Populate list view
        dataManager = new NewsItemConversor(getActivity().getBaseContext());
        ArrayList<NewsItem> data = dataManager.getAllFromSection(sectionNumber);

        if(data.size() == 0) {
            listItems.setVisibility(View.GONE);
            noItems.setVisibility(View.VISIBLE);

            // Load news if DB section is empty
            new DownloadNews().execute();
        } else {
            NewsAdapter adapter = new NewsAdapter(getActivity(), data);
            listItems.setAdapter(adapter);
        }

        // Swipe to refresh
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.headlines_swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.blue_dark, R.color.blue_normal, R.color.blue_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new DownloadNews().execute();
            }
        });

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Close the database connection
        dataManager.close();
    }

    public void setSectionNumber(int sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    private static class XmlFeedUrls {
        private static String urls[] = new String[] {
                "http://ep00.epimg.net/rss/tags/ultimas_noticias.xml",
                "http://ep00.epimg.net/rss/elpais/portada.xml",
                "http://elpais.com/tag/rss/europa/a/",
                "http://ep00.epimg.net/rss/internacional/portada.xml"
        };

        public static String getUrl(int position) {
            return urls[position];
        }
    }

    private class DownloadNews extends AsyncTask<Void, Void, ArrayList<NewsItem>> {

        @Override
        protected ArrayList<NewsItem> doInBackground(Void... params) {
            ArrayList<NewsItem> data =  null;

            try {
                data = loadXmlFromNetwork(XmlFeedUrls.getUrl(sectionNumber));
                if(data.size() > 0) {
                    dataManager.removeAllFromSection(sectionNumber);

                    for(NewsItem item : data) {
                        item.setSection(sectionNumber);
                        dataManager.save(item);
                    }
                }

            } catch (Exception e) {
                Log.e("NewsItems", "Error on XML parsing...");
            }

            return data;
        }

        @Override
        protected void onPostExecute(ArrayList<NewsItem> data) {
            if(data != null) {
                noItems.setVisibility(View.GONE);
                listItems.setVisibility(View.VISIBLE);

                NewsAdapter adapter = new NewsAdapter(getActivity(), data);
                listItems.setAdapter(adapter);
            } else {
                listItems.setVisibility(View.GONE);
                noItems.setVisibility(View.VISIBLE);
                noItems.setText(R.string.error);
            }

            // Stop refreshing animation
            swipeRefreshLayout.setRefreshing(false);
        }

        /**
         * Loads the XML file from the server
         * @param urlString
         * @return
         * @throws XmlPullParserException
         * @throws IOException
         */
        private ArrayList<NewsItem> loadXmlFromNetwork(String urlString) throws Exception {
            ArrayList<NewsItem> entries = null;
            // Instantiate the parser
            RssReader rssReader = new RssReader(urlString);
            entries = rssReader.getItems();
            return entries;
        }
    }
}