package com.solitary.ksapp.utils;

public interface  Constants {

    interface RatingDialog{
        String BUNDLE_POSITION_ID="position_id";
        String BUNDLE_POSITION_IMAGE_ID ="position_image_id";
        String BUNDLE_POSITION_TITLE = "position_title";

    }

    interface TermsAndCondition{
        String PREF_TERMS_AGREE_KEY="is_agreed";
        String PREF_NAME ="TermsPref";
        String INTENT_TIPS_LIST_KEY = "tips_list";
        String PREF_RATING_GIVEN_KEY="is_Rating";

    }
    interface PositionPref{

        String POSITION_RATING_PREF ="PositionRatingPref";
        String POSITION_LIKE_PREF ="PositionLikePref";
        String POSITION_TRIED_PREF ="PositionTriedPref";
        String POSITION_RATING = "rating";
    }

    interface PositionDetail{

        String POSITION_LIST_INTENT_KEY ="PositionList";
        String POSITION_LIST_ITEM_INDEX ="Index";

    }

    interface ShareConstants{
        String INTENT_SHARE_IMAGE_ID="imageId";
        String INTENT_SHARE_DETAIL_ID ="Detail";
        String INTENT_SHARE_TITLE_ID = "title";

    }

    interface AppRating{
        int RATING_DIALOG_TYPE= 0;

    }

    interface WebView{
        String INTENT_URL= "url";
        String TIPS_COUNT = "tips_count";

    }


}
