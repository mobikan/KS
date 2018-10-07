package com.solitary.ksapp.model;

import java.util.ArrayList;

public class SpotList {

    private ArrayList<Spot> spots;

    public ArrayList<Spot> getSpots()
    {
        return spots;
    }

    public void setSpots(ArrayList<Spot> kisses)
    {
        this.spots = kisses;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [KissList = "+ spots +"]";
    }


}
