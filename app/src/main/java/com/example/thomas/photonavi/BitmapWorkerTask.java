package com.example.thomas.photonavi;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {

    private final String serverUrl = "http://img.kbs.co.kr/cms/2tv/enter/mecard/about/cast01/";
    private final WeakReference<ImageView> imageViewReference;
    private String imgUrl = "";

    public BitmapWorkerTask( ImageView imageView){
        // WeakReference 를 사용하는 이유는 image 처럼 메모리를 많이 차지하는 객체에 대한
        // 가비지컬렉터를 보장하기 위해서입니다.
        imageViewReference = new WeakReference<ImageView>(imageView);
    }

    @Override
    protected Bitmap doInBackground(String... param){

        Bitmap bitmapImg = null;

        try {

            // Character is converted to 'UTF-8' to prevent broken
            imgUrl = param[0];
            Log.d("Map", "이미지 파일 명" + imgUrl);
            //URL url = new URL(serverUrl + URLEncoder.encode(imgUrl, "utf-8"));
            //URL url = new URL(serverUrl + imgUrl);
            URL url = new URL(imgUrl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.connect();

            InputStream is = conn.getInputStream();
            ByteArrayOutputStream bao =  new ByteArrayOutputStream();

            byte[] bytes = IOUtils.toByteArray(is);

            int bytesRead = 0;
            while((bytesRead = is.read(bytes)) != -1) {
                bao.write(bytes, 0, bytesRead);
            }

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            Bitmap optBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);

            Log.d("Map", "Bitmap 이미지 OptionData Scale " + options.inSampleSize);
            Log.d("Map", "Bitmap 이미지 OptionData OutWidth " + options.outWidth);
            Log.d("Map", "Bitmap 이미지 OptionData OutHeight " + options.outHeight);

            int scale = 1;

            // 실제 이미지 메모리에 생성
            options.inJustDecodeBounds = false;
            options.inSampleSize = scale;
            bitmapImg = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return bitmapImg;

    }

    @Override
    protected void onPostExecute( Bitmap bitmap){
        if( imageViewReference != null && bitmap != null){
            final ImageView imageView = imageViewReference.get();
            if( imageView != null){
                imageView.setImageBitmap( bitmap);
            }
        }
    }
}