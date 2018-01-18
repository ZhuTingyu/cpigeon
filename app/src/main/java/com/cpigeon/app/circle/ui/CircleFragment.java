package com.cpigeon.app.circle.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.adapter.fragmentpager.FragmentPagerItemAdapter;
import com.cpigeon.app.commonstandard.view.adapter.fragmentpager.FragmentPagerItems;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.utils.IntentBuilder;
import com.cpigeon.app.utils.ToastUtil;
import com.cpigeon.app.utils.customview.smarttab.SmartTabLayout;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Zhu TingYu on 2018/1/4.
 */

public class CircleFragment extends BaseMVPFragment{

    CircleImageView imgHeadIcon;
    TextView tvUserName;
    TextView tvFans;
    TextView tvFocus;

    SmartTabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected boolean isCanDettach() {
        return false;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_pigeon_circle_layout;
    }

    @Override
    public void finishCreateView(Bundle state) {
        setTitle("鸽迷圈");
        toolbar.setNavigationIcon(R.drawable.vector_pigeon_friends);
        toolbar.setNavigationOnClickListener(v -> {
            IntentBuilder.Builder().startParentActivity(getActivity(), CircleFriendFragment.class);
        });
        toolbar.getMenu().clear();
        toolbar.getMenu().add("").setIcon(R.drawable.vector_push_pigeon_circle)
                .setOnMenuItemClickListener(item -> {
                    IntentBuilder.Builder().startParentActivity(getActivity(), PushCircleMessageFragment.class);
                    return false;
                }).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        imgHeadIcon = findViewById(R.id.head_img);
        tvUserName = findViewById(R.id.user_name);
        tvFans = findViewById(R.id.tv_fans);
        tvFocus = findViewById(R.id.tv_focus);

        tabLayout = findViewById(R.id.tab_view);
        viewPager = findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(3);

        initHeadView();

        Bundle bundle = new Bundle();
        bundle.putBoolean(IntentBuilder.KEY_BOOLEAN, false);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getActivity().getSupportFragmentManager(), FragmentPagerItems.with(getContext())
                .add("全部动态", BaseCircleMessageFragment.class, bundle)
                .add("我的关注", BaseCircleMessageFragment.class, bundle)
                .add("我的发布", BaseCircleMessageFragment.class, bundle)
                .create());

        viewPager.setAdapter(adapter);
        tabLayout.setViewPager(viewPager);

    }

    private void initHeadView() {
        Glide.with(getContext())
                .load("http://img3.imgtn.bdimg.com/it/u=1611505379,380489200&fm=27&gp=0.jpg")
                .into(imgHeadIcon);
        tvUserName.setText("小朱");
        tvFans.setText(String.format("粉丝:%s","12"));
        tvFocus.setText(String.format("关注:%s","1323"));

    }
}
