package com.fhss.shop.account

import android.accounts.Account
import android.accounts.AccountAuthenticatorActivity
import android.accounts.AccountManager
import android.content.ContentResolver
import android.os.Bundle
import android.text.TextUtils
import com.fhss.shop.R
import com.fhss.shop.activity.LoginActivity
import com.fhss.shop.activity.RegisterActivity
import com.fhss.shop.constants.AccountConstants.Companion.ACCOUNT_TYPE
import com.zrq.base.util.ToastUtils
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.layout_title_bar_1.*

/**
 *
 * @author leifeng
 *         2018/10/8 14:54
 */

open class AuthenticatorActivity : AccountAuthenticatorActivity() {
    private lateinit var mAccountManager: AccountManager

    override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        setContentView(R.layout.activity_login)

        mAccountManager = getSystemService(ACCOUNT_SERVICE) as AccountManager
        // 获取系统帐户列表中已添加的帐户是否存在我们的帐户，用TYPE做为标识
        val accounts = mAccountManager.getAccountsByType(ACCOUNT_TYPE)
        if (accounts.isNotEmpty()) {
            // 已经添加了我们的账户
            finish()
        }

        setListener()
    }

    fun setListener() {
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
            startActivityForResult(RegisterActivity.newIntent(this), LoginActivity.LOGIN_TO_REGISTER_REQUEST_CODE)
        }
    }

    /**
     * 检测输入信息
     */
    private fun checkInfo() {
        val account = mAccountEditText.text.toString().trim()
        if (TextUtils.isEmpty(account)) {
            ToastUtils.showShort(this, "请输入手机号")
            return
        }

        val yzm = mYzmEditText.text.toString().trim()
        if (TextUtils.isEmpty(yzm)) {
            ToastUtils.showShort(this, "请输入验证码")
            return
        }

        val mAccount = Account(getString(R.string.app_name), ACCOUNT_TYPE)
        // TODO 帐户密码和信息这里用null演示
        mAccountManager.addAccountExplicitly(mAccount, null, null)
        // 自动同步
        val bundle= Bundle()
//        ContentResolver.setIsSyncable(mAccount, AccountProvider.AUTHORITY, 1)
//        ContentResolver.setSyncAutomatically(mAccount, AccountProvider.AUTHORITY,true)
        // 间隔时间为30秒
//        ContentResolver.addPeriodicSync(mAccount, AccountProvider.AUTHORITY,bundle, 30)
        // 手动同步
        ContentResolver.requestSync(mAccount, AccountProvider.AUTHORITY, bundle)

        // 检测成功进行网络请求 请求成功直接finish
        finish()
    }
}