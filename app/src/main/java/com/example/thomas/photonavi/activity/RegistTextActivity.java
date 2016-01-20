package com.example.thomas.photonavi.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import com.example.thomas.photonavi.R;

public class RegistTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_text);

        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.regist_actionbar, null);

        ImageButton imageButton = (ImageButton) mCustomView.findViewById(R.id.imgBtnBack);
        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        getSupportActionBar().setDisplayOptions(getSupportActionBar().DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(mCustomView);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFCDDC39));

    }

}
