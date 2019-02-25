package com.fhss.shop.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.fhss.shop.R
import com.fhss.shop.adapter.HistoryKeywordAdapter
import com.fhss.shop.utils.PreferencesSaver
import com.fhss.shop.utils.SystemBarUtils
import com.zrq.base.base.BaseActivity
import com.zrq.base.util.ToastUtils
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.layout_clear_history.*
import java.util.*

/**
 * 描述:搜索界面
 *
 * @author leifeng
 *         2018/10/8 14:54
 */

class SearchActivity : BaseActivity() {

    companion object {
        const val PREFERENCES_KEY_KEYWORD_LIST = "keyword"
        fun newIntent(context: Context): Intent {
            return Intent(context, SearchActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SystemBarUtils.setStatusBarColor(this, ContextCompat.getColor(mContext, R.color.colorWhite))
        SystemBarUtils.setStatusBarLightMode(this, true)
        findViewById<View>(android.R.id.content).setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorWhite))
    }

    override fun loadViewLayout() {
        setContentView(R.layout.activity_search)
    }

    override fun initView() {
        mSearchHistoryList.setLayoutManager(LinearLayoutManager(mContext))
    }

    override fun setListener() {
        mSearchImageViewLayout.setOnClickListener {
            val keyword = mSearchEditText.text.trim().toString()
            if (TextUtils.isEmpty(keyword)) {
                ToastUtils.showMsg(mContext, "请输入搜索关键字")
                return@setOnClickListener
            }
            addKeyword(keyword)
            // 跳转到搜索结果界面
            startActivity(SearchGoodsListActivity.newIntent(mContext, keyword,""))
        }
    }

    override fun processLogic() {
        setTitleLeftBack()
        iv_title_left.setImageResource(R.mipmap.ic_back)
        setHistoryData()
    }

    /**
     * 设置搜索历史数据
     */
    private fun setHistoryData() {
        val list = PreferencesSaver.getStrList(this, PREFERENCES_KEY_KEYWORD_LIST)
        if (list != null && list.size > 0) {
            list.reverse()
            val mAdapter = HistoryKeywordAdapter(R.layout.item_history_keyword_layout, null, object : HistoryKeywordAdapter.OnIsHasKeywordListener {
                override fun setIsHasKeyword(isHasKeyword: Boolean) {
                    if (isHasKeyword) {
                        mClearHistoryLayout.visibility = View.GONE
                    } else {
                        mClearHistoryLayout.visibility = View.VISIBLE
                    }
                }

            })
            mAdapter.addData(list)
            mSearchHistoryList.setAdapter(mAdapter)
            mAdapter.setOnItemClickListener { _, _, position ->
                val item = mAdapter.getItem(position)
                startActivity(SearchGoodsListActivity.newIntent(this@SearchActivity, item,""))
            }
            mClearHistoryLayout.visibility = View.VISIBLE
        } else {
            mClearHistoryLayout.visibility = View.GONE
        }
    }

    /**
     * 添加搜索历史关键字
     *
     * @param query
     */
    private fun addKeyword(query: String) {
        // 判断搜索历史里是否有盖关键字
        var flag = false
        var list = PreferencesSaver.getStrList(this, PREFERENCES_KEY_KEYWORD_LIST)
        if (list == null) {
            list = ArrayList<String>()
        } else {
            for (i in list.indices) {
                if (query == list[i]) {
                    flag = true
                    break
                }
            }
        }

        if (!flag) {// 搜索历史列表没有 加进去
            list.add(query)
            PreferencesSaver.putStrList(this, PREFERENCES_KEY_KEYWORD_LIST, list)
        }
    }
}