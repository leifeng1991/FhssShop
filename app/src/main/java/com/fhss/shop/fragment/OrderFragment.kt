package com.fhss.shop.fragment

import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fhss.shop.R
import com.fhss.shop.activity.LogisticsInformationActivity
import com.fhss.shop.adapter.OrderAdapter
import com.fhss.shop.base.FhssApplication
import com.fhss.shop.bean.MyOrderListBean
import com.zrq.base.base.BaseActivity
import com.zrq.base.base.ReuseViewFragment
import com.zrq.base.net.OnMyActivityRequestListener
import kotlinx.android.synthetic.main.fragment_order_list.view.*
import retrofit2.Call

/**
 * 描述:订单列表
 *
 * @author leifeng
 *         2018/10/9 9:17
 */

class OrderFragment : ReuseViewFragment() {
    private lateinit var mOrderAdapter: OrderAdapter
    private lateinit var mOrderState: String

    companion object {
        const val ORDER_STATE_1 = 0 // 全部
        const val ORDER_STATE_2 = 1 // 待付款
        const val ORDER_STATE_3 = 2 // 待发货

        fun newInstance(state: String): OrderFragment {
            val fragment = OrderFragment()
            fragment.mOrderState = state
            return fragment
        }

    }

    override fun loadViewLayout(inflater: LayoutInflater, container: ViewGroup?): View {
        return inflater.inflate(R.layout.fragment_order_list, container, false)
    }

    override fun initView() {
        // 订单列表
        rootView.mMyOrderList.setLayoutManager(LinearLayoutManager(mContext))
        mOrderAdapter = OrderAdapter(R.layout.item_order, null)
        rootView.mMyOrderList.setAdapter(mOrderAdapter)

    }

    override fun setListener() {
        mOrderAdapter.setOnLogisticsInformationListener(object : OrderAdapter.OnLogisticsInformationListener {
            override fun onLogisticsInformation(orderId: String) {
                startActivity(LogisticsInformationActivity.newIntent(mContext, orderId))
            }

        })
    }

    override fun processLogic() {
        getOrderList()
    }

    /**
     * 获取商品列表
     */
    private fun getOrderList() {
        val loginUserDoLogin = FhssApplication.getFhssApplication().getLoginUserDoLogin(mActivity)
        // 订单状态：0待提交订单（APP未确认） 1待审核订单（APP未确认）  2取消订单（APP）  3驳回订单 4待确认订单
        if (loginUserDoLogin != null) {
            val orderList: Call<MyOrderListBean> = if (TextUtils.isEmpty(mOrderState)) FhssApplication.getFhssApplication().getFhssApi().findAllGoods(loginUserDoLogin.map.user.userId)
            else FhssApplication.getFhssApplication().getFhssApi().getOrderList(loginUserDoLogin.map.user.userId, mOrderState)

            orderList.enqueue(object : OnMyActivityRequestListener<MyOrderListBean>(mActivity as BaseActivity?) {
                override fun onSuccess(bean: MyOrderListBean?) {
                    rootView.mMyOrderList.handlerSuccess(mOrderAdapter, bean?.data)
                }

                override fun onFailed(isServiceHintError: Boolean, code: Int, message: String?) {
                    super.onFailed(isServiceHintError, code, message)
                    rootView.mMyOrderList.handlerError(mOrderAdapter)
                }
            })
        }

    }

}