package com.example.thomas.photonavi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.example.thomas.photonavi.R;

public class ContentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contents);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("전체"));
        tabLayout.addTab(tabLayout.newTab().setText("내꺼"));
        tabLayout.addTab(tabLayout.newTab().setText("친구"));
        tabLayout.addTab(tabLayout.newTab().setText("스크랩"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new com.example.thomas.photonavi.PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // 사진 등록 버튼

        ImageButton imgBtnNew = (ImageButton) findViewById(R.id.imgBtnNew);

        imgBtnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getApplication(), RegistActivity.class)); // 로딩이 끝난후 이동할 Activity
                startActivity(new Intent(getApplication(), CameraActivity.class));
            }
        });

    }

    /*
    @Override
    public void onClick(View v) {

        Log.d("Map","버튼 아이디는 " + v.getId());

        switch (v.getId()) {
            case R.id.imgBtnNew:
                // 사진을 갤러리에서 선택할지 촬영할지 선택
                AlertDialog.Builder builder = new AlertDialog.Builder(ContentsActivity.this);

                builder.setMessage("사진을 선택하세요").setCancelable(false)
                        .setPositiveButton("갤러리", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(getApplication(), RegistActivity.class));
                            }
                        })
                        .setNegativeButton("촬영", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(getApplication(), CameraActivity.class));
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;


            default:
                break;
        }
    }
    */


}
