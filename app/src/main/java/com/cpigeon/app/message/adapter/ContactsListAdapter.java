package com.cpigeon.app.message.adapter;

import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseMultiSelectAdapter;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.entity.ContactsGroupEntity;
import com.cpigeon.app.entity.MultiSelectEntity;
import com.cpigeon.app.utils.CommonTool;
import com.cpigeon.app.utils.Lists;

import java.util.List;


/**
 * Created by Zhu TingYu on 2017/11/21.
 */

public class ContactsListAdapter extends BaseMultiSelectAdapter<ContactsGroupEntity,BaseViewHolder> {

    public ContactsListAdapter() {
        super(R.layout.item_contacts_list_layout, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder holder, ContactsGroupEntity item) {
        super.convert(holder, item);

        holder.setText(R.id.title,item.fzmc);
        holder.setText(R.id.number,mContext.getString(R.string.string_text_contacts_number
                ,String.valueOf(item.fzcount)));


    }

    @Override
    public void setNewData(List<ContactsGroupEntity> data) {
        super.setNewData(data);
        if(data.isEmpty()){
            CommonTool.setEmptyView(this,"没有分组信息");
        }
    }
}
