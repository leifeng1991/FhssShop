package com.fhss.shop.adapter

import com.chad.library.adapter.base.BaseViewHolder
import com.fhss.shop.bean.CollectListBean
import com.fhss.shop.utils.ImageLoadUtils
import com.zrq.base.base.BaseAdapter
import kotlinx.android.synthetic.main.item_collect_center.view.*

/**
 * 描述:
 *
 * @author leifeng
 *         2018/10/10 15:38
 */

class CollectCenterAdapter(layoutResId: Int, data: MutableList<CollectListBean.ListBean>?) : BaseAdapter<CollectListBean.ListBean>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder, item: CollectListBean.ListBean) {
        helper.itemView.apply {
            ImageLoadUtils.setImageBig(mContext, item.goodsImg, mImageViewLayout)
            mMessageTitleTextView.text = item.goodsName
            mBackIntegralTextView.text = "(会员可返还${item.goodsIntegral}积分)"
            mPriceTextView.text = "零售价：${item.goodsMarket}"
            mVipPriceTextView.text = "${item.goodsMembers}"
        }
    }

}