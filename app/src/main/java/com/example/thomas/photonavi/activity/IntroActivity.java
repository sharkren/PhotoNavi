package com.example.thomas.photonavi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.thomas.photonavi.R;

public class IntroActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        Handler hd = new Handler();
        hd.postDelayed(new splashhandler(), 4000); // 3초 후에 hd Handler 실행
    }

    private class splashhandler implements Runnable{
        public void run() {

            startActivity(new Intent(getApplication(), LoginActivity.class)); // 인트로가 끝난후 이동할 Activity
            IntroActivity.this.finish(); // 인트로 Activity Stack에서 제거
        }
    }


}
