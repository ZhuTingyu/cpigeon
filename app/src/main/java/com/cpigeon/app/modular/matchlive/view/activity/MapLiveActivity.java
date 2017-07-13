package com.cpigeon.app.modular.matchlive.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.activity.BaseActivity;
import com.cpigeon.app.commonstandard.view.adapter.fragmentpager.FragmentPagerItemAdapter;
import com.cpigeon.app.commonstandard.view.adapter.fragmentpager.FragmentPagerItems;
import com.cpigeon.app.modular.matchlive.model.bean.GYTRaceLocation;
import com.cpigeon.app.modular.matchlive.model.bean.GeCheJianKongRace;
import com.cpigeon.app.modular.matchlive.view.fragment.GeCheJianKongListFragment;
import com.cpigeon.app.modular.matchlive.view.fragment.MapLiveFragment;
import com.cpigeon.app.modular.matchlive.view.fragment.MapPhotoFragment;
import com.cpigeon.app.modular.matchlive.view.fragment.MapVideoFragment;
import com.cpigeon.app.utils.customview.smarttab.SmartTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    private GeCheJianKongRace geCheJianKongRace;
    @Override
    public int getLayoutId() {
        return R.layout.layout_com_tab_viewpager;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        geCheJianKongRace = (GeCheJianKongRace) bundle.getSerializable("geCheJianKongRace");
        toolbar.setTitle(geCheJianKongRace.getRaceName());
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        initFragments();
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


    public GeCheJianKongRace getGeCheJianKongRace()
    {
        return geCheJianKongRace;
    }

}
