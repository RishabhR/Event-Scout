package com.example.rajagopalan.gpslocation;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rajagopalan on 4/13/2017.
 *
 * Performer class contains properties and behaviour for a performer
 */

public class Performer implements Parcelable {

    private String name;
    private String image;

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
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
        destinationParcel.writeString(this.name);
        destinationParcel.writeString(this.image);
    }

    /**
     * Parametrised Constructor which reads data from the parcel
     * @param in Parcel from which data is being read
     */
    private Performer(Parcel in){
        this.name = in.readString();
        this.image = in.readString();
    }

    /**
     * Auto generated code needed for parcelable implementation
     */
    public static final Creator<Performer> CREATOR = new Creator<Performer>() {
        @Override
        public Performer createFromParcel(Parcel source) {
            return new Performer(source);
        }

        @Override
        public Performer[] newArray(int size) {
            return new Performer[size];
        }
    };
}
