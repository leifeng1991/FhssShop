package com.fhss.shop.adapter

import com.chad.library.adapter.base.BaseViewHolder
import com.fhss.shop.bean.ShopCartGoodsListBean
import com.zrq.base.base.BaseAdapter
import kotlinx.android.synthetic.main.item_confirm_order.view.*

/**
 * 描述:
 *
 * @author leifeng
 *         2018/10/10 15:38
 */

class ConfirmOrderAdapter(layoutResId: Int, data: MutableList<ShopCartGoodsListBean.MapBean.ListBean>?) : BaseAdapter<ShopCartGoodsListBean.MapBean.ListBean>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder, item: ShopCartGoodsListBean.MapBean.ListBean?) {
        val position = data.indexOf(item)
        helper.itemView.apply {
            mNumberTextView.text = "$position"
            mNameTextView.text = item?.goodsName
            mCountTextView.text = item?.goodsNumber
            mNewPriceTextView.text = item?.goodsMembers
        }
    }

}