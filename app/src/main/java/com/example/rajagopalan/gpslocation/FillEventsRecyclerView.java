package com.example.rajagopalan.gpslocation;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Rajagopalan on 4/13/2017.
 *
 * This class fills the Recycler view with event information
 */
public class FillEventsRecyclerView extends RecyclerView.Adapter<FillEventsRecyclerView.ViewHolder>{

    public ArrayList<Event> eventList;
    public static final String EVENT = "EVENT";
    public FillEventsRecyclerView(ArrayList<Event> events) {
        this.eventList = events;
    }

    /**
     * Method fills screen with views. Recycles view when scrolling.
     *
     * @param parentViewGroup parent ViewGroup object
     * @param type int parameter needed to override the superclass method. No functionality here
     * @return A new ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parentViewGroup, int type) {
        final View movieListView = LayoutInflater.from(parentViewGroup.getContext()).
                inflate(R.layout.event, parentViewGroup, false);
        return new ViewHolder(movieListView);
    }

    /**
     * Method fills title and event type into the required position in the list
     *
     * @param viewHolder the ViewHolder containing the Views we need to fill
     * @param index      the index into the arrayList of Events
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int index) {
        final Event event = eventList.get(index);
        String text = "Type: ";
        viewHolder.eventTitle.setText(event.getTitle());
        viewHolder.eventType.setText(text + event.getType());

        // This OnclickListener allows us to click on the details button to see additional info
        viewHolder.detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EventDetailsActivity.class);
                intent.putExtra(EVENT, event);
                v.getContext().startActivity(intent);
            }
        });
        }

    /**
     * Returns number of events in the arrayList
     * @return the number of events in the arrayList
     */
    @Override
    public int getItemCount() {
        return eventList.size();
    }

    /**
     * Caching references to subviews
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private TextView eventTitle;
        private TextView eventType;
        private Button detailsButton;

        private ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            eventTitle = (TextView) itemView.findViewById(R.id.titleTextView);
            eventType = (TextView) itemView.findViewById(R.id.eventTypeTextView);
            detailsButton = (Button) itemView.findViewById(R.id.detailsButton);
        }
    }
}
