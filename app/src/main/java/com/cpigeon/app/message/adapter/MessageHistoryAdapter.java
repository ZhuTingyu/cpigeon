package com.cpigeon.app.message.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cpigeon.app.R;
import com.cpigeon.app.utils.Lists;

import java.util.List;

/**
 * Created by Zhu TingYu on 2017/11/21.
 */

public class MessageHistoryAdapter extends BaseQuickAdapter<String, BaseViewHolder>{

    public MessageHistoryAdapter() {
        super(R.layout.item_message_history_layout, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder holder, String item) {
        holder.setText(R.id.number, mContext.getString(R.string.string_text_message_addressee_number, String.valueOf(1231)));
        holder.setText(R.id.date,"3123131");
        holder.setText(R.id.content, "1231312311131312");
    }
}
