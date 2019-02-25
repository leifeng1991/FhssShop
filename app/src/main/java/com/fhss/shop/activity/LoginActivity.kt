package com.fhss.shop.activity

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import com.fhss.shop.R
import com.fhss.shop.base.GrayStatusBarBaseActivity
import com.zrq.base.util.ToastUtils
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.layout_title_bar_1.*
import android.accounts.AccountManager
import android.accounts.Account
import com.fhss.shop.base.FhssApplication
import com.fhss.shop.bean.User
import com.fhss.shop.constants.AccountConstants.Companion.ACCOUNT_TYPE
import com.fhss.shop.constants.BaseConstants
import com.fhss.shop.event.CompleteInfoSuccessEvent
import com.fhss.shop.utils.CheckUtils
import com.fhss.shop.utils.VerCodeUtils
import com.tencent.mm.opensdk.diffdev.IDiffDevOAuth
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.zrq.base.model.BaseBean
import com.zrq.base.net.OnMyActivityRequestListener
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.regex.Pattern


/**
 * 描述:登录界面
 *
 * @author leifeng
 *         2018/10/8 14:54
 */

class LoginActivity : GrayStatusBarBaseActivity() {

    companion object {
        // 跳转到注册界面的请求码
        const val LOGIN_TO_REGISTER_REQUEST_CODE = 100

        fun newIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

    override fun loadViewLayout() {
        setContentView(R.layout.activity_login)
    }

    override fun initView() {
    }

    override fun setListener() {
        EventBus.getDefault().register(this)
        // 返回点击监听事件
        iv_title_left.setOnClickListener {
            finish()
        }

        // 登录点击监听事件
        mLoginButton.setOnClickListener {
            checkInfo()
        }
        // 注册点击监听事件
        mRegisterTextView.setOnClickListener {
            // 跳转到注册界面
            startActivityForResult(RegisterActivity.newIntent(mContext), LOGIN_TO_REGISTER_REQUEST_CODE)
        }
        // 获取验证码
        mGetYzmTextView.setOnClickListener {
            val phone = mAccountEditText.text.toString().trim()
            if (TextUtils.isEmpty(phone)) {
                ToastUtils.showShort(mContext, "请输入手机号")
                return@setOnClickListener
            }
            if (!Pattern.matches(CheckUtils.REGEX_MOBILE, phone)) {
                ToastUtils.showShort(mContext, "请输入正确手机号")
                return@setOnClickListener
            }
            VerCodeUtils.getVerificationCode(LoginActivity@ this, phone, mGetYzmTextView)
        }
        // 微信登录
        mWeChatLogin.setOnClickListener {
            val req = SendAuth.Req()
            req.scope = "snsapi_userinfo"
            req.state = "none"
            FhssApplication.getFhssApplication().api.sendReq(req)
        }
    }

    override fun processLogic() {
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // 注册成功直接finish
        if (requestCode == LOGIN_TO_REGISTER_REQUEST_CODE && data != null) {
            finish()
        }
    }

    /**
     * 检测输入信息
     */
    private fun checkInfo() {
        val phone = mAccountEditText.text.toString().trim()
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showShort(mContext, "请输入手机号")
            return
        }
        if (!Pattern.matches(CheckUtils.REGEX_MOBILE, phone)) {
            ToastUtils.showShort(mContext, "请输入正确手机号")
            return
        }

        val yzm = mYzmEditText.text.toString().trim()
        if (TextUtils.isEmpty(yzm)) {
            ToastUtils.showShort(mContext, "请输入验证码")
            return
        }

//        val mAccount = Account(phone, ACCOUNT_TYPE)
//        val am = AccountManager.get(this)
//        am.addAccountExplicitly(mAccount, "123456", null)

        // 检测成功进行网络请求 请求成功直接finish
        login(phone, yzm)
    }

    /**
     * 登录
     */
    private fun login(phone: String, code: String) {
        FhssApplication.getFhssApplication().getFhssApi().login(phone, code).enqueue(object : OnMyActivityRequestListener<User>(this) {
            override fun onSuccess(bean: User) {
                FhssApplication.getFhssApplication().setSuccessLoginUser(bean)
                ToastUtils.showMsg(mContext, "登录成功")
                finish()
            }
        })
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun finishLoginActivity(event: CompleteInfoSuccessEvent) {
        finish()
    }

}