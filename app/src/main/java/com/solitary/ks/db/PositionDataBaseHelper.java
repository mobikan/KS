package com.solitary.ks.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.solitary.ks.model.Position;

public class PositionDataBaseHelper {

    private KSDatabaseHelper mDbHelper;

    private String[] columns = {Columns.Position.COLUMN_ID, Columns.Position.COLUMN_BENEFITS, Columns.Position.COLUMN_DESCRIPTION,
            Columns.Position.COLUMN_TIPS_HER, Columns.Position.COLUMN_TIPS_HIS, Columns.Position.COLUMN_TITLE, Columns.Position.COLUMN_TRY_THIS,
            Columns.Position.COLUMN_RATING, Columns.Position.COLUMN_FAVOURITE, Columns.Position.COLUMN_TRIED, Columns.Position.COLUMN_LIKED};

    public PositionDataBaseHelper(KSDatabaseHelper mDbHelper) {
        this.mDbHelper = mDbHelper;
    }

    public ArrayList<Position> getAllPositions() {

        ArrayList<Position> positions = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Columns.Position.TABLE_NAME;

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Position position = new Position();
                    position.setId(cursor.getString(cursor.getColumnIndex(Columns.Position.COLUMN_ID)));
                    position.setBenefits(cursor.getString(cursor.getColumnIndex(Columns.Position.COLUMN_BENEFITS)));
                    position.setDescription(cursor.getString(cursor.getColumnIndex(Columns.Position.COLUMN_DESCRIPTION)));
                    position.setTips_her(cursor.getString(cursor.getColumnIndex(Columns.Position.COLUMN_TIPS_HER)));
                    position.setTips_his(cursor.getString(cursor.getColumnIndex(Columns.Position.COLUMN_TIPS_HIS)));
                    position.setTitle(cursor.getString(cursor.getColumnIndex(Columns.Position.COLUMN_TITLE)));
                    position.setTry_this(cursor.getString(cursor.getColumnIndex(Columns.Position.COLUMN_TRY_THIS)));
                    position.setRating(cursor.getInt(cursor.getColumnIndex(Columns.Position.COLUMN_RATING)));
                    position.setUserRating(cursor.getInt(cursor.getColumnIndex(Columns.Position.COLUMN_USER_RATING)));

                    boolean isFavourite = cursor.getString(cursor.getColumnIndex(Columns.Position.COLUMN_FAVOURITE)).equalsIgnoreCase("true") ? true : false;
                    boolean isTried = cursor.getString(cursor.getColumnIndex(Columns.Position.COLUMN_TRIED)).equalsIgnoreCase("true") ? true : false;
                    position.setTried(isTried);
                    position.setFavourite(isFavourite);

                    boolean isLiked = cursor.getString(cursor.getColumnIndex(Columns.Position.COLUMN_LIKED)).equalsIgnoreCase("true") ? true : false;
                    position.setLiked(isLiked);

                    positions.add(position);
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            //db.endTransaction();
        }
        // close db connection

