package com.cpigeon.app.modular.matchlive.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.view.activity.BasePageTurnActivity;
import com.cpigeon.app.modular.matchlive.model.bean.Bulletin;
import com.cpigeon.app.modular.matchlive.model.bean.MatchInfo;
import com.cpigeon.app.modular.matchlive.presenter.RacePre;
import com.cpigeon.app.modular.matchlive.view.adapter.RaceReportAdapter;
import com.cpigeon.app.modular.matchlive.view.adapter.RaceXunFangAdapter;
import com.cpigeon.app.modular.matchlive.view.fragment.RaceDetailsXunFangFragment;
import com.cpigeon.app.modular.matchlive.view.fragment.viewdao.IReportData;
import com.cpigeon.app.utils.NetUtils;
import com.cpigeon.app.utils.customview.MarqueeTextView;
import com.cpigeon.app.utils.customview.SaActionSheetDialog;
import com.cpigeon.app.utils.customview.SearchEditText;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 家飞测试和训放
 * Created by Administrator on 2017/4/23.
 */

public class RaceXunFangActivity extends BasePageTurnActivity<RacePre, RaceXunFangAdapter, MultiItemEntity> implements IReportData {

    @BindView(R.id.race_details_marqueetv)
    MarqueeTextView raceDetailsMarqueetv;
    @BindView(R.id.list_header_race_detial_gg)
    MarqueeTextView listHeaderRaceDetialGg;
    @BindView(R.id.layout_gg)
    LinearLayout layoutGg;
    @BindView(R.id.searchEditText)
    SearchEditText searchEditText;
    @BindView(R.id.list_header_race_detial_table_header_1)
    TextView listHeaderRaceDetialTableHeader1;
    @BindView(R.id.list_header_race_detial_table_header_2)
    TextView listHeaderRaceDetialTableHeader2;
    @BindView(R.id.list_header_race_detial_table_header_3)
    TextView listHeaderRaceDetialTableHeader3;
    @BindView(R.id.layout_list_table_header)
    LinearLayout layoutListTableHeader;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private MatchInfo matchInfo;//赛事信息
    private Bundle bundle;
    private Intent intent;
    private String sKey = "";//当前搜索关键字
    private int lastExpandItemPosition = -1;//最后一个索引

    public Bulletin getBulletin() {
        return bulletin;
    }

    private Bulletin bulletin;
    private String loadType;

    public MatchInfo getMatchInfo() {
        return matchInfo;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_race_xunfang_details;
    }

    @Override
    public RacePre initPresenter() {
        return new RacePre(this);
    }


    @Override
    public void initView() {
        initData();
        initToolbar();
        initInfo();
        super.initView();
    }

    private void initInfo() {
        if (bulletin != null && TextUtils.isEmpty(bulletin.getContent()))
            listHeaderRaceDetialGg.setText(bulletin.getContent());
        listHeaderRaceDetialTableHeader1.setText("名次");
        searchEditText.setOnSearchClickListener(new SearchEditText.OnSearchClickListener() {
            @Override
            public void onSearchClick(View view, String keyword) {
                search(keyword);
                searchEditText.setText(keyword);
            }
        });
    }

