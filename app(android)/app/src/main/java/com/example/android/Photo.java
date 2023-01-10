package com.example.android;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Photo implements Serializable {

    private String caption;
    private ArrayList<Tag> tags;
    private Calendar photoDate;
    private String filePath;
    public String uri;

    public Photo(String path) {

        this.filePath = path;
        this.tags = new ArrayList<Tag>();

        tags.add(new Tag("location"));
        tags.add(new Tag("people"));

    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String cap) {
        this.caption = cap;
    }


    public String getFilePath() {
        return filePath;
    }


    public Bitmap toBM()
    {
        byte[] imageAsBytes = Base64.decode(this.filePath.getBytes(), Base64.DEFAULT);

        Bitmap bm = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);

        return  bm;
    }


    public ArrayList<Tag> getTags() {
        return tags;
    }

    public void add(Tag added) {
        tags.add(added);
    }
}