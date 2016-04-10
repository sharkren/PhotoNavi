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

        item[0] = new Recycler_item("http://wc-z.com/wp/wp-content/uploads/2014/12/mS51j0l-300x198.jpg","피닉스 포스");
        item[0].setMemo("불사조다\n" +
                "평상시는 티어3등급정도라고 보면된다.\n" +
                "진그레이와 결합하면 티어2등급이다.");
        item[1] = new Recycler_item("http://cfile28.uf.tistory.com/image/2362F73A5465DAC927EFEC","리빙트리뷰널");
        item[1].setMemo("질서의 유지자이다\n" +
                "우주의 사건을 해결하는 전지전능 캐릭터다.\n" +
                " 목이 없고 얼굴이 세개인 캐릭터.");
        item[2] = new Recycler_item("http://file.instiz.net/data/file/20140329/9/c/d/9cd53bd4dc686a93142a5ee4dc0adbec.jpg","얼티밋 둠");
        item[2].setMemo("그 중에서도 가장 강력한 세계중의 하나인 얼티밋 유니버스. 그 안에서의 둠은 가히 엄청난 파워를 가지고 있습니다.\n" +
                "\n" +
                "그는 갑옷 뿐만 아니라 자신의 모든 장기를 금속으로 교체하여 엄청난 내구도를 자랑하며\n" +
                "\n" +
                "판타스틱4를 어떠한 기술도 쓰지 않고 단지 맨손으로 작살내버릴 정도의 능력을 가지고 있습니다.\n" +
                "\n" +
                "노멀 둠보다 패션센스 또한 증가해서 어깨부분에 모피까지 달려있습니다.\n" +
                "\n");
        item[3] = new Recycler_item("http://cfile2.uf.tistory.com/image/214FFA395465DC63315BBE","원 어보브 올");
        item[3].setMemo("우주의 창조자다.\n" +
                "전지전능하다.\n" +
                "티어 1등급 말이 필요없는 존재");
        item[4] = new Recycler_item("http://cfile21.uf.tistory.com/image/2220604A5465D4CE113551","프랭클린");
        item[4].setMemo("현실조작능력을 가지고 있다.\n" +
                "판타스틱4의 리드리차드와 수잔스톰의 아들이다.\n" +
                "상상하면 이루어지는 능력자");

        for(int i = 0; i < item.length; i++)
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
