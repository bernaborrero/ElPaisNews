package com.bernabeborrero.elpaisnews.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.bernabeborrero.elpaisnews.model.Constants;
import com.bernabeborrero.elpaisnews.model.DataConnection;
import com.bernabeborrero.elpaisnews.model.NewsItem;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by Bernabé Borrero on 20/02/15.
 */
public class NewsItemConversor implements DataConnection {

    private NewsItemSQLiteHelper helper;
    private SQLiteDatabase db;

    public NewsItemConversor(Context context) {
        this.helper = new NewsItemSQLiteHelper(context, Constants.DB.DB_NAME, null, Constants.DB.DB_VERSION);
        db = this.helper.getWritableDatabase();
    }

    @Override
    public long save(NewsItem newsItem) {
        long index = -1;

        ContentValues data = new ContentValues();
        //  data.put("id", newsItem.getId());
        data.put("title", newsItem.getTitle());
        data.put("description", newsItem.getDescription());
        data.put("content", newsItem.getContent());
        data.put("link", newsItem.getLink());
        data.put("section", newsItem.getSection());

        if(newsItem.getImage() != null) {
            ByteArrayOutputStream imageStream = new ByteArrayOutputStream();
            newsItem.getImage().compress(Bitmap.CompressFormat.JPEG, 100, imageStream);
            byte[] bytesImage = imageStream.toByteArray();
            data.put("image", bytesImage);
        }

        try {
            index = db.insertOrThrow("NewsItems", null, data);
        } catch (Exception e) {
            Log.e("NewsItems", e.getMessage());
        }

        return index;
    }

    @Override
    public ArrayList<NewsItem> getAll() {
        ArrayList<NewsItem> items = new ArrayList<>();

        Cursor c = db.query(true, "NewsItems",
                new String[]{"id", "title", "description", "content", "link", "image"},
                null, null, null, null, null, null);

        while(c.moveToNext()) {
            byte[] bytesImage = c.getBlob(5);
            Bitmap image = null;
            if(bytesImage != null) {
                image = BitmapFactory.decodeByteArray(bytesImage, 0, bytesImage.length);
            }

            items.add(new NewsItem(c.getLong(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), image));
        }

        c.close();

        return items;
    }

    @Override
    public ArrayList<NewsItem> getAllFromSection(int sectionNumber) {
        ArrayList<NewsItem> items = new ArrayList<>();

        Cursor c = db.query(true, "NewsItems",
                new String[]{"id", "title", "description", "content", "link", "image"},
                "section = ?", new String[]{String.valueOf(sectionNumber)}, null, null, null, null);

        while(c.moveToNext()) {
            byte[] bytesImage = c.getBlob(5);
            Bitmap image = null;
            if(bytesImage != null) {
                image = BitmapFactory.decodeByteArray(bytesImage, 0, bytesImage.length);
            }

            items.add(new NewsItem(c.getLong(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), image));
        }

        c.close();

        return items;
    }

    @Override
    public NewsItem getById(long id) {
        NewsItem item = null;

        Cursor c = db.query(true, "NewsItems",
                new String[]{"id", "title", "description", "content", "link", "image"},
                "id = ?", new String[]{String.valueOf(id)}, null, null, null, null);

        if(c.getCount() > 0) {
            byte[] bytesImage = c.getBlob(5);
            Bitmap image = null;
            if(bytesImage != null) {
                image = BitmapFactory.decodeByteArray(bytesImage, 0, bytesImage.length);
            }

            item = new NewsItem(c.getLong(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), image);
        }

        return item;
    }

    @Override
    public boolean remove(NewsItem newsItem) {
        return db.delete("NewsItems", "id = ?", new String[]{String.valueOf(newsItem.getId())}) > 0;
    }

    @Override
    public boolean removeAll() {
        return db.delete("NewsItems", null, null) > 0;
    }

    @Override
    public int removeAllFromSection(int sectionNumber) {
        return db.delete("NewsItems", "section = ?", new String[]{String.valueOf(sectionNumber)});
    }

    @Override
    public void close() {
        db.close();
    }


}
