package com.example.prasenjit.xmlfeedapp;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.Serializable;

/**
 * Created by PRASENJIT on 1/5/2015.
 */
public class RssItem implements Serializable {

    private String title;
    private String date;
    private StringBuffer description = new StringBuffer();
    private Bitmap image = null;
    private String link;
    private String imageUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getLink() {
        return link;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public StringBuffer getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description.append(description);
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "Feed [title =" + title + ", date " + date + ", link=" +link+ "]";
    }
}
