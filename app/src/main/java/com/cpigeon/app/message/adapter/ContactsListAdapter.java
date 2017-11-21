package com.cpigeon.app.message.adapter;

import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseMultiSelectAdapter;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.entity.MultiSelectEntity;
import com.cpigeon.app.utils.Lists;


/**
 * Created by Zhu TingYu on 2017/11/21.
 */

public class ContactsListAdapter extends BaseMultiSelectAdapter<MultiSelectEntity,BaseViewHolder> {

    public ContactsListAdapter() {
        super(R.layout.item_contacts_list_layout, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder holder, MultiSelectEntity item) {
        super.convert(holder, item);

        holder.setText(R.id.title,"川南地区");
        holder.setText(R.id.number,mContext.getString(R.string.string_text_contacts_number,"1232321"));


    }
}
