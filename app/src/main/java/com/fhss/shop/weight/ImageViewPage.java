package com.fhss.shop.weight;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * 描述:
 *
 * @author zhangrq
 *         2017/5/5 17:10
 */
public class ImageViewPage extends ViewPager {
    public ImageViewPage(Context context) {
        super(context);
    }

    public ImageViewPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        try {
            return super.onInterceptTouchEvent(event);
        } catch (IllegalArgumentException e) {
            Log.e("ImageOriginPager-error", "IllegalArgumentException 错误被活捉了！");
            e.printStackTrace();
        }
        return false;
    }
}
