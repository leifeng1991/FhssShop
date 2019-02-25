package com.fhss.shop.fragment

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.fhss.shop.MainActivity
import com.fhss.shop.R
import com.fhss.shop.activity.*
import com.fhss.shop.adapter.BrandImageAdapter
import com.fhss.shop.adapter.GoodsAdapter
import com.fhss.shop.adapter.HomeBannerImageLoader
import com.fhss.shop.adapter.HomeMenuAdapter
import com.fhss.shop.base.FhssApplication
import com.fhss.shop.bean.BannerBean
import com.fhss.shop.bean.BrandListBean
import com.fhss.shop.bean.GoodsListBean
import com.fhss.shop.bean.GoodsNewListBean
import com.fhss.shop.utils.ImageLoadUtils
import com.fhss.shop.weight.GridDividerItemDecoration
import com.zrq.base.base.BaseActivity
import com.zrq.base.base.BaseFragmentPagerAdapter
import com.zrq.base.base.ReuseViewFragment
import com.zrq.base.net.OnMyActivityRequestListener
import com.zrq.base.util.BasePopupWindow
import com.zrq.base.util.PixelUtils
import com.zrq.base.util.ToastUtils
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.header_home_banner.*
import kotlinx.android.synthetic.main.header_home_banner.view.*
import kotlinx.android.synthetic.main.popu_window_selected.view.*


/**
 * 描述:首页
 *
 * @author leifeng
 *         2018/10/9 9:17
 */

