package com.solitary.ksapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Spot implements Parcelable{
    public String title;
    public String detail;
    public String imageId;
    public String type;
    public String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public Spot() {
    }



    @Override
    public String toString() {
        return "Kiss{" +
                "title='" + title + '\'' +
                ", detail='" + detail + '\'' +
                ", imageId='" + imageId + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.detail);
        dest.writeString(this.imageId);
        dest.writeString(this.type);
        dest.writeString(this.id);
    }

    protected Spot(Parcel in) {
        this.title = in.readString();
        this.detail = in.readString();
        this.imageId = in.readString();
        this.type = in.readString();
        this.id = in.readString();
    }

    public static final Creator<Spot> CREATOR = new Creator<Spot>() {
        @Override
        public Spot createFromParcel(Parcel source) {
            return new Spot(source);
        }

        @Override
        public Spot[] newArray(int size) {
            return new Spot[size];
        }
    };
}
