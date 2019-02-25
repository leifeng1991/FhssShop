package com.fhss.shop.adapter

import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseViewHolder
import com.fhss.shop.R
import com.zrq.base.base.BaseAdapter

class HomeMenuAdapter(layoutResId: Int, data: MutableList<String>?, ids: MutableList<Int>) : BaseAdapter<String>(layoutResId, data) {
    val ids: MutableList<Int> = ids

    override fun convert(helper: BaseViewHolder, item: String) {
        val mMenuImageView = helper.getView<ImageView>(R.id.mMenuImageView)
        val mMenuTitleTextView = helper.getView<TextView>(R.id.mMenuTitleTextView)
        val position = data.indexOf(item)
        mMenuImageView.setImageResource(ids[position])
        mMenuTitleTextView.text = item
    }

}