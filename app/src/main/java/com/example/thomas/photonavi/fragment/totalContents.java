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

        item[0] = new Recycler_item("__icsFiles/afieldfile/2015/01/30/012_1.jpg","에반");
        item[1] = new Recycler_item("__icsFiles/afieldfile/2015/01/30/022_1.jpg","테로");
        item[2] = new Recycler_item("__icsFiles/afieldfile/2015/01/30/032_1.jpg","무간");
        item[3] = new Recycler_item("__icsFiles/afieldfile/2015/02/17/shuma_1.jpg","슈마");
        item[4] = new Recycler_item("__icsFiles/afieldfile/2015/03/31/4_1.jpg","타이탄");

        for(int i = 0; i < 5; i++)
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
