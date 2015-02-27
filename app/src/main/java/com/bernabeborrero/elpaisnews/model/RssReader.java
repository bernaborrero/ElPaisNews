package com.bernabeborrero.elpaisnews.model;

import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by berna on 13/02/15.
 */
public class RssReader {

    // Our class has an attribute which represents RSS Feed URL
    private String rssUrl;
    /**
     * We set this URL with the constructor
     */
    public RssReader(String rssUrl) {
        this.rssUrl = rssUrl;
    }
    /**
     * Get RSS items. This method will be called to get the parsing process result.
     * @return
     */
    public ArrayList<NewsItem> getItems() throws Exception {
        // At first we need to get an SAX Parser Factory object
        SAXParserFactory factory = SAXParserFactory.newInstance();
        // Using factory we create a new SAX Parser instance
        SAXParser saxParser = factory.newSAXParser();
        // We need the SAX parser handler object
        RssParseHandler handler = new RssParseHandler();
        // We call the method parsing our RSS Feed
        saxParser.parse(rssUrl, handler);
        // The result of the parsing process is being stored in the handler object
        return handler.getItems();
    }

}
