package com.fhss.shop.adapter

import android.support.v4.content.ContextCompat
import android.widget.TextView
import com.chad.library.adapter.base.BaseViewHolder
import com.fhss.shop.R
import com.fhss.shop.bean.ClassifyListBean
import com.zrq.base.base.BaseAdapter
import kotlinx.android.synthetic.main.item_category_left_title.view.*

/**
 * 描述:
 *
 * @author leifeng
 *         2018/10/10 15:38
 */

class CategoryLeftTitleAdapter(layoutResId: Int, data: MutableList<ClassifyListBean.DataBean>?) : BaseAdapter<ClassifyListBean.DataBean>(layoutResId, data) {
    open var selectionIndex = 0

    override fun convert(helper: BaseViewHolder, item: ClassifyListBean.DataBean) {
        val mLeftTitleTextView = helper.getView<TextView>(R.id.leftTitleTextView)
        helper.itemView.setBackgroundColor(ContextCompat.getColor(mContext, if (getItem(selectionIndex) == item) R.color.colorSelectedBg else R.color.colorNormalBg))
        helper.itemView.apply {
            leftTitleTextView.setTextColor(ContextCompat.getColor(mContext, if (getItem(selectionIndex) == item) R.color.colorSelectedTextColor else R.color.colorTextBlack_333333))
        }
        mLeftTitleTextView.text = item.classifyName
    }

}