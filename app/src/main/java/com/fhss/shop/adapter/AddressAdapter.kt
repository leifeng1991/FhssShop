package com.fhss.shop.adapter

import com.chad.library.adapter.base.BaseViewHolder
import com.fhss.shop.bean.MyAddressListBean
import com.zrq.base.base.BaseAdapter
import kotlinx.android.synthetic.main.item_address.view.*

/**
 * 描述:
 *
 * @author leifeng
 *         2018/10/10 15:38
 */

class AddressAdapter(layoutResId: Int, data: MutableList<MyAddressListBean.ListBean>?) : BaseAdapter<MyAddressListBean.ListBean>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder, item: MyAddressListBean.ListBean?) {
        helper.itemView.apply {
            val consignee = item?.consignee ?: ""
            mUserButton.setText(if (consignee.length > 2) consignee.substring(consignee.length - 2, consignee.length) else consignee)
            mUserNameTextView.text = consignee
            mPhoneTextView.text = item?.mobile ?: ""
            mDetailAddressTextView.text = item?.address ?: ""

        }
    }

}