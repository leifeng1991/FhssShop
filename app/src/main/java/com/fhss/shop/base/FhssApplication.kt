package com.fhss.shop.base

import android.app.Activity
import android.app.Application
import android.text.TextUtils
import com.fhss.shop.activity.LoginActivity
import com.fhss.shop.bean.User
import com.fhss.shop.constants.BaseConstants.Companion.WECHAT_APPID
import com.google.gson.Gson
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.zrq.base.net.RetrofitFactory
import com.zrq.base.util.SPUtils

/**
 * 描述:
 *
 * @author leifeng
 *         2018/10/9 11:00
 */

class FhssApplication : Application() {
    private var loginUser: User? = null
    open lateinit var api: IWXAPI

    /**
     * 静态
     */
    companion object {
        private lateinit var application: FhssApplication
        private const val USER = "user"

        fun getFhssApplication(): FhssApplication {
            return application
        }
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        // 初始化微信
        api = WXAPIFactory.createWXAPI(applicationContext, WECHAT_APPID, true)
        api.registerApp(WECHAT_APPID)
    }

    fun getFhssApi(): FhssAPI {
        return getRetrofitFactory().create<FhssAPI>(FhssAPI::class.java)
    }

    private fun getRetrofitFactory(): RetrofitFactory {
        return RetrofitFactory.getInstance(FhssAPI.BASE_URL,
//                HttpAddHeadersInterceptor(), // 添加请求头
//                HttpRequestEncryptInterceptor(), // 请求体加密
//                HttpResponseDecryptInterceptor(), // 处理返回信息解密
                HttpLogInterceptor())// 打印日志
    }

    /**
     * 获取登录用户，如果没登录跳到登录页面
     *
     * @return 返回null代表没登录
     */
    fun getLoginUserDoLogin(activity: Activity): User? {
        if (loginUser == null) {
            val context = activity.applicationContext
            val userString = SPUtils.getStringAttr(context, USER)
            if (TextUtils.isEmpty(userString)) {
                // 用户没登录，并且未保存本地（退出登录效果）,重新登录
                activity.startActivity(LoginActivity.newIntent(this))
            } else {
                // 用户没登录，保存本地保存了，用本地的
                loginUser = Gson().fromJson<User>(userString, User::class.java)
            }
        }
        return loginUser
    }

    /**
     * 获取登录用户
     *
     * @return 返回null代表没登录
     */
    fun getLoginUser(): User? {
        if (loginUser == null) {
            val userString = SPUtils.getStringAttr(this, USER)
            if (!TextUtils.isEmpty(userString)) {
                // 用户没登录，本地保存了，用本地的
                loginUser = Gson().fromJson<User>(userString, User::class.java)
            }
        }
        return loginUser
    }

    /**
     * 设置成功登录用户
     */
    fun setSuccessLoginUser(loginUser: User?) {
        this@FhssApplication.loginUser = loginUser
        // user保存
        if (loginUser == null)
            SPUtils.removeAttr(this, USER)
        else
            SPUtils.setStringAttr(this, USER, Gson().toJson(loginUser))
    }

    /**
     * 设置退出登录
     */
    fun setExitLoginUser() {
        // user清空
        this.loginUser = null
        SPUtils.removeAttr(this, USER)
    }


}