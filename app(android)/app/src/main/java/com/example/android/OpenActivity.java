package com.example.android;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;

public class OpenActivity extends AppCompatActivity {


    TextView openTitle;
    Button openAdd;
    Button openDelete;
    Button openMove;
    Button openCopy;

    Spinner openAlbumList;
    GridView openGrid;


    User user;
    int albumSelect;

    ArrayAdapter<Album> adapter;
    ImageAdapter adapterr;

    int select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.open)));
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.open));

        Intent passer = getIntent();

        loadData();


        albumSelect = AlbumActivity.albumSelect;
        openTitle = findViewById(R.id.openTitle);
        openTitle.setText(user.albums.get(albumSelect).getAlbumName());

        openAlbumList = (Spinner) findViewById(R.id.openAlbumList);
        openAdd = findViewById(R.id.openAdd);

        openGrid = findViewById(R.id.openGridS);
        adapter = new ArrayAdapter<Album>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, user.albums);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        openAlbumList.setAdapter(adapter);

        adapterr = new ImageAdapter(getApplicationContext(), R.layout.image, user.albums.get(albumSelect).getPhotos());
        openGrid.setAdapter(adapterr);

        adapterr.setSelectedPosition(0);



        openGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                adapterr.setSelectedPosition(position);
                adapterr.notifyDataSetChanged();
                select = position;
            }
        });
    }

    public void addPhoto(View v)
    {

        imageSelect();
    }

    public void imageSelect()
    {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction((Intent.ACTION_GET_CONTENT));

        launchMe.launch(i);

    }

    ActivityResultLauncher<Intent> launchMe = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK)
                {
                    Intent data = result.getData();

                    if (data != null && data.getData() != null)
                    {
                        Uri selectImageUri = data.getData();

                        Bitmap selectImageBitmap;

                        try{


                            ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), selectImageUri);
                            selectImageBitmap = ImageDecoder.decodeBitmap(source);

                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            selectImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
                            byte[] b = baos.toByteArray();

                            String filePath = Base64.encodeToString(b, Base64.DEFAULT);

                            user.albums.get(albumSelect).getPhotos().add(new Photo(filePath));


                            saveData();
                            Log.d("openA", user.albums.get(albumSelect).getPhotos().size() + "");

                            finish();
                            startActivity(getIntent());
                        }
                        catch (IOException ex)
                        {
                            ex.printStackTrace();
                        }
                    }
                }

            }
    );

    public void deletePhoto(View v)
    {

        AlertDialog diaBox = AskOption();
        diaBox.show();

    }

    private AlertDialog AskOption()
    {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)

                .setTitle("Delete selected photo")
                .setMessage("Are you sure about that?")
                .setIcon(R.drawable.deletepl)

                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {

                    public void onClick(DialogInterface dialog, int whichButton)
                    {

                        user.albums.get(albumSelect).getPhotos().remove(select);

                        adapterr.removeItem(select);

                        saveData();


                        adapterr.setSelectedPosition(0);

                        dialog.dismiss();

                    }

                })
                .setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {


                        dialog.dismiss();

                    }
                })
                .create();



        return myQuittingDialogBox;
    }

    public void movePhoto(View v)
    {
        int albumTarget = openAlbumList.getSelectedItemPosition();

        Photo selectPhoto = user.albums.get(albumSelect).getPhotos().get(select);

        user.albums.get(albumTarget).getPhotos().add(selectPhoto);

        user.albums.get(albumSelect).getPhotos().remove(select);

        adapterr.removeItem(select);

        saveData();

    }






    public void display(View v)
    {

        if (user.albums.get(albumSelect).getPhotos().size() == 0)
        {
            Toast.makeText(getApplicationContext(), "no photos to display", Toast.LENGTH_LONG).show();
            return;
        }

        Intent i = new Intent(this, DisplayActivity.class);

        i.putExtra("photoSelect", select);

        openGrid.setAdapter(null);

        saveData();
        startActivity(i);
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