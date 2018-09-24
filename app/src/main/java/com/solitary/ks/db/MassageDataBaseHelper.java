package com.solitary.ks.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.solitary.ks.model.Kiss;
import com.solitary.ks.model.Massage;
import com.solitary.ks.model.MassageList;

public class MassageDataBaseHelper {


    private SQLiteDatabase mDbHelper;
    public MassageDataBaseHelper(SQLiteDatabase mDbHelper)
    {
     this.mDbHelper = mDbHelper;
    }



    public List<Massage> getAllMassage() {

        List<Massage> massageList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Columns.Massage.TABLE_NAME;

        Cursor cursor = mDbHelper.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Massage massage = new Massage();
                massage.setId(cursor.getString(cursor.getColumnIndex(Columns.Massage.COLUMN_ID)));
                massage.setTitle(cursor.getString(cursor.getColumnIndex(Columns.Massage.COLUMN_TITLE)));
                massage.setDetail(cursor.getString(cursor.getColumnIndex(Columns.Massage.COLUMN_DESCRIPTION)));
                massage.setImageId(cursor.getString(cursor.getColumnIndex(Columns.Massage.COLUMN_IMAGE_ID)));
                massage.setVideoLink(cursor.getString(cursor.getColumnIndex(Columns.Massage.COLUMN_VIDEO_LINK)));
                boolean isLike = cursor.getInt(cursor.getColumnIndex(Columns.Massage.COLUMN_LIKE)) == 0 ? false : true;
                massage.setLike(isLike);

                massageList.add(massage);
            } while (cursor.moveToNext());
        }

        // close db connection
        mDbHelper.close();
        cursor.close();
        // return notes list
        return massageList;
    }



}

