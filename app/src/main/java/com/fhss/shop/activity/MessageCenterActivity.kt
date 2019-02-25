package com.fhss.shop.activity

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.fhss.shop.R
import com.fhss.shop.adapter.MessageCenterAdapter
import com.fhss.shop.base.FhssApplication
import com.fhss.shop.base.FhssBaseActivity
import com.fhss.shop.bean.MessageListBean
import com.zrq.base.model.BaseBean
import com.zrq.base.net.OnMyActivityRequestListener
import kotlinx.android.synthetic.main.activity_message_center.*

/**
 * 描述:消息中心
 *
 * @author leifeng
 *         2018/10/8 14:54
 */

class MessageCenterActivity : FhssBaseActivity() {
    private lateinit var mMessageCenterAdapter: MessageCenterAdapter

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MessageCenterActivity::class.java)
        }
    }

    override fun loadViewLayout() {
        setContentView(R.layout.activity_message_center)
    }

    override fun initView() {
        setTitleName("消息中心")
        setTitleLeftBack()
        setTitleRightImage(R.mipmap.ic_person_center)
        mMessageList.setLayoutManager(LinearLayoutManager(mContext))
        mMessageCenterAdapter = MessageCenterAdapter(R.layout.item_message_center, null)
        mMessageList.setAdapter(mMessageCenterAdapter)

    }

    override fun setListener() {

    }

    override fun processLogic() {
        getMessageList()
    }

    override fun onTitleRightClick(view: View?) {
        super.onTitleRightClick(view)
        startActivity(PersonInfoActivity.newIntent(mContext))
    }

    /**
     * 获取消息列表
     */
    private fun getMessageList() {
        val loginUserDoLogin = FhssApplication.getFhssApplication().getLoginUserDoLogin(this)
        if (loginUserDoLogin != null)
            FhssApplication.getFhssApplication().getFhssApi().findAllByUid(loginUserDoLogin.map.user.userId).enqueue(object : OnMyActivityRequestListener<MessageListBean>(this) {
                override fun onSuccess(bean: MessageListBean) {
                    mMessageList.handlerSuccess(mMessageCenterAdapter, bean.data)
                }

                override fun onFailed(isServiceHintError: Boolean, code: Int, message: String?) {
                    super.onFailed(isServiceHintError, code, message)
                    mMessageList.handlerError(mMessageCenterAdapter)
                }
            })
    }
}