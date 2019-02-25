package com.zrq.base;

import android.content.Context;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zrq.base.base.BaseAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * 描述: 封装的 RecyclerView
 *
 * @author zhangrq
 * 2016/9/2 14:08
 */
@SuppressWarnings("unused")
public class MyRecyclerView extends RelativeLayout {
    /**
     * 每次加载的数量
     */
    public int loadSize = 20;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSmartRefreshLayout;
    private OnClickListener onNoNetViewClickListener;
    private OnRefreshListener mRefreshListener;
    private Context mContext;
    private int page = 1;

    public MyRecyclerView(Context context) {
        this(context, null);
    }

    public MyRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        View rootView = View.inflate(context, R.layout.view_my_recyclerview, null);
        // initView
        mSmartRefreshLayout = rootView.findViewById(R.id.smartRefreshLayout);
        mRecyclerView = rootView.findViewById(R.id.recyclerView);
        // init
        mSmartRefreshLayout.setRefreshHeader(new ClassicsHeader(context));
        mSmartRefreshLayout.setRefreshFooter(new ClassicsFooter(context));
        setPullRefreshAndLoadingMoreEnabled(true, false);// 设置可刷新，不可加载
        // addView
        addView(rootView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    /**
     * 处理成功，适用于带刷新加载的
     */
    public <T> void handlerSuccess(BaseAdapter<T> adapter, List<T> addData) {
        if (adapter == null) return;
        // 检查是否初始化 EmptyView
        checkInitEmptyView(adapter);
        if (addData == null) addData = new ArrayList<>();// 兼容null
        int size = addData.size();
        if (page == 1) {
            // 刷新
            adapter.replaceData(addData);// 先清后删数据
            refreshComplete();// 设置动画刷新完成
            mSmartRefreshLayout.setNoMoreData(false);// 重置
        } else {
            // 加载
            if (addData.size() > 0)
                adapter.addData(addData);// 增数据
            loadMoreComplete();// 设置动画加载完成
        }

        if (size < loadSize) {
            // 显示没有更多数据
            mSmartRefreshLayout.finishLoadMoreWithNoMoreData();
        }

    }

    /**
     * RecyclerView处理失败，刷新失败清空数据
     */
    public <T> void handlerError(BaseAdapter<T> adapter) {
        if (adapter == null) return;
        // 检查是否初始化 EmptyView
        checkInitEmptyView(adapter);
        if (page == 1) {
            // 刷新
            adapter.replaceData(new ArrayList<T>());// 刷新失败，清空内容
            refreshComplete();// 设置刷新动画结束
        } else {
            // 加载
            adapter.loadMoreFail();// 加载失败，设置加载失败提示
            loadMoreComplete();// 设置加载动画结束
        }
    }

    /**
     * 检查是否初始化 EmptyView
     */
    private <T> void checkInitEmptyView(BaseAdapter<T> adapter) {
        if (adapter == null)
            return;
        ViewGroup emptyView = (ViewGroup) adapter.getEmptyView();
        if (emptyView == null || emptyView.getChildCount() == 0) {
            // 没有设置过空布局，设置空布局
            View rootView = View.inflate(mContext, R.layout.layout_no_data, null);
            TextView tv_no_data_btn = rootView.findViewById(R.id.tv_no_data_btn);
            OnClickListener onClickListener = new OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 有监听的话，先走监听，其次没监听的话，走刷新
                    if (onNoNetViewClickListener != null) {
                        onNoNetViewClickListener.onClick(view);
                    } else {
                        if (getRefreshListener() != null) {
                            getRefreshListener().onRefresh(mSmartRefreshLayout);
                        }
                    }
                }
            };
            tv_no_data_btn.setOnClickListener(onClickListener);
            // 设置空布局
            adapter.setEmptyView(rootView);
        }
    }

    /**
     * 单独设置刷新监听器
     */
    public void setOnRefreshListener(final OnRefreshListener listener) {
        this.mRefreshListener = listener;
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                // 设置页数
                page = 1;
                // 通知刷新
                if (listener != null)
                    listener.onRefresh(refreshLayout);
            }
        });
    }

    /**
     * 获取刷新监听
     */
    public OnRefreshListener getRefreshListener() {
        return mRefreshListener;
    }

    /**
     * 单独设置加载监听器
     */
    public void setOnLoadMoreListener(final OnLoadMoreListener listener) {
        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                // 设置页数，大于等于一页内容（说明还有下一页），获取下一页的，否则获取第一页的
                BaseAdapter baseAdapter = getBaseAdapter();
                page = baseAdapter != null && baseAdapter.getItemCount() >= loadSize ? baseAdapter.getItemCount() / loadSize + 1 : 1;
                // 通知加载
                if (listener != null)
                    listener.onLoadMore(refreshLayout);
            }
        });
    }

    /**
     * 同时设置刷新和加载监听器
     */
    public void setOnRefreshLoadMoreListener(OnRefreshLoadMoreListener listener) {
        setOnRefreshListener(listener);// 设置刷新的监听
        setOnLoadMoreListener(listener);// 设置加载的监听
    }

    /**
     * 获取RecyclerView
     */
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    /**
     * 获取SmartRefreshLayout
     */
    public SmartRefreshLayout getSmartRefreshLayout() {
        return mSmartRefreshLayout;
    }

    /**
     * 设置每次加载的数量
     *
     * @param loadSize 加载的数量
     */
    public void setLoadSize(int loadSize) {
        this.loadSize = loadSize;
    }

    public int getLoadSize() {
        return loadSize;
    }

    /**
     * 设置刷新和加载是否可用
     *
     * @param pullRefreshEnabled true为设置刷新可用
     * @param loadingMoreEnabled true为设置加载可用
     */
    public void setPullRefreshAndLoadingMoreEnabled(boolean pullRefreshEnabled, boolean loadingMoreEnabled) {
        mSmartRefreshLayout.setEnableRefresh(pullRefreshEnabled);
        mSmartRefreshLayout.setEnableLoadMore(loadingMoreEnabled);
    }

    /**
     * 设置RecyclerView的adapter
     */
    public void setAdapter(BaseAdapter adapter) {
        mRecyclerView.setAdapter(adapter);
        // 检查是否初始化 EmptyView
        checkInitEmptyView(adapter);
    }

    /**
     * 设置RecyclerView的排列方式
     */
    public void setLayoutManager(RecyclerView.LayoutManager layout) {
        mRecyclerView.setLayoutManager(layout);
    }

    /**
     * 设置没数据的view
     */
    public void setEmptyView(View emptyView) {
        BaseAdapter baseAdapter = getBaseAdapter();
        if (baseAdapter != null) {
            // 先把原来的设置隐藏，再让其控制noDataView
            baseAdapter.setEmptyView(emptyView);
        }
    }

    /**
     * RecyclerView增加头
     */
    public void addHeaderView(View header) {
        BaseAdapter baseAdapter = getBaseAdapter();
        if (baseAdapter != null) {
            // 先把原来的设置隐藏，再让其控制noDataView
            baseAdapter.addHeaderView(header);
        }
    }

    /**
     * RecyclerView增加尾
     */
    public void addFooterView(View footer) {
        BaseAdapter baseAdapter = getBaseAdapter();
        if (baseAdapter != null) {
            // 先把原来的设置隐藏，再让其控制noDataView
            baseAdapter.addFooterView(footer);
        }
    }

    /**
     * 设置xRecyclerView加载是否可用
     */
    public void addItemDecoration(RecyclerView.ItemDecoration decor) {
        mRecyclerView.addItemDecoration(decor);
    }


    /**
     * 刷新完成
     */
    public void refreshComplete() {
        mSmartRefreshLayout.finishRefresh(0);
    }

    /**
     * 加载完成
     */
    public void loadMoreComplete() {
        mSmartRefreshLayout.finishLoadMore(0);
    }

    /**
     * 设置无网络图片的点击监听
     */
    public void setOnNoNetViewClickListener(OnClickListener onNoNetViewClickListener) {
        this.onNoNetViewClickListener = onNoNetViewClickListener;
    }

    /**
     * 获取 BaseAdapter
     *
     * @return 如果没设置 BaseAdapter 则返回 null
     */
    public BaseAdapter getBaseAdapter() {
        return mRecyclerView.getAdapter() instanceof BaseAdapter ? (BaseAdapter) mRecyclerView.getAdapter() : null;
    }

    public int getPage() {
        return page;
    }

    // 解决魅族手机上的提示的view id重名冲突
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        try {
            super.onRestoreInstanceState(state);
        } catch (Exception ignored) {

        }
    }
}
