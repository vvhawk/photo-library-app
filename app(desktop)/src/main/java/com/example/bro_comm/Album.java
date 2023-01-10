package com.example.bro_comm;

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

    public void setAlbumName(String rename) {
        this.albumName = rename;
    }

    public ArrayList<Photo> getPhotos() {
        return albumOfPhotos;
    }

    public int getCount() {
        return albumOfPhotos.size();
    }

    public String getFirstDate()
    {
        int currentFirst = 0;

        for (int i = 0; i < albumOfPhotos.size(); i++)
        {
            if (albumOfPhotos.get(i).getDate().getTime().compareTo(albumOfPhotos.get(currentFirst).getDate().getTime()) == -1)
            {
                currentFirst = i;
            }

        }

        if (albumOfPhotos.size() == 0)
        {
            return "";
        }
        Calendar cal = albumOfPhotos.get(currentFirst).getDate();
        SimpleDateFormat format1 = new SimpleDateFormat("MM-dd-yyyy");
        String formatted = format1.format(cal.getTime());

        return formatted;

    }


    public String getLastDate()
    {


        int currentLast = 0;

        for (int i = 0; i < albumOfPhotos.size(); i++)
        {
            if (albumOfPhotos.get(i).getDate().getTime().compareTo(albumOfPhotos.get(currentLast).getDate().getTime()) == 1)
            {
                currentLast = i;
            }

        }

        if (albumOfPhotos.size() == 0)
        {
            return "";
        }

        Calendar cal = albumOfPhotos.get(currentLast).getDate();
        SimpleDateFormat format1 = new SimpleDateFormat("MM-dd-yyyy");
        String formatted = format1.format(cal.getTime());

        return formatted;

    }

}
