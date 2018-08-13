package com.mobikan.ks.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Position implements Parcelable {
    private String id;

    private String Description;

    private String try_this;

    private String tips_his;

    private String tips_her;

    private String Benefits;

    private String Title;


    private int rating;

    private boolean isFavourite;

    private boolean isGspot;

    private boolean isTried;

    private boolean isLiked;

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        this.isLiked = liked;
    }

    public boolean isTried() {
        return isTried;
    }

    public void setTried(boolean tried) {
        isTried = tried;
    }

    public boolean isGspot() {
        return isGspot;
    }

    public void setGspot(boolean gspot) {
        isGspot = gspot;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getDescription ()
    {
        return Description;
    }

    public void setDescription (String Description)
    {
        this.Description = Description;
    }

    public String getTry_this ()
    {
        return try_this;
    }

    public void setTry_this (String try_this)
    {
        this.try_this = try_this;
    }

    public String getTips_his ()
    {
        return tips_his;
    }

    public void setTips_his (String tips_his)
    {
        this.tips_his = tips_his;
    }

    public String getBenefits ()
    {
        return Benefits;
    }

    public void setBenefits (String Benefits)
    {
        this.Benefits = Benefits;
    }

    public String getTitle ()
    {
        return Title;
    }

    public void setTitle (String Title)
    {
        this.Title = Title;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", Description = "+Description+", try_this = "+try_this+", tips_his = "+tips_his+", Benefits = "+Benefits+", Title = "+Title+"]";
    }

    public String getTips_her() {
        return tips_her;
    }

    public void setTips_her(String tips_her) {
        this.tips_her = tips_her;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public Position() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.Description);
        dest.writeString(this.try_this);
        dest.writeString(this.tips_his);
        dest.writeString(this.tips_her);
        dest.writeString(this.Benefits);
        dest.writeString(this.Title);
        dest.writeInt(this.rating);
        dest.writeByte(this.isFavourite ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isGspot ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isTried ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isLiked ? (byte) 1 : (byte) 0);
    }

    protected Position(Parcel in) {
        this.id = in.readString();
        this.Description = in.readString();
        this.try_this = in.readString();
        this.tips_his = in.readString();
        this.tips_her = in.readString();
        this.Benefits = in.readString();
        this.Title = in.readString();
        this.rating = in.readInt();
        this.isFavourite = in.readByte() != 0;
        this.isGspot = in.readByte() != 0;
        this.isTried = in.readByte() != 0;
        this.isLiked = in.readByte() != 0;
    }

    public static final Creator<Position> CREATOR = new Creator<Position>() {
        @Override
        public Position createFromParcel(Parcel source) {
            return new Position(source);
        }

        @Override
        public Position[] newArray(int size) {
            return new Position[size];
        }
    };
}

