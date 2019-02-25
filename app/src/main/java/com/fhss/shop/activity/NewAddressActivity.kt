package com.fhss.shop.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import cn.qqtheme.framework.entity.City
import cn.qqtheme.framework.entity.County
import cn.qqtheme.framework.entity.Province
import com.fhss.shop.R
import com.fhss.shop.base.FhssBaseActivity
import kotlinx.android.synthetic.main.activity_new_address.*
import kotlinx.android.synthetic.main.layout_title_bar.*
import com.fhss.shop.base.FhssApplication
import com.fhss.shop.utils.AddressPickTask
import com.zrq.base.model.BaseBean
import com.zrq.base.net.OnMyActivityRequestListener
import com.zrq.base.util.ToastUtils


/**
 * 描述:新增地址
 *
 * @author leifeng
 *         2018/10/8 14:54
 */

class NewAddressActivity : FhssBaseActivity(), TextWatcher {
    private lateinit var mProvince: String
    private lateinit var mCity: String

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, NewAddressActivity::class.java)
        }
    }

    override fun loadViewLayout() {
        setContentView(R.layout.activity_new_address)
    }

    override fun initView() {

    }

    override fun setListener() {
        mNameEditText.addTextChangedListener(this)
        mBankNoEditText.addTextChangedListener(this)
        mEditText.addTextChangedListener(this)
        mPostalCodeEditText.addTextChangedListener(this)
        // 地区选择
        mRegionTextView.setOnClickListener {
            selectCity()
        }
    }

    override fun processLogic() {
        setTitleName("新增地址")
        setTitleRightText("完成")
        tv_title_right.setTextColor(resources.getColor(R.color.colorFinishText1))
        tv_title_right.isEnabled = false
        setTitleLeftBack()
    }

    override fun onTitleRightClick(view: View?) {
        super.onTitleRightClick(view)
        changeFinishStateByInputInfo(true)
    }

    override fun afterTextChanged(s: Editable?) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        changeFinishStateByInputInfo(false)
    }

    /**
     * 通过用户输入信息更改完成按钮状态以及是否可以点击
     */
    private fun changeFinishStateByInputInfo(isSubmit: Boolean) {
        val name = mNameEditText.text.toString().trim()// 收货人
        val phone = mBankNoEditText.text.toString().trim()// 收货人手机号
        val area = mRegionTextView.text.toString().trim()// 收货人所在地区
        val detailAddress = mEditText.text.toString().trim()// 收获人详细地址
        val postalCode = mPostalCodeEditText.text.toString().trim()// 收获人邮政编码
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(area) || TextUtils.isEmpty(detailAddress) || TextUtils.isEmpty(postalCode)) {
            // 收货信息不全
            tv_title_right.setTextColor(resources.getColor(R.color.colorFinishText1))
            tv_title_right.isEnabled = false
        } else {
            // 收货信息全部填完
            tv_title_right.setTextColor(resources.getColor(R.color.colorFinishText2))
            tv_title_right.isEnabled = true
        }

        if (isSubmit) {
            // 进行网络请求 成功之后关闭界面
            val map = HashMap<String, String>()
            map["userId"] = FhssApplication.getFhssApplication().getLoginUser()?.map?.user?.userId ?: ""
            map["consignee"] = name // 联系人
            map["province"] = mProvince // 收件人省份
            map["city"] = mCity // 收件人城市
            map["mobile"] = phone // 收件人电话号
            map["address"] = detailAddress // 收件人详细地址
            map["postcode"] = postalCode // 邮编地址
            FhssApplication.getFhssApplication().getFhssApi().insertUserAddress(map).enqueue(object : OnMyActivityRequestListener<BaseBean>(this) {
                override fun onSuccess(bean: BaseBean?) {
                    setResult(Activity.RESULT_OK, Intent())
                    finish()
                }
            })
        }
    }

    private fun selectCity() {
        val task = AddressPickTask(this)
        task.setHideProvince(false)
        task.setHideCounty(false)
        task.setCallback(object : AddressPickTask.Callback {
            override fun onAddressInitFailed() {
                ToastUtils.showShort(mContext, "数据初始化失败")
            }

            override fun onAddressPicked(province: Province, city: City, county: County?) {
                mProvince = province.areaName
                mCity = city.areaName
                mRegionTextView.text = "$mProvince  $mCity  ${county?.areaName}"
            }
        })
        // 默认选中
        task.execute("北京市", "北京市", "东城区")
    }

}