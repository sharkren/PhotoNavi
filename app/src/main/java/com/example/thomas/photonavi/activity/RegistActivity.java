package com.example.thomas.photonavi.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.thomas.photonavi.R;
import com.skp.Tmap.TMapAddressInfo;
import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapTapi;
import com.skp.Tmap.TMapView;

import java.io.IOException;

public class RegistActivity extends AppCompatActivity {

    private String address = "";
    final int REQ_CODE_SELECT_IMAGE=100;
    final int RESULT_OK = -1;
    public ImageView imgPhoto;
    private static final int MAX_IMAGE_SIZE = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.regist_actionbar, null);

        // Action Bar 뒤로가기 버튼
        ImageButton imgBtnBack = (ImageButton) mCustomView.findViewById(R.id.imgBtnBack);
        imgBtnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        final EditText etLocation = (EditText)findViewById(R.id.etLocation);
        final GPSLocationListener listener = new GPSLocationListener();

        TMapView tmapView = new TMapView(this);
        tmapView.setSKPMapApiKey("ff33d858-1d53-32c3-b1f5-1ae138b8f399");

        TMapTapi tmaptapi = new TMapTapi(this);
        tmaptapi.setSKPMapAuthentication("ff33d858-1d53-32c3-b1f5-1ae138b8f399");

        ImageButton imgBtnLocation = (ImageButton)findViewById(R.id.imgBtnLocation);
        imgBtnLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                getLocation(listener);
                etLocation.setText(address);
            }
        });

        imgPhoto =  (ImageView)findViewById(R.id.imgRegistPhoto);
        imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);

            }
        });

        getSupportActionBar().setDisplayOptions(getSupportActionBar().DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(mCustomView);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFCDDC39));

    }

    @Override
    protected void onDestroy() {
        Log.d("Map", "onDestroy");
        recycleBitmap(imgPhoto);

        super.onDestroy();
    }

    private static void recycleBitmap(ImageView iv) {
        Drawable d = iv.getDrawable();
        if (d instanceof BitmapDrawable) {
            Bitmap b = ((BitmapDrawable)d).getBitmap();
            b.recycle();
        } // 현재로서는 BitmapDrawable 이외의 drawable 들에 대한 직접적인 메모리 해제는 불가능하다.

        d.setCallback(null);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Gallary에서 이미지를 선택하면 요청코드와 결과코드로 정상 여부 판단
        if (requestCode == REQ_CODE_SELECT_IMAGE && resultCode == RESULT_OK && null != data) {
            final Uri selPhotoUri = data.getData();

            // 선택이미지 파일 경로 구하기
            final String[] filePathColumn = {MediaStore.Images.Media.DATA};
            final Cursor imageCursor = getContentResolver().query(selPhotoUri, filePathColumn, null, null, null);
            imageCursor.moveToFirst();

            final int columnIndex = imageCursor.getColumnIndex(filePathColumn[0]);
            final String imagePath = imageCursor.getString(columnIndex);
            imageCursor.close();


            // 비트맵 이미지 처리
            // 이미지를 메모리에 생성하지 않고 이미지의 기본정보(width, height) 정보를 얻어와
            // 스케일을 재조정하여 이미지 생성 OOM (Out Of Memory) 방지
            BitmapFactory.Options options = new BitmapFactory.Options();

            // inJustDecodeBounds 옵션은 true일 경우에는 비트맵을 메모리에 생성하지 않고
            // 이미지의 정보만 제공한다.
            options.inJustDecodeBounds = true;
            Bitmap optBitmap = BitmapFactory.decodeFile(imagePath, options);

            Log.d("Map", "Bitmap 이미지 OptionData Scale " + options.inSampleSize);
            Log.d("Map", "Bitmap 이미지 OptionData OutWidth " + options.outWidth);
            Log.d("Map", "Bitmap 이미지 OptionData OutHeight " + options.outHeight);

            // 파일 확장정보(방향값) 를 얻기위한 객체생성
            ExifInterface exif = null;
            try {
                exif = new ExifInterface(imagePath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (exif != null) {
                String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
                int orientation = orientString != null ? Integer.parseInt(orientString) : ExifInterface.ORIENTATION_NORMAL;
                int rotationAngle = 0;

                if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90;
                if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180;
                if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270;

                int scale = 1;

                // 이미지의 크기를 이용해서 ImageView 보다 클경우 Scale을 재조정
                if (options.outHeight > MAX_IMAGE_SIZE || options.outWidth > MAX_IMAGE_SIZE) {
                    scale = (int)Math.pow(2, (int)Math.round(Math.log(MAX_IMAGE_SIZE/(double)Math.max(options.outHeight, options.outWidth)) / Math.log(0.5)));
                }

                Matrix matrix = new Matrix();
                matrix.setRotate(rotationAngle, (float) options.outWidth / 2, (float) options.outHeight / 2);

                // 실제 이미지 메모리에 생성
                options.inJustDecodeBounds = false;
                options.inSampleSize = scale;
                Bitmap allocBitmap = BitmapFactory.decodeFile(imagePath, options);
                Bitmap rotatedBitmap = Bitmap.createBitmap(allocBitmap, 0, 0, options.outWidth, options.outHeight, matrix, true);

                // 이미지뷰에 방향정보 수정된 이미지 할당
                imgPhoto.setImageBitmap(rotatedBitmap);
            }


        }
    }

    private void getLocation(GPSLocationListener listener) {

        // 내위치 찾기 버튼
        // GPS 설정 여부 확인을 위해 로케이션관리자 생성
        final LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        // GPS가 비활성일 경우
        if (!lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("GPS설정이 꺼져있습니다.\n" +
                    "활성화 하시겠습니까?\n" +
                    "활성화 후 버튼을 한번더 클릭해주세요").setCancelable(false)
                    .setPositiveButton("GPS켜기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent gpsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(gpsIntent);
                        }
                    })
                    .setNegativeButton("닫기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        else {
            // GPS가 활성일 경우 현재 위치로 주소 얻어오기

            // provider 기지국||GPS 를 통해서 받을건지 알려주는 Stirng 변수
            // minTime 최소한 얼마만의 시간이 흐른후 위치정보를 받을건지 시간간격을 설정 설정하는 변수
            // minDistance 얼마만의 거리가 떨어지면 위치정보를 받을건지 설정하는 변수
            // manager.requestLocationUpdates(provider, minTime, minDistance, listener);
            // 10초
            long minTime = 1000;

            // 거리는 0으로 설정
            // 그래서 시간과 거리 변수만 보면 움직이지않고 10초뒤에 다시 위치정보를 받는다
            float minDistance = 1;

            if (listener.getPosYn() == false) {
                try {

                    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, listener);
                } catch (SecurityException se) {
                    se.getMessage();
                }
            }
            else {

                // Tmap API로 좌표(Latitude, Longitude)로 주소 얻어오기
                TMapData tmapdata = new TMapData();
                tmapdata.reverseGeocoding(listener.getLatitude(), listener.getLongitude(), "A03",
                        new TMapData.reverseGeocodingListenerCallback() {
                            @Override
                            public void onReverseGeocoding(TMapAddressInfo addressInfo) {
                                address = addressInfo.strFullAddress;
                            }
                        });
            }

        }
    }

    class GPSLocationListener implements LocationListener {

        private Double latitude;
        private Double longitude;
        private Boolean posYn = false;

        // 위치정보는 아래 메서드를 통해서 전달된다.
        @Override
        public void onLocationChanged(Location location) {
            // appendText("onLocationChanged()가 호출되었습니다");

            latitude = location.getLatitude();
            longitude = location.getLongitude();
            posYn = true;

            Log.d("Map", "현재위치정보");
            Log.d("Map","현재위치정보 longitude " + longitude);
            Log.d("Map", "현재위치정보 latitude " + latitude);
        }

        public Double getLatitude() {
            return this.latitude;
        }

        public Double getLongitude() {
            return this.longitude;
        }

        public Boolean getPosYn() {
            return posYn;
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

    }

}
