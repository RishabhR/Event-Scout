package com.example.rajagopalan.gpslocation;

import org.junit.Test;

import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the JsonParser class
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class JsonParserTest {

    /**
     * Tests the URL response code for a Get request
     * @throws Exception
     */
    @Test
    public void parseURL() throws Exception {
        URL url = new URL("https://api.seatgeek.com/2/events?lat=44.519159" +
                "&lon=-88.0198300" + "&range=12mi&client_id=" + ApiKey.API_KEY);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        int code = connection.getResponseCode();
        final int SUCCESS = 200;
        assertEquals(SUCCESS , code);
    }
}