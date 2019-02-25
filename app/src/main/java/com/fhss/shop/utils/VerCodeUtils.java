package com.fhss.shop.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

import com.fhss.shop.base.FhssApplication;
import com.fhss.shop.bean.ClassifyListBean;
import com.fhss.shop.weight.ShapeButton;
import com.zrq.base.base.BaseActivity;
import com.zrq.base.model.BaseBean;
import com.zrq.base.net.OnMyActivityRequestListener;
import com.zrq.base.util.ToastUtils;

/**
 * 获取 验证码的工具类
 *
 * @author zhangrq
 */
public class VerCodeUtils {
    /**
     * 获取验证码
     *
     * @param phoneNumber 手机号
     */
    public static void getVerificationCode(BaseActivity activity, String phoneNumber, final ShapeButton verCodeBtn) {
        CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                verCodeBtn.setEnabled(false);
                verCodeBtn.setText(String.format("%s S", millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                verCodeBtn.setEnabled(true);
                verCodeBtn.setText("获取验证码");
            }

        };

        FhssApplication.Companion.getFhssApplication().getFhssApi().getYzm(phoneNumber).enqueue(new OnMyActivityRequestListener<BaseBean>(activity) {
            @Override
            public void handlerStart() {
                super.handlerStart();
                // 请求网络开始，不能再次点击
                verCodeBtn.setEnabled(false);
            }

            @Override
            public void onSuccess(BaseBean bean) {
                // 请求网络成功，开始倒计时，60秒之后可点击
                countDownTimer.start();
                ToastUtils.showShort(getContext(), "验证码已发送，请注意查收");
            }

            @Override
            public void onFailed(boolean isServiceHintError, int code, String message) {
                super.onFailed(isServiceHintError, code, message);
                // 请求网络失败，已提示对应信息，按钮继续可点击
                verCodeBtn.setEnabled(true);
            }
        });
    }

}
