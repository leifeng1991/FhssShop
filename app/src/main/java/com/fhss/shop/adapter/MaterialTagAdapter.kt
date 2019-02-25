package com.fhss.shop.adapter

import android.app.Activity
import android.support.v4.content.ContextCompat
import android.view.View
import com.fhss.shop.R
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.item_flow_text.view.*

/**
 * 描述:
 *
 * @author leifeng
 *         2018/10/16 11:41
 */

class MaterialTagAdapter(activity: Activity, datas: MutableList<String>?) : TagAdapter<String>(datas) {
    private val mActivity: Activity = activity
    var selection = 1

    override fun getView(parent: FlowLayout?, position: Int, s: String?): View {
        val  mView = mActivity.layoutInflater.inflate(R.layout.item_flow_text, null)
        mView.mTextView.text = s
        if (position == 0) {
            mView.mTextView.setTextColor(ContextCompat.getColor(mActivity, R.color.colorTextGray_999999))
            mView.mTextView.setBackgroundResource(R.drawable.shape_flow_bg)
        } else {
            mView.mTextView.setTextColor(ContextCompat.getColor(mActivity, if (selection == position) R.color.colorWhite else R.color.colorTextBlack_333333))
            mView.mTextView.setBackgroundResource(if (selection == position) R.drawable.shape_flow_selected_bg else R.drawable.shape_flow_normal_bg)
        }
        return mView
    }
}