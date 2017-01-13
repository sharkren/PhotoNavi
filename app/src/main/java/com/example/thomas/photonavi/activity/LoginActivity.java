package com.example.thomas.photonavi.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.Manifest;
import android.widget.Toast;

import com.example.thomas.photonavi.R;
import com.example.thomas.photonavi.common.Global;
import com.example.thomas.photonavi.service.MyPhoneNumList;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends Activity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private ArrayList<String> permissioncheck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);

        // 로그인 버튼
        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = etEmail.getText().toString();
                String pwd = etPassword.getText().toString();

                // 로그인 체크
                JSONObject paramJson = new JSONObject();
                try {
                    paramJson.put("email", email);
                    paramJson.put("pwd", pwd);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                /*RestApiClient restApiClient = null;
                try {
                    restApiClient = new RestApiClient();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }*/

                JSONObject retMsg = new JSONObject();//RestApiClient.restApiCall(getApplication(), paramJson, Global.USER_LOGIN);
                try {

                    retMsg.put("retCode",Global.STATUS_OK);
                    // 컨탠츠 화면으로 이동동
                    if (Global.STATUS_OK.equals(retMsg.get("retCode"))) {

                        // 전화번호 목록으로 친구목록 현행화 최초 1회
                        MyPhoneNumList mpnList = new MyPhoneNumList(LoginActivity.this);
                        mpnList.run();

                        startActivity(new Intent(getApplication(), ContentsActivity.class)); // 로딩이 끝난후 이동할 Activity
                        //startActivity(new Intent(getApplication(), MainActivity.class)); // 로딩이 끝난후 이동할 Activity
                        LoginActivity.this.finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        // 권한 체크
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //checkPermission();
        }
    }

    /***** ANDROID -> PERMISSON CHECK *************************************************************/

/*
    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission() {
        ArrayList<String> _permissions = new ArrayList();

        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA);
            _permissions.add(Manifest.permission.CAMERA);
        }
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION);
            _permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION);
            _permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            _permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE);
            _permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS);
            _permissions.add(Manifest.permission.READ_CONTACTS);
        }


        if (!_permissions.isEmpty()) {
            requestPermissions(_permissions.toArray(new String[_permissions.size()]), Global.REQUEST_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        permissioncheck = new ArrayList<String>();

        switch(requestCode) {

            case Global.REQUEST_STORAGE:

                for(String permission : permissions) {

                    switch (permission) {

                        case Manifest.permission.CAMERA:{
                            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                                if(Global.DEBUG)  Log.i(Global.TAG, "Main() -> premissonState(OK) -> type : " + permissions[0]);
                                permissioncheck.add("CAMERA");
                            } else {
                                if(Global.DEBUG)  Log.i(Global.TAG, "Main() -> premissonState(CANCEL) -> type : " + permissions[0]);
                                finish();
                            }
                            break;
                        }
                        case Manifest.permission.ACCESS_FINE_LOCATION:{

                            if(grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                                if(Global.DEBUG)  Log.i(Global.TAG, "Main() -> premissonState(OK) -> type : " + permissions[1]);
                                permissioncheck.add("ACCESS_FINE_LOCATION");

                            } else {
                                if(Global.DEBUG)  Log.i(Global.TAG, "Main() -> premissonState(CANCEL) -> type : " + permissions[1]);
                                finish();
                            }
                            break;
                        }
                        case Manifest.permission.ACCESS_COARSE_LOCATION:{

                            if(grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                                if(Global.DEBUG)  Log.i(Global.TAG, "Main() -> premissonState(OK) -> type : " + permissions[2]);
                                permissioncheck.add("ACCESS_COARSE_LOCATION");
                            } else {
                                if(Global.DEBUG)  Log.i(Global.TAG, "Main() -> premissonState(CANCEL) -> type : " + permissions[2]);
                            }
                            break;

                        }
                        case Manifest.permission.WRITE_EXTERNAL_STORAGE:{
                            if(grantResults[3] == PackageManager.PERMISSION_GRANTED) {
                                if(Global.DEBUG)  Log.i(Global.TAG, "Main() -> premissonState(OK) -> type : " + permissions[3]);
                                permissioncheck.add("WRITE_EXTERNAL_STORAGE");
                            } else {
                                if(Global.DEBUG)  Log.i(Global.TAG, "Main() -> premissonState(CANCEL) -> type : " + permissions[3]);
                            }
                            break;
                        }
                        case Manifest.permission.READ_EXTERNAL_STORAGE:{
                            if(grantResults[4] == PackageManager.PERMISSION_GRANTED) {
                                if(Global.DEBUG)  Log.i(Global.TAG, "Main() -> premissonState(OK) -> type : " + permissions[4]);
                                permissioncheck.add("READ_EXTERNAL_STORAGE");
                            } else {
                                if(Global.DEBUG)  Log.i(Global.TAG, "Main() -> premissonState(CANCEL) -> type : " + permissions[4]);
                            }
                            break;
                        }
                        case Manifest.permission.GET_ACCOUNTS:{
                            if(grantResults[5] == PackageManager.PERMISSION_GRANTED) {
                                if(Global.DEBUG)  Log.i(Global.TAG, "Main() -> premissonState(OK) -> type : " + permissions[5]);
                                permissioncheck.add("GET_ACCOUNTS");
                            } else {
                                if(Global.DEBUG)  Log.i(Global.TAG, "Main() -> premissonState(CANCEL) -> type : " + permissions[5]);

                            }
                            break;
                        }
                        case Manifest.permission.READ_PHONE_STATE:

                            if(grantResults[6] == PackageManager.PERMISSION_GRANTED) {
                                if(Global.DEBUG)  Log.i(Global.TAG, "Main() -> premissonState(OK) -> type : " + permissions[6]);
                                permissioncheck.add("READ_PHONE_STATE");
                            } else {
                                if(Global.DEBUG)  Log.i(Global.TAG, "Main() -> premissonState(CANCEL) -> type : " + permissions[6]);

                            }
                            break;
                    }
                }

                if(Global.DEBUG)  Log.i(Global.TAG, "Main() -> premissonState(size) -> type : " + permissioncheck.size());

                if(permissioncheck.size() == 7){
                    Toast.makeText(this, "허용 되지 않은 권한이 있습니다. 해당 권한을 허용해야 앱 사용이 가능합니다.\n앱을 종료 합니다.", Toast.LENGTH_LONG).show();
                    finish();
                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }
*/

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path")
                // TODO: Make sure this auto-generated app deep link URI is correct.
               // Uri.parse("android-app://com.example.thomas.photonavi.activity/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path")
                // TODO: Make sure this auto-generated app deep link URI is correct.
                //Uri.parse("android-app://com.example.thomas.photonavi.activity/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
