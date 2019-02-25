package com.zrq.base.net;


import android.content.Context;

import com.zrq.base.base.BaseActivity;


/**
 * 描述:
 *
 * @author zhangrq
 *         2016/8/16 10:34
 */
@SuppressWarnings("WeakerAccess")
public abstract class OnActivityRequestListener<T> extends OnRequestListener<T> {
    private BaseActivity baseActivity;

    public OnActivityRequestListener(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
        handlerStart();
    }

    public Context getContext() {
        return baseActivity.getApplicationContext();
    }

    public void handlerStart() {
        baseActivity.showLoadDataAnim();
    }

    @Override
    public void handlerEnd() {
        baseActivity.hideLoadDataAnim();
        baseActivity = null;
    }
}
