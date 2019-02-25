package com.fhss.shop.adapter

import com.chad.library.adapter.base.BaseViewHolder
import com.fhss.shop.bean.BankListBean
import com.zrq.base.base.BaseAdapter
import kotlinx.android.synthetic.main.item_bank.view.*

/**
 * 描述:
 *
 * @author leifeng
 *         2018/10/10 15:38
 */

class BankAdapter(layoutResId: Int, data: MutableList<BankListBean.ListsBean>?) : BaseAdapter<BankListBean.ListsBean>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder, item: BankListBean.ListsBean?) {
        helper.itemView.apply {
            mBankNameTextView.text = item?.bankName
            mBankNoTextView.text = item?.cardNum
        }
    }

}