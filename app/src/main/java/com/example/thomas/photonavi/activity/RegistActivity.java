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

import com.example.thomas.photonavi.GeoDegree;
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
    private static final int MAX_IMAGE_SIZE = 200;

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
            }
        });

        imgPhoto =  (ImageView)findViewById(R.id.imgRegistPhoto);
        imgPhoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(RegistActivity.this);

                builder.setMessage("사진을 선택하세요").setCancelable(false)
                        .setPositiveButton("갤러리", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);

                            }
                        })
                        .setNegativeButton("촬영", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(cameraIntent,REQ_CODE_SELECT_IMAGE);
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });

        getSupportActionBar().setDisplayOptions(getSupportActionBar().DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(mCustomView);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFFF9800));

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
        }

        /*
        2016-01-22
        Question : 왜 Drawable 객체의 Callback 함수 호출시 오류가 발생하는가?
        Answer :
        d.setCallback(null);
         */
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("Map", "결과코드 " + resultCode);
        Log.d("Map", "요청코드 " + requestCode);
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
                GeoDegree geoDegree = new GeoDegree(exif);

                Log.d("Map", ">>>>>>>>>>>>>>>> geoDegree " + geoDegree.isValid());

                if (geoDegree.isValid()) {
                    // Tmap API로 좌표(Latitude, Longitude)로 주소 얻어오기
                    TMapData tmapdata = new TMapData();

                    /*
                    <주소 타입>
                    A00 - 선택한 좌표계에 해당하는 행정동,법정동 주소 입니다.
                    A01 - 선택한 좌표게에 해당하는 행정동 입니다.
                        - 예) 망원2동, 일산1동
                    A02 - 선택한 좌표계에 해당하는 법정동 주소입니다.
                        - 예) 방화동, 목동
                    A03 - 선택한 좌표계에 해당하는 새주소 길입니다.
                    A04 - 선택한 좌표계에 해당하는 건물 번호입니다.
                        - 예) 양천로 14길 95-11
                    A10 선택한 좌표계에 해당하는 행정동+법정동+도로명 주소입니다.
                     */
                    tmapdata.reverseGeocoding(Double.valueOf(geoDegree.getLatitude().toString()),
                            Double.valueOf(geoDegree.getLongitude().toString()), "A03",
                            new TMapData.reverseGeocodingListenerCallback() {
                                @Override
                                public void onReverseGeocoding(TMapAddressInfo addressInfo) {
                                    address = addressInfo.strFullAddress;
                                    /*
                                    아래와 같이 Thread를 사용하지 않고 직접
                                    EditText tempLoc = (EditText) findViewById(R.id.etLocation);
                                    tempLoc.setText(address);
                                    위와 같은 코드를 실행하게 된다면 해당코드는 다른스레드에서 UI에
                                    접근하기때문에 CalledFromWrongThreadException 예외가 발생

                                    - CalledFromWrongThreadException 발생 원인 -
                                    왜 다른스레드에서 UI를 변경하려고 하면 해당 예외가 발생하는지
                                    알아보자면 UI변경이 있게되면 안드로이드 뷰에서는 invalidate를
                                    호출하게 되는데 여기서 보게 되면 invalidate()에서는 ViewParent의
                                    invalidateChild()를 호출
                                    invalidate()에서는 checkThread()를 호출합니다. checkThread()에서는
                                    위와 같이 현재 실행중인 스레드가 ViewRoot가 가지고 있는 mThread와
                                    참조가 같은지 비교하고 아니라면 CalledFromWrongThreadException 예외 발생
                                    http://csjung.tistory.com/153
                                     */
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    EditText tempLoc = (EditText) findViewById(R.id.etLocation);
                                                    tempLoc.setText(address);
                                                }
                                            });
                                        }
                                    }).start();

                                }
                            });

                }

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
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        runOnUiThread(new Runnable(){
                                            @Override
                                            public void run() {
                                                EditText tempLoc = (EditText) findViewById(R.id.etLocation);
                                                tempLoc.setText(address);
                                            }
                                        });
                                    }
                                }).start();
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
