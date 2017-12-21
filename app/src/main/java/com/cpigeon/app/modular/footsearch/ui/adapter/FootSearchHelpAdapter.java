package com.cpigeon.app.modular.footsearch.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.utils.Lists;

import java.util.List;

/**
 * Created by Zhu TingYu on 2017/12/21.
 */

public class FootSearchHelpAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public FootSearchHelpAdapter() {
        super(R.layout.item_line_text_with_icon_layout, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder holder, String item) {
        holder.setViewVisible(R.id.icon, View.GONE);
        holder.setViewVisible(R.id.icon_right, View.GONE);
        holder.setText(R.id.title,"121312312312312asdfasdfas312312312312312312312");
    }
}
