package com.example.android;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import java.util.List;

public class ImageAdapter extends BaseAdapter {


    private Context context;
    private List<Photo> photosList;
    int albumSelect = AlbumActivity.albumSelect;

    private int selectedPosition = -1;



    public ImageAdapter(Context context, int photo, List<Photo> photosList){
        this.context = context;
        this.photosList = photosList;
    }
    @Override
    public int getCount() {
        return photosList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View openGrid = inflater.inflate(R.layout.image, viewGroup, false);

        ImageView imageView = openGrid.findViewById(R.id.imageViewGrid);


        Bitmap bitmap = photosList.get(i).toBM();



        imageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 120, 120, false));


        if (i == selectedPosition) {
            openGrid.setBackgroundColor(ContextCompat.getColor(context, R.color.highlighter));
        } else {
            openGrid.setBackgroundColor(ContextCompat.getColor(context, R.color.black));
        }

        return openGrid;
    }


    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    public void removeItem(int position)
    {
        notifyDataSetChanged();
    }

    public void moveItem(int position)
    {
        notifyDataSetChanged();
    }




}
