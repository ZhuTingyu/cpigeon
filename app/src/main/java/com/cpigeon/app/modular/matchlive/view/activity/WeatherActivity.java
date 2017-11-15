package com.cpigeon.app.modular.matchlive.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.weather.LocalWeatherLive;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.activity.BaseActivity;
import com.cpigeon.app.modular.matchlive.view.adapter.AfterWeatherListAdapter;
import com.cpigeon.app.utils.Lists;
import com.cpigeon.app.utils.WeatherManager;
import com.cpigeon.app.utils.http.GsonUtil;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/7/25.
 */

public class WeatherActivity extends BaseActivity implements AMap.InfoWindowAdapter {
    Polyline polyline;
    @BindView(R.id.map)
    MapView mMapView;
    @BindView(R.id.recyclerview_weather)
    RecyclerView recyclerviewWeather;
    AMap aMap;

    List<LatLng> afterPoints;
    AfterWeatherListAdapter adapter;
    Toolbar toolbar;
    /*RegeocodeAddress[] addressList;
    LocalWeatherLive[] weatherList;*/

    ArrayList<RegeocodeAddress> addressList;
    ArrayList<LocalWeatherLive> weatherList;

    WeatherManager manager;

    Map<String, Integer> icMap;

    int i = 0;



    @Override
    public int getLayoutId() {
        return R.layout.activity_weather;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mMapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        adapter = new AfterWeatherListAdapter();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("沿途天气");
        toolbar.setNavigationOnClickListener(v -> finish());

        initIcMap();
        getAfterPoint();
        initView();

        manager = new WeatherManager(this);

        /*addressList = new RegeocodeAddress[afterPoints.size()];
        weatherList = new LocalWeatherLive[afterPoints.size()];*/

        addressList = Lists.newArrayList();
        weatherList = Lists.newArrayList();

        showLoading();

        searchCityByPoint();
    }

    private void initIcMap() {
        icMap = new HashMap<>();
        icMap.put("雷阵雨并伴有冰雹",R.drawable.ic_weather_white_hail);
        icMap.put("大雪",R.drawable.ic_weather_white_heavy_snow);
        icMap.put("大雨",R.drawable.ic_weather_white_heavy_rain);
        icMap.put("多云",R.drawable.ic_weather_white_cloudy);
        icMap.put("雷阵雨",R.drawable.ic_weather_white_thunder_shower);
        icMap.put("霾",R.drawable.ic_weather_white_smog);
        icMap.put("晴",R.drawable.ic_weather_white_sunny);
        icMap.put("雾",R.drawable.ic_weather_white_fog);
        icMap.put("小雪",R.drawable.ic_weather_white_light_snow);
        icMap.put("小雨",R.drawable.ic_weather_white_light_rain);
        icMap.put("阴",R.drawable.ic_weather_white_yin);
        icMap.put("雨夹雪",R.drawable.ic_weather_white_sleet);
        icMap.put("阵雪",R.drawable.ic_weather_white_heavy_snow);
        icMap.put("阵雨",R.drawable.ic_weather_white_heavy_rain);
        icMap.put("中雨",R.drawable.ic_weather_white_moderate_rain);
    }


    private void searchCityByPoint() {

        /*for (int i = 0, len = afterPoints.size(); i < len; i++) {
            LatLng latLng = afterPoints.get(i);
            int finalI = i;
            manager.seacherCityByLatLng(latLng).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(r -> {
                if (r.isOk()) {
                    addressList[finalI] = r.data;
                    if (Lists.isFull(addressList)) {
                        requestWeatherByCityName();
                    }
                }
            });
        }*/

        manager.searchCityByLatLng(afterPoints.get(i),r -> {
            manager.requestWeatherByCityName(r.data.getCity(),response -> {
                if(response.isOk()){
                    weatherList.add(response.data);
                    if(weatherList.size() == afterPoints.size()){
                        bindListData();
                        initMap();
                        hideLoading();
                    }else {
                        searchCityByPoint();
                    }
                }
            });
            i++;
        });

    }

    /*private void requestWeatherByCityName() {
        for(int i = 0, len = addressList.length;i < len; i++){
            int finalI = i;
            manager.requestWeatherByCityName(addressList[i].getCity()).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(r -> {
                if(r.isOk()){
                    weatherList[finalI] = r.data;
                    if(Lists.newArrayList(weatherList).size() == afterPoints.size()){
                        bindListData();
                        initMap();
                    }
                }else {
                    showTips("获取天气失败",TipType.DialogError);
                }
            });
        }
    }*/


    private void initView() {
        recyclerviewWeather = findViewById(R.id.recyclerview_weather);
        recyclerviewWeather.setLayoutManager(new LinearLayoutManager(this));
        addItemDecorationLine(recyclerviewWeather);
        recyclerviewWeather.setAdapter(adapter);

    }

    private void bindListData() {
        adapter.setNewData(weatherList);
    }


    private void initMap() {

        aMap.addPolyline(new PolylineOptions().
                addAll(afterPoints).width(10).color(R.color.colorBlue));

        ArrayList<MarkerOptions> markers = new ArrayList<>();
        for (int i = 0, len = afterPoints.size(); i < len; i++) {
            markers.add(new MarkerOptions().position(afterPoints.get(i)).snippet(GsonUtil.toJson(weatherList.get(i))));
        }

        aMap.addMarkers(markers, true);

        aMap.setInfoWindowAdapter(this);

    }

    private void getAfterPoint() {

        afterPoints = new ArrayList<>();
        Intent intent = getIntent();
        double startLa = intent.getDoubleExtra("v1", 0);
        double startLo = intent.getDoubleExtra("v2", 0);
        double stopLa = intent.getDoubleExtra("v3", 0);
        double stopLo = intent.getDoubleExtra("v4", 0);


        double x = (stopLo - startLo) * 0.2;
        double y = (stopLa - startLa) * 0.2;
        Logger.e("斜度:" + x);

        for (int i = 0; i <= 5; i++) {
            double x1 = startLa + (y * i);//第一个点La
            double x2 = startLo + (x * i);//第一个点Long
            LatLng latLng = new LatLng(x1, x2);
            afterPoints.add(latLng);
        }

    }

    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图

    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    View infoWindow = null;

    @Override
    public View getInfoWindow(Marker marker) {
        if(infoWindow == null) {
            infoWindow = View.inflate(this,R.layout.item_weather_info_window_layout,null);
        }
        render(marker, infoWindow);
        return infoWindow;
    }

    private void render(Marker marker, View infoWindow) {
        LocalWeatherLive weatherLive = GsonUtil.fromJson(marker.getSnippet(),new TypeToken<LocalWeatherLive>(){}.getType());
        BaseViewHolder holder = new BaseViewHolder(infoWindow);
        holder.setText(R.id.temp, mContext.getString(R.string.text_temperature,weatherLive.getTemperature()));
        holder.setText(R.id.address, weatherLive.getProvince() + weatherLive.getCity());
        holder.setText(R.id.text1, weatherLive.getWeather());
        holder.setText(R.id.text2, mContext.getString(R.string.text_wind_direction,weatherLive.getWindDirection()));
        int integer = icMap.get(weatherLive.getWeather());
        if(integer == 0){
            holder.setImageResource(R.id.icon,icMap.get("阴"));
        }else {
            holder.setImageResource(R.id.icon,icMap.get(weatherLive.getWeather()));
        }
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
