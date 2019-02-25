package com.fhss.shop.wxapi;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.fhss.shop.activity.CompleteInformationActivity;
import com.fhss.shop.base.FhssApplication;
import com.fhss.shop.bean.TokenBean;
import com.fhss.shop.bean.User;
import com.fhss.shop.bean.WeChatUserInfoBean;
import com.fhss.shop.bean.WechatUserBean;
import com.fhss.shop.constants.BaseConstants;
import com.fhss.shop.event.CompleteInfoSuccessEvent;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zrq.base.model.BaseBean;
import com.zrq.base.net.OnRequestListener;
import com.zrq.base.util.LogUtil;
import com.zrq.base.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import cn.qqtheme.framework.util.LogUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.fhss.shop.constants.BaseConstants.WECHAT_APPID;


/**
 * 微信回调
 * Created by Administrator on 2017/4/13.
 */

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final int RETURN_MSG_TYPE_SHARE = 2;
    private IWXAPI api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //如果没回调onResp，八成是这句没有写
        api = WXAPIFactory.createWXAPI(this, WECHAT_APPID, true);
        api.registerApp(WECHAT_APPID);
        api.handleIntent(getIntent(), this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    //app发送消息给微信，处理返回消息的回调
    @Override
    public void onResp(BaseResp resp) {
        LogUtils.error(resp.errStr);
        LogUtils.error("错误码 : " + resp.errCode + "");
        switch (resp.errCode) {

            case BaseResp.ErrCode.ERR_AUTH_DENIED:
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                if (RETURN_MSG_TYPE_SHARE == resp.getType()) ToastUtils.showShort(this, "分享失败");
                else ToastUtils.showShort(this, "登录失败");
                break;
            case BaseResp.ErrCode.ERR_OK:
                switch (resp.getType()) {
                    case RETURN_MSG_TYPE_LOGIN:
                        //拿到了微信返回的code,立马再去请求access_token
                        String code = ((SendAuth.Resp) resp).code;
                        LogUtils.error("code = " + code);

                        //就在这个地方，用网络库什么的或者自己封的网络api，发请求去咯，注意是get请求
                        getToken(code);
                        break;

                    case RETURN_MSG_TYPE_SHARE:
                        break;
                }
                break;
        }
    }

    private void getToken(String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + BaseConstants.WECHAT_APPID + "&secret=" + BaseConstants.WECHAT_APPSECRET + "&code=" + code + "&grant_type=authorization_code";
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder().url(url);
        Request request = requestBuilder.build();
        Call mcall = mOkHttpClient.newCall(request);
        mcall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (null != response.cacheResponse()) {
                    String str = response.cacheResponse().toString();
                    LogUtils.error("========================1" + str);
                } else {
                    String jsonString = response.body().string();
                    Gson gson = new Gson();
                    TokenBean tokenBean = gson.fromJson(jsonString, TokenBean.class);
                    getWechatUserInfo(tokenBean.getAccess_token(), tokenBean.getOpenid());
                }
            }
        });
    }

    private void getWechatUserInfo(String token, String openId) {
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + token + "&openid=" + openId;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder().url(url);
        Request request = requestBuilder.build();
        Call mcall = mOkHttpClient.newCall(request);
        mcall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(getApplicationContext(), "授权失败", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (null != response.cacheResponse()) {
                    String str = response.cacheResponse().toString();
                    LogUtils.error("========================1" + str);
                } else {
                    String jsonString = response.body().string();
                    Gson gson = new Gson();
                    WechatUserBean wechatUserBean = gson.fromJson(jsonString, WechatUserBean.class);
                    LogUtil.e("==============" + jsonString);
                    runOnUiThread(() -> Toast.makeText(getApplicationContext(), "授权成功", Toast.LENGTH_SHORT).show());
                    register(wechatUserBean);
                }

            }
        });
    }

    private void register(WechatUserBean mWeChatUserBean){
        FhssApplication.Companion.getFhssApplication().getFhssApi().registUser(mWeChatUserBean.getNickname(),mWeChatUserBean.getHeadimgurl(),mWeChatUserBean.getOpenid()).enqueue(new OnRequestListener<WeChatUserInfoBean>() {
            @Override
            public void handlerSuccess(WeChatUserInfoBean bean) {
                if (bean.getUser()==null){
                    startActivity(CompleteInformationActivity.Companion.newIntent(getApplicationContext(),mWeChatUserBean));
                }else {
                    User user = new User();
                    User.MapBean mapBean = new User.MapBean();
                    mapBean.setUser(bean.getUser());
                    user.setMap(mapBean);
                    EventBus.getDefault().post(new CompleteInfoSuccessEvent());
                    FhssApplication.Companion.getFhssApplication().setSuccessLoginUser(user);
                    runOnUiThread(() -> Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show());
                }
                finish();
            }

            @Override
            public void handlerError(int errorCode, String errorMessage) {

            }
        });
    }
}
