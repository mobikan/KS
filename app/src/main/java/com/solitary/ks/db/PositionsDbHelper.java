package com.solitary.ks.db;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import static com.solitary.ks.db.DataBaseHelper.DB_NAME_POSITION;

public class PositionsDbHelper extends SQLiteAssetHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = DB_NAME_POSITION;

    public PositionsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public PositionsDbHelper(Context context,String dbName,int dbVersion) {
        super(context, dbName, null, dbVersion);
    }


}