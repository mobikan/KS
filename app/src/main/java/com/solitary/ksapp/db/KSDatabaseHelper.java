package com.solitary.ksapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.solitary.ksapp.model.Position;

import java.util.ArrayList;

import static com.solitary.ksapp.db.Columns.Kiss.SQL_CREATE_KISS;
import static com.solitary.ksapp.db.Columns.Massage.SQL_CREATE_MASSAGE;
import static com.solitary.ksapp.db.Columns.Position.DB_NAME;
import static com.solitary.ksapp.db.Columns.Position.SQL_CREATE_POSITIONS;

public class KSDatabaseHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = DB_NAME;
    private static KSDatabaseHelper sInstance;
    public KSDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized KSDatabaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new KSDatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_POSITIONS);
        db.execSQL(SQL_CREATE_MASSAGE);
        db.execSQL(SQL_CREATE_KISS);

    }

    public void initDataBase(ArrayList<Position> positions) {


        try {
            // The user might already exist in the database (i.e. the same user created multiple posts).
            for (Position position : positions) {
                long userId = addOrUpdateUser(position);
            }
            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.

        } catch (Exception e) {
            Log.d("PositionDbHelper", "Error while trying to add post to database");
        } finally {

        }

    }

    public long addOrUpdateUser(Position position) {
        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
        SQLiteDatabase db = getWritableDatabase();
        long userId = -1;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(Columns.Position.COLUMN_ID, position.getId());
            values.put(Columns.Position.COLUMN_BENEFITS, position.getBenefits());

            values.put(Columns.Position.COLUMN_DESCRIPTION, position.getDescription());
            values.put(Columns.Position.COLUMN_TIPS_HER, position.getTips_her());
            values.put(Columns.Position.COLUMN_TIPS_HIS, position.getTips_his());
            values.put(Columns.Position.COLUMN_TITLE, position.getTitle());
            values.put(Columns.Position.COLUMN_TRY_THIS, position.getTry_this());
            values.put(Columns.Position.COLUMN_RATING, position.getRating());
            values.put(Columns.Position.COLUMN_USER_RATING, position.getUserRating());

            String fav = position.isFavourite() ? "true" : "false";
            values.put(Columns.Position.COLUMN_FAVOURITE, fav);

            String tried = position.isTried() ? "true" : "false";
            values.put(Columns.Position.COLUMN_TRIED, tried);

            String liked = position.isLiked() ? "true" : "false";
            values.put(Columns.Position.COLUMN_LIKED, liked);


            // First try to update the user in case the user already exists in the database
            // This assumes userNames are unique
            int rows = db.update(Columns.Position.TABLE_NAME, values, Columns.Position.COLUMN_ID + "= ?", new String[]{position.getId()});

            // Check if update succeeded
            if (rows == 1) {
                // Get the primary key of the user we just updated
                String usersSelectQuery = String.format("SELECT %s FROM %s WHERE %s = ?",
                        Columns.Position.COLUMN_ID, Columns.Position.TABLE_NAME, Columns.Position.COLUMN_ID);
                Cursor cursor = db.rawQuery(usersSelectQuery, new String[]{String.valueOf(position.getId())});
                try {
                    if (cursor.moveToFirst()) {
                        userId = cursor.getInt(0);
                        db.setTransactionSuccessful();
                    }
                } finally {
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                }
            } else {
                // user with this userName did not already exist, so insert new user
                userId = db.insert(Columns.Position.TABLE_NAME, null, values);
                Log.d("PositionDbHelper", "userId " + userId);
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.d("PositionDbHelper", "Error while trying to add or update user");
        } finally {
            db.endTransaction();
        }
        return userId;
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + Columns.Position.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + Columns.Massage.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + Columns.Kiss.TABLE_NAME);
            onCreate(db);
        }
    }
}