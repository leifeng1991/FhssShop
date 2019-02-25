package com.fhss.shop.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.CoordinatorLayout
import android.support.v4.content.ContextCompat
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.fhss.shop.MainActivity
import com.fhss.shop.R
import com.fhss.shop.adapter.*
import com.fhss.shop.base.FhssApplication
import com.fhss.shop.base.FhssBaseActivity
import com.fhss.shop.bean.ConfirmOderGoodsBean
import com.fhss.shop.bean.GoodsDetailBean
import com.fhss.shop.bean.ShopCartGoodsListBean
import com.fhss.shop.utils.AddShopCartUtil
import com.fhss.shop.utils.PixelUtil
import com.youth.banner.BannerConfig
import com.zrq.base.model.BaseBean
import com.zrq.base.net.OnMyActivityRequestListener
import com.zrq.base.util.LogUtil
import com.zrq.base.util.TextViewUtils
import com.zrq.base.util.ToastUtils
import com.zrq.spanbuilder.Spans
import kotlinx.android.synthetic.main.activity_goods_detail.*
import kotlinx.android.synthetic.main.dialog_goods_parameter_list.view.*
import kotlinx.android.synthetic.main.layout_goods_detail_bottom.*
import kotlinx.android.synthetic.main.toolbar_layout_1.*


/**
 * 描述:商品详情
 *
 * @author leifeng
 *         2018/10/8 14:54
 */

class GoodsDetailActivity : FhssBaseActivity() {
    private var dialog: BottomSheetDialog? = null
    private lateinit var dialogView: View
    private lateinit var mGoodsParameterAdapter: GoodsParameterAdapter
    private lateinit var mMaterialTagAdapter: MaterialTagAdapter
    private lateinit var mTypeTagAdapter: MaterialTagAdapter
    private lateinit var mRelatedProductsAdapter: RelatedProductsAdapter
    private lateinit var mGoodsImageAdapter: GoodsImageAdapter
    private lateinit var mGoodsDetailBean: GoodsDetailBean.DataBean
    //距离top高度
    private var statusHeight: Int = 0
    // true:透明度从1-0 false:透明度从0-1
    private var isAlphaShow = true
    private lateinit var mGoodsId: String
    private lateinit var bannerImageUrls: ArrayList<String>

    companion object {
        const val GOODS_ID = "goods_id"
        fun newIntent(context: Context, mGoodsId: String): Intent {
            val intent = Intent(context, GoodsDetailActivity::class.java)
            intent.putExtra(GOODS_ID, mGoodsId)
            return intent
        }
    }

