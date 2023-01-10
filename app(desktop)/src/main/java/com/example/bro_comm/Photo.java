package com.example.bro_comm;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Photo implements Serializable
{

    private String caption;
    private ArrayList<Tag> tags;
    private Calendar photoDate;
    private String filePath;

    public Photo(String path)
    {

        this.filePath = path;
        this.caption = "";
        this.tags = new ArrayList<Tag>();

        tags.add(new Tag("location"));
        tags.add(new Tag("people"));

    }

    public String getCaption()
    {
        return caption;
    }

    public void setCaption(String cap)
    {
        this.caption = cap;
    }


    public String getFilePath()
    {
        return filePath;
    }

    public ArrayList<Tag> getTags()
    {
        return tags;
    }

    public void add (Tag added)
    {
        tags.add(added);
    }

    public Calendar getDate()
    {
        Date date = new Date(new File(filePath).lastModified());
        this.photoDate = toCalendar(date);
        this.photoDate.set(Calendar.MILLISECOND, 0);
        return photoDate;
    }


    public static Calendar toCalendar(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }




}
