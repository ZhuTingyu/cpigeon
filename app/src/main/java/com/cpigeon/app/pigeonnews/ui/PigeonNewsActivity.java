package com.cpigeon.app.pigeonnews.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.activity.BaseActivity;
import com.cpigeon.app.commonstandard.view.adapter.fragmentpager.FragmentPagerItemAdapter;
import com.cpigeon.app.commonstandard.view.adapter.fragmentpager.FragmentPagerItems;
import com.cpigeon.app.utils.customview.smarttab.SmartTabLayout;

/**
 * Created by Zhu TingYu on 2018/1/6.
 */

public class PigeonNewsActivity extends BaseActivity {

    SmartTabLayout tabLayout;
    ViewPager viewPager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_tab_layout;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setTitle("中鸽快报");
        tabLayout = findViewById(R.id.tab_view);
        viewPager = findViewById(R.id.view_pager);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("新闻资讯",NewsFragment.class)
                .add("地震信息", NewsFragment.class)
                .add("太阳磁暴", NewsFragment.class)
                .create());

        viewPager.setAdapter(adapter);

        tabLayout.setViewPager(viewPager);

    }
}
