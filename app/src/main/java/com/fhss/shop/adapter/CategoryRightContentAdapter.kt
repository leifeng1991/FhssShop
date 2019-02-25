package com.fhss.shop.adapter

import android.app.Activity
import com.chad.library.adapter.base.BaseViewHolder
import com.fhss.shop.R
import com.fhss.shop.activity.SearchGoodsListActivity
import com.fhss.shop.bean.ClassifyChildListBean
import com.fhss.shop.weight.ShapeButton
import com.zrq.base.base.BaseAdapter
import com.zrq.base.util.ToastUtils

/**
 * 描述:
 *
 * @author leifeng
 *         2018/10/10 15:38
 */

class CategoryRightContentAdapter(var activity: Activity?, layoutResId: Int, data: MutableList<ClassifyChildListBean.DataBean.ChildBean>?) : BaseAdapter<ClassifyChildListBean.DataBean.ChildBean>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder, item: ClassifyChildListBean.DataBean.ChildBean) {
        // 三级分类标题
        val mCategoryRightContentBtn = helper.getView<ShapeButton>(R.id.mCategoryRightContentBtn)
        mCategoryRightContentBtn.setText(item.classifyName)
        mCategoryRightContentBtn.setOnClickListener {
            // 跳转到搜索商品列表界面
            if (activity != null)
                activity?.startActivity(SearchGoodsListActivity.newIntent(mContext, "", "${item.classifyId}"))
        }
    }

}