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

        item[0] = new Recycler_item("__icsFiles/afieldfile/2015/04/07/05_1.jpg","킹죠스");
        item[1] = new Recycler_item("__icsFiles/afieldfile/2015/04/14/01_1.jpg","독꼬리");
        item[2] = new Recycler_item("__icsFiles/afieldfile/2015/04/14/03_1.jpg","만타리");
        item[3] = new Recycler_item("__icsFiles/afieldfile/2015/04/14/01_3.jpg","타나토스");
        item[4] = new Recycler_item("__icsFiles/afieldfile/2015/04/20/1_3.jpg","프린스콩");

        for(int i = 0; i < 5; i++)
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
