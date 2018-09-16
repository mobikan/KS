package com.solitary.ks.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.solitary.ks.R;
import com.solitary.ks.db.DataBaseHelper;
import com.solitary.ks.db.PositionsDbHelper;
import com.startapp.android.publish.adsCommon.StartAppSDK;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.solitary.ks.utils.Constants.TermsAndCondition.PREF_NAME;
import static com.solitary.ks.utils.Constants.TermsAndCondition.PREF_TERMS_AGREE_KEY;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //init();
        StartAppSDK.init(this, getString(R.string.start_app_id), true);

        setContentView(R.layout.splash_activity);
        SharedPreferences pref = getApplicationContext().getSharedPreferences(PREF_NAME, 0); // 0 - for private mode
        final boolean isAgree = pref.getBoolean(PREF_TERMS_AGREE_KEY, false);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());
                try {
                    dataBaseHelper.createDatabase();
                    //PositionsDbHelper dbHelper = new PositionsDbHelper(SplashActivity.this);//,"Position.db",1);
               } catch (IOException e) {
                    e.printStackTrace();
               }
                if(isAgree) {
                    startActivity(new Intent(SplashActivity.this, HomePageActivity.class));
                }
                else {
                    startActivity(new Intent(SplashActivity.this, TermsAndConditionsActivity.class));
                }
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
