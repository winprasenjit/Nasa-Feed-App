package com.example.prasenjit.xmlfeedapp;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by PRASENJIT on 1/5/2015.
 */
public class RssReader {
    // Our class has an attribute which represents RSS Feed URL
    private String rssUrl;
    /**
     * We set this URL with the constructor
     */
    public RssReader(String rssUrl) {

        this.rssUrl = rssUrl;
    }
    /**
     * Get RSS items. This method will be called to get the parsing process result.
     * @return
     */
    public List<RssItem> getItems() throws Exception {
        // At first we need to get an SAX Parser Factory object
        SAXParserFactory factory = SAXParserFactory.newInstance();
        // Using factory we create a new SAX Parser instance
        SAXParser saxParser = factory.newSAXParser();
        XMLReader reader = saxParser.getXMLReader();
        // We need the SAX parser handler object
        RssParseHandler handler = new RssParseHandler();
        // The result of the parsing process is being stored in the handler object
        reader.setContentHandler(handler);
        InputStream inputStream = new URL(rssUrl).openStream();
        reader.parse(new InputSource(inputStream));

        return handler.getRssItems();
    }
}
