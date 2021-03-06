package com.solitary.ksapp.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.solitary.ksapp.R;
import com.solitary.ksapp.db.PositionDataBaseHelper;
import com.solitary.ksapp.db.KSDatabaseHelper;
import com.solitary.ksapp.firebase.FireBaseQueries;
import com.solitary.ksapp.model.Position;

import java.util.Objects;

import static com.solitary.ksapp.utils.Constants.RatingDialog.BUNDLE_POSITION_ID;
import static com.solitary.ksapp.utils.Constants.RatingDialog.BUNDLE_POSITION_IMAGE_ID;
import static com.solitary.ksapp.utils.Constants.RatingDialog.BUNDLE_POSITION_TITLE;


public class RatingDialogFragment extends DialogFragment implements View.OnClickListener{

    private  RatingBar ratingBar;
    private String positionId = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.rating_dialog_layout, container, false);
        Objects.requireNonNull(getDialog().getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView positionIcon =  v.findViewById(R.id.positionIcon);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.circleCrop().override(100, 100);

        TextView positionName =  v.findViewById(R.id.positionName);

        CardView submit = v.findViewById(R.id.submitButton);
        submit.setOnClickListener(this);

        ratingBar = v.findViewById(R.id.RatingBar);

        if(getArguments() != null)
        {
            positionId = getArguments().getString(BUNDLE_POSITION_ID);
            int imageId = getArguments().getInt(BUNDLE_POSITION_IMAGE_ID);
            Glide.with(getActivity()).load(imageId)
                    .apply(requestOptions)
                    .into(positionIcon);
            positionName.setText(getArguments().getString(BUNDLE_POSITION_TITLE));
        }

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

        int rating = (int) Math.floor(ratingBar.getRating());
        if(ratingBar.getRating() == .5)
        {
            rating = 1;
        }
        FireBaseQueries.getInstance().updateRating(FireBaseQueries.LIKE_POSITION, positionId, rating);

        PositionDataBaseHelper positionDataBaseHelper = new PositionDataBaseHelper(KSDatabaseHelper.getInstance(getActivity()));
        Position position = new Position();
        position.setId(positionId);
        position.setUserRating(rating);
        positionDataBaseHelper.setUserRating(position);
        dismiss();

    }
}
