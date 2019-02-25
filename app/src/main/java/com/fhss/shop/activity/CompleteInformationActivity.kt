package com.fhss.shop.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.View
import com.fhss.shop.R
import com.fhss.shop.base.FhssApplication
import com.fhss.shop.base.GrayStatusBarBaseActivity
import com.fhss.shop.bean.User
import com.fhss.shop.bean.WechatUserBean
import com.fhss.shop.event.CompleteInfoSuccessEvent
import com.fhss.shop.utils.CheckUtils.REGEX_ID_CARD
import com.fhss.shop.utils.CheckUtils.REGEX_MOBILE
import com.zrq.base.model.BaseBean
import com.zrq.base.net.OnMyActivityRequestListener
import com.zrq.base.util.ToastUtils
import kotlinx.android.synthetic.main.activity_complete_information.*
import org.greenrobot.eventbus.EventBus
import java.util.regex.Pattern

/**
 * 描述:微信登录用户完善信息界面
 *
 * @author leifeng
 *         2018/10/8 14:54
 */


class CompleteInformationActivity : GrayStatusBarBaseActivity() {
    private lateinit var mWeChatUserBean: WechatUserBean

    companion object {
        const val REGISTER_REQUEST_CODE = 101
        private const val WE_CHAT_USER_BEAN = "we_chat_user_bean"
        /**
         * mWeChatUserBean：微信用户信息
         */
        fun newIntent(context: Context, mWeChatUserBean: WechatUserBean): Intent {
            val intent = Intent(context, CompleteInformationActivity::class.java)
            intent.putExtra(WE_CHAT_USER_BEAN, mWeChatUserBean)
            return intent
        }
    }

    override fun loadViewLayout() {
        setContentView(R.layout.activity_complete_information)
    }

    override fun initView() {
    }

    override fun setListener() {
        // 完善个人信息
        mCompleteInfoButton.setOnClickListener {
            mLayout1.visibility = View.GONE
            mLayout2.visibility = View.VISIBLE
        }
        // 提交
        mSubmitButton.setOnClickListener {
            checkInfo()
        }
    }

    override fun processLogic() {
        mWeChatUserBean = intent.getSerializableExtra(WE_CHAT_USER_BEAN) as WechatUserBean
        mAccountEditText.setText(mWeChatUserBean.nickname)
//        register()
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
        FhssApplication.getFhssApplication().getFhssApi().saveUserInfo(id, phone, name, generalize).enqueue(object : OnMyActivityRequestListener<User>(this) {
            override fun onSuccess(bean: User) {
                EventBus.getDefault().post(CompleteInfoSuccessEvent())
                FhssApplication.getFhssApplication().setSuccessLoginUser(bean)
                ToastUtils.showMsg(mContext, "注册成功")
                finish()
            }
        })
    }
}