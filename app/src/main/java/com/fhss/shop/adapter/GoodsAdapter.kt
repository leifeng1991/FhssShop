package com.fhss.shop.adapter

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseViewHolder
import com.fhss.shop.R
import com.fhss.shop.bean.GoodsListBean
import com.fhss.shop.utils.AddShopCartUtil
import com.fhss.shop.utils.ImageLoadUtils
import com.zrq.base.base.BaseActivity
import com.zrq.base.base.BaseAdapter
import com.zrq.spanbuilder.Spans
import kotlinx.android.synthetic.main.item_goods_list.view.*

/**
 * 描述:
 *
 * @author leifeng
 *         2018/10/10 15:38
 */

class GoodsAdapter(activity: BaseActivity, layoutResId: Int, data: MutableList<GoodsListBean.DataBean.RowsBean>?) : BaseAdapter<GoodsListBean.DataBean.RowsBean>(layoutResId, data) {
    val activity: BaseActivity = activity

    override fun convert(helper: BaseViewHolder, item: GoodsListBean.DataBean.RowsBean) {
        val index = data.indexOf(item)
        helper.itemView.mLeftSpace.visibility = if (index % 2 == 0) View.VISIBLE else View.GONE
        helper.itemView.mRightSpace.visibility = if (index % 2 == 0) View.GONE else View.VISIBLE

        val mMessageImageView = helper.getView<ImageView>(R.id.mImageViewLayout)
        val mGoodsDescribeTextView = helper.getView<TextView>(R.id.mGoodsDescribeTextView)
        val mPriceTextView = helper.getView<TextView>(R.id.mPriceTextView)
        val mPricesTextView = helper.getView<TextView>(R.id.mPricesTextView)
        val mShopCartImageView = helper.getView<ImageView>(R.id.mShopCartImageView)
        // 设置数据
        ImageLoadUtils.setImageBig(mContext, item.imgUrl[0], mMessageImageView)
        mGoodsDescribeTextView.text = item.goodsName
        mPriceTextView.text = item.goodsMembers
        val goodsMarket = item.goodsMarket
        mPricesTextView.text = if (TextUtils.isEmpty(goodsMarket)) "--" else Spans.builder().text(goodsMarket).deleteLine().build()

        mShopCartImageView.setOnClickListener {
            // 加入购物车
            AddShopCartUtil.addShopCart(activity, item.goodsId, item.goodsName, item.goodsMarket, item.goodsMembers, item.goodsAgent, "1", item.imgUrl[0], item.goodsIntegral, item.goodsColor)
        }
    }


}