package com.cpigeon.app.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.cpigeon.app.MainActivity;
import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.home.adpter.HomeAdAdapter;
import com.cpigeon.app.home.adpter.HomeLeadAdapter;
import com.cpigeon.app.home.adpter.HomeNewAdapter;
import com.cpigeon.app.message.ui.home.PigeonMessageHomeFragment;
import com.cpigeon.app.modular.matchlive.view.activity.GeCheJianKongListActicity;
import com.cpigeon.app.utils.IntentBuilder;
import com.cpigeon.app.utils.Lists;
import com.cpigeon.app.utils.RxUtils;
import com.cpigeon.app.utils.ToastUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import io.reactivex.disposables.Disposable;

/**
 * Created by Zhu TingYu on 2017/12/29.
 */

public class HomeNewFragment extends BaseMVPFragment {

    private static final int TYPE_NEWS_HEAD = 0;
    private static final int TYPE_DYNAMIC_HEAD = 1;

    MZBannerView banner;

    RecyclerView leadList;
    RecyclerView adList;
    RecyclerView newsList;
    RecyclerView dynamicList;

    HomeLeadAdapter leadAdapter;
    HomeAdAdapter adAdapter;
    HomeNewAdapter newAdapter;

    MainActivity activity;

    Disposable disposable;

    int adPosition = 0;


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
        return R.layout.fragment_new_home_layout;
    }

    @Override
    public void finishCreateView(Bundle state) {

        activity = (MainActivity) getActivity();

        setTitle("中鸽网");

        toolbar.setNavigationIcon(R.mipmap.ic_home_top_my);
        toolbar.setNavigationOnClickListener(v -> {
            ToastUtil.showLongToast(getContext(), "home");
        });

        toolbar.getMenu().clear();
        toolbar.getMenu().add("").setIcon(R.mipmap.ic_home_top_message)
                .setOnMenuItemClickListener(item -> {
                    return false;
                }).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        banner = findViewById(R.id.banner);
        leadList = findViewById(R.id.lead_list);
        adList = findViewById(R.id.ad_list);
        newsList = findViewById(R.id.news_list);

        banner.setPages(Lists.newArrayList("", "", "", ""), () -> {
            return new BannerViewHolder();
        });
        banner.start();

        initLeadList();
        initAdList();
        initNewList();
        initDynamicList();
    }

    private void initDynamicList() {

    }

    private void initAdList() {
        adList.setLayoutManager(new LinearLayoutManager(getContext()));
        adAdapter = new HomeAdAdapter();
        adList.setAdapter(adAdapter);

        disposable = RxUtils.rollPoling(3, 2000, aLong -> {

            if (adPosition > adAdapter.getData().size() - 1) {
                adPosition = 0;
            }
            adList.smoothScrollToPosition(adPosition);
            adPosition += 1;

        });

        adAdapter.setNewData(Lists.newArrayList("", "", "", "", "", ""));
    }


    private void initNewList() {
        newsList.setLayoutManager(new LinearLayoutManager(getContext()));
        newAdapter = new HomeNewAdapter();
        newAdapter.addHeaderView(initHeadView(TYPE_NEWS_HEAD));
        newAdapter.setNewData(Lists.newArrayList("",""));
        newsList.setAdapter(newAdapter);
        addItemDecorationLine(newsList);
    }

    private void initLeadList() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 4) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        leadList.setLayoutManager(layoutManager);
        leadAdapter = new HomeLeadAdapter();
        leadList.setAdapter(leadAdapter);
        leadAdapter.setOnItemClickListener((adapter, view, position) -> {
            switch (position) {
                case 0://比赛直播
                    activity.setCurrIndex(1);
                    break;
                case 1://比赛监控
                    IntentBuilder.Builder(getActivity(), GeCheJianKongListActicity.class).startActivity();
                    break;
                case 2://鸽信通
                    IntentBuilder.Builder().startParentActivity(getActivity(), PigeonMessageHomeFragment.class);
                    break;
                case 3://足环查询
                    activity.setCurrIndex(2);
                    break;
            }
        });
    }

    private View initHeadView(int type) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_home_list_head_layout, null);
        BaseViewHolder holder = new BaseViewHolder(view);
        if (type == TYPE_NEWS_HEAD) {
            holder.setImageResource(R.id.icon, R.drawable.ic_news_head);
            holder.setText(R.id.title, "新闻资讯");
            holder.setText(R.id.title2, "实时显示新闻资讯");
        } else {
            holder.setImageResource(R.id.icon, R.drawable.ic_home_dynamic);
            holder.setText(R.id.title, "鸽友动态");
            holder.setText(R.id.title2, "实时显示最新动态");
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        banner.pause();
    }
}

class BannerViewHolder implements MZViewHolder<String> {
    private SimpleDraweeView mImageView;

    @Override
    public View createView(Context context) {
        // 返回页面布局
        View view = LayoutInflater.from(context).inflate(R.layout.item_banner_layou, null);
        mImageView = view.findViewById(R.id.iamgeView);
        return view;
    }

    @Override
    public void onBind(Context context, int position, String data) {
        // 数据绑定
        mImageView.setImageURI("http://img.zcool.cn/community/01e4a2577deac20000018c1bdd823a.jpg@1280w_1l_2o_100sh.jpg");
    }
}
