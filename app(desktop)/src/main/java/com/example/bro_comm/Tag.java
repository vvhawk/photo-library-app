package com.example.bro_comm;

import java.io.Serializable;
import java.util.ArrayList;

public class Tag implements Serializable {

    private String tagName;
    private ArrayList<String> tagValues;



    public Tag(String tagName)
    {

        this.tagName = tagName;
        tagValues = new ArrayList<String>();

    }

    public String getName()
    {
        return tagName;
    }

    public void setName(String name)
    {
        this.tagName = name;
    }

    public ArrayList<String> getTagValue()
    {
        return tagValues;
    }

    public void setTagValue(String tag)
    {
        tagValues.add(tag);
    }



}
