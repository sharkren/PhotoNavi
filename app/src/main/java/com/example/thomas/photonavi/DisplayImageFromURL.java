package com.example.thomas.photonavi;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by thomas on 2016-01-19.
 */
public class DisplayImageFromURL extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;
    ProgressDialog pd;

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        //pd = new ProgressDialog(AndroidAdvanceImageLoad.this);
        //pd.setMessage("Loading...");
        //pd.show();
    }
    public DisplayImageFromURL(ImageView bmImage) {
        this.bmImage = bmImage;
    }
    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        return mIcon11;

    }
    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
        //pd.dismiss();
    }

}
