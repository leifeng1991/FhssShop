package com.fhss.shop.account

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * 描述:
 *
 * @author leifeng
 *         2018/10/18 10:47
 */

class SyncService : Service() {

    companion object {
        val mSyncAdapterLock = Object()
        var mSyncAdapter: SyncAdapter? = null
    }

    override fun onCreate() {
        super.onCreate()
        synchronized(mSyncAdapterLock) {

            if (mSyncAdapter == null) {
                mSyncAdapter = SyncAdapter(applicationContext, true)
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return mSyncAdapter?.syncAdapterBinder
    }
}

