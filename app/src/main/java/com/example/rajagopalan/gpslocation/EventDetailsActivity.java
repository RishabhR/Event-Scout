package com.example.rajagopalan.gpslocation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * The activity which displays the description and additional info for each event
 */
public class EventDetailsActivity extends AppCompatActivity {

    private TextView titleTextView;
    private TextView cityTextView;
    private TextView venueTextView;
    private TextView dateTextView;
    private TextView timeTextView;
    private Button commentsButton;
    private Button performersButton;
    private Button ticketButton;

    /**
     * On Create method
     * @param savedInstanceState Saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        initialize();
        Intent intent = getIntent();
        final Event event = intent.getParcelableExtra(FillEventsRecyclerView.EVENT);
        // Retrieve data from Parcel

        int lastDateIndex = event.getDatetime_local().indexOf("T");
        String date = event.getDatetime_local().substring(0, lastDateIndex);
        String time = event.getDatetime_local().substring(lastDateIndex + 1);
        // In the Json data, date and time information is separated by 'T' character
        fillViews(event, date, time);

        ticketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri webpage = Uri.parse(event.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent); // Opens the required web page
                }
            }
        });

        performersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), PerformerDetailsActivity.class);
                myIntent.putExtra("event", event);
                v.getContext().startActivity(myIntent);
            }
        });

        commentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CommentsActivity.class);
                intent.putExtra("event", event);
                v.getContext().startActivity(intent);
            }
        });
    }

    /**
     * Fills all the UI elements with appropriate values
     *
     * @param event The event selected by the user
     * @param date The date of the event
     * @param time The time of the event
     */
    public void fillViews(Event event, String date, String time) {
        final String ACTION_BAR_TITLE = "Event Details";
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle(ACTION_BAR_TITLE);
        titleTextView.setText(event.getTitle());
        String venue = getString(R.string.VENUE)+ " " + event.getVenueName();
        venueTextView.setText(venue);
        String city = getString(R.string.city) + " " + event.getCity();
        cityTextView.setText(city);
        String eventDate = getString(R.string.edate) + " " + date;
        dateTextView.setText(eventDate);
        String eventTime = getString(R.string.etime) + " " + time;
        timeTextView.setText(eventTime);
        commentsButton.setText(getString(R.string.comment));
        performersButton.setText(getString(R.string.performers));
        ticketButton.setText(getString(R.string.tickets));
    }

    /**
     * Initializes all the UI elements with their id's in the layout
     */
    public void initialize() {
        titleTextView = (TextView) findViewById(R.id.titleTextView);
        venueTextView = (TextView) findViewById(R.id.venueTextView);
        cityTextView = (TextView) findViewById(R.id.cityTextView);
        dateTextView = (TextView) findViewById(R.id.dateTextView);
        timeTextView = (TextView) findViewById(R.id.timeTextView);
        commentsButton = (Button) findViewById(R.id.commentsButton);
        performersButton = (Button) findViewById(R.id.performersButton);
        ticketButton = (Button) findViewById(R.id.ticketButton);
    }
}
