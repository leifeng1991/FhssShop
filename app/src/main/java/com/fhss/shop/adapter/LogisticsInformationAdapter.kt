package com.fhss.shop.adapter

import android.support.v4.content.ContextCompat
import android.view.View
import com.chad.library.adapter.base.BaseViewHolder
import com.fhss.shop.R
import com.fhss.shop.bean.WuLiuData
import com.zrq.base.base.BaseAdapter
import kotlinx.android.synthetic.main.item_logistics_information.view.*

/**
 * 描述:
 *
 * @author leifeng
 *         2018/10/10 15:38
 */

class LogisticsInformationAdapter(layoutResId: Int, data: MutableList<WuLiuData.DataBean>?) : BaseAdapter<WuLiuData.DataBean>(layoutResId, data) {
    // state 快递单当前签收状态，包括0在途中、1已揽收、2疑难、3已签收、4退签、5同城派送中、6退回、7转单等7个状态，其中4-7需要另外开通才有效
    var state: Int? = 0

    override fun convert(helper: BaseViewHolder, item: WuLiuData.DataBean) {
        helper.itemView.mTimeTextView.text = item.ftime
        helper.itemView.mContentTextView.text = item.context

        helper.itemView.mLine.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorTextGray_999999))
        var stateString = ""
        when (state) {
            0 -> {
                stateString = "在途中"
            }
            1 -> {
                stateString = "已揽收"
            }
            2 -> {
                stateString = "疑难"
            }
            3 -> {
                stateString = "已签收"
            }
            4 -> {
                stateString = "退签"
            }
            5 -> {
                stateString = "同城派送中"
            }
            6 -> {
                stateString = "退回"
            }
            7 -> {
                stateString = "转单"
            }
        }

        helper.itemView.mStateTextView.visibility = if (data.indexOf(item) == 0) View.VISIBLE else View.GONE
        helper.itemView.mStateTextView.text = stateString
    }


}