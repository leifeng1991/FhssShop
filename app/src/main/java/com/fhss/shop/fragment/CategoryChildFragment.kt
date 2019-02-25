package com.fhss.shop.fragment

import android.app.Activity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fhss.shop.R
import com.fhss.shop.adapter.CategoryRightAdapter
import com.fhss.shop.base.FhssApplication
import com.fhss.shop.bean.ClassifyChildListBean
import com.zrq.base.base.BaseActivity
import com.zrq.base.base.ReuseViewFragment
import com.zrq.base.net.OnMyActivityRequestListener
import kotlinx.android.synthetic.main.fragment_child_category.view.*

/**
 * 描述:类别-子分类
 *
 * @author leifeng
 *         2018/10/9 9:17
 */

class CategoryChildFragment : ReuseViewFragment() {
    private lateinit var mCategoryRightAdapter: CategoryRightAdapter
    private lateinit var classifyId: String
    private var activity: Activity? = null

    companion object {
        fun newInstance(classifyId: String): CategoryChildFragment {
            val fragment = CategoryChildFragment()
            fragment.classifyId = classifyId
            return fragment
        }

    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        this.activity = activity
    }

    override fun loadViewLayout(inflater: LayoutInflater, container: ViewGroup?): View {
        return inflater.inflate(R.layout.fragment_child_category, container, false)
    }

    override fun initView() {
        // top分类标题列表
        rootView.topTitleList.layoutManager = LinearLayoutManager(mContext)
        mCategoryRightAdapter = CategoryRightAdapter(activity,R.layout.item_category_right, null)
        rootView.topTitleList.adapter = mCategoryRightAdapter

    }

    override fun setListener() {
        // top分类标题列表item点击事件
        mCategoryRightAdapter.setOnItemClickListener { _, _, position ->
            // 设置选中位置
            mCategoryRightAdapter.selectionIndex = position
            // 更新
            mCategoryRightAdapter.notifyDataSetChanged()
        }

    }

    override fun processLogic() {
        FhssApplication.getFhssApplication().getFhssApi().classifyChild(classifyId).enqueue(object : OnMyActivityRequestListener<ClassifyChildListBean>(activity as BaseActivity?) {
            override fun onSuccess(bean: ClassifyChildListBean) {
                val parent = bean.data.parent
                val newParent = ArrayList<ClassifyChildListBean.DataBean.ParentBean>()
                // 遍历所有二级分类
                for (parentBean in parent) {
                    val list = ArrayList<ClassifyChildListBean.DataBean.ChildBean>()
                    // 遍历所有三级分类
                    for (childBean in bean.data.child) {
                        if (parentBean.classifyId == childBean.classifyParentId) {
                            list.add(childBean)
                        }
                    }
                    if (list.size > 0) {
                        // 二级分类下有三级分类才显示
                        parentBean.list = list
                        newParent.add(parentBean)
                    }
                }
                // 设置数据
                mCategoryRightAdapter.replaceData(newParent)
            }

        })
    }

}