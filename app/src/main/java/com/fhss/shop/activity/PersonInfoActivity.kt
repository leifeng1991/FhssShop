package com.fhss.shop.activity

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.View
import cn.qqtheme.framework.picker.OptionPicker
import com.fhss.shop.R
import com.fhss.shop.base.FhssApplication
import com.fhss.shop.base.FhssBaseActivity
import com.fhss.shop.bean.User
import com.fhss.shop.utils.CheckUtils
import com.fhss.shop.utils.ImageLoadUtils
import com.fhss.shop.utils.OnTextChangedListener
import com.fhss.shop.utils.addTextChange
import com.zrq.base.net.OnMyActivityRequestListener
import com.zrq.base.util.ToastUtils
import kotlinx.android.synthetic.main.activity_person_info.*
import java.util.regex.Pattern

/**
 * 描述:个人信息
 *
 * @author leifeng
 *         2018/10/8 14:54
 */

class PersonInfoActivity : FhssBaseActivity() {
    private var imageUrl = ""
    private var user: User.MapBean.UserBean? = null
    private var loginUser: User? = null

    companion object {
        const val AVATAR_REQUEST_CODE = 200

        fun newIntent(context: Context): Intent {
            return Intent(context, PersonInfoActivity::class.java)
        }

    }

    override fun loadViewLayout() {
        setContentView(R.layout.activity_person_info)
    }

    override fun onResume() {
        super.onResume()
        loginUser = FhssApplication.getFhssApplication().getLoginUser()
        setUserInfo(loginUser)
    }

    override fun initView() {
        setTitleName("个人信息")
        setTitleLeftBack()
        setTitleRightText("完成")
    }

    override fun setListener() {
        // 头像点击事件
        mUserButton.setOnClickListener {
            // 跳转到头像界面
            startActivityForResult(AvatarActivity.newIntent(mContext), AVATAR_REQUEST_CODE)
        }
        // 性别选择
        mUserSexTextView.setOnClickListener {
            // 选择性别弹框
            selectGender()
        }
        // 家庭住址
        mHomeAddressTextView.setOnClickListener {
            ToastUtils.showMsg(mContext, "暂无开放")
        }
        // 身份证号
        mIdNumberTextView.setOnClickListener {
            ToastUtils.showMsg(mContext, "暂无开放")
        }
        mUserNameTextView.addTextChange(object : OnTextChangedListener {
            override fun onTextChanged(text: String) {
                user?.alias = text
            }

        })
        mRealNameTextView.addTextChange(object : OnTextChangedListener {
            override fun onTextChanged(text: String) {
                user?.userName = text
            }

        })
    }

    override fun onTitleRightClick(view: View?) {
        super.onTitleRightClick(view)
        updateUserInfo()
    }

    override fun processLogic() {
    }

    private fun setUserInfo(loginUser: User?) {
        if (loginUser == null) {
            finish()
            return
        }
        user = loginUser.map.user
        val imageUrl = user?.userImg ?: ""
        if (TextUtils.isEmpty(imageUrl)) {
            mUserButton.setImageResource(R.mipmap.ic_defalut_avatar)
        } else {
            ImageLoadUtils.setImageBig(mContext, imageUrl, mUserButton)
        }
        mUserNameTextView.setText(user?.alias ?: "")
        mUserSexTextView.text = if ("1" == user?.sex ?: "1") "男" else "女"
        mNoTextView.text = user?.generalize ?: ""
        mRealNameTextView.setText(user?.userName ?: "")
        mTelephoneTextView.setText(user?.userPhone ?: "")
    }

    /**
     * 选择性别
     */
    private fun selectGender() {
        val picker = OptionPicker(this, arrayOf("男", "女"))
        picker.setOnOptionPickListener(object : OptionPicker.OnOptionPickListener() {
            override fun onOptionPicked(index: Int, item: String) {
                mUserSexTextView.text = item
                user?.sex = if ("男" == item) "1" else "2"
            }
        })
        picker.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AVATAR_REQUEST_CODE && data != null) {
            imageUrl = AvatarActivity.getIntentImageUrl(data)
            user?.userImg = imageUrl
            // 设置头像
            ImageLoadUtils.setImageSmall(mContext, imageUrl, mUserButton)
        }
    }

    /**
     * 更新用户信息
     */
    private fun updateUserInfo() {
        val phone = mTelephoneTextView.text.trim().toString()
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showShort(mContext, "请输入手机号")
            return
        }
        if (!Pattern.matches(CheckUtils.REGEX_MOBILE, phone)) {
            ToastUtils.showShort(mContext, "请输入正确手机号")
            return
        }
        user?.userPhone = phone
        val map = mutableMapOf<String, String>()
        map["userId"] = user?.userId ?: ""
        map["alias"] = user?.alias ?: ""
        map["userName"] = user?.userName ?: ""
        map["userImg"] = user?.userImg ?: ""
        map["userPhone"] = user?.userPhone ?: ""
        map["userPhone"] = user?.userPhone ?: ""
        map["sex"] = user?.sex ?: "1"
        FhssApplication.getFhssApplication().getFhssApi().updateUserInfo(map).enqueue(object : OnMyActivityRequestListener<User>(this) {
            override fun onSuccess(bean: User) {
                loginUser?.map?.user = user
                FhssApplication.getFhssApplication().setSuccessLoginUser(loginUser)
                ToastUtils.showShort(mContext, "修改成功")
            }

            override fun onFailed(isServiceHintError: Boolean, code: Int, message: String?) {
                if (500 == code) {
                    ToastUtils.showShort(mContext, "修改失败")
                } else {
                    super.onFailed(isServiceHintError, code, message)
                }
            }

        })
    }

}