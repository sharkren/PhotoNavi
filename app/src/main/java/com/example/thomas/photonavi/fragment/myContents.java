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
public class myContents extends Fragment {

    private View myView;
    public myContents() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_my_contents, container, false);
        RecyclerView recyclerView = (RecyclerView) myView.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity().getApplicationContext());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        List<Recycler_item> items = new ArrayList<>();
        Recycler_item[] item = new Recycler_item[5];

        item[0] = new Recycler_item("http://wc-z.com/wp/wp-content/uploads/2014/12/3151353-strange_vol_1_5_textless-200x300.jpg","닥터 스트레인져");
        item[0].setMemo("닥터 스트레인지는 과거에 신경외과의사였고 현재 마법과 .\n 초자연적인것으로부터 지구를 보호하는 \n소서러슈프림(Sorcerer Supreme)으로 활동하고 있다.");

        item[1] = new Recycler_item("http://cfile30.uf.tistory.com/image/2165A2475465D6540B3378","갤럭투스");
        item[1].setMemo("행성의 파괴자, 행성을 먹는다.\n" +
                "배가 고프면 떨어지고\n" +
                "배가 부르면 4등급까지 올라간다.\n" +
                "행성을 먹고 안먹고 차이");
        item[2] = new Recycler_item("http://wc-z.com/wp/wp-content/uploads/2014/12/i0224962513-300x244.png","데스");
        item[2].setMemo("타노스가 사랑하는\n" +
                "죽음을 관장하는 존재이다.");
        item[3] = new Recycler_item("http://wc-z.com/wp/wp-content/uploads/2014/12/Eternity-300x285.jpg","이터너티");
        item[3].setMemo("주의 5대 본질 중 하나이며 시간의 관리자인 이터니티\n" +
                "\n" +
                "그는 우주의 5대 본질 중에서도 가장 강력한 존재.\n" +
                "\n");
        item[4] = new Recycler_item("http://cfile8.uf.tistory.com/image/254E113B54D8378E17FF62","타노스");
        item[4].setMemo("인피니티 건틀렛(Infinity Gauntlet)을 착용하면\n등급이 올라간다.\n7등급에서 3등급으로");

        for(int i = 0; i < item.length; i++)
            items.add(item[i]);

        recyclerView.setAdapter(new RecyclerAdapter(getActivity().getApplicationContext(),items,R.layout.fragment_my_contents));
        return myView;
    }

    @Override
    public void onDestroy() {

        Log.d("Map", "myContents onDestroy() called");
        Log.d("Map", "myContents onDestroy() totView [" + myView +"]");
        if (myView != null) {
            recycleView(myView.findViewById(R.id.recyclerview));
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
