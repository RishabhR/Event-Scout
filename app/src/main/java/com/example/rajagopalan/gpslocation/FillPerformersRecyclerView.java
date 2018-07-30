package com.example.rajagopalan.gpslocation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Rajagopalan on 4/22/2017.
 *
 * This class fills the recycler view with performer names and images
 */
public class FillPerformersRecyclerView extends
        RecyclerView.Adapter<FillPerformersRecyclerView.ViewHolder> {

    private ArrayList<Performer> performerList;

    public FillPerformersRecyclerView(ArrayList<Performer> performers) {
        this.performerList = performers;
    }

    /**
     * Method fills screen with views. Recycles view when scrolling.
     *
     * @param parentViewGroup parent ViewGroup object
     * @param type int parameter needed to override the superclass method. No functionality here
     * @return A new ViewHolder
     */
    @Override
    public FillPerformersRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parentViewGroup,
                                                                    int type) {
        final View performerListView = LayoutInflater.from(parentViewGroup.getContext()).
                inflate(R.layout.performer, parentViewGroup, false);
        return new FillPerformersRecyclerView.ViewHolder(performerListView);
    }

    /**
     * Method fills performer name and image into the required position in the list
     *
     * @param viewHolder the ViewHolder containing the Views we need to fill
     * @param index      the index into the arrayList of performers
     */
    @Override
    public void onBindViewHolder(FillPerformersRecyclerView.ViewHolder viewHolder, int index) {
        final Performer performer = performerList.get(index);
        viewHolder.performerName.setText(performer.getName());
            Picasso.with(viewHolder.performerImage.getContext())
                    .load(performer.getImage()).
                    into(viewHolder.performerImage);
    }

    /**
     * Returns number of performers in the arrayList
     *
     * @return the number of performers in the arrayList
     */
    @Override
    public int getItemCount() {
        return performerList.size();
    }

    /**
     * Caching references to subviews
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView performerName;
        public ImageView performerImage;

        public ViewHolder(View itemView) {
            super(itemView);
            performerName = (TextView) itemView.findViewById(R.id.nameTextView);
            performerImage = (ImageView) itemView.findViewById(R.id.performerImageView);
        }
    }
}
