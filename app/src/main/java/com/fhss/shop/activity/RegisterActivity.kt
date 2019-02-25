package com.fhss.shop.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import com.fhss.shop.R
import com.fhss.shop.base.FhssApplication
import com.fhss.shop.base.GrayStatusBarBaseActivity
import com.fhss.shop.utils.CheckUtils
import com.fhss.shop.utils.CheckUtils.REGEX_ID_CARD
import com.fhss.shop.utils.CheckUtils.REGEX_MOBILE
import com.fhss.shop.utils.VerCodeUtils
import com.zrq.base.model.BaseBean
import com.zrq.base.net.OnMyActivityRequestListener
import com.zrq.base.util.ToastUtils
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.layout_title_bar_1.*
import java.util.regex.Pattern

/**
 * 描述:注册界面
 *
 * @author leifeng
 *         2018/10/8 14:54
 */


class RegisterActivity : GrayStatusBarBaseActivity() {
    companion object {
        const val REGISTER_REQUEST_CODE = 101
        fun newIntent(context: Context): Intent {
            return Intent(context, RegisterActivity::class.java)
        }
    }

    override fun loadViewLayout() {
        setContentView(R.layout.activity_register)
    }

    override fun initView() {
    }

    override fun setListener() {
        // 返回
        iv_title_left.setOnClickListener {
            // 直接关闭此界面
            finish()
        }
        // 获取验证码
        mGetYzmTextView.setOnClickListener {
            val phone = mBankNoEditText.text.toString().trim()
            if (TextUtils.isEmpty(phone)) {
                ToastUtils.showShort(mContext, "请输入手机号")
                return@setOnClickListener
            }
            if (!Pattern.matches(CheckUtils.REGEX_MOBILE, phone)) {
                ToastUtils.showShort(mContext, "请输入正确手机号")
                return@setOnClickListener
            }
            VerCodeUtils.getVerificationCode(RegisterActivity@ this, phone, mGetYzmTextView)
        }
        // 下一步
        mNextButton.setOnClickListener {
            checkInfo()
        }
    }

    override fun processLogic() {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // 注册成功直接finish
        if (requestCode == REGISTER_REQUEST_CODE && data != null) {
            setResult(Activity.RESULT_OK, Intent())
            finish()
        }
    }

    /**
     * 检测用户输入信息
     */
    private fun checkInfo() {
        val name = mAccountEditText.text.toString().trim()
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showShort(mContext, "请输入用户名")
            return
        }

        val cardId = mCardIdEditText.text.toString().trim()
        if (TextUtils.isEmpty(cardId)) {
            ToastUtils.showShort(mContext, "请输入用户身份证")
            return
        }
        if (!Pattern.matches(REGEX_ID_CARD, cardId)) {
            ToastUtils.showLong(mContext, "请输入正确身份证号")
            return
        }
        val phone = mBankNoEditText.text.toString().trim()
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showShort(mContext, "请输入用户手机号")
            return
        }
        if (!Pattern.matches(REGEX_MOBILE, phone)) {
            ToastUtils.showShort(mContext, "请输入正确手机号")
            return
        }

        val yzm = mYzmEditText.text.toString().trim()
        if (TextUtils.isEmpty(yzm)) {
            ToastUtils.showShort(mContext, "请输入验证码")
            return
        }

        val tjm = mTjmEditText.text.toString().trim()
        if (TextUtils.isEmpty(tjm)) {
            ToastUtils.showShort(mContext, "请输入推荐码")
            return
        }
        register(cardId, phone, name, tjm)
    }

    /**
     * 注册
     */
    private fun register(id: String, phone: String, name: String, generalize: String) {
        FhssApplication.getFhssApplication().getFhssApi().register(id, phone, name, generalize).enqueue(object : OnMyActivityRequestListener<BaseBean>(this) {
            override fun onSuccess(bean: BaseBean) {
                ToastUtils.showMsg(mContext, "注册成功")
                finish()
            }
        })
    }
}