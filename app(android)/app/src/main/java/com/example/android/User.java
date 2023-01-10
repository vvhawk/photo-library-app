package com.example.android;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable
{

    public ArrayList<Album> albums;

    private static boolean stock = true;



    public User()
    {

        this.albums= new ArrayList<Album>();
    }



    public static boolean getStock()
    {
        return stock;
    }

    public static void killStock()
    {
        stock = false;
    }



}

