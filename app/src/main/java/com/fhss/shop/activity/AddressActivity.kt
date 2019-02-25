package com.fhss.shop.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.fhss.shop.R
import com.fhss.shop.adapter.AddressAdapter
import com.fhss.shop.base.FhssApplication
import com.fhss.shop.base.FhssBaseActivity
import com.fhss.shop.bean.MyAddressListBean
import com.zrq.base.net.OnMyActivityRequestListener
import kotlinx.android.synthetic.main.activity_address.*


/**
 * 描述:地址列表
 *
 * @author leifeng
 *         2018/10/8 14:54
 */

class AddressActivity : FhssBaseActivity() {
    private lateinit var mAddressAdapter: AddressAdapter
    private lateinit var mFooterView: View

    companion object {
        private const val ADDRESS = "address"
        fun newIntent(context: Context): Intent {
            return Intent(context, AddressActivity::class.java)
        }

        fun getIntentResult(intent: Intent?): MyAddressListBean.ListBean? {
            if (intent == null) return null
            return intent?.getSerializableExtra(ADDRESS) as MyAddressListBean.ListBean
        }
    }

    override fun loadViewLayout() {
        setContentView(R.layout.activity_address)
    }

    override fun initView() {
        setTitleName("地址")
        setTitleLeftBack()
        setTitleRightImage(R.mipmap.ic_add_address)
        // 地址列表
        mAddressList.setLayoutManager(LinearLayoutManager(mContext))
        mAddressList.setPullRefreshAndLoadingMoreEnabled(false, false)
        mAddressAdapter = AddressAdapter(R.layout.item_address, null)
        mAddressList.setAdapter(mAddressAdapter)
        // 尾部布局
        mFooterView = layoutInflater.inflate(R.layout.footer_address, null)
        mAddressList.addFooterView(mFooterView)
    }

    override fun setListener() {
        // 新增地址
        mNewAddressTextView.setOnClickListener {
            // 跳转到新增地址界面
            startActivity(NewAddressActivity.newIntent(mContext))
        }
        // 新增地址
        mFooterView.setOnClickListener {
            // 跳转到新增地址界面
            startActivity(NewAddressActivity.newIntent(mContext))
        }
        // 地址列表点击事件
        mAddressAdapter.setOnItemClickListener { _, _, position ->
            val intent = Intent()
            intent.putExtra(ADDRESS, mAddressAdapter.getItem(position))
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    override fun processLogic() {
        val loginUserDoLogin = FhssApplication.getFhssApplication().getLoginUserDoLogin(this)
        if (loginUserDoLogin != null) {
            FhssApplication.getFhssApplication().getFhssApi().selectUserAddress(loginUserDoLogin.map.user.userId).enqueue(object : OnMyActivityRequestListener<MyAddressListBean>(this) {
                override fun onSuccess(bean: MyAddressListBean?) {
                    mAddressList.handlerSuccess(mAddressAdapter, bean?.list)
                }

                override fun onFailed(isServiceHintError: Boolean, code: Int, message: String?) {
                    super.onFailed(isServiceHintError, code, message)
                    mAddressList.handlerError(mAddressAdapter)
                }
            })
        }
    }

    override fun onTitleRightClick(view: View?) {
        super.onTitleRightClick(view)
        // 跳转到新增地址界面
        startActivity(NewAddressActivity.newIntent(mContext))
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_OK, null)
        finish()
    }


}