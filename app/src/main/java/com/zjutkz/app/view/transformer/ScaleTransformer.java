package com.zjutkz.app.view.transformer;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by kangzhe on 16/9/10.
 */
public class ScaleTransformer implements ViewPager.PageTransformer {


    @Override
    public void transformPage(View page, float position) {
        if (position < -1) {
            page.setAlpha(0);
        } else if (position <= 0) {
            page.setScaleX(1 + position);
            page.setScaleY(1 + position);
            page.setAlpha(1 + position);

        } else if (position <= 1) {
            page.setScaleX(1 - position);
            page.setScaleY(1 - position);
            page.setAlpha(1 - position);
        } else {
            page.setAlpha(0);
        }
    }
}
