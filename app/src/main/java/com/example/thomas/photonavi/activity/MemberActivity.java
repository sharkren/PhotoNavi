package com.example.thomas.photonavi.activity;

import android.app.DatePickerDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.thomas.photonavi.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MemberActivity extends AppCompatActivity {

    private EditText etBirth;
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

        getSupportActionBar().setDisplayOptions(getSupportActionBar().DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(mCustomView);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF009688));

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

        /*
        날짜 입력을 위한 DatePicker 호출
         */
        final int year, month, day;
        GregorianCalendar calendar = new GregorianCalendar();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day= calendar.get(Calendar.DAY_OF_MONTH);

        etBirth = (EditText) findViewById(R.id.etBirth);
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

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub
            String msg = String.format("%d년 %02d월 %02d일", year, monthOfYear+1, dayOfMonth);
            etBirth.setText(msg);
        }

    };

}
