package com.example.prasenjit.xmlfeedapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by PRASENJIT on 1/5/2015.
 */
public class RssParseHandler extends DefaultHandler {

    //List of the item parsed
    private List<RssItem> rssItems;
    private RssItem currentItem;
    private boolean parseTitle;
    private boolean parseDate;
    private boolean parseImage;
    private boolean parseDescription;
    private boolean parseLink;
    private boolean inUrl = false;
    private String imageUrl;

    public RssParseHandler() {
        rssItems = new ArrayList();
    }

    public List<RssItem> getRssItems() {
        return rssItems;
    }

    // The StartElement method creates an empty RssItem object when an item start tag is being processed. When a title or link tag are being processed appropriate indicators are set to true. - See more at: http://www.itcuties.com/android/how-to-write-android-rss-parser/#sthash.XY3y1ag9.dpuf
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        if( "item".equals(qName) ) {
            currentItem = new RssItem();
        }
        else if( "enclosure".equals(qName) ) {
            if(attributes.getValue(0).contains(".jpg")) {
                inUrl = true;
                imageUrl = attributes.getValue(0).toString();
                currentItem.setImage(getBitmap(imageUrl));
                currentItem.setImageUrl(imageUrl);
            }
        }
        else if ( "title".equals(qName) )
            parseTitle = true;
        else if ( "pubDate".equals(qName) )
            parseDate = true;
        else if( "description".equals(qName) )
            parseDescription = true;
        else if( "link".equals(qName) )
            parseLink = true;
    }

    // The EndElement method adds the  current RssItem to the list when a closing item tag is processed. It sets appropriate indicators to false -  when title and link closing tags are processed - See more at: http://www.itcuties.com/android/how-to-write-android-rss-parser/#sthash.XY3y1ag9.dpuf
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        if( "item".equals(qName) )
            rssItems.add( currentItem );
        else if( "enclosure".equals(qName) )
            inUrl = false;
        else if ( "title".equals(qName) )
            parseTitle = false;
        else if ( "pubDate".equals(qName) )
            parseDate = false;
        else if( "description".equals(qName) )
            parseDescription = false;
        else if( "link".equals(qName) )
            parseLink = false;
    }

    // Characters method fills current RssItem object with data when title and link tag content is being processed - See more at: http://www.itcuties.com/android/how-to-write-android-rss-parser/#sthash.XY3y1ag9.dpuf
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if ( parseTitle ) {
            if ( currentItem != null )
                currentItem.setTitle(new String(ch, start, length));
        }
        else if ( parseDate ) {
            if ( currentItem != null ) {
                currentItem.setDate(new String(ch, start, length));
            }
        }
        else if ( parseDescription ) {
            if ( currentItem != null ) {
                currentItem.setDescription(new String(ch, start, length));
            }
        }
        else if ( parseLink ) {
            if ( currentItem != null ) {
                currentItem.setLink(new String(ch, start, length));
            }
        }
    }

    private Bitmap getBitmap(String url) {
        Bitmap bitmap = null;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inSampleSize = 2;

        try {
            bitmap = BitmapFactory
                    .decodeStream(new
                                    URL(url).openStream(),
                            null, bmOptions);
        } catch (MalformedURLException e) {
            Log.i("MalformedURLException","MalformedURLException");
            e.printStackTrace();
        } catch (IOException e) {
            Log.i("IOException","IOException");
            e.printStackTrace();
        }

        return bitmap;
    }
}