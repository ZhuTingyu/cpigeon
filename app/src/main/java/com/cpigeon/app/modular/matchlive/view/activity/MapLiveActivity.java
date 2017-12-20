package com.cpigeon.app.modular.matchlive.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.activity.BaseActivity;
import com.cpigeon.app.commonstandard.view.adapter.fragmentpager.FragmentPagerItemAdapter;
import com.cpigeon.app.commonstandard.view.adapter.fragmentpager.FragmentPagerItems;
import com.cpigeon.app.message.ui.home.PigeonMessageHomeFragment;
import com.cpigeon.app.modular.matchlive.model.bean.GYTRaceLocation;
import com.cpigeon.app.modular.matchlive.model.bean.GeCheJianKongRace;
import com.cpigeon.app.modular.matchlive.presenter.GYTRaceLocationPre;
import com.cpigeon.app.modular.matchlive.view.fragment.MapLiveFragment;
import com.cpigeon.app.modular.matchlive.view.fragment.MapPhotoFragment;
import com.cpigeon.app.modular.matchlive.view.fragment.MapVideoFragment;
import com.cpigeon.app.modular.matchlive.view.fragment.viewdao.IMapLiveView;
import com.cpigeon.app.utils.CommonTool;
import com.cpigeon.app.utils.IntentBuilder;
import com.cpigeon.app.utils.customview.MarqueeTextView;
import com.cpigeon.app.utils.customview.smarttab.SmartTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.JZVideoPlayer;

/**
 * Created by Administrator on 2017/7/12.
 */

public class MapLiveActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewpagertab)
    SmartTabLayout viewpagertab;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.title)
    MarqueeTextView title;
    private GeCheJianKongRace geCheJianKongRace;

    private double v1;
    private double v2;
    private double v3;
    private double v4;

    @Override
    public int getLayoutId() {
        return R.layout.activity_map_live_layout;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        geCheJianKongRace = (GeCheJianKongRace) bundle.getSerializable("geCheJianKongRace");
        title.setText(geCheJianKongRace.getRaceName());
        toolbar.getMenu().clear();
        toolbar.getMenu().add("沿途天气")
                .setOnMenuItemClickListener(item -> {
                    startWeather();
                    return false;
                }).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        toolbar.setNavigationOnClickListener(v -> finish());

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
        initFragments();

    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    private void initFragments() {

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("路程回放", MapLiveFragment.class)
                .add("路程照片", MapPhotoFragment.class)
                .add("路程视频", MapVideoFragment.class)
                .create());

        viewPager.setAdapter(adapter);
        viewpagertab.setViewPager(viewPager);

    }


    public GeCheJianKongRace getGeCheJianKongRace() {
        return geCheJianKongRace;
    }

    private void startWeather() {
        Intent intent = new Intent(getApplicationContext(), WeatherActivity.class);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.putExtra("v1", v1);
        intent.putExtra("v2", v2);
        intent.putExtra("v3", v3);
        intent.putExtra("v4", v4);
        startActivity(intent);
        /*IntentBuilder.Builder(getActivity(), WeatherActivity.class)
                .putExtra("v1", v1)
                .putExtra("v2", v2)
                .putExtra("v3", v3)
                .putExtra("v4", v4)
                .startActivity();*/
    }

    public void setPoint(List<Double> points){
        v1 = points.get(0);
        v2 = points.get(1);
        v3 = points.get(2);
        v4 = points.get(3);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
