package com.cpigeon.app.home.adpter;

import android.view.View;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.entity.BaseDynamicEntity;
import com.cpigeon.app.entity.DynamicEntity;
import com.cpigeon.app.entity.ImageEntity;
import com.cpigeon.app.utils.Lists;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/1/2.
 */

public class CircleDynamicAdapter extends BaseMultiItemQuickAdapter<DynamicEntity, BaseViewHolder> {

    public CircleDynamicAdapter() {
        super(Lists.newArrayList());
        addItemType(BaseDynamicEntity.IMAGE_0, R.layout.item_dynamic_1_img_layout);
        addItemType(BaseDynamicEntity.IMAGE_1, R.layout.item_dynamic_1_img_layout);
        addItemType(BaseDynamicEntity.IMAGE_2, R.layout.item_dynamic_2_img_layout);
        addItemType(BaseDynamicEntity.IMAGE_3, R.layout.item_dynamic_3_img_layout);
    }

    @Override
    protected void convert(BaseViewHolder holder, DynamicEntity item) {

        holder.setText(R.id.user_name,item.nicheng);
        holder.setText(R.id.content_text, item.content);
        holder.setImageResource(R.id.icon, item.guanzhu ? R.mipmap.ic_home_followed : R.mipmap.ic_home_follow);
        holder.setSimpleImageView(R.id.user_icon, item.headurl);
        List<ImageEntity> list = item.imglist;
        holder.setSimpleImageView(R.id.content_img, list.get(0).imgurl);

        Glide.with(mContext).load(list.get(0).imgurl).into()
        /*switch (holder.getItemViewType()){
            case BaseDynamicEntity.IMAGE_0:
                holder.setViewVisible(R.id.content_img, View.GONE);
                break;
            case BaseDynamicEntity.IMAGE_1:
                holder.setSimpleImageView(R.id.content_img, list.get(0).imgurl);
                break;
            case BaseDynamicEntity.IMAGE_2:
                holder.setSimpleImageView(R.id.content_img1, list.get(0).imgurl);
                holder.setSimpleImageView(R.id.content_img2, list.get(1).imgurl);
                break;
            case BaseDynamicEntity.IMAGE_3:
                holder.setSimpleImageView(R.id.content_img1, list.get(0).imgurl);
                holder.setSimpleImageView(R.id.content_img2, list.get(1).imgurl);
                holder.setSimpleImageView(R.id.content_img3, list.get(2).imgurl);
                break;

        }*/
    }
}
