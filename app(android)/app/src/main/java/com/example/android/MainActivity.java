package com.example.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{

    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Home");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.coral)));
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.coral));


        loadData();

    }
        public void openAlbum (View v) throws IOException
        {

            Intent i = new Intent(this, AlbumActivity.class);
            i.putExtra("id", user);
            saveData();
            startActivity(i);

        }

        public void about (View v) throws IOException
        {

            Intent j = new Intent(this, AboutActivity.class);
            saveData();
            startActivity(j);

        }

        @Override
        public void onBackPressed()
        {
            saveData();
            super.onBackPressed();

        }

        public void saveData()
        {
            SharedPreferences sp = getSharedPreferences("sp", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            Gson gson = new Gson();
            String json = gson.toJson(user);
            editor.putString("dude", json);
            editor.apply();
        }

        public void loadData()
        {
            SharedPreferences sp = getSharedPreferences("sp", MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sp.getString("dude", null);
            Type type = new TypeToken<User>() {}.getType();
            user = gson.fromJson(json, type);

            if (user == null)
            {
                user = new User();
            }
        }
    }
