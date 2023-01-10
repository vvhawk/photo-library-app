package com.example.android;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Album implements Serializable {

    private String albumName;

    private ArrayList<Photo> albumOfPhotos;

    public Album(String albumName, ArrayList<Photo> photos) {

        this.albumName = albumName;
        this.albumOfPhotos = photos;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String toString()
    {
        return albumName;
    }

    public void setAlbumName(String rename) {
        this.albumName = rename;
    }

    public ArrayList<Photo> getPhotos() {
        return albumOfPhotos;
    }

}