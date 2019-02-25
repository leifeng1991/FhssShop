package com.fhss.shop.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.fhss.shop.utils.ImageLoadUtils;
import com.youth.banner.loader.ImageLoader;

/**
 * 描述:
 *
 * @author zhangrq
 * 2018/4/10 11:36
 */
public class BannerImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        if (path instanceof String) {
            ImageLoadUtils.setImageBig(context, (String) path, imageView);
        }
    }
}
