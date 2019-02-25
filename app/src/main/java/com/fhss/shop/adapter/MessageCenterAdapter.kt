package com.fhss.shop.adapter

import com.chad.library.adapter.base.BaseViewHolder
import com.fhss.shop.bean.MessageListBean
import com.zrq.base.base.BaseAdapter
import kotlinx.android.synthetic.main.item_message_center.view.*

/**
 * 描述:
 *
 * @author leifeng
 *         2018/10/10 15:38
 */

class MessageCenterAdapter(layoutResId: Int, data: MutableList<MessageListBean.DataBean>?) : BaseAdapter<MessageListBean.DataBean>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder, item: MessageListBean.DataBean) {
        helper.itemView.apply {
            mMessageTitleTextView.text = "消息"
            mMessageTimeTextView.text = item.createTime
            mMessageContentTextView.text = item.infoCentent
        }
    }

}