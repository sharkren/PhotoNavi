package com.example.thomas.photonavi.fragment;


import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thomas.photonavi.R;
import com.example.thomas.photonavi.RecyclerAdapter;
import com.example.thomas.photonavi.Recycler_item;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class totalContents extends Fragment {

    private View totView;

    public totalContents() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        totView = inflater.inflate(R.layout.fragment_total_contents, container, false);

        RecyclerView recyclerView = (RecyclerView) totView.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity().getApplicationContext());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        List<Recycler_item> items = new ArrayList<>();
        Recycler_item[] item = new Recycler_item[5];

        item[0] = new Recycler_item("http://cfile6.uf.tistory.com/image/275A9B505465CFAE367FD9","토르");
        item[0].setMemo("힘은 헐크하고 맞먹는 힘을 가지고 있지만\n" +
                "머리가 나빠서 자신의 파워를 제대로\n" +
                "쓰는 일이 거의 없다.");
        item[1] = new Recycler_item("http://cfile4.uf.tistory.com/image/2658D54B5465D18703A48B","노템 타노스");
        item[1].setMemo("어벤저스2에도 나온다.\n" +
                "노템시에는 8등급이지만\n" +
                "이후 나오는 풀템에서는 등급이 상승한다.\n" +
                "템빨!!");
        item[2] = new Recycler_item("http://cfile30.uf.tistory.com/image/2347B74B5465D188176058","메피스토텔레스");
        item[2].setMemo("지옥의 군주다\n" +
                "지옥영역에서는 더 8등급을 넘어선 더\n" +
                "강력한 힘을 발휘한다.");
        item[3] = new Recycler_item("http://cfile2.uf.tistory.com/image/22557B4B5465D188088A7A","실버서퍼");
        item[3].setMemo("판타스틱4에 나온 실버서퍼\n" +
                "영하에서는 좀 평가 절하되서 나온거 같다.\n" +
                "블랙홀에서도 생존할 수 있는 존재인데.");
        item[4] = new Recycler_item("http://cfile30.uf.tistory.com/image/2371B2505465D3942BB7F4","고스트라이더");
        item[4].setMemo("메피스토에 의해 힘을 얻은 고스트 라이더\n" +
                "인간의 인격을 누르고 풀파워를 내면\n" +
                "블랙하트정도는 그냥 보내버린답니다.");

        for(int i = 0; i < item.length; i++)
            items.add(item[i]);

        recyclerView.setAdapter(new RecyclerAdapter(getActivity().getApplicationContext(),items,R.layout.fragment_total_contents));

        return totView;
    }


    //Fragment에서 onDestroy()를 호출해봐야 ViewPager를 포함하고있는 호스트 액티비티가 종료되야 반환
    @Override
    public void onDestroy() {

        Log.d("Map", "totalContents onDestroy() called");
        Log.d("Map", "totalContents onDestroy() totView [" + totView +"]");
        if (totView != null) {
            recycleView(totView.findViewById(R.id.recyclerview));
        }
        super.onDestroy();
    }

    private void recycleView(View view) {
        if(view != null) {
            Drawable bg = view.getBackground();

            if(bg != null) {
                bg.setCallback(null);
                ((BitmapDrawable)bg).getBitmap().recycle();
                view.setBackground(null);
            }
        }
    }

}
