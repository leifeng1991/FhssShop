package com.fhss.shop.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.fhss.shop.R;
import com.fhss.shop.utils.BitmapUtils;
import com.fhss.shop.utils.ImageLoadUtils;
import com.fhss.shop.weight.zoomimage.PhotoView;
import com.zrq.base.base.ViewPageFragment;

import java.io.File;

/**
 * 描述:附近-人
 */
public class ImageFragment extends ViewPageFragment {

    public static final String URL = "url";
    private PhotoView imageView;
    private ViewGroup vg_loading_layout;
    private ImageView loadingImageView;
    private String path;

    /**
     * @param url 图片地址（必传）
     */
    public static ImageFragment newInstance(String url) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putString(URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View loadViewLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_image_viewpage, container, false);
    }

    @Override
    protected void initView() {
        imageView = getView(R.id.imageView);
        vg_loading_layout = getView(R.id.vg_loading_layout);
        loadingImageView = getView(R.id.loadingImageView);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void processLogic() {
        // 获取值
        path = getArguments().getString(URL);
    }

    @Override
    public void refreshOnceData() {
        // 设置图片显示
        setImage(path);
    }

    /**
     * 设置图片显示
     */
    private void setImage(String path) {
        // 展示加载动画
        showLoadAnim(vg_loading_layout, loadingImageView);
        if (path.contains("http")) {
            // 网络地址
            ImageLoadUtils.setImageBigNoCenterCrop(mContext, path, imageView, new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    // hide加载动画
                    hideLoadAnim(vg_loading_layout, loadingImageView);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    // hide加载动画
                    hideLoadAnim(vg_loading_layout, loadingImageView);
                    return false;
                }
            });
        } else {
            // 本地地址
            Bitmap bitmap = BitmapUtils.INSTANCE.decodeBitmapFromFile(new File(path), imageView.getWidth(), imageView.getHeight());//按照屏幕宽高的3/4比例进行缩放显示
            imageView.setImageBitmap(bitmap);
            // hide加载动画
            hideLoadAnim(vg_loading_layout, loadingImageView);
        }
    }


    private void showLoadAnim(ViewGroup vg_loading_layout, ImageView loadingImageView) {
        vg_loading_layout.setVisibility(View.VISIBLE);
        AnimationDrawable animationDrawable = (AnimationDrawable) loadingImageView.getBackground();
        animationDrawable.start();
    }

    private void hideLoadAnim(ViewGroup vg_loading_layout, ImageView loadingImageView) {
        AnimationDrawable animationDrawable = (AnimationDrawable) loadingImageView.getBackground();
        animationDrawable.stop();
        vg_loading_layout.setVisibility(View.GONE);
    }
}
