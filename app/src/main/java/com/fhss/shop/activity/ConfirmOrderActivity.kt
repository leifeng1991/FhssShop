package com.fhss.shop.activity

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.fhss.shop.R
import com.fhss.shop.adapter.ConfirmOrderAdapter
import com.fhss.shop.base.FhssApplication
import com.fhss.shop.base.FhssBaseActivity
import com.fhss.shop.bean.*
import com.fhss.shop.utils.DialogUtil
import com.google.gson.Gson
import com.zrq.base.model.BaseBean
import com.zrq.base.net.OnMyActivityRequestListener
import com.zrq.base.util.LogUtil
import com.zrq.base.util.ToastUtils
import kotlinx.android.synthetic.main.activity_confirm_order.*
import kotlinx.android.synthetic.main.footer_confirm_order.view.*
import okhttp3.RequestBody


/**
 * 描述:确认订单
 *
 * @author leifeng
 *         2018/10/8 14:54
 */

class ConfirmOrderActivity : FhssBaseActivity() {
    private lateinit var mConfirmOrderAdapter: ConfirmOrderAdapter
    private lateinit var mFooterView: View
    private lateinit var mHeaderView: View
    private lateinit var mConfirmOderGoodsBean: ConfirmOderGoodsBean
    private lateinit var mTotalPrice: String
    private lateinit var totalPrice: String
    private lateinit var totalIntegral: String
    private var mIntegralBean: IntegralBean? = null
    private var mAddressBean: MyAddressListBean.ListBean? = null

    companion object {
        private const val CONFIRM_ODER_GOODS_BEAN = "mConfirmOderGoodsBean"
        private const val TOTAL_PRICE = "totalPrice"
        private const val TOTAL_INTEGRAL = "totalIntegral"
        private const val REQUEST_ADDRESS_CODE = 500
        private const val REQUEST_ADD_ADDRESS_CODE = 501

        /**
         * mConfirmOderGoodsBean:商品集
         * totalPrice:总价
         * totalIntegral:返还总积分
         */
        fun newIntent(context: Context, mConfirmOderGoodsBean: ConfirmOderGoodsBean, totalPrice: String, totalIntegral: String): Intent {
            val intent = Intent(context, ConfirmOrderActivity::class.java)
            intent.putExtra(CONFIRM_ODER_GOODS_BEAN, mConfirmOderGoodsBean)
            intent.putExtra(TOTAL_PRICE, totalPrice)
            intent.putExtra(TOTAL_INTEGRAL, totalIntegral)
            return intent
        }
    }

    override fun loadViewLayout() {
        setContentView(R.layout.activity_confirm_order)
    }

    override fun onResume() {
        super.onResume()

    }

    override fun initView() {
        setTitleName("确认订单")
        setTitleLeftBack()
//        setTitleRightImage(R.mipmap.ic_more)
        // 地址列表
        mGoodsList.setLayoutManager(LinearLayoutManager(mContext))
        mGoodsList.setPullRefreshAndLoadingMoreEnabled(false, false)
        mConfirmOrderAdapter = ConfirmOrderAdapter(R.layout.item_confirm_order, null)
        mGoodsList.setAdapter(mConfirmOrderAdapter)
        // 头部布局
        mHeaderView = layoutInflater.inflate(R.layout.item_confirm_order, null)
        // 尾部布局
        mFooterView = layoutInflater.inflate(R.layout.footer_confirm_order, null)

    }

