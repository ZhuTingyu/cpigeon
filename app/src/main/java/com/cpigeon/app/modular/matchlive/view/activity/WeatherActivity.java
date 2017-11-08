package com.cpigeon.app.modular.matchlive.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.activity.BaseActivity;
import com.cpigeon.app.entity.AddressEntity;
import com.cpigeon.app.modular.matchlive.view.adapter.AfterWeatherListAdapter;
import com.cpigeon.app.utils.CommonTool;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/7/25.
 */

public class WeatherActivity extends BaseActivity implements GeocodeSearch.OnGeocodeSearchListener, WeatherSearch.OnWeatherSearchListener {
    Polyline polyline;
    @BindView(R.id.map)
    MapView mMapView;
    @BindView(R.id.recyclerview_weather)
    RecyclerView recyclerviewWeather;
    AMap aMap;

    List<LatLng> afterPoints;
    AfterWeatherListAdapter adapter;
    Toolbar toolbar;
    List<AddressEntity> addressList;
    GeocodeSearch geocoderSearch;

    WeatherSearchQuery mquery;
    WeatherSearch mweathersearch;


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
        initMap();
        initListView();

        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);

        searchAbdCode();
    }

    private void searchWeather(String city) {
        mquery = new WeatherSearchQuery("北京", WeatherSearchQuery.WEATHER_TYPE_LIVE);
        mweathersearch = new WeatherSearch(this);
        mweathersearch.setOnWeatherSearchListener(this);
        mweathersearch.setQuery(mquery);
        mweathersearch.searchWeatherAsyn(); //异步搜索
    }

    private void searchAbdCode() {

        addressList = new ArrayList<>();

        for (int i = 0, len = afterPoints.size(); i < len; i++) {
            LatLng latLng = afterPoints.get(i);
            RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(latLng.latitude, latLng.longitude), 200, GeocodeSearch.AMAP);

            geocoderSearch.getFromLocationAsyn(query);
        }
    }

    private void searchWeather(){

    }

    private void initListView() {
        recyclerviewWeather = (RecyclerView) findViewById(R.id.recyclerview_weather);
        recyclerviewWeather.setLayoutManager(new LinearLayoutManager(this));
        addItemDecorationLine(recyclerviewWeather);
        ArrayList list = new ArrayList();
        for (int i = 0; i < 5; i++) {
            list.add(new Object());
        }
        adapter.setNewData(list);
        recyclerviewWeather.setAdapter(adapter);

    }


    private void initMap() {

        aMap.addPolyline(new PolylineOptions().
                addAll(afterPoints).width(10).color(Color.argb(255, 1, 1, 1)));

        ArrayList<MarkerOptions> markers = new ArrayList<>();
        for (int i = 0, len = afterPoints.size(); i < len; i++) {
            markers.add(new MarkerOptions().position(afterPoints.get(i)).title("北京").snippet("DefaultMarker"));
        }

        aMap.addMarkers(markers, true);

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

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        AddressEntity entity = new AddressEntity();
        entity.province = regeocodeResult.getRegeocodeAddress().getProvince();
        entity.city = regeocodeResult.getRegeocodeAddress().getCity();
        entity.adcCode = regeocodeResult.getRegeocodeAddress().getAdCode();
        addressList.add(entity);
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    @Override
    public void onWeatherLiveSearched(LocalWeatherLiveResult localWeatherLiveResult, int i) {

    }

    @Override
    public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int i) {

    }
}
