package com.bernabeborrero.elpaisnews.controller;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bernabeborrero.elpaisnews.DetailActivity;
import com.bernabeborrero.elpaisnews.R;
import com.bernabeborrero.elpaisnews.model.NewsItem;

import java.util.ArrayList;

/**
 * Created by berna on 6/02/15.
 */
public class NewsAdapter extends ArrayAdapter<NewsItem> {
    private Activity context;
    private ArrayList<NewsItem> dades;

    public NewsAdapter(Activity context, ArrayList<NewsItem> dades) {
        super(context, R.layout.news_list_item, dades);
        this.context = context;
        this.dades = dades;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View element = convertView;
        NewsItemView vista;

        if(element == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            element = inflater.inflate(R.layout.news_list_item, null);

            vista = new NewsItemView();
            vista.cardView = (CardView)element.findViewById(R.id.card_view);
            vista.image = (ImageView)element.findViewById(R.id.itemImage);
            vista.title = (TextView)element.findViewById(R.id.newsitem_title);
            vista.subtitle = (TextView)element.findViewById(R.id.newsitem_subtitle);

            element.setTag(vista);

        } else {
            vista = (NewsItemView)element.getTag();
        }

        // Obtain data
        vista.image.setImageBitmap(dades.get(position).getImage());
        vista.image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        vista.title.setText(dades.get(position).getTitle());
        vista.subtitle.setText(Html.fromHtml(dades.get(position).getDescription()));

        vista.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(context, DetailActivity.class);
                detailIntent.putExtra(DetailActivity.ITEM, dades.get(position));
                context.startActivity(detailIntent);
            }
        });

        return element;
    }



    private class NewsItemView {
        public CardView cardView;
        public ImageView image;
        public TextView title;
        public TextView subtitle;
    }


}
