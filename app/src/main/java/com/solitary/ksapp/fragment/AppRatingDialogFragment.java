package com.solitary.ksapp.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RatingBar;
import android.widget.TextView;

import com.solitary.ksapp.R;
import com.solitary.ksapp.utils.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.solitary.ksapp.utils.Constants.TermsAndCondition.PREF_NAME;
import static com.solitary.ksapp.utils.Constants.TermsAndCondition.PREF_RATING_GIVEN_KEY;

public class AppRatingDialogFragment extends DialogFragment implements View.OnClickListener,RatingBar.OnRatingBarChangeListener{

    private RatingBar ratingBar;
    private TextView positiveButton;
    private TextView cancelButton;
    private TextView dialog_rating_msg;
    private List<String> msgList =  Arrays.asList("Very Bad", "Not good", "Quite ok", "Very Good", "Excellent !!!");
    protected TextView dialog_rating_title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.app_rating_dialog, container, false);
        Objects.requireNonNull(getDialog().getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ratingBar = v.findViewById(R.id.dialog_rating_rating_bar);
        ratingBar.setOnRatingBarChangeListener(this);
        positiveButton =  v.findViewById(R.id.dialog_rating_button_positive);
        positiveButton.setOnClickListener(this);
        cancelButton=  v.findViewById(R.id.dialog_rating_button_negative);
        cancelButton.setOnClickListener(this);
        dialog_rating_msg = v.findViewById(R.id.dialog_rating_msg);
        dialog_rating_title = v.findViewById(R.id.dialog_rating_title);
        return v;
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Objects.requireNonNull(dialog.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.dialog_rating_button_positive:
                if(ratingBar.getRating()>=3.0f) {
                    Log.v("Rating ", "Rating "+ratingBar.getRating());
                    dismiss();
                    Utils.openAppOnGooglePlay(getActivity().getApplicationContext());
                    SharedPreferences pref = getActivity().getSharedPreferences(PREF_NAME, 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean(PREF_RATING_GIVEN_KEY, true);
                    editor.apply();
                }
            break;
        }
        if(isVisible()) {
            dismiss();
        }

    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
        dialog_rating_msg.setVisibility(View.VISIBLE);
        int index = (int) ratingBar.getRating();
        if(index>0) {
            dialog_rating_msg.setText(msgList.get(index - 1));
        }
    }
}
