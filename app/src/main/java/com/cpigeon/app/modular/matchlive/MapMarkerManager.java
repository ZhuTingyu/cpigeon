package com.cpigeon.app.modular.matchlive;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.cpigeon.app.utils.http.GsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenshuai on 2017/11/10.
 */

public class MapMarkerManager  {

    private AMap aMap;
    private ArrayList<MarkerOptions> markerList;
    private Context context;

    public MapMarkerManager(AMap aMap, Context context){
        this.aMap = aMap;
        markerList = new ArrayList<>();
        this.context = context;
    }

    public void addMarker(double lat, double lng, @Nullable String title, @Nullable String snippet){
        LatLng latLng = new LatLng(lat,lng);
        MarkerOptions marker = new MarkerOptions().position(latLng);
        if(!title.isEmpty()){
            marker.title(title);
        }
        if(!snippet.isEmpty()){
            marker.snippet(snippet);
        }
        markerList.add(marker);
    }

    public void addCustomMarker(double lat, double lng, @DrawableRes int ResId){
        LatLng latLng = new LatLng(lat,lng);
        MarkerOptions marker = new MarkerOptions().position(latLng);
        marker.draggable(true);//设置Marker可拖动
        marker.setFlat(true);//设置marker平贴地图效果
        marker.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(context.getResources(),ResId)));
        markerList.add(marker);
    }

    public void addMarkerList(List<LatLng> point, List<String> context){

        for (int i = 0, len = point.size(); i < len; i++) {
            markerList.add(new MarkerOptions().position(point.get(i)).snippet(context.get(i)));
        }

    }


    public void addMap(){
        aMap.addMarkers(markerList,true);
    }

}
