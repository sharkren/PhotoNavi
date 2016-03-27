package com.example.thomas.photonavi.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.thomas.photonavi.BackPressCloseHandler;
import com.example.thomas.photonavi.R;

public class ContentsActivity extends AppCompatActivity {

    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contents);

        /*
        뒤로가기 2번 선택시 App 종료
        http://dsnight.tistory.com/14 참조
        */
        backPressCloseHandler = new BackPressCloseHandler(this);

        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.contents_actionbar, null);

        /*
        ActionBar 하단의 그림자 삭제
         */
        getSupportActionBar().setElevation(0f);
        getSupportActionBar().setDisplayOptions(getSupportActionBar().DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(mCustomView);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFFF9800));

        // 설정버튼
        ImageButton imgBtnSetting = (ImageButton) mCustomView.findViewById(R.id.imgBtnSetting);

        imgBtnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplication(), SettingActivity.class)); // 인트로가 끝난후 이동할 Activity
            }
        });

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        final float norSize = 15.0f;
        final float selSize = 16.0f;
        final String strColor = "#84827f";

        // 동적으로 탭 레이아웃 텍스뷰 생성.
        // 기본 탭 텍스트를 사용하면 스타일 변경이 안됨.
        TextView view1 = new TextView(this);
        view1.setTextSize(selSize);
        view1.setTextColor(Color.parseColor("#FFFFFF"));
        view1.setGravity(View.TEXT_ALIGNMENT_CENTER);
        view1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        view1.setText(R.string.total);
        tabLayout.addTab(tabLayout.newTab().setCustomView(view1));

        TextView view2 = new TextView(this);
        view2.setTextSize(norSize);
        view2.setTextColor(Color.parseColor(strColor));
        view2.setGravity(View.TEXT_ALIGNMENT_CENTER);
        view2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        view2.setText(R.string.my);
        tabLayout.addTab(tabLayout.newTab().setCustomView(view2));

        TextView view3 = new TextView(this);
        view3.setTextSize(norSize);
        view3.setTextColor(Color.parseColor(strColor));
        view3.setGravity(View.TEXT_ALIGNMENT_CENTER);
        view3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        view3.setText(R.string.friend);
        tabLayout.addTab(tabLayout.newTab().setCustomView(view3));

        TextView view4 = new TextView(this);
        view4.setTextSize(norSize);
        view4.setTextColor(Color.parseColor(strColor));
        view4.setGravity(View.TEXT_ALIGNMENT_CENTER);
        view4.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        view4.setText(R.string.bookmarkBold);
        tabLayout.addTab(tabLayout.newTab().setCustomView(view4));

        //tabLayout.addTab(tabLayout.newTab().setText(R.string.my));
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.friend));
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.bookmarkBold));
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new com.example.thomas.photonavi.PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public  void onPageSelected(int position) {
                Log.d("Map", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                Log.d("Map", "onPageSelected Called " + position);
                Log.d("Map", ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                resizePager(position, viewPager);
                for (int i = 0 ; i < 4; i++) {
                    TextView tv = (TextView) tabLayout.getTabAt(i).getCustomView();
                    // 선택된 탭만 폰트 폰트 크기 흰색으로 변경
                    //Log.d("Map", "not PageSelected");
                    tv.setTextColor(Color.parseColor(strColor));
                    tv.setTextSize(norSize);
                }

                TextView tv = (TextView) tabLayout.getTabAt(position).getCustomView();
                tv.setTextColor(Color.parseColor("#FFFFFF"));
                tv.setTextSize(selSize);
            }
        });


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

        // 글쓰기,사진,동영상 등록 버튼
        ImageButton imgBtnNew = (ImageButton) findViewById(R.id.imgBtnNew);

        imgBtnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplication(), RegistActivity.class)); // 로딩이 끝난후 이동할 Activity
            }
        });

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }


    public void resizePager(int position, ViewPager viewPager) {

        View view = viewPager.findViewWithTag(position);
        if (view == null) {
            return;
        }

        //view.measure(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        int width = 0;
        int height = 0;

        if (viewPager.getChildCount() > 0) {
            View firstChild = viewPager.getChildAt(0);

            width = View.MeasureSpec.makeMeasureSpec(viewPager.getWidth(), View.MeasureSpec.EXACTLY);

            firstChild.measure(width, View.MeasureSpec.makeMeasureSpec(viewPager.getHeight(), View.MeasureSpec.AT_MOST));
            height = firstChild.getMeasuredHeight();
        }

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width,height);
        viewPager.setLayoutParams(params);

    }

}
