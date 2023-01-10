package com.example.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class DisplayActivity extends AppCompatActivity {


    TextView displayTitle;

    Button displayForward;
    Button displayReverse;

    ImageView displayView;

    User user;
    int albumSelect;

    int tracer;

    Spinner displayTagSpin;
    ArrayAdapter<Tag> adapter;

    Spinner displayTagList;
    ArrayAdapter<String> adapterL;

    EditText displayAddTagField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.display)));
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.display));

        Intent passer = getIntent();

        loadData();

        tracer = (int) passer.getIntExtra("photoSelect", 0);
        albumSelect = AlbumActivity.albumSelect;

        displayTitle = findViewById(R.id.displayTitle);
        displayTitle.setText(user.albums.get(albumSelect).getAlbumName());

        displayView = findViewById(R.id.displayView);

        displayForward = findViewById(R.id.displayForward);
        displayReverse = findViewById(R.id.displayReverse);

        displayTagSpin = findViewById(R.id.displayTagSpin);

        displayAddTagField = findViewById(R.id.displayAddTagField);

        displayTagList = findViewById(R.id.displayTagList);


        displayView.setImageBitmap(user.albums.get(albumSelect).getPhotos().get(tracer).toBM());

        ArrayList<Tag> myTags = user.albums.get(albumSelect).getPhotos().get(tracer).getTags();

        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, myTags);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        displayTagSpin.setAdapter(adapter);


        displayTagSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {

                ArrayList<String> tagValues = null;

                Tag location = user.albums.get(albumSelect).getPhotos().get(tracer).getTags().get(0);
                Tag people = user.albums.get(albumSelect).getPhotos().get(tracer).getTags().get(1);

                if (displayTagSpin.getSelectedItem().toString().equals("location"))
                {
                    Log.d("display", "location listen");
                    tagValues = location.getTagValue();
                }
                else if (displayTagSpin.getSelectedItem().toString().equals("people"))
                {
                    Log.d("display", "people listen");
                    tagValues = people.getTagValue();
                }

                adapterL = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, tagValues);
                displayTagList.setAdapter(adapterL);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });


    }

    public void displayForward(View v)
    {
        tracer++;

        if (tracer > user.albums.get(albumSelect).getPhotos().size() - 1)
        {
            tracer = 0;
        }

        displayView.setImageBitmap(user.albums.get(albumSelect).getPhotos().get(tracer).toBM());

        Tag location = user.albums.get(albumSelect).getPhotos().get(tracer).getTags().get(0);
        Tag people = user.albums.get(albumSelect).getPhotos().get(tracer).getTags().get(1);

        ArrayList<String> tagValues = null;

        if (displayTagSpin.getSelectedItem().toString().equals("location"))
        {
            Log.d("display", "location");
            tagValues = location.getTagValue();
        }
        else if (displayTagSpin.getSelectedItem().toString().equals("people"))
        {
            Log.d("display", "people");
            tagValues = people.getTagValue();
        }

        adapterL = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, tagValues);
        displayTagList.setAdapter(adapterL);
    }

    public void displayReverse(View v)
    {
        tracer--;

        if (tracer < 0)
        {
            tracer = user.albums.get(albumSelect).getPhotos().size() - 1;
        }

        displayView.setImageBitmap(user.albums.get(albumSelect).getPhotos().get(tracer).toBM());

        Tag location = user.albums.get(albumSelect).getPhotos().get(tracer).getTags().get(0);
        Tag people = user.albums.get(albumSelect).getPhotos().get(tracer).getTags().get(1);

        ArrayList<String> tagValues = null;

        if (displayTagSpin.getSelectedItem().toString().equals("location"))
        {
            Log.d("display", "location");
            tagValues = location.getTagValue();
        }
        else if (displayTagSpin.getSelectedItem().toString().equals("people"))
        {
            Log.d("display", "people");
            tagValues = people.getTagValue();
        }

        adapterL = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, tagValues);
        displayTagList.setAdapter(adapterL);

    }



    public void addTag(View v)
    {

        if (displayAddTagField.getText().toString().equals(""))
        {
            Toast.makeText(this, "tag field empty", Toast.LENGTH_LONG).show();
            return;
        }

        String tagInput = displayAddTagField.getText().toString();



        Tag location = user.albums.get(albumSelect).getPhotos().get(tracer).getTags().get(0);
        Tag people = user.albums.get(albumSelect).getPhotos().get(tracer).getTags().get(1);

        if (displayTagSpin.getSelectedItem().toString().equals("location"))
        {
            Log.d("display", "location");
            if (location.getTagValue().size() == 0)
            {
                location.setTagValue(tagInput);
                saveData();
            }
            else
            {
                Toast.makeText(this, "only one location allowed", Toast.LENGTH_LONG).show();
                return;
            }


            ArrayList<String> tagValues = location.getTagValue();

            adapterL = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, tagValues);
            displayTagList.setAdapter(adapterL);

            displayAddTagField.setText(null);




        }
        else if (displayTagSpin.getSelectedItem().toString().equals("people"))
        {
            Log.d("display", "people");
            people.setTagValue(tagInput);
            saveData();

            ArrayList<String> tagValues = people.getTagValue();

            adapterL = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, tagValues);
            displayTagList.setAdapter(adapterL);

            displayAddTagField.setText(null);

            displayTagList.setSelection(tagValues.size() - 1);
        }






    }

    public void deleteTag(View v)
    {
        Log.d("display", "delete -1");
        Tag location = user.albums.get(albumSelect).getPhotos().get(tracer).getTags().get(0);
        Tag people = user.albums.get(albumSelect).getPhotos().get(tracer).getTags().get(1);

        if (displayTagList.getSelectedItem() == null)
        {
            return;
        }
        String select = displayTagList.getSelectedItem().toString();
        int selectPos = displayTagList.getSelectedItemPosition();


        if (displayTagSpin.getSelectedItem().toString().equals("location"))
        {

            for (int i = 0; i < location.getTagValue().size(); i++)
            {

                if (location.getTagValue().get(i).equals(select))
                {

                    location.getTagValue().remove(i);
                    saveData();
                    break;
                }
            }

            ArrayList<String> tagValues = location.getTagValue();

            adapterL = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, tagValues);
            displayTagList.setAdapter(adapterL);


        }
        else if (displayTagSpin.getSelectedItem().toString().equals("people"))
        {
            for (int i = 0; i < people.getTagValue().size(); i++)
            {
                if (people.getTagValue().get(i).equals(select))
                {
                    people.getTagValue().remove(i);
                    saveData();
                    break;
                }
            }

            ArrayList<String> tagValues = people.getTagValue();

            adapterL = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, tagValues);
            displayTagList.setAdapter(adapterL);


            if (selectPos  == tagValues.size())
            {
                selectPos = 0;
            }

            displayTagList.setSelection(selectPos);


        }

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