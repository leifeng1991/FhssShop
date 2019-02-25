package com.fhss.shop.adapter

import android.app.Activity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.chad.library.adapter.base.BaseViewHolder
import com.fhss.shop.R
import com.fhss.shop.bean.ClassifyChildListBean
import com.zrq.base.base.BaseAdapter
import kotlinx.android.synthetic.main.item_category_right.view.*

/**
 * 描述:
 *
 * @author leifeng
 *         2018/10/10 15:38
 */

class CategoryRightAdapter(var activity: Activity?, layoutResId: Int, data: MutableList<ClassifyChildListBean.DataBean.ParentBean>?) : BaseAdapter<ClassifyChildListBean.DataBean.ParentBean>(layoutResId, data) {
    open var selectionIndex = 0
    override fun convert(helper: BaseViewHolder, item: ClassifyChildListBean.DataBean.ParentBean) {
        // 二级分类标题
        val mTitleTextView = helper.getView<TextView>(R.id.mTitleTextView)
        mTitleTextView.text = item.classifyName
        // 三级分类列表
        val mCategoryRightContentList = helper.getView<RecyclerView>(R.id.mCategoryRightContentList)
        mCategoryRightContentList.layoutManager = GridLayoutManager(mContext, 3) as RecyclerView.LayoutManager?
        val adapter = CategoryRightContentAdapter(activity,R.layout.item_category_right_content, null)
        mCategoryRightContentList.adapter = adapter
        adapter.replaceData(item.list)
    }
}