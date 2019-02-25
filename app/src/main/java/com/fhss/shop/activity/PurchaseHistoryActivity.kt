package com.fhss.shop.activity

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.fhss.shop.R
import com.fhss.shop.adapter.BankAdapter
import com.fhss.shop.adapter.PurchaseHistoryAdapter
import com.fhss.shop.base.FhssBaseActivity
import kotlinx.android.synthetic.main.activity_purchase_history.*

/**
 * 描述:购买记录
 *
 * @author leifeng
 *         2018/10/8 14:54
 */

class PurchaseHistoryActivity : FhssBaseActivity() {
    private lateinit var mPurchaseHistoryAdapter: PurchaseHistoryAdapter
    private lateinit var mFooterView: View

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, PurchaseHistoryActivity::class.java)
        }
    }

    override fun loadViewLayout() {
        setContentView(R.layout.activity_purchase_history)
    }

    override fun initView() {
        setTitleName("购买记录")
        setTitleLeftBack()
        // 购买记录列表
        mPurchaseHistoryList.setLayoutManager(LinearLayoutManager(mContext))
        mPurchaseHistoryList.setPullRefreshAndLoadingMoreEnabled(false, false)
        mPurchaseHistoryAdapter = PurchaseHistoryAdapter(R.layout.item_purchase_history, null)
        mPurchaseHistoryList.setAdapter(mPurchaseHistoryAdapter)

    }

    override fun setListener() {

    }

    override fun processLogic() {
        val list = ArrayList<String>()
        list.add("1")
        list.add("1")
        list.add("1")
        list.add("1")
        list.add("1")
        mPurchaseHistoryAdapter.addData(list)
    }
}