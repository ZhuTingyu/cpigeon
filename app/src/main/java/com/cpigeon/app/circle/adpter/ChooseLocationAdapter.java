package com.cpigeon.app.circle.adpter;

import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseQuickAdapter;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.utils.Lists;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/1/17.
 */

public class ChooseLocationAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ChooseLocationAdapter() {
        super(R.layout.item_contacts_list_layout, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }



}
