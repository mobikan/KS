package com.solitary.ksapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.solitary.ksapp.R;
import com.solitary.ksapp.db.DataBaseHelper;
import com.solitary.ksapp.db.KSDatabaseHelper;
import com.solitary.ksapp.model.PositionsList;
import com.solitary.ksapp.utils.Utils;
import com.startapp.android.publish.adsCommon.StartAppSDK;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static com.solitary.ksapp.utils.Constants.TermsAndCondition.PREF_NAME;
import static com.solitary.ksapp.utils.Constants.TermsAndCondition.PREF_TERMS_AGREE_KEY;


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
                    readDataFromDB();
                    //KSDatabaseHelper dbHelper = new KSDatabaseHelper(SplashActivity.this);//,"Position.db",1);
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


    protected void readDataFromDB()
    {
        try {
            // PositionDataBaseHelper positionDataBaseHelper = new PositionDataBaseHelper(dataBaseHelper.openDatabase(DataBaseHelper.DB_NAME_POSITION));
            String positionJson = Utils.readFromAssets("position.json", this);
            PositionsList positionsList =  null;
            try {
                JSONObject jsonObject = new JSONObject(positionJson);

                positionsList = new Gson().fromJson(jsonObject.toString(), PositionsList.class);
                KSDatabaseHelper dbHelper = new KSDatabaseHelper(this);
                dbHelper.initDataBase(positionsList.getPosition());

            } catch (Exception e) {
                e.printStackTrace();
            }



        } catch (Exception e) {
            e.printStackTrace();
        }

    }




}
