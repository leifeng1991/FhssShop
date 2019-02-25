package com.fhss.shop.activity

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.fhss.shop.R
import com.fhss.shop.adapter.LogisticsInformationAdapter
import com.fhss.shop.base.FhssApplication
import com.fhss.shop.base.FhssBaseActivity
import com.fhss.shop.bean.LogisticsInformationBean
import com.fhss.shop.bean.WuLiuData
import com.google.gson.Gson
import com.zrq.base.model.BaseBean
import com.zrq.base.net.OnMyActivityRequestListener
import kotlinx.android.synthetic.main.activity_logistics_information_address.*

/**
 * 描述:物流信息
 *
 * @author leifeng
 *         2018/10/8 14:54
 */

class LogisticsInformationActivity : FhssBaseActivity() {
    private lateinit var mLogisticsInformationAdapter: LogisticsInformationAdapter
    private lateinit var mHeaderView: View
    private var orderId: String = ""

    companion object {
        const val ORDER_ID = "order_id"

        fun newIntent(context: Context, orderId: String): Intent {
            val intent = Intent(context, LogisticsInformationActivity::class.java)
            intent.putExtra(ORDER_ID, orderId)
            return intent
        }
    }

    override fun loadViewLayout() {
        setContentView(R.layout.activity_logistics_information_address)
    }

    override fun initView() {
        setTitleName("物流信息")
        setTitleLeftBack()
        mLogisticsInformationList.setLayoutManager(LinearLayoutManager(mContext))
        mLogisticsInformationList.setPullRefreshAndLoadingMoreEnabled(false, false)
        mLogisticsInformationAdapter = LogisticsInformationAdapter(R.layout.item_logistics_information, null)
        mLogisticsInformationList.setAdapter(mLogisticsInformationAdapter)
        mHeaderView = layoutInflater.inflate(R.layout.header_logistics_information, null)
        mLogisticsInformationList.addHeaderView(mHeaderView)

    }

    override fun setListener() {

    }

    override fun processLogic() {
        orderId = intent.getStringExtra(ORDER_ID)
        FhssApplication.getFhssApplication().getFhssApi().findLogistics(orderId).enqueue(object : OnMyActivityRequestListener<LogisticsInformationBean>(this) {
            override fun onSuccess(bean: LogisticsInformationBean) {
                val data = bean.data
                var wuliuData = Gson().fromJson(data, WuLiuData::class.java)
                mLogisticsInformationAdapter.state = wuliuData?.state?.toInt() ?: 0
                val list = wuliuData.data
                mLogisticsInformationList.handlerSuccess(mLogisticsInformationAdapter, list)
            }

            override fun onFailed(isServiceHintError: Boolean, code: Int, message: String?) {
                super.onFailed(isServiceHintError, code, message)
                mLogisticsInformationList.handlerError(mLogisticsInformationAdapter)
            }

        })
    }
}