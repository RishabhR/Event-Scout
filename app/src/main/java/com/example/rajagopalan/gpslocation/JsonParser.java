package com.example.rajagopalan.gpslocation;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by Rajagopalan on 4/13/2017.
 *
 * This class pulls and parses Json data from the API
 */
public class JsonParser {

    /**
     * Creates a URL object from the given URL string
     *
     * @return URL object which links to event data from the SeatGeek API
     * @throws MalformedURLException Incorrect or invalid URL's
     */
    public static URL getJsonUrl(double latitude, double longitude) throws MalformedURLException {
        return new URL("https://api.seatgeek.com/2/events?lat=" + latitude +
                "&lon=" + longitude + "&range=25mi&client_id=" + ApiKey.API_KEY);
    }

    /**
     * Parses JSON data from the given URL using the GSON library
     *
     * @param urlList Array of URL objects
     * @return An array of events from the given URL
     * @throws IOException Incorrect or invalid URL's
     */
    public static Event[] parseUrl(URL[] urlList) throws IOException {
        URL url = urlList[0];
        InputStream inputStream = url.openStream();
        InputStreamReader inStreamReader = new InputStreamReader(inputStream,
                Charset.forName("UTF-8"));
        Gson gson = new Gson();
        EventList eventList = gson.fromJson(inStreamReader, EventList.class);
        return eventList.getEvents();
    }
}

