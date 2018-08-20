package com.solitary.ks.model;

import android.os.Parcel;
import android.os.Parcelable;

public class RatingData implements Parcelable {
    private int two;

    private int five;

    private int one;

    private int three;

    private int four;

    public int getTwo ()
    {
        return two;
    }

    public void setTwo (int two)
    {
        this.two = two;
    }

    public int getFive ()
    {
        return five;
    }

    public void setFive (int five)
    {
        this.five = five;
    }

    public int getOne ()
    {
        return one;
    }

    public void setOne (int one)
    {
        this.one = one;
    }

    public int getThree ()
    {
        return three;
    }

    public void setThree (int three)
    {
        this.three = three;
    }

    public int getFour ()
    {
        return four;
    }

    public void setFour (int four)
    {
        this.four = four;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [two = "+two+", five = "+five+", one = "+one+", three = "+three+", four = "+four+"]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.two);
        dest.writeInt(this.five);
        dest.writeInt(this.one);
        dest.writeInt(this.three);
        dest.writeInt(this.four);
    }

    public RatingData() {
    }

    protected RatingData(Parcel in) {
        this.two = in.readInt();
        this.five = in.readInt();
        this.one = in.readInt();
        this.three = in.readInt();
        this.four = in.readInt();
    }

    public static final Parcelable.Creator<RatingData> CREATOR = new Parcelable.Creator<RatingData>() {
        @Override
        public RatingData createFromParcel(Parcel source) {
            return new RatingData(source);
        }

        @Override
        public RatingData[] newArray(int size) {
            return new RatingData[size];
        }
    };
}