package com.solitary.ks.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Utils {


    public static String readFromAssets(String filename, Context context) {

        StringBuffer sb = new StringBuffer();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open(filename)));

            // do reading, usually loop until end of file reading

            String mLine;
            while ((mLine = reader.readLine()) != null) {
                //process line
                sb.append(mLine);

            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }


        return sb.toString();

    }


    public static String getImageName(String title)
    {

        title = title.replace(",", "").replace("-", "_");

        String[] titleArr =  title.trim().split(" ");
        StringBuffer name = new StringBuffer();
        int count = 0;
        for (String str : titleArr)
        {
            name.append(str);
            if(count <titleArr.length-1)
            {
                name.append("_");
            }
            count++;
        }
        return name.toString().toLowerCase();
    }


    public static void shareApp(Context context) {
        if(context != null) {
            final String appPackageName = context.getPackageName();
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Check this cool Sticker App at: https://play.google.com/store/apps/details?id=" + appPackageName);
            sendIntent.setType("text/plain");
            context.startActivity(sendIntent);
        }
    }

    public static void openApp(Context context)
    {
        if(context != null) {
            final String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object
            try {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        }
    }
}

