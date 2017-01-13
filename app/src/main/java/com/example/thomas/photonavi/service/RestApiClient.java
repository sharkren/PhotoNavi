package com.example.thomas.photonavi.service;

import android.content.Context;
import android.util.Log;

import com.example.thomas.photonavi.common.Global;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by Administrator on 2016-03-27.
 */
public class RestApiClient {


    private static JSONObject resJson;

    public RestApiClient() throws UnsupportedEncodingException {
        //this.context = context;
        //this.reqParamJson = reqParamJson;
    }

    public static JSONObject restApiCall(Context context, JSONObject reqParamJson, String apiName) {

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        StringEntity entity = null;
        try {
            Log.d("Map", "PORK_LOG >>> Rest Api Parameters" + reqParamJson.toString());
            entity = new StringEntity(reqParamJson.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(context, Global.REST_AIP_URL+apiName, entity, "application/json", new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("Map", "PORK_LOG >>> Login Check Success");
                Log.d("Map", "PORK_LOG >>> Received Msg:"+ String.valueOf(response.toString()));
                makeResponse(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject e)  {
                //retVal = "9999";
            }
        });

        return resJson;
    }

    private static void makeResponse(JSONObject response) {
        resJson = response;
    }
}
