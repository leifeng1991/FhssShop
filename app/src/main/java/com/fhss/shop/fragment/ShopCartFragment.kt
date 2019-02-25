package com.fhss.shop.fragment

import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fhss.shop.R
import com.fhss.shop.activity.ConfirmOrderActivity
import com.fhss.shop.adapter.ShopCartAdapter
import com.fhss.shop.base.FhssApplication
import com.fhss.shop.bean.ConfirmOderGoodsBean
import com.fhss.shop.bean.ShopCartGoodsListBean
import com.zrq.base.base.BaseActivity
import com.zrq.base.base.ReuseViewFragment
import com.zrq.base.net.OnMyActivityRequestListener
import com.zrq.base.util.ToastUtils
import kotlinx.android.synthetic.main.fragment_shop_cart.view.*

/**
 * 描述:购物车
 *
 * @author leifeng
 *         2018/10/9 9:17
 */

class ShopCartFragment : ReuseViewFragment() {
    private lateinit var mShopCartAdapter: ShopCartAdapter
    private var mIsCheckedAll = false

    override fun loadViewLayout(inflater: LayoutInflater, container: ViewGroup?): View {
        return inflater.inflate(R.layout.fragment_shop_cart, null)
    }

    override fun onResume() {
        super.onResume()
        getShopCartList()
    }

    override fun initView() {
        setTitleName("购物车")
//        setTitleRightImage(R.mipmap.ic_more)
        // 购物车商品列表
        rootView.mShopCartGoodsList.setLayoutManager(LinearLayoutManager(mContext))
        rootView.mShopCartGoodsList.setPullRefreshAndLoadingMoreEnabled(false, false)
        mShopCartAdapter = ShopCartAdapter(R.layout.item_shop_cart, null)
        rootView.mShopCartGoodsList.setAdapter(mShopCartAdapter)

    }

    override fun setListener() {
        // 结算点击事件
        rootView.mGoodsNumberButton.setOnClickListener {
            if (mShopCartAdapter.getGoodsNumber() > 0) {
                // 跳转到确认订单界面
                startActivity(ConfirmOrderActivity.newIntent(mContext, mShopCartAdapter.getConfirmOrderGoodsList(), "${mShopCartAdapter.calculateCheckedPrice()}", "${mShopCartAdapter.getIntegralTotal()}"))
            } else {
                ToastUtils.showShort(mContext, "请选择要下单的商品")
            }

        }
        // TODO 后期adapter所有监听换成一个
        // 价格变化监听
        mShopCartAdapter.setOnPriceListener(object : ShopCartAdapter.OnPriceListener {
            override fun onPrice(totalPrice: Double) {
                rootView.mTotalPriceTextView.text = "$totalPrice"
                val integral = mShopCartAdapter.getIntegralTotal()
                rootView.mBackIntegralTextView.text = "(返还积分$integral)"
                rootView.mSavePriceTextView.text = "节省￥$integral"
            }
        })
        // 全选监听
        mShopCartAdapter.setOnCheckedAllListener(object : ShopCartAdapter.OnCheckedAllListener {
            override fun onCheckedAll(isCheckedAll: Boolean) {
                mIsCheckedAll = isCheckedAll
                rootView.mSelectedImageView.setImageResource(if (isCheckedAll) R.mipmap.ic_circle_checked else R.mipmap.ic_circle_normal)
                rootView.mGoodsNumberButton.setText("(结算${mShopCartAdapter.getGoodsNumber()})")
            }

        })
        // 全选
        rootView.mCheckedAllLayout.setOnClickListener {
            mIsCheckedAll = !mIsCheckedAll
            for (datum in mShopCartAdapter.data) {
                datum.isChecked = mIsCheckedAll
            }
            rootView.mSelectedImageView.setImageResource(if (mIsCheckedAll) R.mipmap.ic_circle_checked else R.mipmap.ic_circle_normal)
            rootView.mTotalPriceTextView.text = "${mShopCartAdapter.calculateCheckedPrice()}"
            rootView.mGoodsNumberButton.setText("(结算${mShopCartAdapter.getGoodsNumber()})")
            val integral = mShopCartAdapter.getIntegralTotal()
            rootView.mBackIntegralTextView.text = "(返还积分$integral)"
            rootView.mSavePriceTextView.text = "节省￥$integral"
            mShopCartAdapter.notifyDataSetChanged()
        }
    }

    override fun processLogic() {

    }

    private fun getShopCartList() {
        val mUser = FhssApplication.getFhssApplication().getLoginUserDoLogin(mActivity)
        if (mUser != null) {
            FhssApplication.getFhssApplication().getFhssApi().getShopCartGoodsList(mUser.map.user.userId).enqueue(object : OnMyActivityRequestListener<ShopCartGoodsListBean>(mActivity as BaseActivity?) {
                override fun onSuccess(bean: ShopCartGoodsListBean) {
                    reset()
                    rootView.mShopCartGoodsList.handlerSuccess(mShopCartAdapter, bean.map.list)
                }

                override fun onFailed(isServiceHintError: Boolean, code: Int, message: String?) {
                    super.onFailed(isServiceHintError, code, message)
                    rootView.mShopCartGoodsList.handlerError(mShopCartAdapter)
                }
            })
        }

    }

    private fun reset() {
        mIsCheckedAll = false
        rootView.mSelectedImageView.setImageResource(if (mIsCheckedAll) R.mipmap.ic_circle_checked else R.mipmap.ic_circle_normal)
        rootView.mTotalPriceTextView.text = "0.00"
        rootView.mGoodsNumberButton.setText("(结算0)")
        rootView.mBackIntegralTextView.text = "(返还积分0)"
        rootView.mSavePriceTextView.text = "节省￥0"
    }

}