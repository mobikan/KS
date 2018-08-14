package com.mobikan.ks.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Kiss implements Parcelable {

    public String title;
    public String detail;
    public String imageId;
    public String id;
    public boolean isLike;
    public boolean isLiked;

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Kiss() {
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
        dest.writeString(this.id);
        dest.writeByte(this.isLike ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isLiked ? (byte) 1 : (byte) 0);
    }

    protected Kiss(Parcel in) {
        this.title = in.readString();
        this.detail = in.readString();
        this.imageId = in.readString();
        this.id = in.readString();
        this.isLike = in.readByte() != 0;
        this.isLiked = in.readByte() != 0;
    }

    public static final Creator<Kiss> CREATOR = new Creator<Kiss>() {
        @Override
        public Kiss createFromParcel(Parcel source) {
            return new Kiss(source);
        }

        @Override
        public Kiss[] newArray(int size) {
            return new Kiss[size];
        }
    };
}
