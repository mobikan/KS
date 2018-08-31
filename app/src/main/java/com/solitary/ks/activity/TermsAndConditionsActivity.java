package com.solitary.ks.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.solitary.ks.R;

import static com.solitary.ks.utils.Constants.TermsAndCondition.PREF_NAME;
import static com.solitary.ks.utils.Constants.TermsAndCondition.PREF_TERMS_AGREE_KEY;


public class TermsAndConditionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terms_and_conitions);

        CheckBox checkBox = findViewById(R.id.checkbox);
        final CardView agree = findViewById(R.id.submitButton);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
             if(b)
             {
                 agree.setVisibility(View.VISIBLE);
             }
             else
             {
                 agree.setVisibility(View.INVISIBLE);
             }

            }
        });

        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openHomePage();

            }
        });
    }

    private void openHomePage()
    {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(PREF_NAME, 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(PREF_TERMS_AGREE_KEY, true);
        editor.apply();
        startActivity(new Intent(TermsAndConditionsActivity.this, HomePageActivity.class));
        TermsAndConditionsActivity.this.finish();
    }
}
