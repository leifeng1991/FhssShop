package com.fhss.shop.adapter

import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseViewHolder
import com.fhss.shop.R
import com.fhss.shop.bean.GoodsNewListBean
import com.fhss.shop.utils.AddShopCartUtil
import com.fhss.shop.utils.ImageLoadUtils
import com.zrq.base.base.BaseActivity
import com.zrq.base.base.BaseAdapter
import com.zrq.spanbuilder.Spans

/**
 * 描述:
 *
 * @author leifeng
 *         2018/10/10 15:38
 */

class HomeHeaderGoodsAdapter(activity: BaseActivity, layoutResId: Int, data: MutableList<GoodsNewListBean.DataBean.RowsBean>?) : BaseAdapter<GoodsNewListBean.DataBean.RowsBean>(layoutResId, data) {
    val activity: BaseActivity = activity

    override fun convert(helper: BaseViewHolder, item: GoodsNewListBean.DataBean.RowsBean) {
        // 商品图片
        val mMessageImageView = helper.getView<ImageView>(R.id.mImageViewLayout)
        // 商品描述
        val mGoodsDescribeTextView = helper.getView<TextView>(R.id.mGoodsDescribeTextView)
        // 现价
        val mPriceTextView = helper.getView<TextView>(R.id.mPriceTextView)
        // 原价
        val mPricesTextView = helper.getView<TextView>(R.id.mPricesTextView)
        // 购物车
        val mShopCartImageView = helper.getView<ImageView>(R.id.mShopCartImageView)

        // 设置相关数据
        ImageLoadUtils.setImageBig(mContext, item.imgUrl[0], mMessageImageView)
        mGoodsDescribeTextView.text = item.goodsName
        mPriceTextView.text = item.goodsMembers
        mPricesTextView.text = Spans.builder().text(item.goodsMarket).deleteLine().build()
        mShopCartImageView.setOnClickListener {
            // 加入购车网络请求
            // 加入购物车
            AddShopCartUtil.addShopCart(activity, item.goodsId, item.goodsName, item.goodsMarket, item.goodsMembers, item.goodsAgent, "1", item.imgUrl[0], item.goodsIntegral, item.goodsColor)

        }
    }


}
