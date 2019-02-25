package com.fhss.shop.activity

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import com.fhss.shop.R
import com.fhss.shop.adapter.CollectCenterAdapter
import com.fhss.shop.base.FhssApplication
import com.fhss.shop.base.FhssBaseActivity
import com.fhss.shop.bean.CollectListBean
import com.zrq.base.net.OnMyActivityRequestListener
import kotlinx.android.synthetic.main.activity_collect_center.*

/**
 * 描述:收藏中心
 *
 * @author leifeng
 *         2018/10/8 14:54
 */

class CollectCenterActivity : FhssBaseActivity() {
    private lateinit var mCollectCenterAdapter: CollectCenterAdapter

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, CollectCenterActivity::class.java)
        }
    }

    override fun loadViewLayout() {
        setContentView(R.layout.activity_collect_center)
    }

    override fun initView() {
        setTitleName("收藏中心")
        setTitleLeftBack()
        // 商品收藏列表
        mCollectList.setLayoutManager(LinearLayoutManager(mContext))
        mCollectList.setPullRefreshAndLoadingMoreEnabled(false, false)
        mCollectCenterAdapter = CollectCenterAdapter(R.layout.item_collect_center, null)
        mCollectList.setAdapter(mCollectCenterAdapter)


    }

    override fun setListener() {

    }

    override fun processLogic() {
        getCellList()
    }

    /**
     * 获取收藏列表
     */
    private fun getCellList() {
        val loginUserDoLogin = FhssApplication.getFhssApplication().getLoginUserDoLogin(this)
        if (loginUserDoLogin != null)
            FhssApplication.getFhssApplication().getFhssApi().selectCellGoodsList(loginUserDoLogin.map.user.userId).enqueue(object : OnMyActivityRequestListener<CollectListBean>(this) {
                override fun onSuccess(bean: CollectListBean) {
                    mCollectList.handlerSuccess(mCollectCenterAdapter, bean.list)
                }

                override fun onFailed(isServiceHintError: Boolean, code: Int, message: String?) {
                    super.onFailed(isServiceHintError, code, message)
                    mCollectList.handlerError(mCollectCenterAdapter)
                }

            })
    }
}