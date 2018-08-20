package com.solitary.ks.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.solitary.ks.model.Kiss;

public class KissDataBaseHelper {


    private SQLiteDatabase mDbHelper;
    public KissDataBaseHelper(SQLiteDatabase mDbHelper)
    {
     this.mDbHelper = mDbHelper;
    }

    public void deleteAll()
    {
        mDbHelper.delete(Columns.Kiss.TABLE_NAME, null, null);
    }

    public List<Kiss> getAllKisses() {

        List<Kiss> kisses = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Columns.Kiss.TABLE_NAME;

        Cursor cursor = mDbHelper.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Kiss kiss = new Kiss();
                kiss.setId(cursor.getString(cursor.getColumnIndex(Columns.Kiss.COLUMN_ID)));
                kiss.setTitle(cursor.getString(cursor.getColumnIndex(Columns.Kiss.COLUMN_TITLE)));
                kiss.setDetail(cursor.getString(cursor.getColumnIndex(Columns.Kiss.COLUMN_DESCRIPTION)));
                kiss.setImageId(cursor.getString(cursor.getColumnIndex(Columns.Kiss.COLUMN_IMAGE_ID)));
                boolean isLike = cursor.getInt(cursor.getColumnIndex(Columns.Kiss.COLUMN_LIKE)) == 0 ? false : true;
                kiss.setLike(isLike);
               // boolean isLiked = cursor.getInt(cursor.getColumnIndex(Columns.Kiss.COLUMN_LIKED)) == 0 ? false : true;
               // kiss.setLiked(isLiked);

                kisses.add(kiss);
            } while (cursor.moveToNext());
        }

        // close db connection
        mDbHelper.close();

        // return notes list
        return kisses;
    }

    public void insert(Kiss kiss)
    {

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(Columns.Kiss.COLUMN_TITLE, kiss.getTitle());
        values.put(Columns.Kiss.COLUMN_DESCRIPTION, kiss.getDetail());
        values.put(Columns.Kiss.COLUMN_ID, kiss.getId());
        values.put(Columns.Kiss.COLUMN_IMAGE_ID, kiss.getImageId());
        values.put(Columns.Kiss.COLUMN_LIKE, kiss.isLike());

// Insert the new row, returning the primary key value of the new row
        long newRowId = mDbHelper.insert(Columns.Kiss.TABLE_NAME, null, values);
        Log.v("Kiss", "rowId "+newRowId);
    }



}

