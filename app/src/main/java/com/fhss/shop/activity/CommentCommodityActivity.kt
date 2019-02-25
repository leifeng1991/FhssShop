package com.fhss.shop.activity

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import com.fhss.shop.R
import com.fhss.shop.adapter.CommentCommodityAdapter
import com.fhss.shop.base.FhssBaseActivity
import kotlinx.android.synthetic.main.activity_comment_commodity.*

/**
 * 描述:商品评价
 *
 * @author leifeng
 *         2018/10/8 14:54
 */

class CommentCommodityActivity : FhssBaseActivity() {
    private lateinit var mCommentCommodityAdapter: CommentCommodityAdapter

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, CommentCommodityActivity::class.java)
        }
    }

    override fun loadViewLayout() {
        setContentView(R.layout.activity_comment_commodity)
    }

    override fun initView() {
        setTitleName("商品评价")
        setTitleLeftBack()
        // 评论列表
        mCommentList.setLayoutManager(LinearLayoutManager(mContext))
        mCommentList.setPullRefreshAndLoadingMoreEnabled(false, false)
        mCommentCommodityAdapter = CommentCommodityAdapter(R.layout.item_comment_commodity, null)
        mCommentList.setAdapter(mCommentCommodityAdapter)
    }

    override fun setListener() {
        mCommentCommodityAdapter.setOnItemClickListener { adapter, view, position ->
            // 跳转到评论详情页
            startActivity(CommentDetailActivity.newIntent(mContext))
        }
    }

    override fun processLogic() {
        val list = ArrayList<String>()
        list.add("一个月")
        list.add("两个月")
        list.add("三个月")
        list.add("四个月")
        mCommentCommodityAdapter.addData(list)
    }
}