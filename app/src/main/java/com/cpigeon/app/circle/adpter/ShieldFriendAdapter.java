package com.cpigeon.app.circle.adpter;

import android.widget.TextView;

import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseQuickAdapter;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.circle.ui.ShieldFriendFragment;
import com.cpigeon.app.utils.Lists;

/**
 * Created by Zhu TingYu on 2018/1/18.
 */

public class ShieldFriendAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    String type;

    public ShieldFriendAdapter(String type) {
        super(R.layout.item_shield_and_blacklist_layout, Lists.newArrayList());
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder holder, String item) {
        holder.setGlideImageView(mContext,R.id.head_img, "http://img2.imgtn.bdimg.com/it/u=8232468,2916696848&fm=27&gp=0.jpg");
        holder.setText(R.id.user_name, "小朱");
        TextView statue = holder.getView(R.id.state);

        if(type == ShieldFriendFragment.TYPE_SHIELD){
            statue.setText("取消屏蔽");
            statue.setOnClickListener(v -> {

            });
        }else {
            statue.setText("取消拉黑");
            statue.setOnClickListener(v -> {

            });
        }


    }
}
