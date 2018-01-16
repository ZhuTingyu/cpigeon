package com.cpigeon.app.circle.adpter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseQuickAdapter;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.utils.Lists;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/1/16.
 */

public class ChooseImageAdapter extends BaseQuickAdapter<String ,BaseViewHolder> {

    public ChooseImageAdapter() {
        super(R.layout.item_choose_image_layout, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder holder, String item) {
        holder.setGlideImageView(mContext, R.id.images, item);
        holder.getView(R.id.ll_del).setOnClickListener(v -> {

        });
    }

    @Override
    public void setNewData(List<String> data) {
        super.setNewData(data);

    }
}

