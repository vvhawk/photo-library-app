package com.example.android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AlbumActivity extends AppCompatActivity {


    Button albumSearch;
    Button albumDelete;
    Button albumAdd;
    Button albumOpen;
    Button albumRename;

    EditText albumAddField;

    EditText albumRenameField;

    Spinner albumList;

    ArrayAdapter<Album> adapter;

    private int albumCount;

    boolean delete;

    User user;

    static int albumSelect;

    boolean save;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.cornflower)));
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.cornflower));

        Intent passer = getIntent();
        loadData();
        albumCount = user.albums.size();


        albumList = (Spinner) findViewById(R.id.albumList);
        albumAddField = (EditText) findViewById(R.id.albumAddField);
        albumRenameField = (EditText) findViewById(R.id.albumRenameField);

        adapter = new ArrayAdapter<Album>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, user.albums);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        albumList.setAdapter(adapter);

    }


    public void addAlbum(View v)
    {
        boolean dupExists = false;


        try
        {
            if (albumAddField.getText().toString().equals(""))
            {
                System.out.println("empty null renaming");
                return;
            }
        }
        catch (Exception ex)
        {

        }

        if (albumAddField.getText() == null)
        {
            System.out.println("null adding");
            return;
        }

        for (int i = 0; i < user.albums.size(); i++)
        {


            if (albumAddField.getText().toString().equals(user.albums.get(i).getAlbumName()))
            {
                dupExists = true;
            }
        }

        if (dupExists)
        {


            Toast.makeText(this, "album already exists", Toast.LENGTH_LONG).show();
            return;

        }


        user.albums.add(new Album(albumAddField.getText().toString(), new ArrayList<Photo>()));

        albumAddField.setText(null);
        albumList.setAdapter(null);

        adapter = new ArrayAdapter<Album>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, user.albums);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        albumList.setAdapter(adapter);
        albumList.setSelection(albumCount);

        albumCount++;

        saveData();

    }

    public void deleteAlbum(View v)
    {



        if (albumList.getSelectedItem() == null)
        {
            return;
        }

        AlertDialog diaBox = AskOption();
        diaBox.show();



    }



    public void renameAlbum(View v)
    {

        if (albumList.getSelectedItem() == null)
        {
            return;
        }

        if (albumRenameField.getText().toString() == null)
        {

            return;

        }

        try
        {
            if (albumRenameField.getText().toString().equals(""))
            {

                return;
            }
        }
        catch(Exception ex)
        {

        }


        boolean dupExists = false;


        for (int i = 0; i < user.albums.size(); i++)
        {

            if (albumRenameField.getText().toString().equals(user.albums.get(i).getAlbumName()))
            {
                dupExists = true;
            }
        }


        if (dupExists)
        {

            Toast.makeText(this, "album already exists", Toast.LENGTH_LONG).show();
            return;

        }
        else
        {

            for (int i = 0; i < user.albums.size(); i++)
            {

                if (albumList.getSelectedItem().toString().equals(user.albums.get(i).getAlbumName()))
                {

                    user.albums.get(i).setAlbumName(albumRenameField.getText().toString());

                    albumRenameField.setText(null);
                    albumList.setAdapter(null);

                    adapter = new ArrayAdapter<Album>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, user.albums);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    albumList.setAdapter(adapter);
                    albumList.setSelection(i);

                    saveData();



                }
            }

        }

    }

    public void open (View v)
    {

        if (albumList.getSelectedItem() == null)
        {
            return;
        }

        Intent i = new Intent(this, OpenActivity.class);


        albumSelect = albumList.getSelectedItemPosition();

        i.putExtra("id", user);
        i.putExtra("albumSelect", albumSelect);

        saveData();
        startActivity(i);

    }

    public void search (View v)
    {
        if (albumList.getSelectedItem() == null)
        {
            return;
        }

        Intent i = new Intent(this, SearchActivity.class);

        i.putExtra("id", user);

        saveData();
        startActivity(i);

    }

    private AlertDialog AskOption()
    {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)

                .setTitle("Delete " + albumList.getSelectedItem().toString())
                .setMessage("Are you sure about that?")
                .setIcon(R.drawable.deletepl)

                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {

                    public void onClick(DialogInterface dialog, int whichButton)
                    {

                        for (int i = 0; i < user.albums.size(); i++)
                        {
                            if (user.albums.get(i).getAlbumName().equals(albumList.getSelectedItem().toString()))
                            {
                                delete = true;
                                adapter.remove(user.albums.get(i));
                                albumCount--;
                                Toast.makeText(getApplicationContext(), "album deleted", Toast.LENGTH_LONG).show();
                                saveData();
                            }
                        }

                        dialog.dismiss();

                    }

                })
                .setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {

                        delete = false;
                        dialog.dismiss();

                    }
                })
                .create();



        return myQuittingDialogBox;
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


    @Override
    public void onBackPressed()
    {
        saveData();
        super.onBackPressed();
    }
}

