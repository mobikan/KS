package com.mobikan.ks.model;

public class RatingData
{
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
}