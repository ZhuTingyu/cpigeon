package com.cpigeon.app.modular.matchlive;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.cpigeon.app.utils.Lists;
import com.cpigeon.app.utils.http.GsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenshuai on 2017/11/10.
 */

public class MapMarkerManager {

    private AMap aMap;
    private ArrayList<MarkerOptions> markerList;
    private Context context;

    public MapMarkerManager(AMap aMap, Context context) {
        this.aMap = aMap;
        markerList = new ArrayList<>();
        this.context = context;
    }

    public MarkerOptions addMarker(double lat, double lng, @Nullable String title, @Nullable String snippet) {
        LatLng latLng = new LatLng(lat, lng);
        MarkerOptions marker = new MarkerOptions().position(latLng);
        if (!title.isEmpty()) {
            marker.title(title);
        }
        if (!snippet.isEmpty()) {
            marker.snippet(snippet);
        }
        markerList.add(marker);
        return marker;
    }

    public MarkerOptions addCustomMarker(double lat, double lng, @DrawableRes int ResId) {
        LatLng latLng = new LatLng(lat, lng);
        MarkerOptions marker = new MarkerOptions().position(latLng);
        marker.draggable(true);//设置Marker可拖动
        marker.setFlat(true);//设置marker平贴地图效果
        marker.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(context.getResources(), ResId)));
        markerList.add(marker);
        return marker;
    }

    public void addMarkerList(List<LatLng> point, List<String> context) {

        for (int i = 0, len = point.size(); i < len; i++) {
            markerList.add(new MarkerOptions().position(point.get(i)).snippet(context.get(i)));
        }

    }

    public static float getAngle(List<LatLng> points,boolean isPositiveSequence) {

        LatLng startPoint = null;
        LatLng endPoint = null;

        if (isPositiveSequence) {
            startPoint = points.get(0);
            for (LatLng p : points) {
                if (p.latitude != startPoint.latitude){
                    endPoint = p;
                    break;
                }
            }
        } else {
            endPoint = points.get(points.size() - 1);
            for (int i = points.size() - 1; i >= 0; i--) {
                if (points.get(i).latitude != endPoint.latitude){
                    startPoint = points.get(i);
                    break;
                }
            }
        }

        double d = 0;

        if(endPoint != null && startPoint != null){
            double startY = startPoint.latitude;
            double startX = startPoint.longitude;
            double endY = endPoint.latitude;
            double endX = endPoint.longitude;

            double k1 = (double) (startY - startY) / (startX * 2 - startX);
            double k2 = (double) (endY - startY) / (endX - startX);
            double tmpDegree = Math.atan((Math.abs(k1 - k2)) / (1 + k1 * k2)) / Math.PI * 180;

            if (startX < endX && startY < endY) {  //第一象限
                d = 270 + tmpDegree;
            } else if (startX > endX && startY < endY) {//第二象限
                d = 90 - tmpDegree;
            } else if (startX > endX && startY > endY) { //第三象限
                d = 90 + tmpDegree;
            } else if (startX < endX && startY > endY) { //第四象限
                d = 270 - tmpDegree;
            } else if (endX == startX && startY < endY) {
                d = 0;
            } else if (endX == startX && startY > endY) {
                d = 180;
            }

        }

        return (float) d;
    }


    public void addMap() {
        aMap.addMarkers(markerList, true);
    }


}
