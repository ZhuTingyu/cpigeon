package com.cpigeon.app.modular.matchlive.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.maps.utils.overlay.SmoothMoveMarker;
import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.modular.matchlive.model.bean.GYTRaceLocation;
import com.cpigeon.app.modular.matchlive.model.bean.GeCheJianKongRace;
import com.cpigeon.app.modular.matchlive.model.bean.MyLocation;
import com.cpigeon.app.modular.matchlive.presenter.GYTRaceLocationPre;
import com.cpigeon.app.modular.matchlive.view.activity.MapLiveActivity;
import com.cpigeon.app.modular.matchlive.view.fragment.viewdao.IMapLiveView;
import com.cpigeon.app.utils.PointsUtil;
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

public class MapLiveFragment extends BaseMVPFragment<GYTRaceLocationPre> implements IMapLiveView {

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
    private List<MyLocation> mylocationList;
    private AMap aMap;
    private GeCheJianKongRace geCheJianKongRace;
    private Bundle state;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (geCheJianKongRace == null)
            this.geCheJianKongRace = ((MapLiveActivity) getActivity()).getGeCheJianKongRace();
    }

    @Override
    public void showMapData(List<GYTRaceLocation> raceLocations) {
        mylocationList = new ArrayList<>();
        MyLocation mylocation = new MyLocation();
        for (GYTRaceLocation gytlocation :
                raceLocations) {
            mylocation.setLatitude(gytlocation.getLid());
            mylocation.setLongitude(gytlocation.getJd());
//            mylocation.setWeather(gytlocation.getTq().getMc());
            mylocation.setRaceid(geCheJianKongRace.getId());
//            mylocation.setWindDirection(gytlocation.getTq().getFx());
//            mylocation.setTemperature(gytlocation.getTq().getWd()+"°");
            mylocation.setGetReportTime(gytlocation.getSj());
            mylocationList.add(mylocation);
        }
    }

    @Override
    public String getRid() {
        Logger.e(geCheJianKongRace.getId() + "");
        return String.valueOf(geCheJianKongRace.getId());
    }

    @Override
    public String getLid() {
        return null;
    }

    @Override
    public Boolean hw() {
        return null;
    }


    @Override
    public void showSpeed() {

    }

    @Override
    public void showWeather() {

    }

    @Override
    public void showFlyarea() {

    }

    @Override
    public void showFlyareaWeather() {

    }

    @Override
    public void showAreaDistance() {

    }

    @Override
    public void showNowLocation() {

    }

    @Override
    public void showNowWeather() {

    }

    @Override
    protected GYTRaceLocationPre initPresenter() {
        return new GYTRaceLocationPre(this);
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
        mMapView.onCreate(state);
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        mDisplaybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDisplaybtn.isChecked()) {
                    move();
                }
            }
        });

        mPresenter.loadGYTRaceLocation();
        isPrepared = false;
    }

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




    public void move() {
        addPolylineInPlayGround();
        // 获取轨迹坐标点
        List<LatLng> points = readLatLngs();
        if (points != null)
        {


            LatLngBounds.Builder b = LatLngBounds.builder();
            for (int i = 0; i < points.size(); i++) {
                b.include(points.get(i));
            }
            LatLngBounds bounds = b.build();
            aMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));

            final SmoothMoveMarker smoothMarker = new SmoothMoveMarker(aMap);
            // 设置滑动的图标
            smoothMarker.setDescriptor(BitmapDescriptorFactory.fromResource(R.mipmap.car));

            LatLng drivePoint = points.get(0);
            Pair<Integer, LatLng> pair = PointsUtil.calShortestDistancePoint(points, drivePoint);
            points.set(pair.first, drivePoint);
            List<LatLng> subList = points.subList(pair.first, points.size());

            // 设置滑动的轨迹左边点
            smoothMarker.setPoints(subList);
            smoothMarker.setTotalDuration(40);
            smoothMarker.startSmoothMove();
        }else {
            Logger.e("数量太少了");
            mDisplaybtn.setChecked(false);
        }
    }

    private void addPolylineInPlayGround() {
        List<LatLng> list = readLatLngs();
        if (list !=null)
        {
            List<Integer> colorList = new ArrayList<Integer>();
            List<BitmapDescriptor> bitmapDescriptors = new ArrayList<BitmapDescriptor>();

            int[] colors = new int[]{Color.argb(255, 0, 255, 0), Color.argb(255, 255, 255, 0), Color.argb(255, 255, 0, 0)};

            //用一个数组来存放纹理
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

            }

            aMap.addPolyline(new PolylineOptions().setCustomTexture(BitmapDescriptorFactory.fromResource(R.drawable.custtexture)) //setCustomTextureList(bitmapDescriptors)
                    .addAll(list)
                    .useGradient(true)
                    .width(18));
        }else {
            Logger.e("数量太少了");
        }

    }

    private List<LatLng> readLatLngs() {
        List<MyLocation> myLocations = mylocationList;
        Logger.e(myLocations.size()+"条数据");
        if (myLocations.size() > 10)
        {
            List<LatLng> points = new ArrayList<LatLng>();
            for (MyLocation myLocation : myLocations) {
                points.add(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()));
            }
            return points;
        }else {
            return null;
        }
    }



}
