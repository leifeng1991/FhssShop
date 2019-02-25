package com.fhss.shop.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseViewHolder
import com.fhss.shop.R
import com.fhss.shop.bean.BrandListBean
import com.zrq.base.base.BaseAdapter
import com.zrq.base.model.GlideApp

/**
 * 描述:
 *
 * @author leifeng
 *         2018/10/10 15:38
 */

class BrandImageAdapter(layoutResId: Int, data: MutableList<BrandListBean.DataBean>?) : BaseAdapter<BrandListBean.DataBean>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder, item: BrandListBean.DataBean) {
        val mImageView:ImageView  = helper.getView(R.id.mBrandImageView)
        GlideApp.with(mContext).load(item.brandLogo).into(mImageView)
    }

}

