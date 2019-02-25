package com.fhss.shop.adapter

import android.view.View
import com.chad.library.adapter.base.BaseViewHolder
import com.fhss.shop.bean.SearchGoodsListBean
import com.fhss.shop.utils.ImageLoadUtils
import com.zrq.base.base.BaseAdapter
import kotlinx.android.synthetic.main.item_search_goods_list.view.*

/**
 * 描述:
 *
 * @author leifeng
 *         2018/10/10 15:38
 */

class SearchGoodsAdapter(layoutResId: Int, data: MutableList<SearchGoodsListBean.DataBean>?) : BaseAdapter<SearchGoodsListBean.DataBean>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder, item: SearchGoodsListBean.DataBean?) {
        val index = data.indexOf(item)

        helper.itemView.apply {
            mLeftSpace.visibility = if ( index % 2 == 0) View.VISIBLE else View.GONE
            mRightSpace.visibility = if ( index % 2 == 0) View.GONE else View.VISIBLE
            ImageLoadUtils.setImageBig(mContext, item?.imgUrl!![0],mImageViewLayout)
            mGoodsDescribeTextView.text = item?.goodsName
            mIntegralTextView.text = "(会员可获得${item?.goodsIntegral}积分)"
            mPricesTextView.text = "零售价:￥${item?.goodsMarket}"
            mPriceTextView.text = "会员价:￥${item?.goodsMarket}"
        }
    }


}