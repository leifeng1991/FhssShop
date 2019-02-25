package com.fhss.shop.adapter

import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseViewHolder
import com.fhss.shop.R
import com.fhss.shop.bean.ConfirmOderGoodsBean
import com.fhss.shop.bean.ShopCartGoodsListBean
import com.fhss.shop.utils.ImageLoadUtils
import com.zrq.base.base.BaseAdapter
import com.zrq.base.util.ToastUtils

/**
 * 描述:
 *
 * @author leifeng
 *         2018/10/10 15:38
 */

class ShopCartAdapter(layoutResId: Int, data: MutableList<ShopCartGoodsListBean.MapBean.ListBean>?) : BaseAdapter<ShopCartGoodsListBean.MapBean.ListBean>(layoutResId, data) {
    var listener: OnPriceListener? = null
    private var mOnCheckedAllListener: OnCheckedAllListener? = null

    override fun convert(helper: BaseViewHolder, item: ShopCartGoodsListBean.MapBean.ListBean) {
        val mCheckedImageView = helper.getView<ImageView>(R.id.mCheckedImageView)
        val mMessageImageView = helper.getView<ImageView>(R.id.mImageViewLayout)
        val mMessageTitleTextView = helper.getView<TextView>(R.id.mMessageTitleTextView)
        val mPriceTextView = helper.getView<TextView>(R.id.mPriceTextView)
        val mVipPriceTextView = helper.getView<TextView>(R.id.mVipPriceTextView)
        val mBackIntegralTextView = helper.getView<TextView>(R.id.mBackIntegralTextView)
        val mGoodsColorTextView = helper.getView<TextView>(R.id.mGoodsColorTextView)
        val mGoodsNumberTextView = helper.getView<TextView>(R.id.mGoodsNumberTextView)
        val mSubImageView = helper.getView<ImageView>(R.id.mSubImageView)
        val mAddImageView = helper.getView<ImageView>(R.id.mAddImageView)

        // 设置数据
        ImageLoadUtils.setImageBig(mContext, item.goodsImg, mMessageImageView)
        mCheckedImageView.setImageResource(if (item.isChecked) R.mipmap.ic_circle_checked else R.mipmap.ic_circle_normal)
        ImageLoadUtils.setImageBig(mContext, item.goodsImg, mMessageImageView)
        mGoodsColorTextView.text = item.goodsColor
        mMessageTitleTextView.text = item.goodsName
        mPriceTextView.text = "零售价:${item.goodsMarket}"
        mVipPriceTextView.text = item.goodsMembers
        mBackIntegralTextView.text = "(会员可获得${item?.goodsIntegral}积分)"
        mGoodsNumberTextView.text = item.goodsNumber
        // 选中
        mCheckedImageView.setOnClickListener {
            item.isChecked = !item.isChecked
            notifyDataSetChanged()
            listener?.onPrice(calculateCheckedPrice())
            mOnCheckedAllListener?.onCheckedAll(checkIsCheckedAll())
        }
        // 加点击事件
        mAddImageView.setOnClickListener {
            var number = mGoodsNumberTextView.text.trim().toString().toInt()
            number++
            item.goodsNumber = "$number"
            notifyDataSetChanged()
            listener?.onPrice(calculateCheckedPrice())
        }
        // 减点击事件
        mSubImageView.setOnClickListener {
            var number = mGoodsNumberTextView.text.trim().toString().toInt()
            if (number == 1) {
                ToastUtils.showShort(mContext, "已经到最小值")
            } else {
                number--
                item.goodsNumber = "$number"
                notifyDataSetChanged()
                listener?.onPrice(calculateCheckedPrice())
            }

        }

    }

    /**
     * 计算选中商品总价格
     */
    fun calculateCheckedPrice(): Double {
        var totalPrice = 0.00
        for (datum in data) {
            if (datum.isChecked) {
                val goodsNumber = datum.goodsNumber.toInt()
                val goodsPrice = datum.goodsMembers.toDouble()
                totalPrice += goodsNumber * goodsPrice
            }
        }
        return totalPrice
    }

    private fun checkIsCheckedAll(): Boolean {
        for (datum in data) {
            if (!datum.isChecked) {
                return false
            }
        }
        return true
    }

    fun getGoodsNumber(): Int {
        var number = 0
        for (datum in data) {
            if (datum.isChecked) {
                number++
            }
        }
        return number
    }

    fun getConfirmOrderGoodsList(): ConfirmOderGoodsBean {
        val bean = ConfirmOderGoodsBean()
        val list = ArrayList<ShopCartGoodsListBean.MapBean.ListBean>()
        for (datum in data) {
            if (datum.isChecked) {
                list.add(datum)
            }
        }
        bean.list = list
        return bean
    }

    fun getIntegralTotal():Int{
        var integral = 0
        for (datum in data) {
            integral+= datum.goodsIntegral
        }
        return integral
    }

    fun setOnPriceListener(listener: OnPriceListener) {
        this.listener = listener
    }

    fun setOnCheckedAllListener(mOnCheckedAllListener: OnCheckedAllListener) {
        this.mOnCheckedAllListener = mOnCheckedAllListener
    }

    interface OnPriceListener {
        fun onPrice(totalPrice: Double)
    }

    interface OnCheckedAllListener {
        fun onCheckedAll(isCheckedAll: Boolean)
    }
}