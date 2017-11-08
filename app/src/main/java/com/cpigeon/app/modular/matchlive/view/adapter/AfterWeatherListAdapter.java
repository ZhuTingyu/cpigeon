package com.cpigeon.app.modular.matchlive.view.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cpigeon.app.R;
import com.cpigeon.app.utils.Lists;

import java.util.List;

/**
 * Created by chenshuai on 2017/11/8.
 */

public class AfterWeatherListAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {

    public AfterWeatherListAdapter() {
        super(R.layout.item_after_weather_layout, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder holder, Object item) {
        holder.setText(R.id.address, "四川省成都市");
        holder.setText(R.id.text1, "晴");
        holder.setText(R.id.text2, "11°C");
        holder.setText(R.id.text3, "南风");
        holder.setText(R.id.text4, "风力5级");
    }
}
