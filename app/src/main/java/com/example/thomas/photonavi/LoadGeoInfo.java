package com.example.thomas.photonavi;

import android.media.ExifInterface;
import android.net.Uri;
import android.util.Log;

import com.skp.Tmap.TMapAddressInfo;
import com.skp.Tmap.TMapData;

import java.io.IOException;

/**
 * Created by thomas on 2016-01-19.
 */
public class LoadGeoInfo {

    ExifInterface exif;
    private final String resPath = "android.resource://com.example.thomas.photonavi/drawable/";
    private String imgPath = "";
    private String exifAttribute;
    private GeoDegree geoDegree;
    public String photaddress;

    // Class 생성자
    LoadGeoInfo (int resId) {
        imgPath = Uri.parse(resPath+resId).toString();
        Log.d("Map", "리소스ID 이미지경로 " + imgPath);
        initExif();
    }


    // 확장정보 클래스 초기화
    private void initExif() {
        try {
            ExifInterface exif = new ExifInterface(imgPath);
            exifAttribute = getExif(exif);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String getExif(ExifInterface exif) {
        String myAttribute = "";
        myAttribute += getTagString(ExifInterface.TAG_GPS_LATITUDE, exif);
        myAttribute += getTagString(ExifInterface.TAG_GPS_LATITUDE_REF, exif);
        myAttribute += getTagString(ExifInterface.TAG_GPS_LONGITUDE, exif);
        myAttribute += getTagString(ExifInterface.TAG_GPS_LONGITUDE_REF, exif);
        geoDegree = new GeoDegree(exif);
        myAttribute += geoDegree.toString();

        return myAttribute;
    }

    private String getTagString(String tag, ExifInterface exif) {
        return (tag + " : " + exif.getAttribute(tag) + "\n");
    }

    public String getAddress () {
        TMapData tmapdata = new TMapData();
        if (geoDegree == null || geoDegree.getLatitude() == null ){
            photaddress = "사진에 위치정보가 없습니다";
        }
        else {
            tmapdata.reverseGeocoding(new Double(geoDegree.getLatitude()), new Double(geoDegree.getLongitude()), "A03",
                    new TMapData.reverseGeocodingListenerCallback() {
                        @Override
                        public void onReverseGeocoding(TMapAddressInfo addressInfo) {
                            Log.d("Map", "사진 위치의 주소는 " + addressInfo.strFullAddress);
                            photaddress = addressInfo.strFullAddress;
                        }
                    });
        }
        return photaddress;

    }
}
