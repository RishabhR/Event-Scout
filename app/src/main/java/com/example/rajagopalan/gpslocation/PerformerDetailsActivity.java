package com.example.rajagopalan.gpslocation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * The activity which displays the description and additional info for each performer
 */
public class PerformerDetailsActivity extends AppCompatActivity {

    /**
     * On create method
     * @param savedInstanceState Saved Instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performer_details);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));

        final String ACTION_BAR_TITLE = "Performers";
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle(ACTION_BAR_TITLE);

        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        final Event eventInfo = data.getParcelable("event"); //Retrieving parceled data
        ArrayList<Performer> performerList = eventInfo.getPerformers();
        FillPerformersRecyclerView performersView = new FillPerformersRecyclerView(performerList);
        //Passes list of performers to fill the performer's recycler view
        recyclerView.setAdapter(performersView);
    }
}
