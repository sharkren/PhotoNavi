package com.example.thomas.photonavi.activity;

import android.app.DatePickerDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.thomas.photonavi.R;
import com.example.thomas.photonavi.service.RestApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MemberActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private EditText etCellNum;
    private EditText etBirth;
    private Spinner spNavigation;
    private Switch swAlarm;
    private Switch swAuto;

    ArrayAdapter<CharSequence>  adspin;

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

        /*
        ActionBar 하단의 그림자 삭제
         */
        getSupportActionBar().setElevation(0f);
        getSupportActionBar().setDisplayOptions(getSupportActionBar().DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(mCustomView);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFFF9800));

        // 가입화면 데이터 Binding
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etCellNum = (EditText) findViewById(R.id.etCellNum);
        etBirth = (EditText) findViewById(R.id.etBirth);
        spNavigation = (Spinner) findViewById(R.id.spNavigation);
        swAlarm = (Switch) findViewById(R.id.swAlarm);
        swAuto = (Switch) findViewById(R.id.swAuto);

        Button btnJoin = (Button) findViewById(R.id.btnJoin);
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //onBackPressed();
                // 회원가입 API 호출
                JSONObject jsonObject = makeParam();

                RestApiClient restApiClient = null;
                try {
                    restApiClient = new RestApiClient();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                String retMsg = "0000";
                retMsg = restApiClient.restApiCall(getApplication(), jsonObject, "joinUser.do");

            }
        });

        Button btnJoinCancle = (Button) findViewById(R.id.btnJoinCancle);
        btnJoinCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        /*
        날짜 입력을 위한 DatePicker 호출
         */
        final int year, month, day;
        GregorianCalendar calendar = new GregorianCalendar();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day= calendar.get(Calendar.DAY_OF_MONTH);

        etBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MemberActivity.this, dateSetListener, year, month, day).show();
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
    }

    /**
     * 입력받은 값으로 param 데이터 생성
     * @return JSONObject paramJson
     */
    private JSONObject makeParam () {
        JSONObject paramJson = new JSONObject();

        try {
            paramJson.put("email", etEmail.getText().toString());
            paramJson.put("pwd", etPassword.getText().toString());
            paramJson.put("cellPhone", etCellNum.getText().toString());
            paramJson.put("birth", etBirth.getText().toString());
            paramJson.put("useNavi", spNavigation.getSelectedItem().toString());
            paramJson.put("push", swAlarm.isChecked() ? "Y" : "N");
            paramJson.put("autoRecommand", swAlarm.isChecked() ? "Y" : "N");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return paramJson;
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub
            Log.d("Map","year = " + year);
            Log.d("Map","monthOfYear = " + monthOfYear);
            Log.d("Map","dayOfMonth = " + dayOfMonth);

            String msg = String.format("%d년 %02d월 %02d일", year, monthOfYear+1, dayOfMonth);
            Log.d("Map", "date = " + msg);
            etBirth.setText(msg);
        }

    };

}
