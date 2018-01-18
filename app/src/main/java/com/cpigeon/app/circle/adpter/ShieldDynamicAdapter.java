package com.cpigeon.app.circle.adpter;

import android.widget.TextView;

import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseQuickAdapter;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.utils.Lists;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/1/18.
 */

public class ShieldDynamicAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ShieldDynamicAdapter() {
        super(R.layout.item_shield_dynamic_layout, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder holder, String item) {
        holder.setGlideImageView(mContext,R.id.head_img, "http://img2.imgtn.bdimg.com/it/u=8232468,2916696848&fm=27&gp=0.jpg");
        holder.setText(R.id.user_name, "小朱");
        holder.setText(R.id.content,"123123234123");

        TextView statue = holder.getView(R.id.state);
        statue.setText("取消屏蔽");
        statue.setOnClickListener(v -> {

        });
    }
}
