package com.solitary.ks.db;

public interface Columns {

     interface Position {
         String DB_NAME = "kamasutra";
           String SQL_CREATE_POSITIONS =
                "CREATE TABLE " + Position.TABLE_NAME + " (" +
                        Position.COLUMN_ID + " TEXT ," +
                        Position.COLUMN_TITLE + " TEXT," +
                        Position.COLUMN_DESCRIPTION + " TEXT," +
                        Position.COLUMN_TRY_THIS + " TEXT," +

                        Position.COLUMN_TIPS_HIS + " TEXT," +
                        Position.COLUMN_TIPS_HER + " TEXT," +
                        Position.COLUMN_RATING + " TEXT DEFAULT 0," +
                        Position.COLUMN_FAVOURITE + " TEXT DEFAULT false," +

                        Position.COLUMN_TRIED + " TEXT DEFAULT false," +
                        Position.COLUMN_LIKED + " TEXT DEFAULT false," +
                        Position.COLUMN_USER_RATING + " INTEGER DEFAULT 0," +
                        Position.COLUMN_BENEFITS + " TEXT)";

           String SQL_DELETE_POSITIONS =
                 "DROP TABLE IF EXISTS " + Position.TABLE_NAME;

        String TABLE_NAME = "positions";
        String COLUMN_ID = "ID";
        String COLUMN_TITLE = "TITLE";
        String COLUMN_BENEFITS = "BENEFITS";
        String COLUMN_DESCRIPTION = "DESCRIPTION";
        String COLUMN_TRY_THIS = "TRY_THIS";
        String COLUMN_TIPS_HIS = "TIPS_HIS";
        String COLUMN_TIPS_HER = "TIPS_HER";
        String COLUMN_RATING = "RATING";
        String COLUMN_FAVOURITE = "FAVOURITE";
        String COLUMN_TRIED = "TRIED";
        String COLUMN_LIKED = "LIKED";
        String COLUMN_USER_RATING = "USER_RATING";

    }

    public interface Kiss {
        String TABLE_NAME = "Kiss";
        String SQL_CREATE_KISS =
                "CREATE TABLE " + Kiss.TABLE_NAME + " (" +
                        Kiss.COLUMN_ID + " TEXT ," +
                        Kiss.COLUMN_TITLE + " TEXT," +
                        Kiss.COLUMN_DESCRIPTION + " TEXT," +
                        Kiss.COLUMN_IMAGE_ID + " TEXT," +
                        Kiss.COLUMN_LIKE + " TEXT," +

                        Kiss.COLUMN_LIKED + " TEXT)";


        String COLUMN_ID = "ID";
        String COLUMN_TITLE = "TITLE";
        String COLUMN_DESCRIPTION = "DESCRIPTION";
        String COLUMN_IMAGE_ID = "IMAGE_ID";
        String COLUMN_LIKE = "LIKE";
        String COLUMN_LIKED = "LIKED";

    }

    public interface Spot {


    }

    public interface Tips {


    }

    public interface Massage {
        String TABLE_NAME = "Massage";
        String SQL_CREATE_MASSAGE =
                "CREATE TABLE " + Massage.TABLE_NAME + " (" +
                        Massage.COLUMN_ID + " TEXT ," +
                        Massage.COLUMN_TITLE + " TEXT," +
                        Massage.COLUMN_DESCRIPTION + " TEXT," +
                        Massage.COLUMN_IMAGE_ID + " TEXT," +
                        Massage.COLUMN_LIKE + " TEXT," +
                        Massage.COLUMN_LIKED + " TEXT," +
                        Massage.COLUMN_VIDEO_LINK + " TEXT)";


        String COLUMN_ID = "ID";
        String COLUMN_TITLE = "TITLE";
        String COLUMN_DESCRIPTION = "DESCRIPTION";
        String COLUMN_IMAGE_ID = "IMAGE_ID";
        String COLUMN_LIKE = "LIKE";
        String COLUMN_VIDEO_LINK = "VIDEO_LINK";
        String COLUMN_LIKED = "LIKED";


    }

}
