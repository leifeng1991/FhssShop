package com.fhss.shop.activity

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import com.fhss.shop.R
import com.fhss.shop.adapter.CommentDetailAdapter
import com.fhss.shop.base.FhssBaseActivity
import kotlinx.android.synthetic.main.activity_comment_detail.*

/**
 * 描述:商品详情
 *
 * @author leifeng
 *         2018/10/8 14:54
 */

class CommentDetailActivity : FhssBaseActivity() {
    private lateinit var mCommentDetailAdapter: CommentDetailAdapter

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, CommentDetailActivity::class.java)
        }
    }

    override fun loadViewLayout() {
        setContentView(R.layout.activity_comment_detail)
    }

    override fun initView() {
        setTitleName("商品评价")
        setTitleLeftBack()
        // 评论列表
        mCommentList.layoutManager = LinearLayoutManager(mContext)
        mCommentDetailAdapter = CommentDetailAdapter(R.layout.item_comment_commodity, null)
        mCommentList.adapter = mCommentDetailAdapter
    }

    override fun setListener() {

    }

    override fun processLogic() {
        val list = ArrayList<String>()
        list.add("一个月")
        list.add("两个月")
        list.add("三个月")
        list.add("四个月")
        mCommentDetailAdapter.addData(list)
    }
}