package com.example.thomas.photonavi;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.thomas.photonavi.fragment.bookMarkContents;
import com.example.thomas.photonavi.fragment.friendContents;
import com.example.thomas.photonavi.fragment.myContents;
import com.example.thomas.photonavi.fragment.totalContents;

/**
 * Created by thomas on 2016-01-18.
 */
public class PagerAdapter extends FragmentStatePagerAdapter{
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        // Factory methods design fattern 적용 검토
        // http://www.androiddesignpatterns.com/2012/05/using-newinstance-to-instantiate.html
        //
        switch (position) {
            case 0:
                totalContents totCont = new totalContents();
                return totCont;
            case 1:
                myContents myCont = new myContents();
                return myCont;
            case 2:
                friendContents friCont = new friendContents();
                return friCont;
            case 3:
                bookMarkContents bookCont = new bookMarkContents();
                return bookCont;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}
