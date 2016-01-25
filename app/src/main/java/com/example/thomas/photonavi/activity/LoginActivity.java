package com.example.thomas.photonavi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.thomas.photonavi.service.MyPhoneNumList;
import com.example.thomas.photonavi.R;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);

        // 로그인 버튼
        Button btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                // 로그인 체크
                String retMsg = loginCheck(email, password);

                // 컨탠츠 화면으로 이동동
                if (retMsg == "0000") {

                    // 전화번호 목록으로 친구목록 현행화 최초 1회
                    MyPhoneNumList mpnList = new MyPhoneNumList(LoginActivity.this);
                    mpnList.run();

                    startActivity(new Intent(getApplication(), ContentsActivity.class)); // 로딩이 끝난후 이동할 Activity
                    //startActivity(new Intent(getApplication(), MainActivity.class)); // 로딩이 끝난후 이동할 Activity
                    LoginActivity.this.finish();
                }
            }
        });

        Button btnJoinMember = (Button) findViewById(R.id.btnJoinMember);
        btnJoinMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplication(), MemberActivity.class)); // 로딩이 끝난후 이동할 Activity
                //LoginActivity.this.finish();
            }
        });

    }


    private String loginCheck(String email, String password) {
        String retVal = "0000";

        Log.d("Map", "Login Email " + email);
        Log.d("Map", "Login Password " + password);

        return retVal;
    }
}
