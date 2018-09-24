package com.solitary.ks.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.solitary.ks.R;
import com.solitary.ks.fragment.AppRatingDialogFragment;
import com.solitary.ks.utils.Utils;

public class ExitDialogFragment extends AppRatingDialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void setTitleMsg()
    {
        dialog_rating_title.setText("");
    }

    @Override
    public void onClick(View v) {

//        switch (v.getId())
//        {
//            case R.id.dialog_rating_button_positive:
//
//                dismiss();
//                Utils.openAppOnGooglePlay(getActivity().getApplicationContext());
//                getActivity().finish();
//
//                break;
//            case R.id.dialog_rating_button_negative:
//                dismiss();
//                getActivity().finish();
//                break;
//
//        }
        super.onClick(v);
        getActivity().finish();

    }
}
