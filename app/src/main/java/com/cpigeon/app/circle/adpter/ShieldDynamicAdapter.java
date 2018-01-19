package com.cpigeon.app.circle.adpter;

import android.widget.TextView;

import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseQuickAdapter;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.entity.HideMessageEntity;
import com.cpigeon.app.utils.Lists;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/1/18.
 */

public class ShieldDynamicAdapter extends BaseQuickAdapter<HideMessageEntity, BaseViewHolder> {

    public ShieldDynamicAdapter() {
        super(R.layout.item_shield_dynamic_layout, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder holder, HideMessageEntity item) {
        holder.setGlideImageView(mContext,R.id.head_img, item.getMsgInfo().getUserinfo().getHeadimgurl());
        holder.setText(R.id.user_name, item.getMsgInfo().getUserinfo().getNickname());
        holder.setText(R.id.content,item.getMsgInfo().getMsg());

        TextView statue = holder.getView(R.id.state);
        statue.setText("取消屏蔽");
        statue.setOnClickListener(v -> {

        });
    }
}
