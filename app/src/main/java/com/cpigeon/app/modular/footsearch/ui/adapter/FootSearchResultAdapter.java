package com.cpigeon.app.modular.footsearch.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cpigeon.app.R;
import com.cpigeon.app.utils.Lists;

import java.util.List;

/**
 * Created by Zhu TingYu on 2017/12/22.
 */

public class FootSearchResultAdapter extends BaseQuickAdapter<String, BaseViewHolder>{

    public FootSearchResultAdapter() {
        super(R.layout.item_foot_search_result_layout, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder holder, String item) {
        holder.setText(R.id.text1_content, "1312313123123");
        holder.setText(R.id.text2_content, "1312313123123");
        holder.setText(R.id.text3_content, "1312313123123");
        holder.setText(R.id.text4_content, "1312313123123");
        holder.setText(R.id.text5_content, "1312313123123");
        holder.setText(R.id.text6_content, "1312313123123");
        holder.setText(R.id.text7_content, "1312313123123");
        holder.setText(R.id.text8_content, "1312313123123");
    }
}
