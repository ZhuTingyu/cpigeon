package com.cpigeon.app.modular.matchlive.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
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
import com.cpigeon.app.utils.WeatherManager;
import com.cpigeon.app.utils.http.GsonUtil;
import com.cpigeon.app.view.WeatherInfoView;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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
    List<RegeocodeAddress> addressList;
    List<LocalWeatherLive> weatherList;

    WeatherManager manager;


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


        getAfterPoint();
        initListView();

        manager = new WeatherManager(this);

        addressList = new ArrayList<>();
        weatherList = new ArrayList<>();

        manager.setseacherCityCallBack().subscribe(r -> {
            if (r.isOk()) {
                addressList.add(r.data);
                if (addressList.size() == afterPoints.size()) {
                    requestWeatherByCityName();
                }
            }
        });

        manager.setrequsetWeatherCallBack().subscribe(r -> {
            if(r.isOk()){
                weatherList.add(r.data);
                if(weatherList.size() == addressList.size()){
                    bindListData();
                    initMap();
                }
            }else {
                showTips("获取天气失败",TipType.DialogError);
            }
        });

        searchCityByPoint();
    }


    private void searchCityByPoint() {

        for (int i = 0, len = afterPoints.size(); i < len; i++) {
            LatLng latLng = afterPoints.get(i);
            manager.seacherCityByLatLng(latLng);
        }
    }

    private void requestWeatherByCityName() {
        for(int i = 0, len = addressList.size();i < len; i++){
            manager.requsetWeatherByCityName(addressList.get(i).getCity());
        }
    }


    private void initListView() {
        recyclerviewWeather = (RecyclerView) findViewById(R.id.recyclerview_weather);
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

        LatLng firstPoint = new LatLng(startLa, startLo);
        LatLng endPoint = new LatLng(stopLa, stopLo);


        double x = (stopLo - startLo) * 0.2;
        double y = (stopLa - startLa) * 0.2;
        Logger.e("斜度:" + x);

        afterPoints.add(firstPoint);
        for (int i = 0; i <= 5; i++) {
            double x1 = startLa + (y * i);//第一个点La
            double x2 = startLo + (x * i);//第一个点Long
            LatLng latLng = new LatLng(x1, x2);
            afterPoints.add(latLng);
        }
        afterPoints.add(endPoint);

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
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
