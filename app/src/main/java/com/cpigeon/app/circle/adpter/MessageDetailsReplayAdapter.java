package com.cpigeon.app.circle.adpter;

import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseQuickAdapter;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.utils.Lists;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/1/18.
 */

public class MessageDetailsReplayAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public MessageDetailsReplayAdapter() {
        super(R.layout.item_news_reply_layout, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder holder, String item) {
        holder.setText(R.id.reply,"小朱 回复 哈哈:12321312312312");
    }
}
