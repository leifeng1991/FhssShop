package com.fhss.shop.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.text.TextUtils
import android.view.View
import com.fhss.shop.R
import com.fhss.shop.adapter.SearchGoodsAdapter
import com.fhss.shop.base.FhssApplication
import com.fhss.shop.bean.SearchGoodsListBean
import com.fhss.shop.utils.SystemBarUtils
import com.fhss.shop.weight.GridDividerItemDecoration
import com.zrq.base.base.BaseActivity
import com.zrq.base.net.OnMyActivityRequestListener
import kotlinx.android.synthetic.main.activity_search_goods_list.*
import kotlinx.android.synthetic.main.layout_search_title_bar.*
import retrofit2.Call

/**
 * 描述:搜索商品列表
 *
 * @author leifeng
 *         2018/10/8 14:54
 */

class SearchGoodsListActivity : BaseActivity() {
    private lateinit var mSearchGoodsAdapter: SearchGoodsAdapter
    private var isSelectedPrice = true
    private var mKeyword: String? = ""
    private var mCId: String? = ""

    companion object {
        private const val KEY_WORD = "keyword"
        private const val C_ID = "cId"
        /**
         * keyword 搜索关键字
         * cId 分类id
         */
        fun newIntent(context: Context, keyword: String?, cId: String?): Intent {
            val intent = Intent(context, SearchGoodsListActivity::class.java)
            intent.putExtra(KEY_WORD, keyword)
            intent.putExtra(C_ID, cId)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SystemBarUtils.setStatusBarColor(this, ContextCompat.getColor(mContext, R.color.colorWhite))
        SystemBarUtils.setStatusBarLightMode(this, true)
        findViewById<View>(android.R.id.content).setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorWhite))
    }

    override fun loadViewLayout() {
        setContentView(R.layout.activity_search_goods_list)
    }

    override fun initView() {
        setTitleLeftBack()
        iv_title_left.setImageResource(R.mipmap.ic_back)
        val gridLayoutManager = GridLayoutManager(mContext, 2)
        mGoodsList.setLayoutManager(gridLayoutManager)
        val gridDividerItemDecoration = GridDividerItemDecoration(mContext)
        mGoodsList.addItemDecoration(gridDividerItemDecoration)
        gridDividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.shape_divider))
        mSearchGoodsAdapter = SearchGoodsAdapter(R.layout.item_search_goods_list, null)
        mGoodsList.setAdapter(mSearchGoodsAdapter)
    }

    override fun setListener() {
        mSearchLayout.setOnClickListener {
            // 跳转到搜索界面
            startActivity(SearchActivity.newIntent(mContext))
        }
        // 价格筛选
        mPriceButton.setOnClickListener {
            if (!isSelectedPrice) {
                mPriceButton.setTextColor(ContextCompat.getColor(mContext, R.color.colorTheme))
                mSalesButton.setTextColor(ContextCompat.getColor(mContext, R.color.colorTextBlack_333333))
                isSelectedPrice = !isSelectedPrice
            }
        }
        // 销售额筛选
        mSalesButton.setOnClickListener {
            if (isSelectedPrice) {
                mSalesButton.setTextColor(ContextCompat.getColor(mContext, R.color.colorTheme))
                mPriceButton.setTextColor(ContextCompat.getColor(mContext, R.color.colorTextBlack_333333))
                isSelectedPrice = !isSelectedPrice
            }
        }
        // 商品列表点击事件
        mSearchGoodsAdapter.setOnItemClickListener { _, _, position ->
            // 跳转到商品详情
            startActivity(GoodsDetailActivity.newIntent(mContext, "${mSearchGoodsAdapter.getItem(position)?.goodsId}"))
        }
    }

    override fun processLogic() {
        mKeyword = intent.getStringExtra(KEY_WORD)
        mCId = intent.getStringExtra(C_ID)
        getGoodsList()
    }

    /**
     * 获取商品列表
     */
    private fun getGoodsList(){
        val call: Call<SearchGoodsListBean> = if (!TextUtils.isEmpty(mKeyword)) {
            // 根据关键字查
            FhssApplication.getFhssApplication().getFhssApi().findByGname(mKeyword ?: "")
        } else {
            // 根据三级分类id查
            FhssApplication.getFhssApplication().getFhssApi().findByCid(mCId ?: "")
        }

        call.enqueue(object : OnMyActivityRequestListener<SearchGoodsListBean>(this) {
            override fun onSuccess(bean: SearchGoodsListBean?) {
                mGoodsList.handlerSuccess(mSearchGoodsAdapter, bean?.data)
            }

            override fun onFailed(isServiceHintError: Boolean, code: Int, message: String?) {
                super.onFailed(isServiceHintError, code, message)
                mGoodsList.handlerError(mSearchGoodsAdapter)
            }

        })
    }
}