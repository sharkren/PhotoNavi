package com.example.thomas.photonavi;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.skp.Tmap.TMapTapi;

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
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_cardview,null);
        return new ViewHolder(view);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Recycler_item item = items.get(position);
        Drawable drawable = context.getResources().getDrawable(item.getImage());

        holder.image.setBackground(drawable);

        //new DisplayImageFromURL((ImageView) view.findViewById(R.id.image))
        //        .execute("http://www.tmonews.com/wp-content/uploads/2012/10/androidfigure.jpg");



        holder.title.setText(item.getTitle());
        holder.location.setText("대한민국 어딘가..(향후 상세주소로 변경)");
        /////////////////////////////////////////////////////////////////////////
        // 이미지에서 위치정보 읽어오기
        // 향후 서버에서 수신하도록 수정
        /////////////////////////////////////////////////////////////////////////
        LoadGeoInfo loadGeoInfo = new LoadGeoInfo(items.get(position).getImage());
        if (loadGeoInfo != null) {
            holder.location.setText(loadGeoInfo.getAddress());
        }

        // 이미지 클릭 이벤트
        holder.image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(context, item.getTitle(), Toast.LENGTH_SHORT).show();
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

    protected void getPath() {

        TMapTapi tmaptapi = new TMapTapi(view.getContext());
        tmaptapi.setSKPMapAuthentication("ff33d858-1d53-32c3-b1f5-1ae138b8f399");

        Boolean bRouteYn = tmaptapi.invokeRoute("선릉역", new Float("37.1234"), new Float("127.1234"));

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
        ImageView image;
        TextView title;
        TextView location;
        CardView cardview;
        ImageButton imgBtnNavi;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView)itemView.findViewById(R.id.image);
            title = (TextView)itemView.findViewById(R.id.title);
            cardview = (CardView)itemView.findViewById(R.id.cardview);
            location = (TextView)itemView.findViewById(R.id.location);
            imgBtnNavi = (ImageButton)itemView.findViewById(R.id.imgBtnNavi);
        }
    }

}
