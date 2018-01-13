package com.cpigeon.app.home.adpter;

import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.entity.HomeNewsEntity;
import com.cpigeon.app.entity.NewsEntity;
import com.cpigeon.app.utils.Lists;
import com.cpigeon.app.utils.ScreenTool;
import com.cpigeon.app.viewholder.NewsViewHolder;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/1/2.
 */

public class PigeonNewsAdapter extends BaseQuickAdapter<HomeNewsEntity, NewsViewHolder> {

    public static final int TYPE_HOME = 0;

    private int type = 1;

    public PigeonNewsAdapter() {
        super(R.layout.item_news_layout);
    }

    @Override
    protected void convert(NewsViewHolder holder, HomeNewsEntity item) {
        holder.bindData(item);
        holder.setListener(mContext);
    }

    public void setType(int type) {
        this.type = type;
    }
}
