package com.fhss.shop.adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseViewHolder
import com.fhss.shop.R
import com.fhss.shop.bean.MyOrderListBean
import com.zrq.base.base.BaseAdapter
import kotlinx.android.synthetic.main.item_order.view.*

/**
 * 描述:
 *
 * @author leifeng
 *         2018/10/10 15:38
 */

class OrderAdapter(layoutResId: Int, data: MutableList<MyOrderListBean.DataBean>?) : BaseAdapter<MyOrderListBean.DataBean>(layoutResId, data) {
    private var mOnLogisticsInformationListener: OnLogisticsInformationListener? = null
    override fun convert(helper: BaseViewHolder, item: MyOrderListBean.DataBean) {
        helper.itemView.apply {
            mTimeTextView.text = item.creatTime
            // 订单状态：0待提交订单（APP未确认） 1待审核订单（APP未确认）  2取消订单（APP）  3驳回订单 4待确认订单
            when (item.orderStatus) {
                0 -> {
                    mStateTextView.text = "待提交"
                }
                1 -> {
                    mStateTextView.text = "待审核"
                }
                2 -> {
                    mStateTextView.text = "已取消"
                }
                3 -> {
                    mStateTextView.text = "已驳回"
                }
                4 -> {
                    mStateTextView.text = "待确认"
                }
            }
            // 订单列表
            mGoodsList.layoutManager = LinearLayoutManager(mContext)
            val mOrderAdapter = OrderItemAdapter(R.layout.item_item_order, item.oGoodsList)
            mGoodsList.adapter = mOrderAdapter

            mButton1.setOnClickListener {
                mOnLogisticsInformationListener?.onLogisticsInformation(item.orderId)
            }
        }
    }

    fun setOnLogisticsInformationListener(mOnLogisticsInformationListener: OnLogisticsInformationListener) {
        this.mOnLogisticsInformationListener = mOnLogisticsInformationListener
    }

    interface OnLogisticsInformationListener {
        fun onLogisticsInformation(orderId: String)
    }

}