package com.solitary.ksapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Massage implements Parcelable {

    public String title;
    public String detail;
    public String imageId;
    public String videoLink;
    public boolean isLike;

    public String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
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

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public Massage() {
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
        dest.writeString(this.videoLink);
        dest.writeByte(this.isLike ? (byte) 1 : (byte) 0);
        dest.writeString(this.id);
    }

    protected Massage(Parcel in) {
        this.title = in.readString();
        this.detail = in.readString();
        this.imageId = in.readString();
        this.videoLink = in.readString();
        this.isLike = in.readByte() != 0;
        this.id = in.readString();
    }

    public static final Creator<Massage> CREATOR = new Creator<Massage>() {
        @Override
        public Massage createFromParcel(Parcel source) {
            return new Massage(source);
        }

        @Override
        public Massage[] newArray(int size) {
            return new Massage[size];
        }
    };
}
