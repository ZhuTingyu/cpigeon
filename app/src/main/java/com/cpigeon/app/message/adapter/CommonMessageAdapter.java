package com.cpigeon.app.message.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.utils.Lists;

import java.util.List;

/**
 * Created by Zhu TingYu on 2017/11/20.
 */

public class CommonMessageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public CommonMessageAdapter() {
        super(layoutResId, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
