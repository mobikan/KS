package com.mobikan.ks.model;

import java.util.ArrayList;

public class QnA
{
    private ArrayList<String> answer;

    private String question;

    public ArrayList<String> getAnswer ()
    {
        return answer;
    }

    public void setAnswer (ArrayList<String> answer)
    {
        this.answer = answer;
    }

    public String getQuestion ()
    {
        return question;
    }

    public void setQuestion (String question)
    {
        this.question = question;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [answer = "+answer+", question = "+question+"]";
    }
}
			