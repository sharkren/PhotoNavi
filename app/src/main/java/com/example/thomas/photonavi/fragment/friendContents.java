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
public class friendContents extends Fragment {

    private View friView;

    public friendContents() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View friView = inflater.inflate(R.layout.fragment_freind_contents, container, false);
        RecyclerView recyclerView = (RecyclerView) friView.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity().getApplicationContext());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        List<Recycler_item> items = new ArrayList<>();
        Recycler_item[] item = new Recycler_item[5];

        item[0] = new Recycler_item(R.drawable.photo11,"#11");
        item[1] = new Recycler_item(R.drawable.photo12,"#12");
        item[2] = new Recycler_item(R.drawable.photo13,"#13");
        item[3] = new Recycler_item(R.drawable.photo14,"#14");
        item[4] = new Recycler_item(R.drawable.photo15,"#15");

        for(int i = 0; i < 5; i++)
            items.add(item[i]);

        recyclerView.setAdapter(new RecyclerAdapter(getActivity().getApplicationContext(), items, R.layout.fragment_freind_contents));

        return friView;
    }

    @Override
    public void onDestroy() {

        Log.d("Map", "friendContents onDestroy() called");
        Log.d("Map", "friendContents onDestroy() friView [" + friView +"]");
        if (friView != null) {
            recycleView(friView.findViewById(R.id.recyclerview));
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
