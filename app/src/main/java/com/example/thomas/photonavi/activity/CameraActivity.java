package com.example.thomas.photonavi.activity;

import android.app.Activity;
import android.os.Bundle;

import com.example.thomas.photonavi.R;
import com.example.thomas.photonavi.fragment.cameraFragment;

public class CameraActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        if (null == savedInstanceState) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, cameraFragment.newInstance()).commit();
        }
    }

}
