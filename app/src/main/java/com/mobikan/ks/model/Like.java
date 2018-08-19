package com.mobikan.ks.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Like implements Parcelable {

    private String id;

    private long no_of_like;

    private float rating;

    private RatingData rating_data;

    public RatingData getRating_data ()
    {
        return rating_data;
    }

    public void setRating_data (RatingData rating_data)
    {
        this.rating_data = rating_data;
    }

    public Like()
    {

    }
    public Like(String id,long no_of_like,float rating)
    {
        this.id = id;
        this.no_of_like = no_of_like;
        this.rating = rating;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public long getNo_of_like ()
    {
        return no_of_like;
    }

    public void setNo_of_like (long no_of_like)
    {
        this.no_of_like = no_of_like;
    }

    public float getRating ()
    {
        return rating;
    }

    public void setRating (float rating)
    {
        this.rating = rating;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", no_of_like = "+no_of_like+", rating = "+rating+"]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeLong(this.no_of_like);
        dest.writeFloat(this.rating);
        dest.writeParcelable(this.rating_data, flags);
    }

    protected Like(Parcel in) {
        this.id = in.readString();
        this.no_of_like = in.readLong();
        this.rating = in.readFloat();
        this.rating_data = in.readParcelable(RatingData.class.getClassLoader());
    }

    public static final Parcelable.Creator<Like> CREATOR = new Parcelable.Creator<Like>() {
        @Override
        public Like createFromParcel(Parcel source) {
            return new Like(source);
        }

        @Override
        public Like[] newArray(int size) {
            return new Like[size];
        }
    };
}
