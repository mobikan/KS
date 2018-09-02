package com.solitary.ks.db;


import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.solitary.ks.db.Columns;
import com.solitary.ks.model.Position;

public class DataBaseHelper extends SQLiteOpenHelper {
        private SQLiteDatabase myDataBase;
        private final Context myContext;
        public static final String DB_NAME_POSITION = "position.db";
        public static final String DB_NAME_BREATH = "Breath.db";
        public static final String DB_NAME_SPOT = "spot.db";
        public static final String DB_NAME_TIPS = "tips.db";
        public static final String DB_NAME_MASSAGE = "Massage therepy.db";
        public static final String DB_NAME_KISS = "kiss.db";

        public static final int DB_POSITION_VERSION =1;
        public static final int DB_SPOT_VERSION =1;
        public static final int DB_KISS_VERSION =1;
        public static final int DB_MASSAGE_VERSION =1;
        public static final int DB_BREATH_VERSION =1;
        public static final int DB_TIPS_VERSION =1;



        private String dbNameArray[] = {DB_NAME_POSITION,DB_NAME_BREATH,DB_NAME_SPOT,DB_NAME_TIPS,DB_NAME_MASSAGE,DB_NAME_KISS};

        private int databaseVersions[]= {DB_POSITION_VERSION,DB_BREATH_VERSION,DB_SPOT_VERSION,DB_TIPS_VERSION,DB_MASSAGE_VERSION,DB_KISS_VERSION};
        private static final String DATABASE_NAME = "position.db";
        public final static String DATABASE_PATH = "/data/data/com.solitary.ks/databases/";
        public static final int DATABASE_VERSION = 1;
        public DataBaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.myContext = context;

        }



        //Create a empty database on the system
        public void createDatabase() throws IOException
        {


            for(int i=0;i< dbNameArray.length;i++) {

                String dbName = dbNameArray[i];
                boolean dbExist = checkDataBase(dbName);

                if (dbExist) {
                    Log.v("DB Exists", "db exists");
                    // By calling this method here onUpgrade will be called on a
                    // writeable database, but only if the version number has been
                    // bumped
                    onUpgrade(myDataBase, 1, databaseVersions[i]);
                }

                boolean dbExist1 = checkDataBase(dbName);
                if (!dbExist1) {
                    this.getReadableDatabase();
                    try {
                        this.close();
                        copyDataBase(dbName);
                    } catch (IOException e) {
                        throw new Error("Error copying database "+dbName);
                    }
                }
            }

        }
        //Check database already exist or not
        private boolean checkDataBase(String dbName)
        {
            boolean checkDB = false;
            try
            {
                String myPath = DATABASE_PATH + dbName;
                File dbfile = new File(myPath);
                checkDB = dbfile.exists();
            }
            catch(SQLiteException e)
            {
            }
            return checkDB;
        }
        //Copies your database from your local assets-folder to the just created empty database in the system folder
        private void copyDataBase(String dbName) throws IOException
        {

            InputStream mInput = myContext.getAssets().open(dbName);
            String outFileName = DATABASE_PATH + dbName;
            OutputStream mOutput = new FileOutputStream(outFileName);
            byte[] mBuffer = new byte[2024];
            int mLength;
            while ((mLength = mInput.read(mBuffer)) > 0) {
                mOutput.write(mBuffer, 0, mLength);
            }
            mOutput.flush();
            mOutput.close();
            mInput.close();
        }
        //delete database
        public void db_delete()
        {
            File file = new File(DATABASE_PATH + DATABASE_NAME);
            if(file.exists())
            {
                file.delete();
                System.out.println("delete database file.");
            }
        }
        //Open database
        public SQLiteDatabase openDatabase(String dbName) throws SQLException
        {
            String myPath = DATABASE_PATH + dbName;
            myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

            return myDataBase;
        }

        public synchronized void closeDataBase()throws SQLException
        {
            if(myDataBase != null)
                myDataBase.close();
            super.close();
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (newVersion > oldVersion)
            {
                Log.v("Database Upgrade", "Database version higher than old.");
                db_delete();
            }

        }





}
