package com.example.thomas.photonavi;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.skp.Tmap.TMapTapi;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by thomas on 2016-01-19.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    Context context;
    List<Recycler_item> items;
    int item_layout;
    View view;

    public RecyclerAdapter(Context context, List<Recycler_item> items, int item_layout) {
        this.context = context;
        this.items = items;
        this.item_layout = item_layout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        /*
        public android.view.View inflate(int resource, android.view.ViewGroup root, boolean attachToRoot)
        ViewGroup root를 넘겨줘야 width height가 layout에 정의한 대로 보여진다.
        그렇지 않으면  match_content로 자동 설정
         */
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_cardview, parent, false);
        return new ViewHolder(view);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Recycler_item item = items.get(position);

        holder.contentImage.setImageBitmap(new ImageRoader().getBitmapImg("androidfigure.jpg"));
        holder.title.setText(item.getTitle());
        holder.location.setText("대한민국 어딘가..(향후 상세주소로 변경)");

        /////////////////////////////////////////////////////////////////////////
        // 이미지에서 위치정보 읽어오기
        // 향후 서버에서 수신하도록 수정
        /////////////////////////////////////////////////////////////////////////
        LoadGeoInfo loadGeoInfo = new LoadGeoInfo(items.get(position).getImageResId());
        if (loadGeoInfo != null) {
            holder.location.setText(loadGeoInfo.getAddress());
        }

        // 이미지 클릭 이벤트
        holder.contentImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            }
        });

        // 네비게이션 이미지버튼 클릭 이벤트
        holder.imgBtnNavi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                getPath();
            }
        });
    }

    /**
     * 안드로이드 버전 3.0 이상부터는 인터넷 연결은 쓰레드나 핸들러에서 처리하도록 정책이 바뀌었다.
     * 그래서 UI 쓰레스에서 인터넷 연결을 시도하면(HttpURLConnection과 같은 것으로) 실행 타임에서
     * 에러가 발생한다. 그런데 위와 같은 코드를 인터넷 연결을 시도하는 코드 앞에 표시해 두면
     * 안드로이드 버전 3.0 이상에서도 정상적으로 잘 실행이 된다.
     * 그런데 위 코드를 인터넷을 연결하는 곳 앞에 하지 않고 onCreate()에 다음과 같이 해도 가능하다.
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public class ThreadPolicy {

        // For smooth networking
        public ThreadPolicy() {

            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);
        }
    }


    public class ImageRoader {

        private final String serverUrl = "http://www.tmonews.com/wp-content/uploads/2012/10/";

        public ImageRoader() {

            new ThreadPolicy();
        }

        public Bitmap getBitmapImg(String imgStr) {

            Bitmap bitmapImg = null;

            try {
                URL url = new URL(serverUrl +
                        URLEncoder.encode(imgStr, "utf-8"));
                // Character is converted to 'UTF-8' to prevent broken

                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                conn.setDoInput(true);
                conn.connect();

                InputStream is = conn.getInputStream();
                bitmapImg = BitmapFactory.decodeStream(is);

            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            return bitmapImg;
        }
    }

    /**
     * Tmap Navigation 호출
     */
    protected void getPath() {

        TMapTapi tmaptapi = new TMapTapi(view.getContext());
        tmaptapi.setSKPMapAuthentication("ff33d858-1d53-32c3-b1f5-1ae138b8f399");

        Boolean bRouteYn = tmaptapi.invokeRoute("서울특별시 강남구 선릉로",
                new Float("127.04948557"), new Float("37.50365057"));

        if (bRouteYn == true) {
            Log.d("Map", "경로 검색 성공");
        }
        else {
            Log.d("Map", "경로 검색 실패");
        }
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView contentImage;
        TextView title;
        TextView location;
        CardView cardview;
        ImageButton imgBtnNavi;

        public ViewHolder(View itemView) {
            super(itemView);
            contentImage = (ImageView)itemView.findViewById(R.id.content_image);
            title = (TextView)itemView.findViewById(R.id.title);
            cardview = (CardView)itemView.findViewById(R.id.cardview);
            location = (TextView)itemView.findViewById(R.id.location);
            imgBtnNavi = (ImageButton)itemView.findViewById(R.id.imgBtnNavi);
        }
    }

}