    override fun loadViewLayout() {
        //设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        //设置全屏
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_goods_detail)
    }

    override fun initView() {
        mGoodsId = intent.getStringExtra(GOODS_ID)
        statusHeight = PixelUtil.dip2px(this, 25f)
        // 标题栏
        id_mtl_toolbar.setBackgroundColor(Color.argb(0, 250, 251, 253))// fafbfd
        id_mtl_toolbar.setPadding(0, statusHeight / 2, 0, statusHeight / 2)

        // 设置图片高度
        val width = PixelUtil.getScreenWidth(mContext)
        mImageViewLayout.layoutParams.height = width
        // 不显示指示器和标题
        mImageViewLayout.setBannerStyle(BannerConfig.NOT_INDICATOR)
        mImageViewLayout.setImageLoader(HomeBannerImageLoader())


        val list1 = ArrayList<String>()
        list1.add("套餐类型")
        list1.add("官方套餐")
        list1.add("套餐一")
        mTypeTagAdapter = MaterialTagAdapter(this, list1)
        mTypeFlowLayout.adapter = mTypeTagAdapter
        // 相关商品
        val gridLayoutManager = GridLayoutManager(mContext, 3)
        gridLayoutManager.isSmoothScrollbarEnabled = true
        gridLayoutManager.isAutoMeasureEnabled = true
        mRelatedProductsList.layoutManager = gridLayoutManager
        mRelatedProductsList.setHasFixedSize(true)
        mRelatedProductsList.isNestedScrollingEnabled = false
        mRelatedProductsAdapter = RelatedProductsAdapter(R.layout.item_related_products, null)
        mRelatedProductsList.adapter = mRelatedProductsAdapter
        mRelatedProductsAdapter.addData(list1)
        // 商品图片列表
        val layoutManager = LinearLayoutManager(this)
        layoutManager.isSmoothScrollbarEnabled = true
        layoutManager.isAutoMeasureEnabled = true
        mImagesDetailsList.layoutManager = layoutManager
        mImagesDetailsList.setHasFixedSize(true)
        mImagesDetailsList.isNestedScrollingEnabled = false
        mGoodsImageAdapter = GoodsImageAdapter(R.layout.item_goods_image, null)
        mImagesDetailsList.adapter = mGoodsImageAdapter

    }

    override fun setListener() {
        // banner点击事件
        mImageViewLayout.setOnBannerListener {
            // 跳转到预览界面
            startActivity(ImageViewPageActivity.newIntent(mContext, bannerImageUrls, it))
        }
        //标题栏背景变化
        mNestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->
            toolbarChange(scrollY)
        })
        // 返回键处理
        leftBackImage.setOnClickListener {
            finish()
        }
        leftBackImages.setOnClickListener {
            finish()
        }
        // 购物车
        mCollectImageView.setOnClickListener {
            startActivity(MainActivity.newIntent(mContext, 2))
        }
        mCollectImageViews.setOnClickListener {
            startActivity(MainActivity.newIntent(mContext, 2))
        }
        // 颜色
        mMaterialFlowLayout.setOnTagClickListener { _, position, _ ->
            mMaterialTagAdapter.selection = position
            mMaterialTagAdapter.notifyDataChanged()
            true
        }
        mTypeFlowLayout.setOnTagClickListener { _, position, _ ->
            mTypeTagAdapter.selection = position
            mTypeTagAdapter.notifyDataChanged()
            true
        }
        // 减
        mSubImageView.setOnClickListener {
            var toInt = mGoodsNumberTextView.text.trim().toString().toInt()
            if (toInt == 1) {
                ToastUtils.showShort(mContext, "已经到达最小值")
                return@setOnClickListener
            }
            toInt--
            mGoodsNumberTextView.text = "$toInt"
        }
        // 加
        mAddImageView.setOnClickListener {
            var toInt = mGoodsNumberTextView.text.trim().toString().toInt()
            toInt++
            mGoodsNumberTextView.text = "$toInt"
        }
        // 商品参数
        mParameterTextView.setOnClickListener {
            showBottomDialog()
        }
        // 图片详情点击事件
        mGoodsImageAdapter.setOnItemClickListener { _, _, position ->
            val list = ArrayList<String>()
            for (datum in mGoodsImageAdapter.data) {
                list.add(datum)
            }
            // 跳转到预览界面
            startActivity(ImageViewPageActivity.newIntent(mContext, list, position))
        }
        // 收藏
        mCollectTextView.setOnClickListener {
            // 添加收藏
            addCell(mGoodsId)
        }
        // 加入购物车
        mAddShopCartButton.setOnClickListener {
            AddShopCartUtil.addShopCart(this, "${mGoodsDetailBean.goodsId}",
                    mGoodsDetailBean.goodsName, "${mGoodsDetailBean.goodsMarket}",
                    "${mGoodsDetailBean.goodsMembers}", "${mGoodsDetailBean.goodsAgent}",
                    mGoodsNumberTextView.text.trim().toString(), mGoodsDetailBean.imgUrl[0], "${mGoodsDetailBean.goodsIntegral}", mGoodsDetailBean.goodsColor)
        }
        // 立即购买
        mBuyNowButton.setOnClickListener {
            val user = FhssApplication.getFhssApplication().getLoginUser()
            val mConfirmOderGoodsBean = ConfirmOderGoodsBean()
            val list = ArrayList<ShopCartGoodsListBean.MapBean.ListBean>()
            val bean = ShopCartGoodsListBean.MapBean.ListBean(
                    "${mGoodsDetailBean.goodsAgent}", mGoodsDetailBean.goodsId, "${mGoodsDetailBean.goodsMarket}",
                    "${mGoodsDetailBean.goodsMembers}", mGoodsDetailBean.goodsName,
                    mGoodsNumberTextView.text.trim().toString(), mGoodsDetailBean.goodsSn, mGoodsDetailBean.goodsImg, user?.map?.user?.userId!!.toInt(), mGoodsDetailBean.goodsIntegral, mGoodsDetailBean.goodsColor)
            list.add(bean)
            mConfirmOderGoodsBean.list = list
            val number = mGoodsNumberTextView.text.trim().toString().toInt()
            // 跳转到确认订单界面
            startActivity(ConfirmOrderActivity.newIntent(mContext, mConfirmOderGoodsBean, "${mGoodsDetailBean.goodsMembers * number}", "${mGoodsDetailBean.goodsIntegral * number}"))
        }
    }

    override fun processLogic() {
        getGoodsDetail()
    }

    //如果你需要考虑更好的体验，可以这么操作
    override fun onStart() {
        super.onStart()
        //开始轮播
        mImageViewLayout.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
        //结束轮播
        mImageViewLayout.stopAutoPlay()
    }

    /**
     * 获取商品详情
     */
    private fun getGoodsDetail() {
        FhssApplication.getFhssApplication().getFhssApi().getGoodsDes(mGoodsId).enqueue(object : OnMyActivityRequestListener<GoodsDetailBean>(this) {
            override fun onSuccess(bean: GoodsDetailBean) {
                setData(bean.data)
            }


        })
    }

    /**
     * 设置数据
     */
    private fun setData(data: GoodsDetailBean.DataBean) {
        mGoodsDetailBean = data
        bannerImageUrls = ArrayList()
        for (s in data.imgUrl) {
            LogUtil.e("-------------------------${s.trim().toString()}")
            bannerImageUrls.add(s.trim().toString())
        }
        mImageViewLayout.setOffscreenPageLimit(bannerImageUrls.size)
        mImageViewLayout.setImages(bannerImageUrls)
        mImageViewLayout.start()
        mMarketPriceTextView.text = "市场价:${data.goodsMarket}"
        mVipPriceTextView.text = Spans.builder().text("会员:").text("${data.goodsMembers}").color(ContextCompat.getColor(mContext, R.color.colorPrice)).build()
        mMessageTitleTextView.text = data.goodsName
        mIntegralTextView.text = "(会员可获得${data.goodsIntegral}积分)"
        mLocationTextView.text = data.goodsLocation

        val colorLists = ArrayList<String>()
        colorLists.add("颜色分类")
        colorLists.add(data.goodsColor)
        mMaterialTagAdapter = MaterialTagAdapter(this, colorLists)
        mMaterialFlowLayout.adapter = mMaterialTagAdapter

        getGoodsSpecById("${data.specifId}")

        val imageUrls = data.goodsDesc
        val list = ArrayList<String>()

        list.add(imageUrls)
        mGoodsImageAdapter.replaceData(list)


    }

    /**
     * 获取商品规格
     */
    private fun getGoodsSpecById(guiGeId: String) {
        FhssApplication.getFhssApplication().getFhssApi().getGoodsSpecById(guiGeId).enqueue(object : OnMyActivityRequestListener<BaseBean>(this) {
            override fun onSuccess(bean: BaseBean) {
            }
        })
    }

    private fun toolbarChange(scrollY: Int) {
        if (scrollY <= 0) {
            // 改变标题栏透明度
            id_mtl_toolbar.setBackgroundColor(Color.argb(0, 250, 251, 253))// fafbfd
            // 设置标题栏 左右图片
//                leftBackImage.setImageResource(R.mipmap.back_slod)
//                leftBackImages.setImageResource(R.mipmap.back_arrow)
//                mShareImageView.setImageResource(R.mipmap.share_gray_icon)
//                mShareImageViews.setImageResource(R.mipmap.share_icon)
            setCollectIcon(true)
//                isHeartFlag = true
            isAlphaShow = true
            setTitleAlpha(1f)
        } else if (scrollY > 0 && scrollY <= id_mtl_toolbar.height) {
            // 透明度值
            var y = 1.0f * scrollY / id_mtl_toolbar.height
            // 改变标题栏透明度
            id_mtl_toolbar.setBackgroundColor(Color.argb((y * 255).toInt(), 250, 251, 253))// fafbfd

            if (isAlphaShow) {// 透明度值变化
                y = 1 - y
            }
            setTitleAlpha(y)
        } else {
            // 改变标题栏透明度
            id_mtl_toolbar.setBackgroundColor(Color.argb(255, 250, 251, 253))// fafbfd
            // 设置标题栏 左右图片
//                leftBackImage.setImageResource(R.mipmap.back_arrow)
//                leftBackImages.setImageResource(R.mipmap.back_slod)
//                mShareImageView.setImageResource(R.mipmap.share_icon)
//                mShareImageViews.setImageResource(R.mipmap.share_gray_icon)
            setCollectIcon(false)
//                isHeartFlag = false
            isAlphaShow = false
            setTitleAlpha(1f)
        }
    }

    /**
     * 设置收藏按钮 状态
     *
     * @param flag
     */
    private fun setCollectIcon(flag: Boolean) {
        /*if (mGoodsDetail != null) {
            if (flag) {
                if (mGoodsDetail.getData().getIs_cell() === 1) {
                    mCollectImageView.setImageResource(R.mipmap.collect_gray_icon)
                    mCollectImageViews.setImageResource(R.mipmap.collect_normal)
                } else {
                    mCollectImageView.setImageResource(R.mipmap.collect_red_icon)
                    mCollectImageViews.setImageResource(R.mipmap.red_heart_stroke)
                }
            } else {
                if (mGoodsDetail.getData().getIs_cell() === 1) {
                    mCollectImageView.setImageResource(R.mipmap.collect_normal)
                    mCollectImageViews.setImageResource(R.mipmap.collect_gray_icon)
                } else {
                    mCollectImageView.setImageResource(R.mipmap.red_heart_stroke)
                    mCollectImageViews.setImageResource(R.mipmap.collect_red_icon)
                }
            }
        } else {
            if (flag) {
                mCollectImageView.setImageResource(R.mipmap.collect_gray_icon)
                mCollectImageViews.setImageResource(R.mipmap.collect_normal)
            } else {
                mCollectImageView.setImageResource(R.mipmap.collect_normal)
                mCollectImageViews.setImageResource(R.mipmap.collect_gray_icon)
            }

        }*/
    }

    /**
     * 设置标题栏按钮透明度
     *
     * @param alpha
     */
    private fun setTitleAlpha(alpha: Float) {
        leftBackImage.alpha = alpha
        mShareImageView.alpha = alpha
        mCollectImageView.alpha = alpha
        leftBackImages.alpha = 1 - alpha
        mShareImageViews.alpha = 1 - alpha
        mCollectImageViews.alpha = 1 - alpha
    }

    /**
     * 展示底部商品参数对话框
     */
    private fun showBottomDialog() {
        if (dialog == null) {
            dialogView = layoutInflater.inflate(R.layout.dialog_goods_parameter_list, null)
            // 参数列表
            dialogView.mParameterList.layoutManager = LinearLayoutManager(mContext)
            mGoodsParameterAdapter = GoodsParameterAdapter(R.layout.item_goods_parameter, null)
            dialogView.mParameterList.adapter = mGoodsParameterAdapter

            val list = ArrayList<String>()
            list.add("")
            list.add("")
            list.add("")
            list.add("")
            list.add("")
            list.add("")
//            list.add("")
//            list.add("")
//            list.add("")
//            list.add("")
//            list.add("")
//            list.add("")
//            list.add("")
//            list.add("")
//            list.add("")
//            list.add("")
            mGoodsParameterAdapter.addData(list)

            dialog = BottomSheetDialog(this)
            dialog!!.setContentView(dialogView)
            // 解决显示不全
            val parent = dialogView.parent as View
            val behavior = BottomSheetBehavior.from(parent)
            dialogView.measure(0, 0)
            behavior.peekHeight = dialogView.measuredHeight
            val params = parent.layoutParams as CoordinatorLayout.LayoutParams
            params.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
            parent.layoutParams = params
            // 展示对话框
            dialog!!.show()


        } else {
            // 正在展示时不再进行展示
            if (!dialog!!.isShowing) {
                dialog!!.show()
            }
        }

    }

    /**
     * 添加收藏
     */
    private fun addCell(goodsId: String) {
        val loginUserDoLogin = FhssApplication.getFhssApplication().getLoginUserDoLogin(this)
        if (loginUserDoLogin != null)
            FhssApplication.getFhssApplication().getFhssApi().insertCellGoods(loginUserDoLogin.map.user.userId, goodsId).enqueue(object : OnMyActivityRequestListener<BaseBean>(this) {
                override fun onSuccess(bean: BaseBean?) {
                    ToastUtils.showShort(mContext, "收藏成功")
                    TextViewUtils.setImageResources(mContext, R.mipmap.ic_cell_checked, Gravity.TOP, mCollectTextView)
                }

            })
    }
}