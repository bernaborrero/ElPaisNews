package com.bernabeborrero.elpaisnews.model;

import java.util.ArrayList;

/**
 * Created by Bernabé Borrero on 20/02/15.
 */
public interface DataConnection {

    /**
     * Save a single item to the database
     * @param newsItem the item to save
     * @return the id of the saved item
     */
    public long save(NewsItem newsItem);

    /**
     * Retrieve all the items in the database
     * @return a list containing all the items
     */
    public ArrayList<NewsItem> getAll();

    /**
     * Retrieve all the items in the corresponding section
     * @param sectionNumber the fragment section
     * @return a list containing all the items in the specified section
     */
    public ArrayList<NewsItem> getAllFromSection(int sectionNumber);

    /**
     * Retrieve a single item from its id
     * @return the item
     */
    public NewsItem getById(long id);

    /**
     * Remove an item from the database
     * @param newsItem the item to remove
     * @return true if the operation succeeded, false otherwise
     */
    public boolean remove(NewsItem newsItem);

    /**
     * Remove all the items in the database
     * @return true if the operation succeeded, false otherwise
     */
    public boolean removeAll();

    /**
     * Remove all the items in the specified section
     * @param sectionNumber the fragment section
     * @return number of items deleted
     */
    public int removeAllFromSection(int sectionNumber);

    /**
     * Close the database connection
     */
    public void close();

}
