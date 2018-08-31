package com.solitary.ks.firebase;


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

        switch (v.getId())
        {
            case R.id.dialog_rating_button_positive:

                dismiss();
                getActivity().finish();
                Utils.openAppOnGooglePlay(getActivity().getApplicationContext());
                break;
            case R.id.dialog_rating_button_negative:
                dismiss();
                getActivity().finish();
                break;

        }

    }
}
