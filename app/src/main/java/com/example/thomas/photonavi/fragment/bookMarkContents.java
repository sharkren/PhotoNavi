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
        Recycler_item[] item = new Recycler_item[6];

        /*item[0] = new Recycler_item("http://img.kbs.co.kr/cms/2tv/enter/mecard/about/cast01/__icsFiles/afieldfile/2015/07/20/1_3.jpg","그리폰");
        item[1] = new Recycler_item("http://img.kbs.co.kr/cms/2tv/enter/mecard/about/cast01/__icsFiles/afieldfile/2015/09/02/1_21.jpg","스핑크스");
        item[2] = new Recycler_item("http://img.kbs.co.kr/cms/2tv/enter/mecard/about/cast01/__icsFiles/afieldfile/2015/10/21/4_3.jpg","요타");
        item[3] = new Recycler_item("http://img.kbs.co.kr/cms/2tv/enter/mecard/about/cast01/__icsFiles/afieldfile/2015/11/12/2_5.jpg","네오");
        item[4] = new Recycler_item("http://img.kbs.co.kr/cms/2tv/enter/mecard/about/cast01/__icsFiles/afieldfile/2016/01/21/2_3.jpg","그리핑크스");*/
        /*
        나중에 DB 읽어 반복 처리 하도록 변경
         */
        item[0] = new Recycler_item("https://scontent.cdninstagram.com/hphotos-xat1/t51.2885-15/s640x640/sh0.08/e35/1172066_982590081813372_2057536268_n.jpg","해성횟집");
        item[0].setAddress("강원도 강릉시 금학동 6");
        item[0].setLatitude(Double.valueOf("37.754245"));
        item[0].setLongitude(Double.valueOf("128.898323"));

        item[1] = new Recycler_item("http://olivem.co.kr/wp-content/uploads/2015/06/gangneung10.jpg","엄지네포장마차");
        item[1].setAddress("강원도 강릉시 옥천동 287-14");
        item[1].setLatitude(Double.valueOf("37.759293"));
        item[1].setLongitude(Double.valueOf("128.900576"));

        item[2] = new Recycler_item("http://img.seeon.kr/place/1352186283806893.JPG","장안횟집");
        item[2].setAddress("강릉시 사천면 사천진리 86-64");
        item[2].setLatitude(Double.valueOf("37.837627"));
        item[2].setLongitude(Double.valueOf("128.875313"));

        item[3] = new Recycler_item("http://img.seeon.kr/place/KT1410_1000235.jpg","금학칼국수");
        item[3].setAddress("강원도 강릉시 임당동 122-2");
        item[3].setLatitude(Double.valueOf("37.754612"));
        item[3].setLongitude(Double.valueOf("128.896299"));

        item[4] = new Recycler_item("https://pbs.twimg.com/media/B5tELNUIQAA8eeU.jpg","동화가든");
        item[4].setAddress("강원도 강릉시 초당동 352");
        item[4].setLatitude(Double.valueOf("37.791093"));
        item[4].setLongitude(Double.valueOf("128.914695"));

        item[5] = new Recycler_item("http://static-a-timetree.zumst.com/cache/images/530x/?http%3A%2F%2Ftimetree.zumst.com%2F2014%2F01%2F27%2F16%2F031fbca8cdf74886bd1f68a844c22600.jpg","만석닭강정");
        item[5].setAddress("강원도 속초시 중앙동 471-9");
        item[5].setLatitude(Double.valueOf("38.204689"));
        item[5].setLongitude(Double.valueOf("128.590197"));

        for(int i = 0; i < item.length; i++)
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
