package com.cpigeon.app.modular.matchlive.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.activity.BaseActivity;
import com.cpigeon.app.commonstandard.view.adapter.fragmentpager.FragmentPagerItemAdapter;
import com.cpigeon.app.commonstandard.view.adapter.fragmentpager.FragmentPagerItems;
import com.cpigeon.app.modular.matchlive.model.bean.GYTRaceLocation;
import com.cpigeon.app.modular.matchlive.model.bean.GeCheJianKongRace;
import com.cpigeon.app.modular.matchlive.presenter.GYTRaceLocationPre;
import com.cpigeon.app.modular.matchlive.view.fragment.GeCheJianKongListFragment;
import com.cpigeon.app.modular.matchlive.view.fragment.MapLiveFragment;
import com.cpigeon.app.modular.matchlive.view.fragment.MapPhotoFragment;
import com.cpigeon.app.modular.matchlive.view.fragment.MapVideoFragment;
import com.cpigeon.app.modular.matchlive.view.fragment.viewdao.IMapLiveView;
import com.cpigeon.app.utils.CommonTool;
import com.cpigeon.app.utils.customview.smarttab.SmartTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.JZVideoPlayer;

/**
 * Created by Administrator on 2017/7/12.
 */

public class MapLiveActivity extends BaseActivity<GYTRaceLocationPre> implements IMapLiveView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewpagertab)
    SmartTabLayout viewpagertab;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private GeCheJianKongRace geCheJianKongRace;
    List<GYTRaceLocation> raceLocations;

    @Override
    public int getLayoutId() {
        return R.layout.layout_com_tab_viewpager;
    }

    @Override
    public GYTRaceLocationPre initPresenter() {
        return new GYTRaceLocationPre(this);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        geCheJianKongRace = (GeCheJianKongRace) bundle.getSerializable("geCheJianKongRace");
        //setSupportActionBar(toolbar);
        toolbar.setTitle(geCheJianKongRace.getRaceName());
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getMenu().clear();
        toolbar.getMenu().add("沿途天气")
                .setOnMenuItemClickListener(item -> {
                    if (geCheJianKongRace.getLatitude() != 0 && geCheJianKongRace.getLongitude() != 0) {
                        startWeather(raceLocations.get(0).getWd(), raceLocations.get(0).getJd(), CommonTool.Aj2GPSLocation(geCheJianKongRace.getLatitude()),
                                CommonTool.Aj2GPSLocation(geCheJianKongRace.getLongitude()));
                    } else {
                        startWeather(raceLocations.get(0).getWd(), raceLocations.get(0).getJd(), raceLocations.get(raceLocations.size() - 1).getWd(),
                                raceLocations.get(raceLocations.size() - 1).getJd());
                    }
                    return false;
                }).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        toolbar.setNavigationOnClickListener(v -> finish());


        mPresenter.loadGYTRaceLocation();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                JZVideoPlayer.releaseAllVideos();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    private void initFragments() {

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("data", (ArrayList<? extends Parcelable>) raceLocations);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("路程回放", MapLiveFragment.class, bundle)
                .add("路程照片", MapPhotoFragment.class)
                .add("路程视频", MapVideoFragment.class)
                .create());

        viewPager.setAdapter(adapter);
        viewpagertab.setViewPager(viewPager);
    }


    public GeCheJianKongRace getGeCheJianKongRace() {
        return geCheJianKongRace;
    }

    private void startWeather(double v1, double v2, double v3, double v4) {
        Intent intent = new Intent(this, WeatherActivity.class);
        intent.putExtra("v1", v1);
        intent.putExtra("v2", v2);
        intent.putExtra("v3", v3);
        intent.putExtra("v4", v4);
        startActivity(intent);
    }

    @Override
    public void showMapData(List<GYTRaceLocation> raceLocations) {

        this.raceLocations = raceLocations;
        initFragments();

    }

    @Override
    public String getRid() {
        return String.valueOf(geCheJianKongRace.getId());
    }

    @Override
    public String getLid() {
        return null;
    }

    @Override
    public String hw() {
        return "y";
    }
}
