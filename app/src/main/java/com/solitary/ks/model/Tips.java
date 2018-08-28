package com.solitary.ks.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Tips implements Parcelable {

    public String url;
    public String id;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.id);
    }

    public Tips() {
    }

    protected Tips(Parcel in) {
        this.url = in.readString();
        this.id = in.readString();
    }

    public static final Parcelable.Creator<Tips> CREATOR = new Parcelable.Creator<Tips>() {
        @Override
        public Tips createFromParcel(Parcel source) {
            return new Tips(source);
        }

        @Override
        public Tips[] newArray(int size) {
            return new Tips[size];
        }
    };
}
