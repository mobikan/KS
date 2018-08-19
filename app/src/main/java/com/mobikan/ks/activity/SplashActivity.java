package com.mobikan.ks.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mobikan.ks.R;
import com.mobikan.ks.db.DataBaseHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //init();

        setContentView(R.layout.splash_activity);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());
                try {
                    dataBaseHelper.createDatabase();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(SplashActivity.this,HomePageActivity.class));
                finish();
            }
        }, 1000);



    }


    private String readDataFromAsset()
    {

        StringBuffer sb = new StringBuffer();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("kisses.txt")));

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


}
