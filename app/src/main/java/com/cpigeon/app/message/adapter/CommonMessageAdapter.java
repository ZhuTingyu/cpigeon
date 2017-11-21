package com.cpigeon.app.message.adapter;

import android.content.Context;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseMultiSelectAdapter;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.entity.MultiSelectEntity;
import com.cpigeon.app.utils.DialogUtils;
import com.cpigeon.app.utils.Lists;

import java.util.List;

/**
 * Created by Zhu TingYu on 2017/11/20.
 */

public class CommonMessageAdapter extends BaseMultiSelectAdapter<MultiSelectEntity, BaseViewHolder> {

    private OnCheckboxClickListener listener;

    public CommonMessageAdapter() {
        super(R.layout.item_common_message_layout, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder holder, MultiSelectEntity item) {
        super.convert(holder,item);

        TextView content = holder.findViewById(R.id.content);

        content.setText("2312312312312312321312212123321332131332321");
        content.setOnClickListener(v -> {
            DialogUtils.createDialog(mContext, "详细内容"
                    , "fadfadfadfa", "确定");
        });

        holder.findViewById(R.id.checkbox).setOnClickListener(v -> {
            listener.OnClick(holder.getAdapterPosition());
        });

    }

    public interface OnCheckboxClickListener{
        void OnClick(int position);
    }

    public void setOnCheckboxClickListener(OnCheckboxClickListener listener){
        this.listener = listener;
    }

}
