package com.cpigeon.app.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.cpigeon.app.MainActivity;
import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.entity.HomeNewsEntity;
import com.cpigeon.app.entity.NewsEntity;
import com.cpigeon.app.home.adpter.HomeAdAdapter;
import com.cpigeon.app.home.adpter.CircleDynamicAdapter;
import com.cpigeon.app.home.adpter.HomeLeadAdapter;
import com.cpigeon.app.home.adpter.PigeonNewsAdapter;
import com.cpigeon.app.message.ui.home.PigeonMessageHomeFragment;
import com.cpigeon.app.modular.footsearch.ui.FootSearchFragment;
import com.cpigeon.app.modular.home.model.bean.HomeAd;
import com.cpigeon.app.modular.home.view.activity.WebActivity;
import com.cpigeon.app.modular.matchlive.view.activity.GeCheJianKongListActicity;
import com.cpigeon.app.pigeonnews.ui.PigeonNewsActivity;
import com.cpigeon.app.utils.CommonTool;
import com.cpigeon.app.utils.ContactsUtil;
import com.cpigeon.app.utils.IntentBuilder;
import com.cpigeon.app.utils.RxUtils;
import com.cpigeon.app.utils.ToastUtil;
import com.cpigeon.app.utils.customview.SaActionSheetDialog;
import com.cpigeon.app.utils.http.LogUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import io.reactivex.disposables.Disposable;

/**
 * Created by Zhu TingYu on 2017/12/29.
 */

public class HomeNewFragment extends BaseMVPFragment<HomePre> {

    private static final int TYPE_NEWS = 0;
    private static final int TYPE_DYNAMIC = 1;

    MZBannerView banner;

    RecyclerView leadList;
    RecyclerView adList;
    RecyclerView newsList;
    RecyclerView dynamicList;

    HomeLeadAdapter leadAdapter;
    HomeAdAdapter adAdapter;
    PigeonNewsAdapter newAdapter;
    CircleDynamicAdapter dynamicAdapter;

    MainActivity activity;

    Disposable AdListDisposable;

    int adPosition = 0;


    @Override
    protected HomePre initPresenter() {
        return new HomePre(getActivity());
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
        toolbar.setNavigationOnClickListener(null);
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
        dynamicList = findViewById(R.id.dynamic_list);

        initBanner();


        initLeadList();
        initAdList();
        initNewList();
        initDynamicList();

        initData();
    }

    private void initBanner() {
        mPresenter.getHomeAd(data -> {

            banner.setBannerPageClickListener((view, position) -> {

                if (data != null && position > 0 && position <= data.size()) {
                    //点击广告
                    String url = data.get(position).getAdUrl();
                    //判断是不是网站URL
                    if (CommonTool.Compile(url, CommonTool.PATTERN_WEB_URL)) {
                        Intent intent = new Intent(getActivity(), WebActivity.class);
                        intent.putExtra(WebActivity.INTENT_DATA_KEY_URL, url);
                        intent.putExtra(WebActivity.INTENT_DATA_KEY_BACKNAME, "首页");
                        startActivity(intent);
                    } else {
                        try {
                            Uri uri = Uri.parse(url);
                            SaActionSheetDialog dialog = new SaActionSheetDialog(getActivity()).builder();

                            if (uri.getScheme().equalsIgnoreCase("cpigeon")
                                    && uri.getHost().equalsIgnoreCase("ad")
                                    ) {

                                final String phone = uri.getQueryParameter("tel");
                                if (uri.getQueryParameter("call") != null && uri.getQueryParameter("call").equals("1")) {
                                    dialog.addSheetItem("拨打电话", new SaActionSheetDialog.OnSheetItemClickListener() {
                                        @Override
                                        public void onClick(int which) {
                                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                                            try {
                                                startActivity(intent);
                                            } catch (Exception ex) {
                                                ex.printStackTrace();
                                                showTips("拨号失败", TipType.ToastShort);
                                            }
                                        }
                                    });

                                }
                                if (uri.getQueryParameter("sms") != null && uri.getQueryParameter("sms").equals("1")) {
                                    dialog.addSheetItem("发送短信", new SaActionSheetDialog.OnSheetItemClickListener() {
                                        @Override
                                        public void onClick(int which) {
                                            //发送短信
                                            Uri uri = Uri.parse("smsto:" + phone);
                                            Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                                            //intent.putExtra("sms_body", "测试发送短信");
                                            try {
                                                startActivity(intent);
                                            } catch (Exception ex) {
                                                ex.printStackTrace();
                                                showTips("打开失败", TipType.ToastShort);
                                            }
                                        }
                                    });

                                }
                                if (uri.getQueryParameter("url") != null && !uri.getQueryParameter("url").equals("")) {
                                    final String u = uri.getQueryParameter("url");
                                    dialog.addSheetItem("打开网页", new SaActionSheetDialog.OnSheetItemClickListener() {
                                        @Override
                                        public void onClick(int which) {
                                            Intent intent = new Intent(getActivity(), WebActivity.class);
                                            intent.putExtra(WebActivity.INTENT_DATA_KEY_URL, u);
                                            intent.putExtra(WebActivity.INTENT_DATA_KEY_BACKNAME, "首页");
                                            startActivity(intent);
                                        }
                                    });


                                }
                            }
//                                                                dialog.builder();
                            dialog.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    banner.start();
                                }
                            });
                            dialog.show();
                            banner.pause();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });

            banner.setPages(data, () -> {
                return new BannerViewHolder();
            });
            banner.start();
        });


    }

