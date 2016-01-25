package com.example.thomas.photonavi.service;

/**
 * Created by thomas on 2016-01-25.
 */
import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by saltfactory on 6/8/15.
 */
public class MyInstanceIDListenerService extends InstanceIDListenerService {

    private static final String TAG = "MyInstanceIDLS";

    @Override
    public void onTokenRefresh() {
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }
}
