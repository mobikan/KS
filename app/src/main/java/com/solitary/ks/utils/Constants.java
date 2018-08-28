package com.solitary.ks.utils;

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

    }

    interface ShareConstants{
        String INTENT_SHARE_IMAGE_ID="imageId";
        String INTENT_SHARE_DETAIL_ID ="Detail";
        String INTENT_SHARE_TITLE_ID = "title";

    }
}
