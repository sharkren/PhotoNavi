package com.example.thomas.photonavi.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.thomas.photonavi.R;

public class SettingActivity extends AppCompatActivity {
    ArrayAdapter<CharSequence>  adspin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.member_actionbar, null);

        TextView tvTitle = (TextView) mCustomView.findViewById(R.id.tvTitle);
        tvTitle.setText(R.string.setting);
        ImageButton imageButton = (ImageButton) mCustomView.findViewById(R.id.imgBtnBack);
        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        /*
        네비게이션 선택
         */
        Spinner spinner = (Spinner) findViewById(R.id.spNavigation);

        adspin = ArrayAdapter.createFromResource(this, R.array.navigateion,
                android.R.layout.simple_spinner_item);

        adspin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adspin);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        /*
        ActionBar 하단의 그림자 삭제
         */
        getSupportActionBar().setElevation(0f);
        getSupportActionBar().setDisplayOptions(getSupportActionBar().DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(mCustomView);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFFF9800));

    }

}
