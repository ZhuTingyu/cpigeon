package com.cpigeon.app.message.adapter;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseMultiSelectAdapter;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.entity.CommonEntity;
import com.cpigeon.app.entity.MultiSelectEntity;
import com.cpigeon.app.utils.DialogUtils;
import com.cpigeon.app.utils.Lists;

import java.util.List;

/**
 * Created by Zhu TingYu on 2017/11/20.
 */

public class CommonMessageAdapter extends BaseMultiSelectAdapter<CommonEntity, BaseViewHolder> {

    private OnCheckboxClickListener listener;

    public CommonMessageAdapter() {
        super(R.layout.item_common_message_layout, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder holder, CommonEntity item) {
        super.convert(holder,item);

        TextView content = holder.findViewById(R.id.content);

        content.setText(item.dxnr);
        content.setOnClickListener(v -> {
            DialogUtils.createDialog(mContext, "详细内容"
                    , item.dxnr, "确定");
        });

        holder.findViewById(R.id.checkbox).setOnClickListener(v -> {
            listener.OnClick(holder.getAdapterPosition());
        });

        holder.findViewById(R.id.btnEdit).setOnClickListener(v -> {
            showAddMessageDialog(mContext,item.dxnr);
        });

    }

    public interface OnCheckboxClickListener{
        void OnClick(int position);
    }

    public void setOnCheckboxClickListener(OnCheckboxClickListener listener){
        this.listener = listener;
    }

    private void showAddMessageDialog(Context context, String contentString) {
        AlertDialog dialogPrompt = new AlertDialog.Builder(context).create();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_add_message_layout, null);

        TextView title = view.findViewById(R.id.title);
        EditText content = view.findViewById(R.id.content);

        TextView btnLeft = view.findViewById(R.id.btn_left);
        TextView btnRight = view.findViewById(R.id.btn_right);

        content.setText(contentString);
        title.setText("编辑短语");
        btnRight.setOnClickListener(v -> {

        });


        btnLeft.setOnClickListener(v -> {
            dialogPrompt.dismiss();
        });

        dialogPrompt.setView(view);
        dialogPrompt.show();

    }

}
