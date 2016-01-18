package com.example.thomas.photonavi.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.thomas.photonavi.R;

public class MemberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.member_actionbar, null);

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

        Button btnJoin = (Button) findViewById(R.id.btnJoin);
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Button btnJoinCancle = (Button) findViewById(R.id.btnJoinCancle);
        btnJoinCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //mActionBar.setCustomView(mCustomView);
        //mActionBar.setDisplayShowCustomEnabled(true);

        // ActionBar에 타이틀 변경
        //getSupportActionBar().setTitle("회원가입");

        // ActionBar의 배경색 변경
        //
        // ActionBar 뒤로가기 버튼 활성화
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
