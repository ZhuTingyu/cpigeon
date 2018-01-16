package com.cpigeon.app.circle.adpter;

import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.entity.ChooseImageEntity;
import com.cpigeon.app.utils.Lists;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/1/16.
 */

public class ChooseImageAdapter extends BaseMultiItemQuickAdapter<ChooseImageEntity, BaseViewHolder> {

    private int maxChoose = 9;

    public final int TYPE_CAMERA = 1;
    public final int TYPE_PICTURE = 2;

    int type;


    public ChooseImageAdapter() {
        super(Lists.newArrayList());
        addItemType(ChooseImageEntity.TYPE_IMG, R.layout.item_choose_image_layout);
        addItemType(ChooseImageEntity.TYPE_ADD, R.layout.item_choose_image_layout);
    }

    @Override
    protected void convert(BaseViewHolder holder, ChooseImageEntity item) {
        if (type == TYPE_PICTURE) {
            switch (holder.getItemViewType()) {

                case ChooseImageEntity.TYPE_IMG:
                    break;

                case ChooseImageEntity.TYPE_ADD:
                    holder.setViewVisible(R.id.ll_del, View.GONE);
                    holder.setImageResource(R.id.images, R.mipmap.ic_add_img);
                    break;
            }
        } else {

        }
    }

    @Override
    public void setNewData(List<ChooseImageEntity> data) {

        for (ChooseImageEntity entity : data) {
            entity.setType(ChooseImageEntity.TYPE_IMG);
        }

        if (data.size() != maxChoose) {
            ChooseImageEntity entity = new ChooseImageEntity();
            entity.setType(ChooseImageEntity.TYPE_ADD);
            data.add(entity);
        }
        super.setNewData(data);
    }

    public void setMaxChoose(int maxChoose) {
        this.maxChoose = maxChoose;
    }

    public void setType(int type) {
        this.type = type;
    }
}


