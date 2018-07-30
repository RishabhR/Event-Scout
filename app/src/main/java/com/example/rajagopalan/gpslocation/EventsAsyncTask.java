package com.example.rajagopalan.gpslocation;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Rajagopalan on 4/13/2017.
 *
 * This class calls methods that parses Json data and fills recycler view on a background thread
 */
public class EventsAsyncTask extends AsyncTask<URL, Void, Event[]> {

    private Context context;
    private ArrayList<Event> eventList = new ArrayList<>();
    private FillEventsRecyclerView eventsView;

    /**
     * Parameterized constructor
     * @param context The context you want to give the object
     */
    public EventsAsyncTask(Context context, ArrayList eventList, FillEventsRecyclerView eventView) {
        this.context = context;
        this.eventList = eventList;
        this.eventsView = eventView;
    }

    /**
     * Parses URL on a background thread
     * @param urlList Array of URL objects
     * @return Array of all the events near the user's location
     */
    @Override
    protected Event[] doInBackground(URL... urlList) {
        Event[] events = null;
        try {
            events = JsonParser.parseUrl(urlList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return events;
    }

    /**
     * Method is called after background task is executed
     * @param events Array of all events near the user's location
     */
    @Override
    protected void onPostExecute(Event[] events) {
        eventList.clear();
        for (int i = 0; i < events.length; i++) {
            eventList.add(events[i]);
        }
        eventsView.notifyDataSetChanged();
    }
}

