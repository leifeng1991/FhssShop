package com.fhss.shop.activity

import android.content.Context
import android.content.Intent
import com.fhss.shop.R
import com.fhss.shop.base.FhssApplication
import com.fhss.shop.base.FhssBaseActivity
import com.fhss.shop.bean.IntegralBean
import com.zrq.base.net.OnMyActivityRequestListener
import kotlinx.android.synthetic.main.activity_integral.*

/**
 * 描述:积分兑换
 *
 * @author leifeng
 *         2018/10/8 14:54
 */

class IntegralActivity : FhssBaseActivity() {
    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, IntegralActivity::class.java)
        }

    }

    override fun onResume() {
        super.onResume()
        getIntegral()
    }

    override fun loadViewLayout() {
        setContentView(R.layout.activity_integral)
    }

    override fun initView() {
        setTitleName("积分")
        setTitleLeftBack()
    }

    override fun setListener() {
        // 充值
        mUpgradeButton.setOnClickListener {
            startActivity(ComBoChoiceActivity.newIntent(mContext))
        }
        // 积分兑换
        mCreditsButton.setOnClickListener {
            startActivity(IntegralExchangeActivity.newIntent(mContext, mIntegralTextView.text.trim().toString().toInt()))
        }
        // 积分兑换规则
        mExplainTextView.setOnClickListener {

        }
    }

    override fun processLogic() {

    }

    private fun getIntegral() {
        val loginUserDoLogin = FhssApplication.getFhssApplication().getLoginUserDoLogin(this)
        if (loginUserDoLogin != null)
            FhssApplication.getFhssApplication().getFhssApi().selectUserIntegral(loginUserDoLogin.map.user.userId).enqueue(object : OnMyActivityRequestListener<IntegralBean>(this) {
                override fun onSuccess(bean: IntegralBean?) {
                    mIntegralTextView.text = "${bean?.num ?: 0}"
                }

            })
    }

}