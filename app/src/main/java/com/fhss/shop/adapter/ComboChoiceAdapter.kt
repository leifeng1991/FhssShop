package com.fhss.shop.adapter

import com.chad.library.adapter.base.BaseViewHolder
import com.fhss.shop.R
import com.fhss.shop.bean.ComBoChoiceListBean
import com.zrq.base.base.BaseAdapter
import kotlinx.android.synthetic.main.item_combo_choice.view.*

/**
 * 描述:
 *
 * @author leifeng
 *         2018/10/10 12:54
 */

class ComboChoiceAdapter(layoutResId: Int, data: MutableList<ComBoChoiceListBean.ListBean>?) : BaseAdapter<ComBoChoiceListBean.ListBean>(layoutResId, data) {
    open var selectIndex = 0
    override fun convert(helper: BaseViewHolder, item: ComBoChoiceListBean.ListBean) {
        val position = data.indexOf(item)
        helper.itemView.apply {
            mTimeTextView.text = item.timeCycle
            mIntegralTextView.text = "${item.price}积分"
            mCheckedImageView.setImageResource(if (selectIndex == position) R.mipmap.ic_circle_checked else R.mipmap.ic_circle_normal)
        }


    }
}