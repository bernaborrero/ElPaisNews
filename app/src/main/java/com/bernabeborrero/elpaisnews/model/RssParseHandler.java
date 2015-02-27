package com.bernabeborrero.elpaisnews.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by berna on 13/02/15.
 */
public class RssParseHandler extends DefaultHandler {

    // List of items parsed
    private ArrayList<NewsItem> rssItems;
    private NewsItem currentItem;
    private boolean parsingTitle;
    private boolean parsingLink;
    private boolean parsingDescription;
    private boolean parsingContent;
    private boolean isFirstImage;

    public RssParseHandler() {
        rssItems = new ArrayList();
    }

    public ArrayList<NewsItem> getItems() {
        return rssItems;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if ("item".equals(qName)) {
            currentItem = new NewsItem();
            isFirstImage = true;
        } else if ("title".equals(qName)) {
            parsingTitle = true;
        } else if ("link".equals(qName)) {
            parsingLink = true;
        } else if ("description".equals(qName)) {
            parsingDescription = true;
        } else if ("content:encoded".equals(qName)) {
            parsingContent = true;
        } else if ("enclosure".equals(qName)) {
            if (currentItem != null) {
                if(isFirstImage) {
                    isFirstImage = false;
                } else {
                    currentItem.setImage(getBitmapFromUrl(attributes.getValue("url")));
                    isFirstImage = false;
                }
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("item".equals(qName)) {
            rssItems.add(currentItem);
            currentItem = null;
        } else if ("title".equals(qName)) {
            parsingTitle = false;
        } else if ("link".equals(qName)) {
            parsingLink = false;
        } else if ("description".equals(qName)) {
            parsingDescription = false;
        } else if ("content:encoded".equals(qName)) {
            parsingContent = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (parsingTitle) {
            if (currentItem != null)
                currentItem.setTitle(new String(ch, start, length));
        } else if (parsingLink) {
            if (currentItem != null) {
                currentItem.setLink(new String(ch, start, length));
                parsingLink = false;
            }
        } else if (parsingDescription) {
            if (currentItem != null) {
                currentItem.setDescription(new String(ch, start, length));
                parsingDescription = false;
            }
        } else if (parsingContent) {
            if (currentItem != null) {
                currentItem.setContent(new String(ch, start, length));
                parsingContent = false;
            }
        }
    }

    private Bitmap getBitmapFromUrl(String imageUrl) {
        Bitmap image = null;
        try {
            InputStream in = new java.net.URL(imageUrl).openStream();
            image = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return image;
    }
}
