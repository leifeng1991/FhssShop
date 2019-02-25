package com.fhss.shop.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.chad.library.adapter.base.BaseViewHolder;
import com.fhss.shop.R;
import com.fhss.shop.utils.PreferencesSaver;
import com.zrq.base.base.BaseAdapter;
import com.zrq.base.util.TextViewUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.fhss.shop.activity.SearchActivity.PREFERENCES_KEY_KEYWORD_LIST;


/**
 * 搜索历史是适配器
 * Created by Administrator on 2017/3/21.
 */

public class HistoryKeywordAdapter extends BaseAdapter<String> {

    private OnIsHasKeywordListener listener;

    public HistoryKeywordAdapter(int layoutResId, @Nullable List<String> data,OnIsHasKeywordListener listener) {
        super(layoutResId, data);
        this.listener = listener;
    }

    @Override
    protected void convert(BaseViewHolder holder, String item) {
        TextView mKeyword = holder.getView(R.id.id_hkl_keyword);
        TextViewUtils.setText(mKeyword,item);
        ImageView mDeleteImage = holder.getView(R.id.id_hkl_delete);
        mDeleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getData().indexOf(item);
                ArrayList<String> list = PreferencesSaver.getStrList(mContext,PREFERENCES_KEY_KEYWORD_LIST);
                Collections.reverse(list);
                if (list != null && list.size() > 0){
                    list.remove(position);
                    PreferencesSaver.putStrList(mContext,PREFERENCES_KEY_KEYWORD_LIST,list);
                }
                remove(position);

                if (listener == null) return;
                if (getItemCount() > 0){
                    listener.setIsHasKeyword(false);
                }else {
                    listener.setIsHasKeyword(true);
                }
            }
        });
    }

    /**
     * item数量大于0
     */
    public interface OnIsHasKeywordListener{
        void setIsHasKeyword(boolean isHasKeyword);
    }
}
