package com.fhss.shop.base

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import com.fhss.shop.R
import com.fhss.shop.utils.SystemBarUtils
import com.zrq.base.base.BaseActivity

/**
 * 描述:
 *
 * @author leifeng
 *         2018/10/9 9:29
 */

abstract class FhssBaseActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SystemBarUtils.setStatusBarColor(this, ContextCompat.getColor(mContext, R.color.colorTheme))
        SystemBarUtils.setStatusBarLightMode(this, true)
        findViewById<View>(android.R.id.content).setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorTheme))
    }
}