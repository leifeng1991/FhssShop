package com.fhss.shop.activity

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import com.fhss.shop.R
import com.fhss.shop.adapter.MyCommonNavigatorAdapter
import com.fhss.shop.base.FhssBaseActivity
import com.fhss.shop.fragment.OrderFragment
import com.zrq.base.base.BaseFragmentPagerAdapter
import kotlinx.android.synthetic.main.activity_my_order.*
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import java.util.*

/**
 * 描述:我的订单
 *
 * @author leifeng
 *         2018/10/8 14:54
 */

class MyOrderActivity : FhssBaseActivity() {
    private val fragmentList = ArrayList<Fragment>()
    private val titleList = ArrayList<String>()
    private lateinit var fragmentPagerAdapter: BaseFragmentPagerAdapter
    private lateinit var indicatorAdapter: MyCommonNavigatorAdapter

    companion object {
        const val INDEX = "index"

        fun newIntent(context: Context): Intent {
            return Intent(context, MyOrderActivity::class.java)
        }
    }

    override fun loadViewLayout() {
        setContentView(R.layout.activity_my_order)
    }

    override fun initView() {
        setTitleName("我的订单")
        setTitleLeftBack()
        // 0全部
        titleList.add("全部")
        fragmentList.add(OrderFragment.newInstance(""))
        // 1待付款
        titleList.add("待付款")
        fragmentList.add(OrderFragment.newInstance("1"))
        // 2待收货
        titleList.add("待收货")
        fragmentList.add(OrderFragment.newInstance("4"))

        // 初始化viewPager和指示器
        fragmentPagerAdapter = BaseFragmentPagerAdapter(supportFragmentManager, fragmentList)
        viewPager.offscreenPageLimit = fragmentList.size// 设置预加载的数量
        viewPager.adapter = fragmentPagerAdapter
        // 指示器，稳健盈、灵活投
        val commonNavigator = CommonNavigator(mContext)
        commonNavigator.scrollPivotX = 1f
        indicatorAdapter = MyCommonNavigatorAdapter(viewPager, titleList)
        commonNavigator.adapter = indicatorAdapter
        magicIndicator.navigator = commonNavigator
        ViewPagerHelper.bind(magicIndicator, viewPager)

        // 设置当前的位置
        val intIndex = intent.getIntExtra(INDEX, 0)
        viewPager.currentItem = intIndex
    }

    override fun setListener() {

    }

    override fun processLogic() {

    }

}