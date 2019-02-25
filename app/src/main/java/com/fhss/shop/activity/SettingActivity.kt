package com.fhss.shop.activity

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import com.fhss.shop.R
import com.fhss.shop.base.FhssApplication
import com.fhss.shop.base.FhssBaseActivity
import com.fhss.shop.utils.DialogUtil
import com.zrq.base.util.DialogBuilder
import com.zrq.base.util.ToastUtils
import kotlinx.android.synthetic.main.activity_setting.*

/**
 * 描述:设置界面
 *
 * @author leifeng
 *         2018/10/8 14:54
 */

class SettingActivity : FhssBaseActivity() {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, SettingActivity::class.java)
        }

    }

    override fun loadViewLayout() {
        setContentView(R.layout.activity_setting)
    }

    override fun initView() {
        setTitleName("设置")
        setTitleLeftBack()
    }

    override fun setListener() {
        mAboutTextView.setOnClickListener {
            // 跳转到关于我们
            startActivity(AboutActivity.newIntent(mContext))
        }
        // 清除缓存
        mClearCacheTextView.setOnClickListener {
            ToastUtils.showShort(mContext, "清除缓存成功")
        }
        // 退出登录
        mPurchaseHistoryTextView.setOnClickListener {
            showExitDialog()
        }
    }

    override fun processLogic() {
    }

    private fun showExitDialog() {
        DialogUtil.showRawDialog(this,"确定要退出登录？") { dialog, _ ->
            FhssApplication.getFhssApplication().setExitLoginUser()
            finish()
            dialog.dismiss()
        }
    }

}