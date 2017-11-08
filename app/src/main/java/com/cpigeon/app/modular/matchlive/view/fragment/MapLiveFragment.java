package com.cpigeon.app.modular.matchlive.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.maps.utils.overlay.SmoothMoveMarker;
import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.modular.matchlive.model.bean.GYTRaceLocation;
import com.cpigeon.app.modular.matchlive.model.bean.GeCheJianKongRace;
import com.cpigeon.app.modular.matchlive.presenter.GYTRaceLocationPre;
import com.cpigeon.app.modular.matchlive.view.activity.MapLiveActivity;
import com.cpigeon.app.modular.matchlive.view.activity.WeatherActivity;
import com.cpigeon.app.modular.matchlive.view.fragment.viewdao.IMapLiveView;
import com.cpigeon.app.utils.CommonTool;
import com.cpigeon.app.utils.DateTool;
import com.cpigeon.app.utils.PointsUtil;
import com.cpigeon.app.utils.ViewExpandAnimation;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 直播界面
 * Created by Administrator on 2017/7/12.
 */

public class MapLiveFragment extends BaseMVPFragment{

    @BindView(R.id.displaybtn)
    ToggleButton mDisplaybtn;
    @BindView(R.id.mapView)
    TextureMapView mMapView;
    @BindView(R.id.tv_map_status)
    TextView tvMapStatus;
    @BindView(R.id.tv_map_location)
    TextView tvMapLocation;
    @BindView(R.id.tv_map_weather)
    TextView tvMapWeather;
    @BindView(R.id.tv_map_nowlocation)
    TextView tvMapNowlocation;
    @BindView(R.id.tv_map_nowweather)
    TextView tvMapNowweather;
    @BindView(R.id.tv_map_nowareadistance)
    TextView tvMapNowareadistance;
    @BindView(R.id.tv_map_distance)
    TextView tvMapDistance;
    @BindView(R.id.tv_map_speed)
    TextView tvMapSpeed;
    @BindView(R.id.constraintlayout_details)
    ConstraintLayout mConstraintLayout;
    @BindView(R.id.fab_weather)
    FloatingActionButton floatingActionButton;
    @BindView(R.id.tv_map_time)
    TextView tvMapTime;
    Unbinder unbinder;
    private AMap aMap;
    private GeCheJianKongRace geCheJianKongRace;
    private Bundle state;
    private List<GYTRaceLocation> gytRaceLocations = new ArrayList<>();
    private SmoothMoveMarker smoothMarker;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (geCheJianKongRace == null)
            this.geCheJianKongRace = ((MapLiveActivity) getActivity()).getGeCheJianKongRace();

