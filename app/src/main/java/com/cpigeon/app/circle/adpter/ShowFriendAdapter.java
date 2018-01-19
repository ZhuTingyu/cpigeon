package com.cpigeon.app.circle.adpter;

import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseQuickAdapter;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.entity.CircleFriendEntity;
import com.cpigeon.app.utils.Lists;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/1/18.
 */

public class ShowFriendAdapter extends BaseQuickAdapter<CircleFriendEntity, BaseViewHolder> {

    public ShowFriendAdapter() {
        super(R.layout.item_show_friend_layout, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder holder, CircleFriendEntity item) {
        holder.setText(R.id.time, item.getTime());
        holder.setGlideImageView(mContext,R.id.head_img, item.getUserinfo().getHeadimgurl());
        holder.setText(R.id.user_name, item.getUserinfo().getNickname());
    }

    @Override
    protected String getEmptyViewText() {
        return "暂时没有朋友";
    }
}
