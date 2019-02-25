package com.zrq.base.net;

import android.support.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author zhangrq
 */
public abstract class OnRequestListener<T> implements Callback<T> {

    @Override
    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
        // 200、404、500走这个
        if (response.isSuccessful()) {
            // 200，成功
            handlerSuccess(response.body());
            handlerEnd();
        } else {
            // 404、500等
            handlerError(response.code(), response.code() + "错误");
            handlerEnd();
        }
    }

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        // 无网络等走这个
        t.printStackTrace();
        handlerError(0, "访问网络失败");
        handlerEnd();
    }

    public abstract void handlerSuccess(T bean);

    public abstract void handlerError(int errorCode, String errorMessage);

    public void handlerEnd() {
    }

}
