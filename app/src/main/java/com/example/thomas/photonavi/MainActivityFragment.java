package com.example.thomas.photonavi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.skp.Tmap.TMapAddressInfo;
import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapTapi;
import com.skp.Tmap.TMapView;

import java.io.IOException;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    final int REQ_CODE_SELECT_IMAGE=100;
    final int RESULT_OK = -1;
    public ImageView imgPhoto;
    private String exifAttribute;
    private LocationManager manager;
    private GeoDegree geoDegree;

    private Double latitude;
    private Double longitude;
    private TMapView tmapView;
    private TMapTapi tmaptapi;
    private String photaddress = "";
    private String address = "";

    private Boolean bGetMyPos = false;

    public MainActivityFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        tmapView = new TMapView(getActivity());
        tmapView.setSKPMapApiKey("ff33d858-1d53-32c3-b1f5-1ae138b8f399");

        tmaptapi = new TMapTapi(getActivity());
        tmaptapi.setSKPMapAuthentication("ff33d858-1d53-32c3-b1f5-1ae138b8f399");

        imgPhoto = (ImageView) view.findViewById(R.id.imgPhoto);

        imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 파일 Exif 값 팝업으로 표현
                /*
                new AlertDialog.Builder(getActivity())
                        .setTitle("사진상세정보")
                        .setMessage(exifAttribute)
                        .setNeutralButton("닫기",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int sumthin) {
                            }

                        }).show();
                */

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

                new AlertDialog.Builder(getActivity())
                        .setTitle("사진 위치의 주소")
                        .setMessage(photaddress)
                        .setNeutralButton("닫기",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int sumthin) {
                            }

                        }).show();

            }
        });

        Button btnLoadPhoto =  (Button) view.findViewById(R.id.btnLoadPhoto);
        btnLoadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);

            }
        });

        Button btnNavi =  (Button) view.findViewById(R.id.btnNavi);
        btnNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (bGetMyPos == true) {
                    TMapData tmapdata = new TMapData();
                    tmapdata.reverseGeocoding(latitude, longitude, "A03",
                            new TMapData.reverseGeocodingListenerCallback() {
                                @Override
                                public void onReverseGeocoding(TMapAddressInfo addressInfo) {
                                    Log.d("Map","현재 위치의 주소는 " + addressInfo.strFullAddress);
                                    address = addressInfo.strFullAddress;
                                }
                            });

                    new AlertDialog.Builder(getActivity())
                            .setTitle("현재위치 주소")
                            .setMessage(address)
                            .setNeutralButton("닫기",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dlg, int sumthin) {
                                    if (!"".equals(address)) {
                                        getPath();
                                    }
                                }
                            }).show();
                }
                else {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("현재위치 주소")
                            .setMessage("아직 위치가 측위되지 않았습니다")
                            .setNeutralButton("닫기",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dlg, int sumthin) {
                                }

                            }).show();
                }
            }
        });

        Button btnGps =  (Button) view.findViewById(R.id.btnGps);
        btnGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMyLocation();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Toast.makeText(getActivity(),"결과코드 " + resultCode, Toast.LENGTH_LONG).show();

        if (requestCode == REQ_CODE_SELECT_IMAGE && resultCode == RESULT_OK && null != data) {
            final Uri selPhotoUri = data.getData();

            final String[] filePathColumn = {MediaStore.Images.Media.DATA};
            final Cursor imageCursor = getActivity().getContentResolver().query(selPhotoUri, filePathColumn, null, null, null);
            imageCursor.moveToFirst();

            final int columnIndex = imageCursor.getColumnIndex(filePathColumn[0]);
            final String imagePath = imageCursor.getString(columnIndex);
            imageCursor.close();

            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            final Bitmap bitmap = BitmapFactory.decodeFile(imagePath,options);
            imgPhoto.setImageBitmap(bitmap);

            try {
                ExifInterface exif = new ExifInterface(imagePath);
                exifAttribute = getExif(exif);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getExif(ExifInterface exif) {
        String myAttribute = "";
        myAttribute += getTagString(ExifInterface.TAG_DATETIME, exif);
        myAttribute += getTagString(ExifInterface.TAG_FLASH, exif);
        myAttribute += getTagString(ExifInterface.TAG_GPS_LATITUDE, exif);
        myAttribute += getTagString(ExifInterface.TAG_GPS_LATITUDE_REF, exif);
        myAttribute += getTagString(ExifInterface.TAG_GPS_LONGITUDE, exif);
        myAttribute += getTagString(ExifInterface.TAG_GPS_LONGITUDE_REF, exif);
        myAttribute += getTagString(ExifInterface.TAG_IMAGE_LENGTH, exif);
        myAttribute += getTagString(ExifInterface.TAG_IMAGE_WIDTH, exif);
        myAttribute += getTagString(ExifInterface.TAG_MAKE, exif);
        myAttribute += getTagString(ExifInterface.TAG_MODEL, exif);
        myAttribute += getTagString(ExifInterface.TAG_ORIENTATION, exif);
        myAttribute += getTagString(ExifInterface.TAG_WHITE_BALANCE, exif);

        geoDegree = new GeoDegree(exif);
        myAttribute += geoDegree.toString();

        //getDirectionsUrl();
        return myAttribute;
    }

    private String getTagString(String tag, ExifInterface exif) {
        return (tag + " : " + exif.getAttribute(tag) + "\n");
    }

    protected void getPath() {

        Boolean bRouteYn = tmaptapi.invokeRoute(photaddress, geoDegree.getLongitude(), geoDegree.getLatitude());

        if (bRouteYn == true) {
            Log.d("Map", "경로 검색 성공");
        }
        else {
            Log.d("Map", "경로 검색 실패");
        }
    }

    // LocationManager 객체 초기화 , LocationListener 리스너 설정
    private void getMyLocation() {
        if (manager == null) {
            manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        }
        // provider 기지국||GPS 를 통해서 받을건지 알려주는 Stirng 변수
        // minTime 최소한 얼마만의 시간이 흐른후 위치정보를 받을건지 시간간격을 설정 설정하는 변수
        // minDistance 얼마만의 거리가 떨어지면 위치정보를 받을건지 설정하는 변수
        // manager.requestLocationUpdates(provider, minTime, minDistance, listener);

        // 10초
        long minTime = 1000;

        // 거리는 0으로 설정
        // 그래서 시간과 거리 변수만 보면 움직이지않고 10초뒤에 다시 위치정보를 받는다
        float minDistance = 1;

        MyLocationListener listener = new MyLocationListener();
        try {
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, listener);

        }catch (SecurityException se) {
            se.getMessage();
        }

        //appendText("내 위치를 요청 했습니다.");
    }

    class MyLocationListener implements LocationListener {

        // 위치정보는 아래 메서드를 통해서 전달된다.
        @Override
        public void onLocationChanged(Location location) {
            // appendText("onLocationChanged()가 호출되었습니다");

            latitude = location.getLatitude();
            longitude = location.getLongitude();

            bGetMyPos = true;

            Log.d("Map","현재위치정보");
            Log.d("Map","현재위치정보 longitude " + longitude);
            Log.d("Map", "현재위치정보 latitude " + latitude);
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

    /*
    private String getDirectionsUrl(){

        // Origin of route
        String str_origin = "origin="+latitude+","+longitude;

        // Destination of route
        String str_dest = "destination="+String.valueOf(geoDegree.getLatitude())+","+String.valueOf(geoDegree.getLongitude());

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        DownloadTask downloadTask = new DownloadTask();

        // Start downloading json data from Google Directions API
        downloadTask.execute(url);

        Log.d("Map","결과 url " + url);

        return url;
    }

    private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Map", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("Map", "경로측정 데이터" + data);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            //parserTask.execute(result);
        }
    } */

    /*private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(2);
                lineOptions.color(Color.RED);
            }

            // Drawing polyline in the Google Map for the i-th route
            map.addPolyline(lineOptions);
        }
    }*/

}