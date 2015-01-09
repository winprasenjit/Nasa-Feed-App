package com.example.prasenjit.xmlfeedapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    public RssReader rssReader;
    public List<RssItem> rssItems;
    ProgressDialog pd;
    public final static String RSS_FEED = "com.example.prasenjit.xmlfeedapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //new MyTask().execute();
        loadRSSFeed();
    }

    protected void loadRSSFeed(){
        new MyTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            loadRSSFeed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void resetDisplay (List<RssItem> rssItems) {

        if( rssItems != null ) {

            // Get a ListView from main view
            ListView itcItems = (ListView) findViewById(R.id.listMainView);
            // Create a list adapter
            CustomAdapter adapter = new CustomAdapter(this, R.layout.single_feed, rssItems);
            // Set list adapter for the ListView
            itcItems.setAdapter(adapter);

            //Set on click every item
            itcItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    RssItem currentItem     = (RssItem)parent.getItemAtPosition(position);
                    currentItem.setImage(null);

                    Intent i                = new Intent(getApplicationContext(),DetailsActivity.class);
                    i.putExtra(RSS_FEED, currentItem);
                    startActivity(i);
                }
            });
        }
        else
            Toast.makeText(getApplicationContext(),"Do something",Toast.LENGTH_SHORT).show();
    }

    public class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Start the Dialogue box
            pd = new ProgressDialog(MainActivity.this);
            pd.setTitle("Please wait");
            pd.setMessage("Loading....");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                // Create RSS reader
                rssReader   = new RssReader("http://www.nasa.gov/rss/dyn/image_of_the_day.rss");
                rssItems    = rssReader.getItems();

                Log.i("ShowDesc",rssItems.get(0).getDescription().toString());

            }
            catch (Exception e) {
                Log.e("MyRssReader", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            resetDisplay (rssItems);
            super.onPostExecute(result);

            //Stop the dialogue box
            pd.dismiss();
        }
    }
}
