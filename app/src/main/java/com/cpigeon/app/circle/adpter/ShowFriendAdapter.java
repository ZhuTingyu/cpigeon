package com.cpigeon.app.circle.adpter;

import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseQuickAdapter;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.utils.Lists;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/1/18.
 */

public class ShowFriendAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ShowFriendAdapter() {
        super(R.layout.item_show_friend_layout, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder holder, String item) {
        holder.setGlideImageView(mContext,R.id.head_img, "http://img2.imgtn.bdimg.com/it/u=8232468,2916696848&fm=27&gp=0.jpg");
        holder.setText(R.id.user_name, "小朱");
    }
}
