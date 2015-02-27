package com.bernabeborrero.elpaisnews.model;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by berna on 6/02/15.
 */
public class NewsItem implements Serializable {
    private long id;
    private int section;
    private String title, description, content, link;
    private SerializableBitmap image;

    public NewsItem() {
        this(-1, -1, "", "", "", "", null);
    }

    public NewsItem(long id, String title, String description, String content, String link, Bitmap image) {
        this(id, -1, title, description, content, link, image);
    }

    public NewsItem(long id, int section, String title, String description, String content, String link, Bitmap image) {
        this.id = id;
        this.section = section;
        this.title = title;
        this.description = description;
        this.content = content;
        this.link = link;
        this.image = new SerializableBitmap(image);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String subtitle) {
        this.description = subtitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Bitmap getImage() {
        if(image != null) {
            return image.getBitmap();
        } else {
            return null;
        }
    }

    public void setImage(Bitmap image) {
        this.image = new SerializableBitmap(image);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }
}
