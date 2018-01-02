package com.cpigeon.app.home.adpter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cpigeon.app.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/1/2.
 */

public class HomeNewApapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public HomeNewApapter(int layoutResId, List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
