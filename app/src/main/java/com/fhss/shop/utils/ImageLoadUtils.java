package com.fhss.shop.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.zrq.base.model.GlideApp;

import java.io.File;

/**
 * 图片加载的工具类
 *
 * @author zhangrq
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class ImageLoadUtils {

    /**
     * 设置图片，先获取图片签名，后设置图片（此为兆邻规则）
     *
     * @param context   上下文
     * @param imgUrl    网络图片地址
     * @param imageView 控件
     */
    private static void setImageByImage(final Context context, final String imgUrl, final ImageView imageView) {

        if (!TextUtils.isEmpty(imgUrl)) {
            // 地址没有问题，获取图片
            setImageRaw(context, imgUrl, imageView);
        } else {
            // 地址有问题
            setImageError(imageView);
        }
    }


    /**
     * 设置图片，最原始的使用方法
     */
    private static void setImageRaw(Context context, String imgUrl, ImageView imageView) {
        GlideApp.with(context).load(imgUrl).centerCrop().into(imageView);
    }


    /**
     * 设置图片，最原始的使用方法
     */
    public static void setImageRaw(Context context, Uri imgUrl, ImageView imageView) {
        GlideApp.with(context).load(imgUrl).centerCrop().into(imageView);
    }

    /**
     * 设置图片，最原始的使用方法
     */
    public static void setImageRaw(Context context, File file, ImageView imageView) {
        GlideApp.with(context).load(file).centerCrop().into(imageView);
    }

    /**
     * 设置大图片
     */
    public static void setImageBig(Context context, String imgUrl, ImageView imageView) {
        setImageByImage(context, imgUrl, imageView);
    }

    /**
     * 设置中图片
     */
    public static void setImageMiddle(Context context, String imgUrl, ImageView imageView) {
        setImageByImage(context, imgUrl, imageView);
    }

    /**
     * 设置小图片
     */
    public static void setImageSmall(Context context, String imgUrl, ImageView imageView) {
        setImageByImage(context, imgUrl, imageView);
    }

    /**
     * 设置等比例缩放图片
     */
    public static void setImageConstrainScale(Context context, String imgUrl, ImageView imageView) {
        GlideApp.with(context).load(imgUrl).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                int originWidth = resource.getIntrinsicWidth();
                int originHeight = resource.getIntrinsicHeight();
                if (imageView == null){
                    return false;
                }
                //计算缩放比例
                double sy = (PixelUtil.getScreenWidth(context) * 0.1) / (originWidth * 0.1);
                int imageViewHeight = (int) (originHeight * sy);
                ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                layoutParams.width = PixelUtil.getScreenWidth(context);
                layoutParams.height = imageViewHeight;
                return false;
            }
        }).into(imageView);
    }

    /**
     * 设置带监听的大图片
     */
    public static void setImageBigNoCenterCrop(final Context context, final String imgUrl, final ImageView imageView, final RequestListener<Drawable> requestListener) {
        if (!TextUtils.isEmpty(imgUrl)) {
            // 地址没有问题，获取图片
            // 获取签名成功，下载图片
            GlideApp.with(context).load(imgUrl).listener(requestListener).into(imageView);
        } else {
            // 地址有问题
            if (requestListener != null)
                requestListener.onLoadFailed(null, null, null, true);
        }
    }

    /**
     * 设置图片error显示
     */
    private static void setImageError(ImageView imageView) {
        imageView.setImageBitmap(null);
    }

    public interface OnGetImageSignatureListener {
        void onSuccess(String sign);

        void onError(int errorCode, String errorMsg);
    }
}