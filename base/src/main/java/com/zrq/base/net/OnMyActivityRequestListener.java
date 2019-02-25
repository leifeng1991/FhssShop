package com.zrq.base.net;


import com.zrq.base.base.BaseActivity;
import com.zrq.base.model.BaseBean;
import com.zrq.base.util.ToastUtils;

/**
 * 描述:
 *
 * @author zhangrq
 * 2016/12/27 14:34
 */
@SuppressWarnings("WeakerAccess")
public abstract class OnMyActivityRequestListener<T extends BaseBean> extends OnActivityRequestListener<T> {

    public OnMyActivityRequestListener(BaseActivity baseActivity) {
        super(baseActivity);
    }

    @Override
    public void handlerSuccess(T bean) {
        // 200	表示成功
        // 400	数据错误或者为空，直接打印message信息
        // 404	没有接收到传值
        if (200 == bean.getCode()) {
            // 200	表示成功
            onSuccess(bean);
        } else {
            // 其余失败
            onFailed(true, bean.getCode(), bean.getMessage());
        }
    }

    @Override
    public void handlerError(int errorCode, String errorMessage) {
        onFailed(false, errorCode, errorMessage);
    }

    /**
     * 成功
     */
    public abstract void onSuccess(T bean);

    /**
     * 失败
     */
    public void onFailed(boolean isServiceHintError, int code, String message) {
        if (isServiceHintError) {
            // 服务器返回提示信息
            if (404==code)
                // 404	没有接收到传值
                onFailedException(404, message);
            else
                // 400等服务器返回的提示信息
                onFailedErrorHint(code, message);
        } else {
            // 本地异常
            onFailedException(code, message);
        }
    }

    /**
     * 失败--服务器返回的错误提示信息
     *
     * @param code    错误码
     * @param message 错误信息
     */
    @SuppressWarnings("unused")
    public void onFailedErrorHint(int code, String message) {
        // 服务器返回的错误
        ToastUtils.showMsg(getContext(), message);
    }

    /**
     * 失败--本地异常信息
     *
     * @param exceptionCode    异常码
     * @param exceptionMessage 异常信息
     */
    public void onFailedException(int exceptionCode, String exceptionMessage) {
        // 异常错误，可控制是否展示error
        ToastUtils.showError(getContext(), exceptionCode, exceptionMessage);
    }
}