    override fun setListener() {
        // 地址
        mFooterView.mAddressLayout.setOnClickListener {
            // 跳转到地址列表
            startActivityForResult(AddressActivity.newIntent(mContext), REQUEST_ADDRESS_CODE)
        }
        mFooterView.mSubmitButton.setOnClickListener {
            createOrder()
        }
        mFooterView.mCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (mIntegralBean == null) {
                ToastUtils.showShort(mContext, "网络请求失败")
            } else {
                var integral = mIntegralBean?.num ?: 0
                if (integral > mTotalPrice.toDouble()){
                    integral = mTotalPrice.toInt()
                    totalPrice = "0"
                }else{
                    totalPrice = (mTotalPrice.toDouble() - integral).toString()
                }
                integral = if (integral > mTotalPrice.toDouble()) mTotalPrice.toInt() else integral
                // 可用积分
                mFooterView.mUsingIntegralTextView.text = if (isChecked) "$integral" else "0"
                mFooterView.mGoodsPriceTextView.text = if (isChecked) totalPrice else mTotalPrice
            }
        }
    }

    override fun processLogic() {
        mConfirmOderGoodsBean = intent.getSerializableExtra(CONFIRM_ODER_GOODS_BEAN) as ConfirmOderGoodsBean
        totalPrice = intent.getStringExtra(TOTAL_PRICE)
        mTotalPrice = intent.getStringExtra(TOTAL_PRICE)
        totalIntegral = intent.getStringExtra(TOTAL_INTEGRAL)
        mConfirmOrderAdapter.replaceData(mConfirmOderGoodsBean.list)
        mGoodsList.addHeaderView(mHeaderView)
        mGoodsList.addFooterView(mFooterView)
        mFooterView.mGoodsPriceTextView.text = totalPrice
        mFooterView.mGoodsNumberTextView.text = "总共${mConfirmOderGoodsBean.list.size}件商品"
        mFooterView.mBackIntegralTextView.text = "会员返还${totalIntegral}积分"

        getAddress()
        getIntegral()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_ADDRESS_CODE -> {
                if (mAddressBean == null) {
                    // 获取地址
                    getAddress()
                } else {
                    if (data != null) {
                        val intentResult = AddressActivity.getIntentResult(data)
                        if (intentResult != null)
                            setAddressData(intentResult)
                    }
                }
            }
            REQUEST_ADD_ADDRESS_CODE -> {
                // 获取地址
                getAddress()
            }
        }
    }

    /**
     * 下单
     */
    private fun createOrder() {
        if (mAddressBean != null) {
            val orderGoodsLit = ArrayList<OrderGoods>()
            for (listBean in mConfirmOderGoodsBean.list) {
                val orderGoods = OrderGoods(listBean.goodsId, listBean.goodsName, listBean.goodsSn,
                        listBean.goodsNumber, listBean.goodsMembers, listBean.goodsImg,"${listBean.goodsIntegral}")
                orderGoodsLit.add(orderGoods)
            }
            val mOrderInfoBean = OrderInfoBean("${mAddressBean?.userId}", mAddressBean?.consignee,
                    mAddressBean?.address, mAddressBean?.mobile, mAddressBean?.postcode, "", totalPrice, "0", totalIntegral, orderGoodsLit)
            val orderInfo = Gson().toJson(mOrderInfoBean)
            LogUtil.e("+++++++++++++++++++++++++", orderInfo)
            val body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), orderInfo)
            FhssApplication.getFhssApplication().getFhssApi().createOrder(body).enqueue(object : OnMyActivityRequestListener<BaseBean>(this) {
                override fun onSuccess(bean: BaseBean?) {
                    ToastUtils.showShort(mContext, "下单成功")
                    finish()
                }
            })
        }
    }

    /**
     * 获取收获地址
     */
    private fun getAddress() {
        val loginUserDoLogin = FhssApplication.getFhssApplication().getLoginUserDoLogin(this)
        if (loginUserDoLogin != null) {
            FhssApplication.getFhssApplication().getFhssApi().selectUserAddress(loginUserDoLogin.map.user.userId).enqueue(object : OnMyActivityRequestListener<MyAddressListBean>(this) {
                override fun onSuccess(bean: MyAddressListBean) {
                    if (bean.list.size > 0) {
                        setAddressData(bean.list[0])
                    } else {
                        showExitDialog()
                    }
                }

                override fun onFailed(isServiceHintError: Boolean, code: Int, message: String?) {
                    super.onFailed(isServiceHintError, code, message)
                    showExitDialog()
                }
            })
        }
    }

    private fun setAddressData(data: MyAddressListBean.ListBean) {
        mAddressBean = data
        mFooterView.mConsigneeTextView.text = mAddressBean?.consignee
        mFooterView.mPhoneTextView.text = mAddressBean?.mobile
        mFooterView.mIdNumberTextView.text = "${mAddressBean?.province} ${mAddressBean?.city} ${mAddressBean?.address}"
    }

    private fun showExitDialog() {
        mAddressBean = null
        DialogUtil.showRawDialog(this, "你还没有添加收获地址,请先添加收货地址") { dialog, _ ->
            startActivityForResult(NewAddressActivity.newIntent(mContext), REQUEST_ADD_ADDRESS_CODE)
            dialog.dismiss()
        }
    }

    private fun getIntegral() {
        val loginUserDoLogin = FhssApplication.getFhssApplication().getLoginUserDoLogin(this)
        if (loginUserDoLogin != null)
            FhssApplication.getFhssApplication().getFhssApi().selectUserIntegral(loginUserDoLogin.map.user.userId).enqueue(object : OnMyActivityRequestListener<IntegralBean>(this) {
                override fun onSuccess(bean: IntegralBean?) {
                    mIntegralBean = bean
                    mFooterView.mIntegralTextView.text = "现有积分${bean?.num ?: 0}"
//                    mFooterView.mUsingIntegralTextView.text = "${bean?.num ?: 0}"
                }

            })
    }
}