        // return notes list
        return positions;
    }

    public List<Position> getAllFavouritePositions() {

        List<Position> positions = new ArrayList<>();

        Cursor cursor = mDbHelper.getWritableDatabase().query(Columns.Position.TABLE_NAME, columns, Columns.Position.COLUMN_FAVOURITE + " = ?", new String[]{"true"}, null, null, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Position position = new Position();
                position.setId(cursor.getString(cursor.getColumnIndex(Columns.Position.COLUMN_ID)));
                position.setBenefits(cursor.getString(cursor.getColumnIndex(Columns.Position.COLUMN_BENEFITS)));
                position.setDescription(cursor.getString(cursor.getColumnIndex(Columns.Position.COLUMN_DESCRIPTION)));
                position.setTips_her(cursor.getString(cursor.getColumnIndex(Columns.Position.COLUMN_TIPS_HER)));
                position.setTips_his(cursor.getString(cursor.getColumnIndex(Columns.Position.COLUMN_TIPS_HIS)));
                position.setTitle(cursor.getString(cursor.getColumnIndex(Columns.Position.COLUMN_TITLE)));
                position.setTry_this(cursor.getString(cursor.getColumnIndex(Columns.Position.COLUMN_TRY_THIS)));
                position.setRating(cursor.getInt(cursor.getColumnIndex(Columns.Position.COLUMN_RATING)));

                boolean isFavourite = cursor.getString(cursor.getColumnIndex(Columns.Position.COLUMN_FAVOURITE)).equalsIgnoreCase("true") ? true : false;
                boolean isTried = cursor.getString(cursor.getColumnIndex(Columns.Position.COLUMN_TRIED)).equalsIgnoreCase("true") ? true : false;
                position.setTried(isTried);
                position.setFavourite(isFavourite);

                boolean isLiked = cursor.getString(cursor.getColumnIndex(Columns.Position.COLUMN_LIKED)).equalsIgnoreCase("true") ? true : false;
                position.setLiked(isLiked);

                positions.add(position);
            } while (cursor.moveToNext());
        }

        // close db connection
        mDbHelper.close();
        cursor.close();
        // return notes list
        return positions;
    }

    public List<Position> getAllTriedPositions() {

        List<Position> positions = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        Cursor cursor = db.query(Columns.Position.TABLE_NAME, columns, Columns.Position.COLUMN_TRIED + " = ?", new String[]{"true"}, null, null, null);

        try {

            if (cursor.moveToFirst()) {
                do {
                    Position position = new Position();
                    position.setId(cursor.getString(cursor.getColumnIndex(Columns.Position.COLUMN_ID)));
                    position.setBenefits(cursor.getString(cursor.getColumnIndex(Columns.Position.COLUMN_BENEFITS)));
                    position.setDescription(cursor.getString(cursor.getColumnIndex(Columns.Position.COLUMN_DESCRIPTION)));
                    position.setTips_her(cursor.getString(cursor.getColumnIndex(Columns.Position.COLUMN_TIPS_HER)));
                    position.setTips_his(cursor.getString(cursor.getColumnIndex(Columns.Position.COLUMN_TIPS_HIS)));
                    position.setTitle(cursor.getString(cursor.getColumnIndex(Columns.Position.COLUMN_TITLE)));
                    position.setTry_this(cursor.getString(cursor.getColumnIndex(Columns.Position.COLUMN_TRY_THIS)));
                    position.setRating(cursor.getInt(cursor.getColumnIndex(Columns.Position.COLUMN_RATING)));

                    boolean isFavourite = cursor.getString(cursor.getColumnIndex(Columns.Position.COLUMN_FAVOURITE)).equalsIgnoreCase("true") ? true : false;
                    boolean isTried = cursor.getString(cursor.getColumnIndex(Columns.Position.COLUMN_TRIED)).equalsIgnoreCase("true") ? true : false;
                    position.setTried(isTried);
                    position.setFavourite(isFavourite);

                    boolean isLiked = cursor.getString(cursor.getColumnIndex(Columns.Position.COLUMN_LIKED)).equalsIgnoreCase("true") ? true : false;
                    position.setLiked(isLiked);

                    positions.add(position);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // db.endTransaction();
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close db connection

        // return notes list
        return positions;
    }

    public void setFavourite(Position position) {
        ContentValues values = new ContentValues();
        values.put(Columns.Position.COLUMN_FAVOURITE, String.valueOf(position.isFavourite()));


        String selection = Columns.Position.COLUMN_ID + " LIKE ?";
        String[] selectionArgs = {position.getId()};
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        try {
            int count = db.update(
                    Columns.Position.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs);
            Log.v("setFavourite", "count " + count);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           // db.endTransaction();
        }

    }


    public void setTried(Position position) {
        ContentValues values = new ContentValues();
        values.put(Columns.Position.COLUMN_TRIED, String.valueOf(position.isTried()));

// Which row to update, based on the title
        String selection = Columns.Position.COLUMN_ID + " LIKE ?";
        String[] selectionArgs = {position.getId()};
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        try {
            int count = db.update(
                    Columns.Position.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs);
            Log.v("setTried", "count " + count);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           // db.endTransaction();
        }

    }

    public void setLiked(Position position) {
        ContentValues values = new ContentValues();
        values.put(Columns.Position.COLUMN_LIKED, String.valueOf(position.isLiked()));

// Which row to update, based on the title
        String selection = Columns.Position.COLUMN_ID + " LIKE ?";
        String[] selectionArgs = {position.getId()};
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        try {
            int count = db.update(
                    Columns.Position.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs);
            Log.v("Favourite", "count " + count);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //db.endTransaction();
        }

    }

    /**
     * Set user rating given by himself
     *
     * @param position
     */
    public void setUserRating(Position position) {
        ContentValues values = new ContentValues();
        values.put(Columns.Position.COLUMN_USER_RATING, String.valueOf(position.getUserRating()));

// Which row to update, based on the title
        String selection = Columns.Position.COLUMN_ID + " LIKE ?";
        String[] selectionArgs = {position.getId()};
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        try {

            int count = db.update(
                    Columns.Position.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs);
            Log.v("setRating", "count " + count);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }


    }

    public void setRating(Position position) throws SQLException {
        ContentValues values = new ContentValues();
        values.put(Columns.Position.COLUMN_RATING, String.valueOf(position.getRating()));

// Which row to update, based on the title
        String selection = Columns.Position.COLUMN_ID + " LIKE ?";
        String[] selectionArgs = {position.getId()};
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        try {
            int count = db.update(
                    Columns.Position.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs);
            Log.v("setRating", "count " + count);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           // db.endTransaction();
        }


    }

    public void closeDb() {
        if (mDbHelper != null) {
            mDbHelper.close();
            mDbHelper = null;
        }

    }

}
