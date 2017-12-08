package com.cpigeon.app.message.ui.order.adpter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cpigeon.app.R;
import com.cpigeon.app.message.ui.order.OderInfoViewHolder;

import java.util.List;

/**
 * Created by Zhu TingYu on 2017/12/8.
 */

public class RechargeHistoryAdapter extends BaseQuickAdapter<String, OderInfoViewHolder> {

    public RechargeHistoryAdapter() {
        super(R.layout.item_order_info_head_layout);
    }

    @Override
    protected void convert(OderInfoViewHolder holder, String item) {
        //holder.bindData();
    }
}
