package com.cpigeon.app.modular.matchlive.view.fragment.viewdao;

import com.cpigeon.app.commonstandard.view.activity.IView;
import com.cpigeon.app.modular.matchlive.model.bean.GYTRaceLocation;

import java.util.List;

/**
 * Created by Administrator on 2017/7/12.
 */

public interface IMapLiveView extends IView {
    void showMapData(List<GYTRaceLocation> raceLocations);
    String getRid();
    String getLid();
    Boolean hw();
    void showSpeed();//获取车速
    void showWeather();//获取天气
    void showFlyarea();//获取司放地坐标
    void showFlyareaWeather();//获取司放地天气
    void showAreaDistance();//获取空距
    void showNowLocation();//获取当前坐标
    void showNowWeather();//获取当前天气
}
