package com.example.thomas.photonavi;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.skp.Tmap.TMapTapi;

import com.kakao.kakaonavi.KakaoNaviParams;
import com.kakao.kakaonavi.KakaoNaviService;
import com.kakao.kakaonavi.Location;
import com.kakao.kakaonavi.NaviOptions;
import com.kakao.kakaonavi.options.CoordType;

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

        BitmapWorkerTask task = new BitmapWorkerTask(holder.contentImage);
        task.execute(item.getImageUrl());

        //holder.contentImage.setImageBitmap(new ImageRoader().getBitmapImg("androidfigure.jpg"));
        holder.title.setText(item.getTitle());
        holder.location.setText(item.getAddress());
        holder.etMemo.setText(item.getMemo());

        /////////////////////////////////////////////////////////////////////////
        // 이미지에서 위치정보 읽어오기
        // 향후 서버에서 수신하도록 수정
        /////////////////////////////////////////////////////////////////////////
        //LoadGeoInfo loadGeoInfo = new LoadGeoInfo(items.get(position).getImageUrl());
        //if (loadGeoInfo != null) {
        //    holder.location.setText(loadGeoInfo.getAddress());
        //}

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
                openNavigation(item);
            }
        });

        holder.tvNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNavigation(item);
            }
        });
    }

    /**
     * Navigation 호출
     */
    protected void openNavigation(final Recycler_item pItem) {

        // 처음 로그인 했다면 새로운 친구가 찾아 추가할지 팝업 호출
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

        builder.setTitle("PORK 알림")
                .setMessage("네비게이션을 선택해주세요")
                .setCancelable(false)
                .setPositiveButton("Tmap", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        openTmap(pItem);
                        dialogInterface.cancel();
                    }
                })
                .setNeutralButton("Kakao Navi", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        openKakao(pItem);
                        dialogInterface.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    private void openTmap(Recycler_item pItem) {

        TMapTapi tmaptapi = new TMapTapi(view.getContext());
        tmaptapi.setSKPMapAuthentication("ff33d858-1d53-32c3-b1f5-1ae138b8f399");


        if (pItem.getLatitude() == null || pItem.getLongitude() == null) {
            Log.d("Map", "위도 / 경도 정보가 없음.");
        }
        else {

            Boolean bRouteYn = tmaptapi.invokeRoute(pItem.getAddress(),
                    new Float(pItem.getLongitude().toString()), new Float(pItem.getLatitude().toString()));

            if (bRouteYn == true) {
                Log.d("Map", "경로 검색 성공");
            } else {
                Log.d("Map", "경로 검색 실패");
            }
        }
    }

    private void openKakao(Recycler_item pItem) {
        Location destination = Location.newBuilder(pItem.getAddress(), Double.valueOf(pItem.getLongitude().toString()),
                Double.valueOf(pItem.getLatitude().toString())).build();

        KakaoNaviParams.Builder builder = KakaoNaviParams.newBuilder(destination)
                .setNaviOptions(NaviOptions.newBuilder().setCoordType(CoordType.WGS84).build());

        KakaoNaviService.shareDestination(view.getContext(), builder.build());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView contentImage;
        TextView title;
        TextView location;
        CardView cardview;
        ImageButton imgBtnNavi;
        TextView tvNavi;
        EditText etMemo;

        public ViewHolder(View itemView) {
            super(itemView);
            contentImage = (ImageView)itemView.findViewById(R.id.content_image);
            title         = (TextView)itemView.findViewById(R.id.title);
            cardview      = (CardView)itemView.findViewById(R.id.cardview);
            location      = (TextView)itemView.findViewById(R.id.location);
            imgBtnNavi    = (ImageButton)itemView.findViewById(R.id.imgBtnNavi);
            tvNavi         = (TextView)itemView.findViewById(R.id.tvNavi);
            etMemo         = (EditText)itemView.findViewById(R.id.etMemo);

        }
    }

}
