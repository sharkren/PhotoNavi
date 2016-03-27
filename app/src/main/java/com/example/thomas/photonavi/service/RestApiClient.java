package com.example.thomas.photonavi.service;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import com.loopj.android.http.JsonHttpResponseHandler;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.Header;

/**
 * Created by Administrator on 2016-03-27.
 */
public class RestApiClient {

    final String REST_AIP_URL = "http://192.168.25.25:8080/pork/api/";
    final String USER_LOGIN = "userLogin.do";
    final String JOIN_USER = "joinUser.do";

    private JSONObject reqParamJson;
    private Context context;

    public RestApiClient() throws UnsupportedEncodingException {
        //this.context = context;
        //this.reqParamJson = reqParamJson;
    }

    public String restApiCall(Context context, JSONObject reqParamJson) {

        this.context = context;
        this.reqParamJson = reqParamJson;

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        StringEntity entity = null;
        try {
            Log.d("Map", "PORK_LOG >>> Rest Api Parameters" + this.reqParamJson.toString());
            entity = new StringEntity(this.reqParamJson.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(this.context, REST_AIP_URL+USER_LOGIN, entity, "application/json", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("Map", "PORK_LOG >>> Login Check Success");
                Log.d("Map", "PORK_LOG >>> Received Msg:"+ String.valueOf(response.toString()));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject e)  {
                //retVal = "9999";
            }
        });

        return "0000";
    }
}
