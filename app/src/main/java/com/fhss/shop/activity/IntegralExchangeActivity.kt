package com.fhss.shop.activity

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import com.fhss.shop.R
import com.fhss.shop.base.FhssApplication
import com.fhss.shop.base.FhssBaseActivity
import com.zrq.base.model.BaseBean
import com.zrq.base.net.OnMyActivityRequestListener
import com.zrq.base.util.ToastUtils
import kotlinx.android.synthetic.main.activity_integral_exchange.*

/**
 * 描述:积分
 *
 * @author leifeng
 *         2018/10/8 14:54
 */

class IntegralExchangeActivity : FhssBaseActivity() {
    private var integral: Int = 0

    companion object {
        const val INTEGRAL = "integral"
        fun newIntent(context: Context, integral: Int): Intent {
            val intent = Intent(context, IntegralExchangeActivity::class.java)
            intent.putExtra(INTEGRAL, integral)
            return intent
        }

    }

    override fun loadViewLayout() {
        setContentView(R.layout.activity_integral_exchange)
    }

    override fun initView() {
        setTitleName("积分提现")
        setTitleLeftBack()
    }

    override fun setListener() {
        // 全部兑换
        mExplainTextView.setOnClickListener {
            mInputIntegralEditText.setText("$integral")
        }
        // 提现
        mSureButton.setOnClickListener {
            submit()
        }
    }

    override fun processLogic() {
        integral = intent.getIntExtra(INTEGRAL, 0)
        mAvailableIntegralNumberTv.text = "可用积分数$integral"
    }

    private fun submit() {
        val toString = mInputIntegralEditText.text.trim().toString()
        if (TextUtils.isEmpty(toString)){
            ToastUtils.showShort(mContext, "请输入提现额度")
            return
        }
        val toInt = toString.toInt()
        if (toInt > integral) {
            ToastUtils.showShort(mContext, "提现积分数不能大于可提现积分数")
            return
        }

        val loginUserDoLogin = FhssApplication.getFhssApplication().getLoginUserDoLogin(this)
        if (loginUserDoLogin != null)
            FhssApplication.getFhssApplication().getFhssApi().insertUserIntegral(loginUserDoLogin.map.user.userId,"1","$toInt").enqueue(object : OnMyActivityRequestListener<BaseBean>(this){
                override fun onSuccess(bean: BaseBean?) {
                    ToastUtils.showShort(mContext,"提现成功")
                }

            })
    }
}