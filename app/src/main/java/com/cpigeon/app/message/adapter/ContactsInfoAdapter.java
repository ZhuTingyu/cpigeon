package com.cpigeon.app.message.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.utils.Lists;

import java.util.List;

/**
 * Created by Zhu TingYu on 2017/11/21.
 */

public class ContactsInfoAdapter extends BaseQuickAdapter<String ,BaseViewHolder> {

    public ContactsInfoAdapter() {
        super(R.layout.item_contacts_list_layout, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder holder, String item) {
        holder.setText(R.id.title, "zhutingyu");
        holder.setText(R.id.number,"123231231312");
    }
}
