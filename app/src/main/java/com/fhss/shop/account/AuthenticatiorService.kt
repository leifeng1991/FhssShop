package com.fhss.shop.account

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * 描述: 实现onBinder()方法，在onBinder方法里返回TestAccountAuthentcator的实例
 *
 * @author leifeng
 *         2018/10/8 16:18
 */

class AuthenticatiorService : Service() {
    private lateinit var userAccountAuthenticator: UserAccountAuthenticator

    override fun onCreate() {
        super.onCreate()
        userAccountAuthenticator = UserAccountAuthenticator(this)
    }

    override fun onBind(intent: Intent?): IBinder? {
        var mIBinder: IBinder? = null
        if (intent != null) {
            if (intent.action == android.accounts.AccountManager.ACTION_AUTHENTICATOR_INTENT)
                mIBinder = userAccountAuthenticator.iBinder
        }
        return mIBinder
    }
}