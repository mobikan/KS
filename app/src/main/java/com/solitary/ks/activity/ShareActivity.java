package com.solitary.ks.activity;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.solitary.ks.R;
import com.solitary.ks.utils.Utils;

import static com.solitary.ks.utils.Constants.ShareConstants.INTENT_SHARE_DETAIL_ID;
import static com.solitary.ks.utils.Constants.ShareConstants.INTENT_SHARE_IMAGE_ID;
import static com.solitary.ks.utils.Constants.ShareConstants.INTENT_SHARE_TITLE_ID;


public class ShareActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shareable_layout);

        ImageView google_play_icon = findViewById(R.id.google_play_icon);
        if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            google_play_icon.setImageResource(R.drawable.ic_google);
        }
        else {
            google_play_icon.setImageResource(R.drawable.ic_google_play);
        }
        if(getIntent() != null)
        {
            int imageId = getIntent().getIntExtra(INTENT_SHARE_IMAGE_ID, 0);
            String details  = getIntent().getStringExtra(INTENT_SHARE_DETAIL_ID);
            String title  = getIntent().getStringExtra(INTENT_SHARE_TITLE_ID);

            ImageView imageView = findViewById(R.id.imageView);
            imageView.setImageResource(imageId);

            TextView detailText= findViewById(R.id.detailText);
            detailText.setText(details);

            TextView titleText= findViewById(R.id.titleText);
            titleText.setText(title);
        }




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                RelativeLayout view = findViewById(R.id.mainLayout);
                view.setDrawingCacheEnabled(true);
                view.buildDrawingCache();
                Bitmap bm = view.getDrawingCache();

                Utils.shareImage(bm, ShareActivity.this);
            }
        }, 1000);

    }

    public static Bitmap getScreenShot(View v) {
        v.setDrawingCacheEnabled(true);

// this is the important code :)
// Without it the view will have a dimension of 0,0 and the bitmap will be null
        v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());

        v.buildDrawingCache(true);
        Bitmap bitmap = Bitmap.createBitmap(v.getDrawingCache());
        v.setDrawingCacheEnabled(false); // clear drawing cache
        return bitmap;
    }


}