    private void initData() {

        mPresenter.getHomeSpeedNews(data -> {
            adAdapter.setNewData(data);
        });

        mPresenter.getHomeNews(data -> {
            newAdapter.setNewData(HomeNewsEntity.get(data, HomeNewsEntity.TYPE_ONE));
            newAdapter.addFooterView(initFootView(TYPE_NEWS));

        });

        mPresenter.getHomeDynamic(data -> {
            dynamicAdapter.setNewData(data);
            dynamicAdapter.addFooterView(initFootView(TYPE_DYNAMIC));
        });
    }

    private void initDynamicList() {
        dynamicList.setLayoutManager(new LinearLayoutManager(getContext()));
        dynamicList.setNestedScrollingEnabled(false);
        dynamicAdapter = new CircleDynamicAdapter();
        dynamicList.setAdapter(dynamicAdapter);
    }

    private void initAdList() {
        ContactsUtil.setRecyclerViewNestedSlide(adList);
        findViewById(R.id.speed_news).setOnClickListener(v -> {
            IntentBuilder.Builder(getActivity(), PigeonNewsActivity.class).startActivity();
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()) {
            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                LinearSmoothScroller smoothScroller =
                        new LinearSmoothScroller(recyclerView.getContext()) {
                            // 返回：滑过1px时经历的时间(ms)。
                            @Override
                            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                                return 150f / displayMetrics.densityDpi;
                            }
                        };

                smoothScroller.setTargetPosition(position);
                startSmoothScroll(smoothScroller);
            }
        };

        adList.setLayoutManager(linearLayoutManager);
        adAdapter = new HomeAdAdapter();
        adAdapter.setOnItemClickListener((adapter, view, position) -> {
            switch (adAdapter.getItem(position).getType()){
                case NewsEntity.TYPE_LIVE:
                    ((MainActivity)getActivity()).setCurrIndex(1);
                    break;
                case NewsEntity.TYPE_DZCB:
                    IntentBuilder.Builder(getActivity(), PigeonNewsActivity.class)
                            .putExtra(IntentBuilder.KEY_DATA, 1)
                            .startActivity();
                    break;

                case NewsEntity.TYPE_NEWS:
                    IntentBuilder.Builder(getActivity(), PigeonNewsActivity.class).startActivity();
            }
        });
        adList.setAdapter(adAdapter);

        adList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LogUtil.print("newState: " + newState);
            }
        });
        rollPolingAdList();
    }

    private void rollPolingAdList() {
        AdListDisposable = RxUtils.rollPoling(3, 4000, aLong -> {

            if (adPosition > adAdapter.getData().size() - 1) {
                adPosition = 0;
            }
            adList.smoothScrollToPosition(adPosition);
            adPosition += 1;

        });
    }

    private void stopRollPolingAdList(){
        if (AdListDisposable != null) {
            AdListDisposable.dispose();
            AdListDisposable = null;
        }
    }


    private void initNewList() {
        newsList.setLayoutManager(new LinearLayoutManager(getContext()));
        newsList.setNestedScrollingEnabled(false);
        newAdapter = new PigeonNewsAdapter();
        newAdapter.setType(PigeonNewsAdapter.TYPE_HOME);
        newsList.setAdapter(newAdapter);
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

                    break;
                case 1://比赛监控
                    IntentBuilder.Builder(getActivity(), GeCheJianKongListActicity.class).startActivity();
                    break;
                case 2://鸽信通
                    IntentBuilder.Builder().startParentActivity(getActivity(), PigeonMessageHomeFragment.class);
                    break;
                case 3://足环查询
                    IntentBuilder.Builder().startParentActivity(getActivity(), FootSearchFragment.class);
                    break;
            }
        });
    }

    private View initFootView(int type) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_home_list_foot_layout, null);
        TextView textView = findViewById(view, R.id.textView);
        if(type == TYPE_NEWS){
            textView.setText("查看更多新闻");
            view.setOnClickListener(v -> {
                IntentBuilder.Builder(getActivity(), PigeonNewsActivity.class).startActivity();
            });
        }else {
            textView.setText("查看更多动态");
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (AdListDisposable == null) {
            rollPolingAdList();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        stopRollPolingAdList();
    }
}

class BannerViewHolder implements MZViewHolder<HomeAd> {
    private SimpleDraweeView mImageView;

    @Override
    public View createView(Context context) {
        // 返回页面布局
        View view = LayoutInflater.from(context).inflate(R.layout.item_banner_layou, null);
        mImageView = view.findViewById(R.id.iamgeView);
        return view;
    }

    @Override
    public void onBind(Context context, int position, HomeAd data) {
        // 数据绑定
        mImageView.setImageURI(data.getAdImageUrl());
    }
}
