package com.example.thomas.photonavi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.thomas.photonavi.R;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Button btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplication(), ContentsActivity.class)); // 로딩이 끝난후 이동할 Activity
                //startActivity(new Intent(getApplication(), MainActivity.class)); // 로딩이 끝난후 이동할 Activity
                LoginActivity.this.finish();
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

}
