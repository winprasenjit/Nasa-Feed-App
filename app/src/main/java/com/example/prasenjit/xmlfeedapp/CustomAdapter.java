package com.example.prasenjit.xmlfeedapp;

import android.content.ClipData;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by PRASENJIT on 1/6/2015.
 */
public class CustomAdapter extends ArrayAdapter<RssItem> {

    private List<RssItem> items;
    Context _context;

    //Initialise adapter
    public CustomAdapter(Context context, int resource, List<RssItem> items){
        super(context, resource, items);
        this._context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View v = convertView;

        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.single_feed, null);
        }

        RssItem i = getItem(position);

        //Get the reference of dom objects
        ImageView imgView   = (ImageView)v.findViewById(R.id.imageView);
        TextView  txtTitle  = (TextView)v.findViewById(R.id.imageTitle);


        //Assign the appropriate data from our alert object above
        txtTitle.setText(i.getTitle());
        imgView.setImageBitmap(i.getImage());


        return v;
    }
}
