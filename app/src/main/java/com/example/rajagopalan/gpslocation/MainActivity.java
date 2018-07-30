package com.example.rajagopalan.gpslocation;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Main activity gets GPS data from android device
 */
public class MainActivity extends AppCompatActivity {

    private ArrayList<Event> eventList = new ArrayList<>();
    private LocationManager locationManager;
    private LocationListener locationListener;
    private double latitude;
    private double longitude;
    final int SUCCESSFUL_RESPONSE = 200;

    /**
     * On Create method
     * @param savedInstanceState Saved Instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {

            /**
             * Method is called when user location changes
             * @param location User's location
             */
            @Override
            public void onLocationChanged(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
            }

            @Override
            public void onProviderEnabled(String s) {
            }

            /**
             * If GPS service is disabled then the method takes you to settings
             * @param s String parameter. No functionality here
             */
            @Override
            public void onProviderDisabled(String s) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        locateUser();
        final String ACTION_BAR_TITLE = "Events Near You";
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle(ACTION_BAR_TITLE);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        FillEventsRecyclerView eventsView = new FillEventsRecyclerView(eventList);
        recyclerView.setAdapter(eventsView);

        URL JSON_URL = null;
        try {
            // Passes location data to be used in API query
            JSON_URL = JsonParser.getJsonUrl(latitude, longitude);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new EventsAsyncTask(this, eventList, eventsView).execute(JSON_URL);
    }

    /**
     * Method is calling after permissions are requested
     * @param requestCode return value after permissions are requested
     * @param permissions The permissions needed to use location data
     * @param grantResults Grant results. No functionality here.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case SUCCESSFUL_RESPONSE:
                locateUser();
        }
    }

    /**
     * Finds user location using GPS data
     */
    public void locateUser() {
        final int TIME_BETWEEN_UPDATE = 100;
        final int DISTANCE_BETWEEN_UPDATE = 500;

        //Checks if all permissions have been obtained
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {

            // Checks for SDK version compatability
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.INTERNET}, SUCCESSFUL_RESPONSE);
            }
            return; // If permissions are not granted, do not proceed
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                DISTANCE_BETWEEN_UPDATE, TIME_BETWEEN_UPDATE, locationListener);
                Location lastKnownLocation = locationManager.getLastKnownLocation
                        (LocationManager.NETWORK_PROVIDER);
                locationListener.onLocationChanged(lastKnownLocation);
    }
}

