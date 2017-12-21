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
        super(R.layout.item_line_two_text_layout,Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder holder, String item) {
        holder.setText(R.id.title, "12312312123");
        holder.setText(R.id.content, "12312312123");
    }
}
