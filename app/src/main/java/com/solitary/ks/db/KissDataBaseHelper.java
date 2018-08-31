package com.solitary.ks.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.solitary.ks.model.Kiss;
import com.solitary.ks.model.Position;

public class KissDataBaseHelper {


    private SQLiteDatabase mDbHelper;

    private String[] columns = {Columns.Kiss.COLUMN_ID,Columns.Kiss.COLUMN_TITLE,
            Columns.Kiss.COLUMN_DESCRIPTION,Columns.Kiss.COLUMN_IMAGE_ID,Columns.Kiss.COLUMN_LIKE};

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
                boolean isLike = cursor.getString(cursor.getColumnIndex(Columns.Kiss.COLUMN_LIKE)).equalsIgnoreCase("true");
                kiss.setLike(isLike);
               // boolean isLiked = cursor.getInt(cursor.getColumnIndex(Columns.Kiss.COLUMN_LIKED)) == 0 ? false : true;
               // kiss.setLike(isLiked);
                Log.v("Kiss List", "Kiss Id "+kiss.getId()+" like " +isLike);
                kisses.add(kiss);
            } while (cursor.moveToNext());
        }

        // close db connection
        mDbHelper.close();
        cursor.close();
        // return notes list
        return kisses;
    }


    public boolean getLike(String kissId)
    {
        ContentValues values = new ContentValues();
        values.put(Columns.Kiss.COLUMN_LIKE, String.valueOf(kissId));
        String selection = Columns.Kiss.COLUMN_ID + " LIKE ?";
        String[] selectionArgs = {kissId };
        Cursor cursor =   mDbHelper.query(Columns.Kiss.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        Kiss kiss = new Kiss();
        if (cursor.moveToFirst()) {
            do {

                kiss.setId(cursor.getString(cursor.getColumnIndex(Columns.Kiss.COLUMN_ID)));
                kiss.setTitle(cursor.getString(cursor.getColumnIndex(Columns.Kiss.COLUMN_TITLE)));
                kiss.setDetail(cursor.getString(cursor.getColumnIndex(Columns.Kiss.COLUMN_DESCRIPTION)));
                kiss.setImageId(cursor.getString(cursor.getColumnIndex(Columns.Kiss.COLUMN_IMAGE_ID)));
                boolean isLike = cursor.getString(cursor.getColumnIndex(Columns.Kiss.COLUMN_LIKE)).equalsIgnoreCase("true");
                kiss.setLike(isLike);
                // boolean isLiked = cursor.getInt(cursor.getColumnIndex(Columns.Kiss.COLUMN_LIKED)) == 0 ? false : true;
                // kiss.setLike(isLiked);
                Log.v("Kiss List", "Kiss Id "+kiss.getId()+" like " +isLike);

            } while (cursor.moveToNext());
        }
        cursor.close();
     return kiss.isLike();
    }
    public void setLike(Kiss kiss)
    {
        ContentValues values = new ContentValues();
        values.put(Columns.Kiss.COLUMN_LIKE, String.valueOf(kiss.isLike()));

// Which row to update, based on the title
        String selection = Columns.Kiss.COLUMN_ID + " LIKE ?";
        String[] selectionArgs = { kiss.getId() };

        try {
            int count = mDbHelper.update(
                    Columns.Kiss.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs);
            Log.v("Update Like in Kiss", "setLike Id "+kiss.getId()+" count " + count);
        }
        catch (SQLiteException e)
        {
            Log.v("Error in Like Kiss", "setLike " + e.getMessage());
        }

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

    public void close()
    {
        if(mDbHelper != null)
        {
            mDbHelper.close();
        }
    }

}

