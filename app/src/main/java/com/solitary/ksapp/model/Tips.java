package com.solitary.ksapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Tips implements Parcelable {

    public String url;
    public String id;
    public String icon;
    public String title;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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

    public Tips() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.id);
        dest.writeString(this.icon);
        dest.writeString(this.title);
    }

    protected Tips(Parcel in) {
        this.url = in.readString();
        this.id = in.readString();
        this.icon = in.readString();
        this.title = in.readString();
    }

    public static final Creator<Tips> CREATOR = new Creator<Tips>() {
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
