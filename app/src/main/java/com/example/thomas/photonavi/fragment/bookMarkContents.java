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
public class bookMarkContents extends Fragment {

    private View bookView;

    public bookMarkContents() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View bookView = inflater.inflate(R.layout.fragment_book_mark_contents, container, false);
        RecyclerView recyclerView = (RecyclerView) bookView.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity().getApplicationContext());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        List<Recycler_item> items = new ArrayList<>();
        Recycler_item[] item = new Recycler_item[5];

        item[0] = new Recycler_item("__icsFiles/afieldfile/2015/07/20/1_3.jpg","그리폰");
        item[1] = new Recycler_item("__icsFiles/afieldfile/2015/09/02/1_21.jpg","스핑크스");
        item[2] = new Recycler_item("__icsFiles/afieldfile/2015/10/21/4_3.jpg","요타");
        item[3] = new Recycler_item("__icsFiles/afieldfile/2015/11/12/2_5.jpg","네오");
        item[4] = new Recycler_item("__icsFiles/afieldfile/2016/01/21/2_3.jpg","그리핑크스");

        for(int i = 0; i < 5; i++)
            items.add(item[i]);

        recyclerView.setAdapter(new RecyclerAdapter(getActivity().getApplicationContext(), items, R.layout.fragment_book_mark_contents));

        return bookView;
    }

    @Override
    public void onDestroy() {

        Log.d("Map", "bookmarkContents onDestroy() called");
        Log.d("Map", "bookmarkContents onDestroy() bookView [" + bookView +"]");
        if (bookView != null) {
            recycleView(bookView.findViewById(R.id.recyclerview));
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
