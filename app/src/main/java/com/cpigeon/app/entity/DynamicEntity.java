package com.cpigeon.app.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by Zhu TingYu on 2018/1/2.
 */

public class DynamicEntity implements MultiItemEntity {

    public static final int IMAGE_0 = 0;
    public static final int IMAGE_1 = 1;
    public static final int IMAGE_2 = 2;
    public static final int IMAGE_3 = 3;

    private int type;

    public DynamicEntity(int type){
        this.type = type;
    }

    @Override
    public int getItemType() {
        return type;
    }
}