        gytRaceLocations = getArguments().getParcelableArrayList("data");
    }

    public void showMapData() {
        if (gytRaceLocations != null && gytRaceLocations.size() > 0) {
            GYTRaceLocation lastLocation = gytRaceLocations.get(gytRaceLocations.size() - 1);
            GYTRaceLocation topLocation = gytRaceLocations.get(0);
            double lastLatitude;
            double lastLongtitude;
            if (geCheJianKongRace.getLatitude() != 0 && geCheJianKongRace.getLongitude() != 0) {
                lastLatitude = geCheJianKongRace.getLatitude();
                lastLongtitude = geCheJianKongRace.getLongitude();
                tvMapNowareadistance.setText("空距:" + DateTool.doubleformat(CommonTool.getDistance(topLocation.getWd(), topLocation.getJd(),
                        CommonTool.Aj2GPSLocation(lastLatitude),
                        CommonTool.Aj2GPSLocation(lastLongtitude)) * 0.001, 2) + " Km");
                tvMapLocation.setText("司放地坐标:" + lastLongtitude + "/" + lastLatitude);
            } else {
                lastLatitude = lastLocation.getWd();
                lastLongtitude = lastLocation.getJd();
                Logger.e("");
                tvMapNowareadistance.setText("空距:" + DateTool.doubleformat(CommonTool.getDistance(topLocation.getWd(), topLocation.getJd(),
                        lastLatitude,
                        lastLongtitude) * 0.001, 2) + "Km");
                tvMapLocation.setText("司放地坐标:" + CommonTool.GPS2AjLocation(lastLongtitude) + "/" + CommonTool.GPS2AjLocation(lastLatitude));
            }


            tvMapNowweather.setText("当前天气:" + lastLocation.getTq().getMc());
            tvMapNowlocation.setText("当前坐标:" + CommonTool.GPS2AjLocation(lastLocation.getJd()) + "/" +
                    CommonTool.GPS2AjLocation(lastLocation.getWd()));
            long usingtime = (
                    (System.currentTimeMillis() / 1000) - topLocation.getSj()
            );

            tvMapDistance.setText("总里程:" + DateTool.doubleformat(lastLocation.getLc() * 0.001, 2) + " Km");
            tvMapTime.setText("已监控:" + DateTool.getTimeFormat(usingtime));
            tvMapSpeed.setText(String.format("平均车速:%s Km/H", DateTool.doubleformat((lastLocation.getLc() / usingtime) * 3.6, 2)));
            tvMapWeather.setText("司放地天气:" + lastLocation.getTq().getMc() + " " + lastLocation.getTq().getWd() + "°"
                    + " " + lastLocation.getTq().getFx() + "风 ");
            tvMapStatus.setText(geCheJianKongRace.getState());
            addPolylineInPlayGround();
            if(geCheJianKongRace.getState().equals("监控中")){
                //mDisplaybtn.setVisibility(View.GONE);

            }else {
                //move();
            }
        } else {
            Toast.makeText(getActivity(), "暂无数据或数据太短", Toast.LENGTH_SHORT).show();
            mDisplaybtn.setChecked(false);
        }

        move();
    }

    private List<LatLng> readLatLngs() {
        List<LatLng> points = new ArrayList<LatLng>();
        for (GYTRaceLocation gytRaceLocation : gytRaceLocations) {
            points.add(new LatLng(gytRaceLocation.getWd(), gytRaceLocation.getJd()));
        }
        return points;
    }


    @Override
    protected GYTRaceLocationPre initPresenter() {
        return null;
    }

    @Override
    protected boolean isCanDettach() {
        return true;
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        //mConstraintLayout.setVisibility(View.GONE);
        mMapView.onCreate(state);
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        isPrepared = false;
        showMapData();
        mDisplaybtn.setOnClickListener(v -> {
            if (mDisplaybtn.isChecked()) {
                //expandinfo();
                smoothMarker.startSmoothMove();

            } else {
                smoothMarker.stopMove();
                //expandinfo();
            }
        });
        /*floatingActionButton.setOnClickListener(v -> {
            if (geCheJianKongRace.getLatitude() != 0 && geCheJianKongRace.getLongitude() != 0) {
                startWeather(gytRaceLocations.get(0).getWd(), gytRaceLocations.get(0).getJd(), CommonTool.Aj2GPSLocation(geCheJianKongRace.getLatitude()),
                        CommonTool.Aj2GPSLocation(geCheJianKongRace.getLongitude()));
            } else {
                startWeather(gytRaceLocations.get(0).getWd(), gytRaceLocations.get(0).getJd(), gytRaceLocations.get(gytRaceLocations.size() - 1).getWd(),
                        gytRaceLocations.get(gytRaceLocations.size() - 1).getJd());
            }
        });*/
    }

    /*private void startWeather(double v1, double v2, double v3, double v4) {
        Intent intent = new Intent(getActivity(), WeatherActivity.class);
        intent.putExtra("v1", v1);
        intent.putExtra("v2", v2);
        intent.putExtra("v3", v3);
        intent.putExtra("v4", v4);
        startActivity(intent);
    }


    private void expandinfo() {
        if (mConstraintLayout.getVisibility() == View.VISIBLE) {
            mConstraintLayout.setVisibility(View.GONE);
        } else {
            mConstraintLayout.setVisibility(View.VISIBLE);
        }
        ViewExpandAnimation expandAnimation = new ViewExpandAnimation(mConstraintLayout);
        mConstraintLayout.startAnimation(expandAnimation);
    }*/

    @Override
    public void finishCreateView(Bundle state) {
        this.state = state;
        isPrepared = true;
        lazyLoad();

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_map_lookback;
    }

    private void addPolylineInPlayGround() {
        List<LatLng> list = readLatLngs();
        /*List<Integer> colorList = new ArrayList<Integer>();
        List<BitmapDescriptor> bitmapDescriptors = new ArrayList<BitmapDescriptor>();

        int[] colors = new int[]{Color.argb(255, 0, 255, 0), Color.argb(255, 255, 255, 0), Color.argb(255, 255, 0, 0)};
        List<BitmapDescriptor> textureList = new ArrayList<BitmapDescriptor>();
        textureList.add(BitmapDescriptorFactory.fromResource(R.drawable.custtexture));

        List<Integer> texIndexList = new ArrayList<Integer>();
        texIndexList.add(0);//对应上面的第0个纹理
        texIndexList.add(1);
        texIndexList.add(2);

        Random random = new Random();
        for (int i = 0; i < list.size(); i++) {
            colorList.add(colors[random.nextInt(3)]);
            bitmapDescriptors.add(textureList.get(0));

        }*/

        aMap.addPolyline(new PolylineOptions().setCustomTexture(BitmapDescriptorFactory.fromResource(R.drawable.custtexture)) //setCustomTextureList(bitmapDescriptors)
                .addAll(list)
                .useGradient(true)
                .width(18));
    }

    public void move() {
        // 获取轨迹坐标点
        List<LatLng> points = readLatLngs();
        LatLngBounds.Builder b = LatLngBounds.builder();
        for (int i = 0; i < points.size(); i++) {
            b.include(points.get(i));
        }
        LatLngBounds bounds = b.build();
        aMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
        aMap.setInfoWindowAdapter(infoWindowAdapter);
        smoothMarker = new SmoothMoveMarker(aMap);
        // 设置滑动的图标
        smoothMarker.setDescriptor(BitmapDescriptorFactory.fromResource(R.mipmap.car));
        smoothMarker.setMoveListener(v -> getActivity().runOnUiThread(() -> {
            if (infoWindowLayout != null && title != null && smoothMarker.getMarker().isInfoWindowShown()) {
                title.setText("距离司放地还有： " + DateTool.doubleformat(v * 0.001, 2) + "Km" + "\n" +
                        "车速：" + DateTool.doubleformat(gytRaceLocations.get(smoothMarker.getIndex()).getSd(), 2) + "Km/H");

            }
            if (v == 0) {
                smoothMarker.getMarker().hideInfoWindow();
            } else {
                LatLng position = smoothMarker.getPosition();
                aMap.animateCamera(CameraUpdateFactory.changeLatLng(position));
            }
        }));
        LatLng drivePoint = points.get(0);
        Pair<Integer, LatLng> pair = PointsUtil.calShortestDistancePoint(points, drivePoint);
        points.set(pair.first, drivePoint);
        List<LatLng> subList = points.subList(pair.first, points.size());

        // 设置滑动的轨迹左边点
        smoothMarker.setPoints(subList);
        smoothMarker.setTotalDuration(40);

    }

    AMap.InfoWindowAdapter infoWindowAdapter = new AMap.InfoWindowAdapter() {
        @Override
        public View getInfoWindow(Marker marker) {

            return getInfoWindowView(marker);
        }

        @Override
        public View getInfoContents(Marker marker) {


            return getInfoWindowView(marker);
        }
    };

    LinearLayout infoWindowLayout;
    TextView title;
    TextView snippet;

    private View getInfoWindowView(Marker marker) {
        if (infoWindowLayout == null) {
            infoWindowLayout = new LinearLayout(getActivity());
            infoWindowLayout.setOrientation(LinearLayout.VERTICAL);
            title = new TextView(getActivity());
            snippet = new TextView(getActivity());
            title.setTextColor(Color.BLACK);
            snippet.setTextColor(Color.BLACK);
            infoWindowLayout.setBackgroundResource(R.mipmap.infowindow_bg);

            infoWindowLayout.addView(title);
            infoWindowLayout.addView(snippet);
        }

        return infoWindowLayout;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        mMapView.onDestroy();
        super.onDestroyView();
    }
}
