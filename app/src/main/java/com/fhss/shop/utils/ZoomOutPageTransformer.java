package com.fhss.shop.utils;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * 设置字体类型工具类
 *
 * @author zhangrq
 */
public class ZoomOutPageTransformer implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.85f;

    private static float defaultScale = 0.85f;

    public void transformPage(View view, float position) {

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setScaleX(defaultScale);
            view.setScaleY(defaultScale);
        } else if (position <= 1) { // [-1,1]

            if (position < 0) {
                // 当前页:从 0.0 ~ -1
                //scaleFactor 变化为：1到最小值
                float scaleFactor = (1 + position) * (1 - MIN_SCALE) + MIN_SCALE;
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
            } else {
                // 前一页、后一页:从1 ~ 0.0
                //scaleFactor 变化为：最小值到1
                float scaleFactor = (1 - position) * (1 - MIN_SCALE) + MIN_SCALE;
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
            }

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setScaleX(defaultScale);
            view.setScaleY(defaultScale);
        }
    }
}
