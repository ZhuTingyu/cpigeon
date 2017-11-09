package com.cpigeon.app.modular.matchlive.view.adapter;

import com.amap.api.services.weather.LocalWeatherLive;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cpigeon.app.R;
import com.cpigeon.app.utils.Lists;

import java.util.List;

/**
 * Created by chenshuai on 2017/11/8.
 */

public class AfterWeatherListAdapter extends BaseQuickAdapter<LocalWeatherLive, BaseViewHolder> {

    public AfterWeatherListAdapter() {
        super(R.layout.item_after_weather_layout, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder holder, LocalWeatherLive weatherLive) {
        holder.setText(R.id.address, weatherLive.getProvince() + weatherLive.getCity());
        holder.setText(R.id.text1, weatherLive.getWeather());
        holder.setText(R.id.text2, mContext.getString(R.string.text_temperature, weatherLive.getTemperature()));
        holder.setText(R.id.text3, mContext.getString(R.string.text_wind_direction, weatherLive.getWindDirection()));
        //holder.setText(R.id.text4, "风力5级");
    }
}
