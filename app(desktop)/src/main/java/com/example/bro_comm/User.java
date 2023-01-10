package com.example.bro_comm;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable
{

        private String name;
        public ArrayList<Album> albums;

        private static boolean stock = true;



        public User(String name)
        {
            this.name = name;
            this.albums= new ArrayList<Album>();
        }

        public String getName()
        {
            return name;
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

