package com.fhss.shop.fragment

import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fhss.shop.R
import com.fhss.shop.activity.GoodsDetailActivity
import com.fhss.shop.adapter.HomeHeaderGoodsAdapter
import com.fhss.shop.adapter.ShopCartAdapter
import com.fhss.shop.bean.GoodsListBean
import com.fhss.shop.bean.GoodsNewListBean
import com.zrq.base.base.BaseActivity
import com.zrq.base.base.ReuseViewFragment
import kotlinx.android.synthetic.main.fragment_home_header_goods_list.view.*

/**
 *
 * @author leifeng
 *         2018/10/9 9:17
 */

class HomeHeaderGoodsFragment : ReuseViewFragment() {
    private lateinit var mHomeHeaderGoodsAdapter: HomeHeaderGoodsAdapter
    private lateinit var goodsList: ArrayList<GoodsNewListBean.DataBean.RowsBean>

    companion object {
        fun newInstance(goodsList: ArrayList<GoodsNewListBean.DataBean.RowsBean>): HomeHeaderGoodsFragment {
            val fragment = HomeHeaderGoodsFragment()
            fragment.goodsList = goodsList
            return fragment
        }
    }

    override fun loadViewLayout(inflater: LayoutInflater, container: ViewGroup?): View {
        return inflater.inflate(R.layout.fragment_home_header_goods_list, null)
    }

    override fun initView() {
        rootView.mHomeHeaderGoodsList.layoutManager = GridLayoutManager(mContext, 3)
        mHomeHeaderGoodsAdapter = HomeHeaderGoodsAdapter(mActivity as BaseActivity, R.layout.item_home_header_goods_list, null)
        rootView.mHomeHeaderGoodsList.adapter = mHomeHeaderGoodsAdapter

    }

    override fun setListener() {
        mHomeHeaderGoodsAdapter.setOnItemClickListener { _, view, position ->
            // 跳转到商品详情
            startActivity(GoodsDetailActivity.newIntent(mContext, mHomeHeaderGoodsAdapter.data[position].goodsId))
        }
    }

    override fun processLogic() {
        mHomeHeaderGoodsAdapter.replaceData(goodsList)
    }

}