package com.fhss.shop.adapter

import com.chad.library.adapter.base.BaseViewHolder
import com.fhss.shop.bean.MyOrderListBean
import com.fhss.shop.utils.ImageLoadUtils
import com.zrq.base.base.BaseAdapter
import kotlinx.android.synthetic.main.item_item_order.view.*

/**
 * 描述:
 *
 * @author leifeng
 *         2018/10/10 15:38
 */

class OrderItemAdapter(layoutResId: Int, data: MutableList<MyOrderListBean.DataBean.OGoodsListBean>?) : BaseAdapter<MyOrderListBean.DataBean.OGoodsListBean>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder, item: MyOrderListBean.DataBean.OGoodsListBean) {
        helper.itemView.apply {
            ImageLoadUtils.setImageBig(mContext, item.goodsImg, mImageViewLayout)
            mMessageTitleTextView.text = item.goodsName
            mPriceTextView.text = "成交价：￥${item.goodsPrice}"
            mBackIntegralTextView.text = "(会员可返还${item.getIntegral}积分)"
        }
    }

}