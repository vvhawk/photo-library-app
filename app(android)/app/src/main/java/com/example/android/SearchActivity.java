package com.example.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {

    EditText searchLocation;
    EditText searchPeople;

    Button searchSearch;
    Switch searchContext;

    boolean context;

    User user;

    ImageAdapter adapterS;

    GridView openGridS;

    int albumSelect = AlbumActivity.albumSelect;

    Album master;

    TextView searchCounter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.search)));
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.search));

        loadData();
        searchLocation = findViewById(R.id.searchLocation);
        searchPeople = findViewById(R.id.searchPeople);


        searchSearch = findViewById(R.id.searchSearch);
        searchContext = findViewById(R.id.searchPrefix);

        searchCounter = findViewById(R.id.searchCounter);

        openGridS = findViewById(R.id.openGridS);


        searchContext.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {

                if (buttonView.isChecked())
                {
                    context = true;
                    Log.d("search", "context true");
                }
                else if (!buttonView.isChecked())
                {
                    context = false;
                    Log.d("search", "context false");
                }
            }
        });

    }

    public void search(View v)
    {


        master = new Album("master", new ArrayList<Photo>());


        String location = searchLocation.getText().toString().toLowerCase();
        String person = searchPeople.getText().toString().toLowerCase();

        if (context == false) {

            for (int i = 0; i < user.albums.size(); i++) {
                for (int j = 0; j < user.albums.get(i).getPhotos().size(); j++) {

                    if (location.isEmpty() && person.isEmpty()) {
                        Log.d("search", "null null");
                        return;
                    } else if (!location.isEmpty() && person.isEmpty()) {
                        for (int k = 0; k < user.albums.get(i).getPhotos().get(j).getTags().get(0).getTagValue().size(); k++) {
                            if (user.albums.get(i).getPhotos().get(j).getTags().get(0).getTagValue().get(k).equalsIgnoreCase(location)) {
                                Log.d("search", "location only ");
                                master.getPhotos().add(user.albums.get(i).getPhotos().get(j));
                            }
                        }
                    } else if (location.isEmpty() && !person.isEmpty()) {
                        for (int k = 0; k < user.albums.get(i).getPhotos().get(j).getTags().get(1).getTagValue().size(); k++) {
                            if (user.albums.get(i).getPhotos().get(j).getTags().get(1).getTagValue().get(k).equalsIgnoreCase(person)) {
                                Log.d("search", "people only ");
                                master.getPhotos().add(user.albums.get(i).getPhotos().get(j));
                            }
                        }
                    } else if (!location.isEmpty() && !person.isEmpty()) {
                        for (int k = 0; k < user.albums.get(i).getPhotos().get(j).getTags().get(0).getTagValue().size(); k++)
                        {
                            for (int t = 0; t < user.albums.get(i).getPhotos().get(j).getTags().get(1).getTagValue().size(); t++)
                            {
                                if (user.albums.get(i).getPhotos().get(j).getTags().get(0).getTagValue().get(k).equalsIgnoreCase(location))
                                {
                                    if (user.albums.get(i).getPhotos().get(j).getTags().get(1).getTagValue().get(t).equalsIgnoreCase(person))
                                    {
                                        Log.d("search", "both");
                                        master.getPhotos().add(user.albums.get(i).getPhotos().get(j));
                                    }
                                }

                            }
                        }
                    }

                }
            }
        }
        else if (context == true)
        {
            for (int i = 0; i < user.albums.size(); i++)
            {
                for (int j = 0; j < user.albums.get(i).getPhotos().size(); j++)
                {

                    if (location.isEmpty() && person.isEmpty())
                    {

                        Log.d("search", "null null");
                        return;
                    }
                    else if (!location.isEmpty() && person.isEmpty()) {
                        for (int k = 0; k < user.albums.get(i).getPhotos().get(j).getTags().get(0).getTagValue().size(); k++) {

                            if (user.albums.get(i).getPhotos().get(j).getTags().get(0).getTagValue().get(k).toLowerCase().startsWith(location)) {
                                Log.d("search", "location only ");
                                master.getPhotos().add(user.albums.get(i).getPhotos().get(j));
                            }
                        }
                    }

                    else if (location.isEmpty() && !person.isEmpty())
                    {
                        for(int k = 0; k < user.albums.get(i).getPhotos().get(j).getTags().get(1).getTagValue().size(); k ++) {
                            if (user.albums.get(i).getPhotos().get(j).getTags().get(1).getTagValue().get(k).toLowerCase().startsWith(person)) {
                                Log.d("search", "people only ");
                                master.getPhotos().add(user.albums.get(i).getPhotos().get(j));
                            }
                        }
                    }
                    else if (!location.isEmpty() && !person.isEmpty())
                    {
                        for (int k = 0; k < user.albums.get(i).getPhotos().get(j).getTags().get(0).getTagValue().size(); k++)
                        {
                            for (int t = 0; t < user.albums.get(i).getPhotos().get(j).getTags().get(1).getTagValue().size(); t++)
                            {
                        if (user.albums.get(i).getPhotos().get(j).getTags().get(0).getTagValue().get(k).toLowerCase().startsWith(location))
                        {
                            if (user.albums.get(i).getPhotos().get(j).getTags().get(1).getTagValue().get(t).toLowerCase().startsWith(person))
                            {
                                Log.d("search", "both");
                                master.getPhotos().add(user.albums.get(i).getPhotos().get(j));
                            }
                        }
                    }}}
                }}
            }



        adapterS = new ImageAdapter(getApplicationContext(), R.layout.image, master.getPhotos());
        openGridS.setAdapter(adapterS);

        searchCounter.setText("Matches found: " + master.getPhotos().size());

        Log.d("search", master.getPhotos().size() + "");
    }


    public void saveDataM()
    {
        SharedPreferences sp = getSharedPreferences("sp", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(master);
        editor.putString("search", json);
        editor.apply();
    }

    public void loadDataM()
    {
        SharedPreferences sp = getSharedPreferences("sp", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sp.getString("search", null);
        Type type = new TypeToken<Album>() {}.getType();
        master = gson.fromJson(json, type);

        if (master == null)
        {
            master = new Album("master", new ArrayList<Photo>());
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