package com.example.rajagopalan.gpslocation;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Rajagopalan on 4/13/2017.
 *
 * Event class containing properties and methods for an event
 */
public class Event implements Parcelable {

    private String title;
    private String datetime_local;
    private String type;
    private String url;
    private ArrayList<Performer> performers;
    private Venue venue = new Venue();

    public String getVenueName() {
        return venue.getName();
    }

    public String getCity() {
        return venue.getCity();
    }

    public String getTitle() {
        return title;
    }

    public String getDatetime_local() {
        return datetime_local;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public ArrayList<Performer> getPerformers() {
        return performers;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Writes required data to the destination parcel
     * @param destinationParcel Parcel to which data is being written
     * @param flags flags
     */
    @Override
    public void writeToParcel(Parcel destinationParcel, int flags) {
        destinationParcel.writeString(this.title);
        destinationParcel.writeString(this.datetime_local);
        destinationParcel.writeString(this.type);
        destinationParcel.writeString(this.url);
        destinationParcel.writeString(this.datetime_local);
        destinationParcel.writeString(this.venue.getName());
        destinationParcel.writeString(this.venue.getCity());
        if (performers == null) {
            destinationParcel.writeByte((byte) (0x00));
        } else {
            destinationParcel.writeByte((byte) (0x01));
            destinationParcel.writeList(performers);
            // Passing an arrayList of parcelable Performer objects
        }
    }

    /**
     * Parametrised Constructor which reads data from the parcel
     * @param in Parcel from which data is being read
     */
    protected Event(Parcel in) {
        this.title = in.readString();
        this.datetime_local = in.readString();
        this.type = in.readString();
        this.url = in.readString();
        this.datetime_local = in.readString();
        this.venue.setName(in.readString());
        this.venue.setCity(in.readString());
        if (in.readByte() == 0x01) {
            performers = new ArrayList<>();
            in.readList(performers, Performer.class.getClassLoader());
            // Reading in an list of Parcelable Performer objects
        } else {
            performers = null;
        }
    }

    /**
     * Auto generated code needed for parcelable implementation
     */
    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}
