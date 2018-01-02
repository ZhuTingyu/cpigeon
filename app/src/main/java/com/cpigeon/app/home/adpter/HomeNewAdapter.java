package com.cpigeon.app.home.adpter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.utils.Lists;
import com.cpigeon.app.viewholder.NewsViewHolder;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/1/2.
 */

public class HomeNewAdapter extends BaseQuickAdapter<String, NewsViewHolder> {

    public HomeNewAdapter() {
        super(R.layout.item_news_layout);
    }

    @Override
    protected void convert(NewsViewHolder holder, String item) {
        holder.bindData(item);
    }
}
