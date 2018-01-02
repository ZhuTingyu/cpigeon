package com.cpigeon.app.home.adpter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.entity.DynamicEntity;
import com.cpigeon.app.utils.Lists;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/1/2.
 */

public class HomeDynamicAdapter extends BaseQuickAdapter<DynamicEntity, BaseViewHolder>{

    public HomeDynamicAdapter() {
        super(Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder helper, DynamicEntity item) {

    }
}
