package com.solitary.ks.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.solitary.ks.BuildConfig;
import com.solitary.ks.R;
import com.solitary.ks.activity.ShareActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
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
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Check this beautiful Kamasutra Positions App at: https://play.google.com/store/apps/details?id=" + appPackageName);
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


    public static Bitmap createBitmapFromLayout(RelativeLayout v) {


//        v.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
//                RelativeLayout.LayoutParams.WRAP_CONTENT));
        v.measure(400,
                900);
        v.layout(0, 0, 400, 900);
        Bitmap bitmap = Bitmap.createBitmap(400,
                900,
                Bitmap.Config.ARGB_8888);

        Canvas c = new Canvas(bitmap);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        v.draw(c);
        return bitmap;
    }

    public static void shareImage(Bitmap bitmap, Context context){
        // save bitmap to cache directory
        try {
            File cachePath = new File(context.getCacheDir(), "images");
            cachePath.mkdirs(); // don't forget to make the directory
            FileOutputStream stream = new FileOutputStream(cachePath + "/image.png"); // overwrites this image every time
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        File imagePath = new File(context.getCacheDir(), "images");
        File newFile = new File(imagePath, "image.png");
        Uri contentUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileprovider", newFile);

        if (contentUri != null) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
            shareIntent.setDataAndType(contentUri, context.getContentResolver().getType(contentUri));
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            shareIntent.setType("image/png");
            context.startActivity(Intent.createChooser(shareIntent, "Choose an app"));
        }
    }


    public static void openShareImageActivity(Context context,String title,String detail,int imageId)
    {
        Intent intent = new Intent(context, ShareActivity.class);
        intent.putExtra(Constants.ShareConstants.INTENT_SHARE_TITLE_ID, title);
        intent.putExtra(Constants.ShareConstants.INTENT_SHARE_IMAGE_ID, imageId);
        intent.putExtra(Constants.ShareConstants.INTENT_SHARE_DETAIL_ID, detail);
        context.startActivity(intent);
    }

}


