package com.cpigeon.app.modular.matchlive.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.activity.BaseActivity;
import com.cpigeon.app.utils.CommonTool;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/7/25.
 */

public class WeatherActivity extends BaseActivity {
    Polyline polyline;
    @BindView(R.id.map)
    MapView mMapView;
    @BindView(R.id.recyclerview_weather)
    RecyclerView recyclerviewWeather;
    AMap aMap;
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
        Intent intent = getIntent();
        double startLa = intent.getDoubleExtra("v1",0);
        double startLo = intent.getDoubleExtra("v2",0);
        double stopLa = intent.getDoubleExtra("v3",0);
        double stopLo = intent.getDoubleExtra("v4",0);

        List<LatLng> latLngs = new ArrayList<LatLng>();
        latLngs.add(new LatLng(startLa,startLo));
        latLngs.add(new LatLng(stopLa,stopLo));
        polyline =aMap.addPolyline(new PolylineOptions().
                addAll(latLngs).width(10).color(Color.argb(255, 1, 1, 1)));
        LatLngBounds.Builder b = LatLngBounds.builder();
        for (int i = 0 ; i < latLngs.size(); i++) {
            b.include(latLngs.get(i));
        }
        LatLngBounds bounds = b.build();
        aMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 16));
        double x = (stopLo-startLo)*0.2;
        double y = (stopLa-startLa)*0.2;
        Logger.e("斜度:"+x);

        for (int i = 0; i<=5;i++)
        {
            double x1 = startLa +(y*i);//第一个点La
            double x2 = startLo +(x*i);//第一个点Long
            LatLng latLng = new LatLng(x1,x2);
            final Marker marker = aMap.addMarker(new MarkerOptions().position(latLng).title("北京").snippet("当前坐标："+
            CommonTool.GPS2AjLocation(latLng.latitude)+"/"+CommonTool.GPS2AjLocation(latLng.longitude)));
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

}