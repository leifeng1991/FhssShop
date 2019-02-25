package com.fhss.shop.activity

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.fhss.shop.R
import com.fhss.shop.adapter.BankAdapter
import com.fhss.shop.base.FhssApplication
import com.fhss.shop.base.FhssBaseActivity
import com.fhss.shop.bean.BankListBean
import com.zrq.base.model.BaseBean
import com.zrq.base.net.OnMyActivityRequestListener
import kotlinx.android.synthetic.main.activity_bank.*

/**
 * 描述:银行卡列表
 *
 * @author leifeng
 *         2018/10/8 14:54
 */

class BankActivity : FhssBaseActivity() {
    private lateinit var mBankAdapter: BankAdapter
    private lateinit var mFooterView: View

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, BankActivity::class.java)
        }
    }

    override fun loadViewLayout() {
        setContentView(R.layout.activity_bank)
    }

    override fun initView() {
        setTitleName("银行卡")
        setTitleLeftBack()
        setTitleRightImage(R.mipmap.ic_add_address)
        // 地址列表
        mBankList.setLayoutManager(LinearLayoutManager(mContext))
        mBankList.setPullRefreshAndLoadingMoreEnabled(false, false)
        mBankAdapter = BankAdapter(R.layout.item_bank, null)
        mBankList.setAdapter(mBankAdapter)
        // 尾部布局
        mFooterView = layoutInflater.inflate(R.layout.footer_bank, null)
        mBankList.addFooterView(mFooterView)

    }

    override fun setListener() {
        // 新增银行卡
        mFooterView.setOnClickListener {
            // 跳转到新增银行卡界面
            startActivity(NewBankActivity.newIntent(mContext))
        }
    }

    override fun processLogic() {
        getBankCardList()
    }

    override fun onTitleRightClick(view: View?) {
        super.onTitleRightClick(view)
        // 跳转到新增银行卡界面
        startActivity(NewBankActivity.newIntent(mContext))
    }

    private fun getBankCardList() {
        val loginUserDoLogin = FhssApplication.getFhssApplication().getLoginUserDoLogin(this)
        if (loginUserDoLogin != null)
            FhssApplication.getFhssApplication().getFhssApi().selectBankCard(loginUserDoLogin.map.user.userId).enqueue(object : OnMyActivityRequestListener<BankListBean>(this) {
                override fun onSuccess(bean: BankListBean) {
                    mBankList.handlerSuccess(mBankAdapter, bean.lists)
                }

            })
    }
}