package com.fhss.shop.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseViewHolder
import com.fhss.shop.R
import com.fhss.shop.utils.ImageLoadUtils
import com.zrq.base.base.BaseAdapter

/**
 * 描述:
 *
 * @author leifeng
 *         2018/10/10 15:38
 */

class GoodsImageAdapter(layoutResId: Int, data: MutableList<String>?) : BaseAdapter<String>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder, item: String?) {
        val mMessageImageView = helper.getView<ImageView>(R.id.mImageViewLayout)
        ImageLoadUtils.setImageConstrainScale(mContext,item,mMessageImageView)
    }

}