class HomeFragment : ReuseViewFragment() {
    private lateinit var mPopupWindow: BasePopupWindow
    private lateinit var mGoodsAdapter: GoodsAdapter
    private lateinit var mHomeMenuAdapter: HomeMenuAdapter
    private lateinit var mBrandImageAdapter: BrandImageAdapter
    private lateinit var mHeaderView: View
    private lateinit var mNewGoodsFragmentPagerAdapter: BaseFragmentPagerAdapter
    private lateinit var mHotGoodsFragmentPagerAdapter: BaseFragmentPagerAdapter
    private var mNewLastPosition = 0
    private var mHotLastPosition = 0
    private lateinit var mMainActivity: MainActivity

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        mMainActivity = activity as MainActivity
    }

    override fun loadViewLayout(inflater: LayoutInflater, container: ViewGroup?): View {
        return inflater.inflate(R.layout.fragment_home, null)
    }

    override fun initView() {
//        setTitleRightImage(R.mipmap.ic_more)
        setTitleName("富和双盛")
//        setTitleLeft(R.mipmap.ic_home_classify)
//        setTitleRightImage(R.mipmap.ic_home_shop_cart)

        mHeaderView = layoutInflater.inflate(R.layout.header_home_banner, null)
        // 初始化轮播图
        mHeaderView.mImageViewLayout.setOffscreenPageLimit(4)
        mHeaderView.mImageViewLayout.setDelayTime(5000)
//        mHeaderView.mBanner.setPageTransformer(true, ZoomOutPageTransformer())
//        (mHeaderView.mBanner.findViewById(com.youth.banner.R.id.bannerViewPager) as ViewPager).pageMargin = PixelUtils.dip2px(mContext, -15f)// 没暴露此方法，只能重新的获取
        mHeaderView.mImageViewLayout.setImageLoader(HomeBannerImageLoader())
        // 菜单
        mHeaderView.mMenuList.layoutManager = GridLayoutManager(mContext, 4) as RecyclerView.LayoutManager?
        val list = ArrayList<Int>()
        list.add(R.mipmap.ic_menu_goods_category)
        list.add(R.mipmap.ic_menu_shop_cart)
        list.add(R.mipmap.ic_menu_credits_exchange)
        list.add(R.mipmap.ic_menu_special_offer)
        list.add(R.mipmap.ic_menu_collect_center)
        list.add(R.mipmap.ic_menu_order_center)
        list.add(R.mipmap.ic_menu_me)
        list.add(R.mipmap.ic_menu_message_center)
        mHomeMenuAdapter = HomeMenuAdapter(R.layout.item_home_menu, null, list)
        mHeaderView.mMenuList.adapter = mHomeMenuAdapter
        // 品牌
        val brandGridLayoutManager = GridLayoutManager(mContext, 3)
        mHeaderView.mBrandList.layoutManager = brandGridLayoutManager
        val brandGridDividerItemDecoration = GridDividerItemDecoration(mContext)
        mHeaderView.mBrandList.addItemDecoration(brandGridDividerItemDecoration)
        brandGridDividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.shape_brand_divider))
        mBrandImageAdapter = BrandImageAdapter(R.layout.item_brand_image, null)
        mHeaderView.mBrandList.adapter = mBrandImageAdapter
        // 新品上市
        val newsGoodsFragments = ArrayList<Fragment>()
        mNewGoodsFragmentPagerAdapter = BaseFragmentPagerAdapter(childFragmentManager, newsGoodsFragments)
        mHeaderView.mNewGoodsViewPager.adapter = mNewGoodsFragmentPagerAdapter
        mHeaderView.mNewGoodsViewPager.currentItem = 0
        // 热销商品
        val hotGoodsFragments = ArrayList<Fragment>()
        mHotGoodsFragmentPagerAdapter = BaseFragmentPagerAdapter(childFragmentManager, hotGoodsFragments)
        mHeaderView.mHotViewPager.adapter = mHotGoodsFragmentPagerAdapter
        mHeaderView.mHotViewPager.currentItem = 0
        // 商品列表
        val gridLayoutManager = GridLayoutManager(mContext, 2)
        rootView.mGoodsList.setLayoutManager(gridLayoutManager)
        val gridDividerItemDecoration = GridDividerItemDecoration(mContext)
        gridDividerItemDecoration.headerCount = 1
        rootView.mGoodsList.addItemDecoration(gridDividerItemDecoration)
        gridDividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.shape_divider))
        mGoodsAdapter = GoodsAdapter(mActivity as BaseActivity, R.layout.item_goods_list, null)
        rootView.mGoodsList.setAdapter(mGoodsAdapter)
        rootView.mGoodsList.addHeaderView(mHeaderView)
        // 初始化popupWindow
        initPopupWindow()
    }

    override fun setListener() {
        mHeaderView.mSearch.setOnClickListener {
            startActivity(SearchActivity.newIntent(mContext))
        }
        mGoodsAdapter.setOnItemClickListener { _, _, position ->
            // 跳转到商品详情
            startActivity(GoodsDetailActivity.newIntent(mContext, mGoodsAdapter.data[position].goodsId))
        }
        mHomeMenuAdapter.setOnItemClickListener { _, _, position ->
            if (position == 0) {
                mMainActivity.setCurrentItem(1)
            } else {
                val user = FhssApplication.getFhssApplication().getLoginUserDoLogin(mActivity)
                if (user != null) {
                    // 已经登录
                    when (position) {
                        0 -> mMainActivity.setCurrentItem(1)
                        1 -> mMainActivity.setCurrentItem(2)
                        2 -> startActivity(IntegralActivity.newIntent(mContext))
                        3 -> ToastUtils.showShort(mContext, "暂未开放")
                        4 -> startActivity(CollectCenterActivity.newIntent(mContext))
                        5 -> startActivity(MyOrderActivity.newIntent(mContext))
                        6 -> {
                            startActivity(PersonInfoActivity.newIntent(mContext))
                        }
                        7 -> startActivity(MessageCenterActivity.newIntent(mContext))
                    }
                }
            }


        }
    }

    override fun processLogic() {
        mHomeMenuAdapter.replaceData(resources.getStringArray(R.array.menuTitles).asList())
        getBannerList()
        getBrandList()
        getGoodsHotList("1", "3")
        getGoodsNewList("1", "3")
        getGoodsList()
    }

    override fun onTitleRightClick(view: View?) {
        super.onTitleRightClick(view)
//        mPopupWindow.showAsDropDown(rootView.mRightLayout, -(PixelUtils.dip2px(mContext, 10f)), 0, Gravity.RIGHT)
    }

    override fun onResume() {
        super.onResume()
        mHeaderView.mImageViewLayout.startAutoPlay()
    }

    override fun onPause() {
        super.onPause()
        mHeaderView.mImageViewLayout.stopAutoPlay()
    }

    private fun getBannerList() {
        FhssApplication.getFhssApplication().getFhssApi().bannerList().enqueue(object : OnMyActivityRequestListener<BannerBean>(activity as BaseActivity?) {
            override fun onSuccess(bean: BannerBean) {
                val imageUrls = ArrayList<String>()
                val list = bean.data
                for (dataBean in list) {
                    if (list.indexOf(dataBean) == list.size - 1) {
                        // 广告
                        ImageLoadUtils.setImageBig(context, dataBean.imgUrl, mHeaderView.mAdsImageView)
                    } else {
                        imageUrls.add(dataBean.imgUrl)
                    }
                }
                mHeaderView.mImageViewLayout.setImages(imageUrls)
                mHeaderView.mImageViewLayout.start()
            }

        })
    }

    private fun getBrandList() {
        FhssApplication.getFhssApplication().getFhssApi().appBrandList().enqueue(object : OnMyActivityRequestListener<BrandListBean>(activity as BaseActivity?) {
            override fun onSuccess(bean: BrandListBean) {
                mBrandImageAdapter.replaceData(bean.data)
            }

        })
    }

    /**
     * 获取热销商品
     */
    private fun getGoodsHotList(pageNumber: String, pageSize: String) {
        FhssApplication.getFhssApplication().getFhssApi().appGoodsHotList(pageNumber, pageSize).enqueue(object : OnMyActivityRequestListener<GoodsNewListBean>(activity as BaseActivity?) {
            override fun onSuccess(bean: GoodsNewListBean) {
                if ("1" == pageNumber && bean.data.total > 3) {
                    getGoodsHotList("2", "3")
                }
                setHotNewGoodsData(mHeaderView.mHotViewPager, mHotGoodsFragmentPagerAdapter, mHeaderView.mHotGoodsDotsLayout, bean.data.rows, pageNumber, mHotPageChangeListener)
            }
        })
    }

    /**
     * 获取新品
     */
    private fun getGoodsNewList(pageNumber: String, pageSize: String) {
        FhssApplication.getFhssApplication().getFhssApi().appGoodsNewList(pageNumber, pageSize).enqueue(object : OnMyActivityRequestListener<GoodsNewListBean>(activity as BaseActivity?) {
            override fun onSuccess(bean: GoodsNewListBean) {
                if ("1" == pageNumber && bean.data.total > 3) {
                    getGoodsNewList("2", "3")
                }
                setHotNewGoodsData(mHeaderView.mNewGoodsViewPager, mNewGoodsFragmentPagerAdapter, mHeaderView.mNewGoodsDotsLayout, bean.data.rows, pageNumber, mNewPageChangeListener)
            }
        })
    }

    /**
     * 设置新品和热销商品数据
     */
    private fun setHotNewGoodsData(viewPager: ViewPager, adapter: BaseFragmentPagerAdapter, mDotsLinearLayout: LinearLayout, list: ArrayList<GoodsNewListBean.DataBean.RowsBean>, pageNumber: String, listener: ViewPager.OnPageChangeListener) {
        if (list.size > 0) {
            adapter.addFragment(HomeHeaderGoodsFragment.newInstance(list))
            if ("2" == pageNumber) {
                mDotsLinearLayout.removeAllViews()
                addDots(mDotsLinearLayout, 0)
                addDots(mDotsLinearLayout, 1)
                viewPager.removeOnPageChangeListener(listener)
                viewPager.addOnPageChangeListener(listener)
            }
        }
    }

    /**
     * 添加指示器
     */
    private fun addDots(mDotsLinearLayout: LinearLayout, i: Int) {

        // 添加原点
        val view = View(mActivity)

        val params: LinearLayout.LayoutParams
        // 设置默认选中原点颜色和非选中原点
        if (i == 0) {
            params = LinearLayout.LayoutParams(20, 20)
            view.setBackgroundResource(R.mipmap.ic_dots_selected)
        } else {
            params = LinearLayout.LayoutParams(20, 20)
            view.setBackgroundResource(R.mipmap.ic_dots_normal)
        }
        // 间距
        if (i != 0) {
            params.leftMargin = 20
        }

        view.layoutParams = params
        mDotsLinearLayout.addView(view)
    }

    private val mNewPageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {

        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        }

        override fun onPageSelected(position: Int) {
            val lastView = mNewGoodsDotsLayout.getChildAt(mNewLastPosition)
            val currentView = mNewGoodsDotsLayout.getChildAt(position)
            mNewLastPosition = position
            lastView.setBackgroundResource(R.mipmap.ic_dots_normal)
            currentView.setBackgroundResource(R.mipmap.ic_dots_selected)
        }

    }

    private val mHotPageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        }

        override fun onPageSelected(position: Int) {
            val lastView = mHotGoodsDotsLayout.getChildAt(mNewLastPosition)
            val currentView = mHotGoodsDotsLayout.getChildAt(position)
            mHotLastPosition = position
            lastView.setBackgroundResource(R.mipmap.ic_dots_normal)
            currentView.setBackgroundResource(R.mipmap.ic_dots_selected)
        }

    }

    /**
     * 获取底部商品列表
     */
    private fun getGoodsList() {
        FhssApplication.getFhssApplication().getFhssApi().appGoodsList("1", "10").enqueue(object : OnMyActivityRequestListener<GoodsListBean>(activity as BaseActivity?) {
            override fun onSuccess(bean: GoodsListBean) {
                mGoodsAdapter.replaceData(bean.data.rows)
            }

        })
    }

    /**
     * 初始化popupWindow
     */
    private fun initPopupWindow() {
        val inflate = View.inflate(mContext, R.layout.popu_window_selected, null)
        mPopupWindow = BasePopupWindow(inflate, PixelUtils.dip2px(mContext, 92f), LinearLayout.LayoutParams.WRAP_CONTENT, true)
        mPopupWindow.apply {
            isFocusable = true
            isTouchable = true
            isOutsideTouchable = true
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            // 点击外部
            setOnDismissListener {
            }
            // 我的订单
            inflate.mMyOrderTextView.setOnClickListener {
                // 跳转到我的订单
                startActivity(MyOrderActivity.newIntent(mContext))
                mPopupWindow.dismiss()
            }
            // 我的积分
            inflate.mMyIntegralTextView.setOnClickListener {
                // 跳转到我的积分
                startActivity(IntegralActivity.newIntent(mContext))
                mPopupWindow.dismiss()
            }
            // 推广码
            inflate.mPromoCodeTextView.setOnClickListener {
                ToastUtils.showShort(mContext, "暂未开放")
                mPopupWindow.dismiss()
            }
            // 我的收藏
            inflate.mMyCollectTextView.setOnClickListener {
                // 跳转到我的收藏
                ToastUtils.showShort(mContext, "暂未开放")
                mPopupWindow.dismiss()
            }
        }


    }


}