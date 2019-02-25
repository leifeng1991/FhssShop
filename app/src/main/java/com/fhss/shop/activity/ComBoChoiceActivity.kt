package com.fhss.shop.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.fhss.shop.R
import com.fhss.shop.adapter.ComboChoiceAdapter
import com.fhss.shop.base.FhssApplication
import com.fhss.shop.base.GrayStatusBarBaseActivity
import com.fhss.shop.bean.ComBoChoiceListBean
import com.zrq.base.model.BaseBean
import com.zrq.base.net.OnMyActivityRequestListener
import com.zrq.base.util.ToastUtils
import kotlinx.android.synthetic.main.activity_collect_center.*
import kotlinx.android.synthetic.main.activity_combo_choice.*
import kotlinx.android.synthetic.main.layout_title_bar_1.*

/**
 * 描述:套餐选择
 *
 * @author leifeng
 *         2018/10/8 14:54
 */

class ComBoChoiceActivity : GrayStatusBarBaseActivity() {
    private lateinit var mComboChoiceAdapter: ComboChoiceAdapter

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, ComBoChoiceActivity::class.java)
        }
    }

    override fun loadViewLayout() {
        setContentView(R.layout.activity_combo_choice)
    }

    override fun initView() {
        // 套餐列表
        mComboChoiceList.setLayoutManager(LinearLayoutManager(mContext))
        mComboChoiceAdapter = ComboChoiceAdapter(R.layout.item_combo_choice, null)
        mComboChoiceList.setAdapter(mComboChoiceAdapter)
        mComboChoiceList.setPullRefreshAndLoadingMoreEnabled(false, false)
    }

    override fun setListener() {
        // 返回点击监听事件
        iv_title_left.setOnClickListener {
            // 需要带返回值
            if (mComboChoiceLayout.visibility == View.GONE)
                setResult(Activity.RESULT_OK, Intent())
            // 关闭界面
            finish()
        }
        // 列表item点击事件
        mComboChoiceAdapter.setOnItemClickListener { _, _, position ->
            // 选中位置
            mComboChoiceAdapter.selectIndex = position
            // 更新
            mComboChoiceAdapter.notifyDataSetChanged()
            ToastUtils.showShort(mContext, "${mComboChoiceAdapter.getItem(position)}")
        }
        // 提交监听事件
        mSubmitButton.setOnClickListener {
            // 进行网络请求
            submit()
        }
    }

    override fun processLogic() {
        FhssApplication.getFhssApplication().getFhssApi().selectALLMemberCombo().enqueue(object : OnMyActivityRequestListener<ComBoChoiceListBean>(this) {
            override fun onSuccess(bean: ComBoChoiceListBean) {
                mComboChoiceList.handlerSuccess(mComboChoiceAdapter, bean.list)
            }

            override fun onFailed(isServiceHintError: Boolean, code: Int, message: String?) {
                super.onFailed(isServiceHintError, code, message)
                mComboChoiceList.handlerError(mComboChoiceAdapter)
            }

        })
    }

    override fun onBackPressed() {
        if (mComboChoiceLayout.visibility == View.GONE)
            setResult(Activity.RESULT_OK, Intent())
        // 关闭界面
        finish()
    }

    /**
     * 提交
     */
    private fun submit() {
        if (mComboChoiceAdapter.data.size > 0) {
            val listBean = mComboChoiceAdapter.data[mComboChoiceAdapter.selectIndex]
            val loginUserDoLogin = FhssApplication.getFhssApplication().getLoginUserDoLogin(this)
            if (loginUserDoLogin != null)
                FhssApplication.getFhssApplication().getFhssApi().insertMemberRecharge(loginUserDoLogin.map.user.userId, "${listBean.price}", "1").enqueue(object : OnMyActivityRequestListener<BaseBean>(this) {
                    override fun onSuccess(bean: BaseBean?) {
                        t(loginUserDoLogin.map.user.userId, listBean.timeCycle, "${listBean.price}")
                    }

                })

        } else {
            ToastUtils.showShort(mContext, "网络请求失败")
        }

    }

    private fun t(userId: String, timeCycle: String, price: String) {
        FhssApplication.getFhssApplication().getFhssApi().insertMemberTime(userId, timeCycle, price).enqueue(object : OnMyActivityRequestListener<BaseBean>(this) {
            override fun onSuccess(bean: BaseBean?) {
                mComboChoiceLayout.visibility = View.GONE
                mSuccessLayout.visibility = View.VISIBLE
                ToastUtils.showShort(mContext, "提交成功")
            }

        })
    }
}