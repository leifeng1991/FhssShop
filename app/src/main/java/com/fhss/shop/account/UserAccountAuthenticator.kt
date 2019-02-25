package com.fhss.shop.account

import android.accounts.AbstractAccountAuthenticator
import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.accounts.AccountManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.fhss.shop.activity.LoginActivity
import com.fhss.shop.constants.AccountConstants
import com.zrq.base.util.LogUtil

@Suppress("JAVA_CLASS_ON_COMPANION")
/**
 * 描述:
 *
 * @author leifeng
 *         2018/10/8 15:14
 */

class UserAccountAuthenticator(context: Context) : AbstractAccountAuthenticator(context) {

    companion object {
        val TAG = this.javaClass.simpleName!!
    }

    private val mContext: Context = context

    override fun getAuthTokenLabel(authTokenType: String?): String? {
        LogUtil.e(TAG, "===========getAuthTokenLabel()")
        return null
    }

    /**
     * 检查用户传递过来的凭证 对应于 AccountManager.confirmCredentials()
     */
    override fun confirmCredentials(response: AccountAuthenticatorResponse?, account: Account?, options: Bundle?): Bundle? {
        // 自己实现：验证用户的密码
        LogUtil.e(TAG, "===========confirmCredentials()")
        return null
    }

    /**
     * 更新用户凭证 对应于AccountManager.updateCredentials()
     */
    override fun updateCredentials(response: AccountAuthenticatorResponse?, account: Account?, authTokenType: String?, options: Bundle?): Bundle? {
        LogUtil.e(TAG, "===========updateCredentials()")
        return null
    }

    /**
     * 获取Token，对应于AccountManager.getAuthToken() 其他进程一般调用这个进行认证
     */
    override fun getAuthToken(response: AccountAuthenticatorResponse?, account: Account, authTokenType: String?, options: Bundle?): Bundle? {
        // 自己完成获取鉴权token的流程
        LogUtil.e(TAG, "===========getAuthToken()")
        val accountManager = AccountManager.get(mContext)
        var authToken = accountManager.peekAuthToken(account, authTokenType)
        // 验证用户
        if (TextUtils.isEmpty(authToken)) {
            val password = accountManager.getPassword(account)
            if (password != null) {
                // 服务器请求获取token
                authToken = "15036833790"
            }
        }

        // If we get an authToken - we return it
        if (!TextUtils.isEmpty(authToken)) {
            val result = Bundle()
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name)
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type)
            result.putString(AccountManager.KEY_AUTHTOKEN, authToken)
            return result
        }

        // If we get here, then we couldn't access the user's password - so we
        // need to re-prompt them for their credentials. We do that by creating
        // an intent to display our AuthenticatorActivity.
        val intent = Intent(mContext, AuthenticatorActivity::class.java)
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response)
//        intent.putExtra(AuthenticatorActivity.ARG_ACCOUNT_TYPE, account.type)
//        intent.putExtra(AuthenticatorActivity.ARG_AUTH_TYPE, authTokenType)
        val bundle = Bundle()
        bundle.putParcelable(AccountManager.KEY_INTENT, intent)

        return bundle
    }

    /**
     * 检查验证account支持的是否支持请求验证的功能
     */
    override fun hasFeatures(response: AccountAuthenticatorResponse?, account: Account?, features: Array<out String>?): Bundle? {
        LogUtil.e(TAG, "===========hasFeatures()")
        return null
    }

    /**
     * 返回一个bundle，这个bundle可以包含一个用于启动编辑账户Properties的Activity的Intent，对应于accountManager.editProperties()
     */
    override fun editProperties(response: AccountAuthenticatorResponse?, accountType: String?): Bundle? {
        LogUtil.e(TAG, "===========editProperties()")
        return null
    }

    /**
     * 返回一个bundle, 包含添加账户Activity的Intent,对应于 accountManager.addAccountExplicitly()
     */
    override fun addAccount(response: AccountAuthenticatorResponse?, accountType: String?, authTokenType: String?, requiredFeatures: Array<out String>?, options: Bundle?): Bundle {
        LogUtil.e(TAG, "===========addAccount()")
        val addAccountIntent = Intent(mContext, LoginActivity::class.java)
        addAccountIntent.putExtra(AccountConstants.ACCOUNT_TYPE, authTokenType)
        if (options != null) {
            addAccountIntent.putExtras(options)
        }
        // 一定要把response传入intent的extra中，便于将登录操作的结果回调给AccountManager
        addAccountIntent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response)

        val bundle = Bundle()
        bundle.putParcelable(AccountManager.KEY_INTENT, addAccountIntent)
        return bundle
    }

    override fun getAccountRemovalAllowed(response: AccountAuthenticatorResponse?, account: Account?): Bundle {
        LogUtil.e(TAG, "===========editProperties()")
        val bundle = Bundle()
        // false:不能在设置账户中删除 true:可以在设置账户中删除
        bundle.putBoolean(AccountManager.KEY_BOOLEAN_RESULT, false)
        return bundle
    }
}

