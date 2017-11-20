package com.cpigeon.app.message.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseMultiSelectAdapter;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.entity.MultiSelectEntity;
import com.cpigeon.app.utils.Lists;

import java.util.List;

/**
 * Created by Zhu TingYu on 2017/11/20.
 */

public class CommonMessageAdapter extends BaseMultiSelectAdapter<MultiSelectEntity, BaseViewHolder> {

    public CommonMessageAdapter() {
        super(R.layout.item_common_message_layout, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder holder, MultiSelectEntity item) {
        super.convert(holder,item);
        holder.setText(R.id.content,"2312312312312312321312212123321332131332321");
    }
}
