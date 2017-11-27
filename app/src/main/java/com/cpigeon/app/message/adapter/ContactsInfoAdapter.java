package com.cpigeon.app.message.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.entity.ContactsEntity;
import com.cpigeon.app.utils.CommonTool;
import com.cpigeon.app.utils.Lists;

import java.util.List;

/**
 * Created by Zhu TingYu on 2017/11/21.
 */

public class ContactsInfoAdapter extends BaseQuickAdapter<ContactsEntity,BaseViewHolder> {

    public ContactsInfoAdapter() {
        super(R.layout.item_contacts_list_layout, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder holder, ContactsEntity item) {
        holder.setText(R.id.title, item.xingming);
        holder.setText(R.id.number,item.sjhm);
    }

    public void setLoadMore(boolean isEnd) {
            if (isEnd) this.loadMoreEnd();
            else
                this.loadMoreComplete();
    }

    @Override
    public void setNewData(List<ContactsEntity> data) {
        super.setNewData(data);
        if(data.isEmpty()){
            CommonTool.setEmptyView(this, "暂时没有联系人");
        }
    }
}
