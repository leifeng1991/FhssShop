package com.fhss.shop.fragment

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fhss.shop.R
import com.fhss.shop.activity.SearchActivity
import com.fhss.shop.adapter.CategoryLeftTitleAdapter
import com.fhss.shop.base.FhssApplication
import com.fhss.shop.bean.ClassifyListBean
import com.zrq.base.base.BaseActivity
import com.zrq.base.base.ReuseViewFragment
import com.zrq.base.net.OnMyActivityRequestListener
import kotlinx.android.synthetic.main.fragment_category.view.*

/**
 * 描述:类别
 *
 * @author leifeng
 *         2018/10/9 9:17
 */

class CategoryFragment : ReuseViewFragment() {
    private lateinit var adapter: CategoryLeftTitleAdapter;
    private var mFragments = ArrayList<Fragment>()


    override fun loadViewLayout(inflater: LayoutInflater, container: ViewGroup?): View {
        return inflater.inflate(R.layout.fragment_category, null)
    }

    override fun initView() {
        setTitleName("全部分类")
//        setTitleRightImage(R.mipmap.ic_more)
        rootView.mLeftTitleList.layoutManager = LinearLayoutManager(mContext)
        adapter = CategoryLeftTitleAdapter(R.layout.item_category_left_title, null)
        rootView.mLeftTitleList.adapter = adapter
    }

    override fun setListener() {
        rootView.mSearchLayout.setOnClickListener {
            // 跳转到搜索界面
            startActivity(SearchActivity.newIntent(mContext))
        }
        adapter.setOnItemClickListener { _, _, position ->
            adapter.selectionIndex = position
            adapter.notifyDataSetChanged()
            // 跳转到评论详情页
            rootView.viewPager.currentItem = position
        }
        rootView.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                adapter.selectionIndex = position
                adapter.notifyDataSetChanged()
            }

        })

    }

    override fun processLogic() {
        FhssApplication.getFhssApplication().getFhssApi().classifyFindAll().enqueue(object : OnMyActivityRequestListener<ClassifyListBean>(activity as BaseActivity?) {
            override fun onSuccess(bean: ClassifyListBean) {
                val datas = bean.data
                adapter.addData(datas)
                for (data in datas) {
                    mFragments.add(CategoryChildFragment.newInstance(data.classifyId))
                }
                rootView.viewPager.adapter = MyFragmentPagerAdapter(childFragmentManager)
            }
        })
    }

    private inner class MyFragmentPagerAdapter internal constructor(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return mFragments[position]
        }

        override fun getCount(): Int {
            return mFragments.size
        }
    }
}