package com.example.thomas.photonavi;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by thomas on 2016-01-26.
 */
public class HeightWrappingViewPager extends ViewPager {


    public HeightWrappingViewPager(Context context) {
        super(context);
    }

    public HeightWrappingViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);

        boolean wrapHeight = MeasureSpec.getMode(heightSpec) == MeasureSpec.AT_MOST;

        if (wrapHeight) {

            int width = getMeasuredWidth();
            int height = getMeasuredHeight();

            widthSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);

            if (getChildCount() > 0) {
                View firstChild = getChildAt(0);

                firstChild.measure(widthSpec,MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST));
                height = firstChild.getMeasuredHeight();
            }

            heightSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
            super.onMeasure(widthSpec, heightSpec);

        }
    }

}