    private void initToolbar() {
        raceDetailsMarqueetv.setText(matchInfo.getMc());
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initData() {
        intent = this.getIntent();
        bundle = intent.getExtras();
        matchInfo = (MatchInfo) bundle.getSerializable("matchinfo");
        loadType = bundle.getString("loadType");
    }


    public void search(String keyword) {
        this.sKey = keyword;
        onRefresh();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        lastExpandItemPosition = -1;//最后一个索引
        if (searchEditText != null) {
            searchEditText.setText(this.sKey);
            searchEditText.setSelection(this.sKey.length());
        }
    }

    @NonNull
    @Override
    public String getTitleName() {
        return matchInfo.getMc();
    }

    @Override
    public int getDefaultPageSize() {
        return 100;
    }

    @Override
    protected String getEmptyDataTips() {
        return "没有报到数据";
    }

    @Override
    public RaceXunFangAdapter getNewAdapterWithNoData() {
        RaceXunFangAdapter adapter = new RaceXunFangAdapter(getMatchType());

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                Object item = ((RaceXunFangAdapter) adapter).getData().get(position);
                Logger.d(item.getClass().getName());
                if (item instanceof RaceXunFangAdapter.MatchTitleGPItem) {
//                    if (!"bs".equals(((RaceReportAdapter.MatchTitleXHItem) item).getMatchReportXH().getDt()))
//                        return;
                    if (((RaceXunFangAdapter.MatchTitleGPItem) item).isExpanded()) {
                        if (lastExpandItemPosition == position) {
                            lastExpandItemPosition = -1;
                        }
                        adapter.collapse(position);
//                        Logger.e("当前被关闭的项的postion" + position);
                    } else {
                        if (lastExpandItemPosition >= 0) {
                            adapter.collapse(lastExpandItemPosition);
//                            Logger.e("上一个关闭的项的postion" + lastExpandItemPosition);
                            if (lastExpandItemPosition > position) {//展开上面的项
                                adapter.expand(position);
                                lastExpandItemPosition = position;
                            } else if (lastExpandItemPosition < position) {//展开下面的项
                                adapter.expand(position - 1);
                                lastExpandItemPosition = position - 1;
                            }

                        } else {
                            lastExpandItemPosition = position;
                            adapter.expand(lastExpandItemPosition);
//                            Logger.e("当前被展开的项的lastExpandItemPosition" + lastExpandItemPosition);
                        }

                    }
                }
            }
        });
        adapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                String key = sKey;
                boolean show = false;
                if (!TextUtils.isEmpty(key)) {
                    new SaActionSheetDialog(RaceXunFangActivity.this)
                            .builder()
                            .addSheetItem(getString(R.string.search_prompt_clear_key), new SaActionSheetDialog.OnSheetItemClickListener() {
                                @Override
                                public void onClick(int which) {
                                    search("");
                                }
                            })
                            .setCancelable(true)
                            .show();
                } else {
                    if ("gp".equals(getMatchType())) {
                        Object item = ((RaceXunFangAdapter) baseQuickAdapter).getData().get(i);
                        if (item instanceof RaceXunFangAdapter.MatchTitleGPItem) {
                            key = ((RaceXunFangAdapter.MatchTitleGPItem) item).getMatchReportGP().getName();
                            show = true;
                        }
                    }
                    final String finalKey = key;
                    if (show)
                        new SaActionSheetDialog(RaceXunFangActivity.this)
                                .builder()
                                .addSheetItem(String.format(getString(R.string.search_prompt_has_key), key), new SaActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        search(finalKey);
                                    }
                                })
                                .setCancelable(true)
                                .show();
                }
                return true;
            }
        });

        return adapter;
    }

    @Override
    protected void loadDataByPresenter() {
        mPresenter.loadRaceData(1);
    }

    @Override
    public String getMatchType() {
        return matchInfo.getLx();
    }

    @Override
    public String getSsid() {
        return matchInfo.getSsid();
    }

    @Override
    public String getFoot() {
        return "";
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public boolean hascz() {
        return matchInfo.isMatch();
    }

    @Override
    public int czIndex() {
        return -1;
    }

    @Override
    public String sKey() {
        return sKey;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_race_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: //android.R.id.home是Android内置home按钮的id
                finish();
                break;
            case R.id.action_save:

                break;
            case R.id.action_details:
                showDialogFragment();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showDialogFragment() {
        FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("xunfangdialogFragment");
        if (fragment != null) {
            mFragmentTransaction.remove(fragment);
        }
        RaceDetailsXunFangFragment detailsFragment = RaceDetailsXunFangFragment.newInstance("训放数据");
        detailsFragment.show(mFragmentTransaction, "xunfangdialogFragment");
    }

}
