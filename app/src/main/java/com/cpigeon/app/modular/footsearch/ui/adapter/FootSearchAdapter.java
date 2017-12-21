package com.cpigeon.app.modular.footsearch.ui.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.utils.Lists;

import java.util.List;

/**
 * Created by Zhu TingYu on 2017/12/21.
 */

public class FootSearchAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public FootSearchAdapter() {
        super(Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder holder, String item) {
        TextView textView = new TextView(mContext);

    }
}
