package com.fhss.shop.activity

import android.content.Context
import android.content.Intent
import com.fhss.shop.R
import com.fhss.shop.base.FhssBaseActivity

/**
 * 描述:关于我们
 *
 * @author leifeng
 *         2018/10/8 14:54
 */

class AboutActivity : FhssBaseActivity() {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, AboutActivity::class.java)
        }
    }

    override fun loadViewLayout() {
        setContentView(R.layout.activity_address)
    }

    override fun initView() {
        setTitleName("关于我们")
        setTitleLeftBack()
    }

    override fun setListener() {

    }

    override fun processLogic() {

    }
}