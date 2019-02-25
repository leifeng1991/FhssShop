package com.fhss.shop.activity

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import com.fhss.shop.R
import com.fhss.shop.base.FhssApplication
import com.fhss.shop.base.FhssBaseActivity
import com.zrq.base.model.BaseBean
import com.zrq.base.net.OnMyActivityRequestListener
import com.zrq.base.util.LogUtil
import com.zrq.base.util.ToastUtils
import kotlinx.android.synthetic.main.activity_new_bank.*

/**
 * 描述:新增银行卡
 *
 * @author leifeng
 *         2018/10/8 14:54
 */

class NewBankActivity : FhssBaseActivity() {
    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, NewBankActivity::class.java)
        }
    }

    override fun loadViewLayout() {
        setContentView(R.layout.activity_new_bank)
    }

    override fun initView() {

    }

    override fun setListener() {
        mSubmitButton.setOnClickListener {
            // 提交
            check()
        }
    }

    override fun processLogic() {
        setTitleName("新增银行卡")
        setTitleLeftBack()

    }

    /**
     * 检测输入内容
     */
    private fun check() {
        val name = mNameEditText.text.trim().toString()
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showShort(mContext, "请输入开户名")
            return
        }
        var bankNo = mBankNoEditText.text.trim().toString()
        if (TextUtils.isEmpty(bankNo)) {
            ToastUtils.showShort(mContext, "请输入银行卡号")
            return
        }
        if (bankNo.length != 16) {
            ToastUtils.showShort(mContext, "请输入16位银行卡号")
            return
        }
        val number1 = bankNo.substring(0,4)
        val number2 = bankNo.substring(4,8)
        val number3 = bankNo.substring(8,12)
        val number4 = bankNo.substring(12,16)
        bankNo = "$number1 $number2 $number3 $number4"

        LogUtil.e("+++++++$bankNo")

        val mMerchantsBank = mMerchantsBankEditText.text.trim().toString()
        if (TextUtils.isEmpty(mMerchantsBank)) {
            ToastUtils.showShort(mContext, "请输入开户行")
            return
        }

        val loginUserDoLogin = FhssApplication.getFhssApplication().getLoginUserDoLogin(this)
        if (loginUserDoLogin != null) {
            val map = HashMap<String, String>()
            map["userId"] = loginUserDoLogin.map.user.userId // 用户ID
            map["realName"] = name // 用户真实姓名
            map["cardNum"] = bankNo // 卡号  总共16位 每四位之间空一格
            map["bankName"] = mMerchantsBank // 银行
            addBank(map)
        }

    }

    /**
     * 添加
     */
    private fun addBank(map: Map<String, String>) {
        FhssApplication.getFhssApplication().getFhssApi().insertBankCard(map).enqueue(object : OnMyActivityRequestListener<BaseBean>(this) {
            override fun onSuccess(bean: BaseBean?) {
                ToastUtils.showShort(mContext, "添加成功")
                finish()
            }

        })
    }

}