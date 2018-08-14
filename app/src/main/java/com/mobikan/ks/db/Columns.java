package com.mobikan.ks.db;

public interface Columns {

    public interface Position {
        String TABLE_NAME = "position";
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

    }

    public interface Kiss {
        String TABLE_NAME = "kiss";
        String COLUMN_ID = "ID";
        String COLUMN_TITLE = "Title";
        String COLUMN_DESCRIPTION = "Description";
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
        String COLUMN_ID = "Id";
        String COLUMN_TITLE = "Title";
        String COLUMN_DESCRIPTION = "Description";
        String COLUMN_IMAGE_ID = "Image_Id";
        String COLUMN_LIKE = "Like";
        String COLUMN_VIDEO_LINK = "Video_link";
        String COLUMN_LIKED = "LIKED";


    }

}
