package com.cpigeon.app.modular.matchlive.view.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.view.activity.BasePageTurnActivity;
import com.cpigeon.app.modular.matchlive.model.bean.MatchInfo;
import com.cpigeon.app.modular.matchlive.presenter.ChaZuBaoDaoDetailsPre;
import com.cpigeon.app.modular.matchlive.view.activity.viewdao.IRacePigeonsView;
import com.cpigeon.app.modular.matchlive.view.adapter.ChaZuBaoDaoDetailsAdapter;
import com.cpigeon.app.utils.ViewExpandAnimation;
import com.cpigeon.app.utils.customview.MarqueeTextView;
import com.cpigeon.app.utils.customview.SaActionSheetDialog;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 插组报到----详情信息
 * Created by Administrator on 2017/4/10.
 */

public class RaceChaZuBaoDaoActivity extends BasePageTurnActivity<ChaZuBaoDaoDetailsPre, ChaZuBaoDaoDetailsAdapter, MultiItemEntity> implements IRacePigeonsView {

    @BindView(R.id.race_details_marqueetv)
    MarqueeTextView raceDetailsMarqueetv;
    @BindView(R.id.race_detial_info_detial_show)
    ImageView raceDetialInfoDetialShow;
    @BindView(R.id.race_detial_info_textview_racename)
    MarqueeTextView raceDetialInfoTextviewRacename;
    @BindView(R.id.race_detial_info_match_name_layout)
    RelativeLayout raceDetialInfoMatchNameLayout;
    @BindView(R.id.race_detial_match_info_title_area)
    TextView raceDetialMatchInfoTitleArea;
    @BindView(R.id.race_detial_match_info_content_area)
    TextView raceDetialMatchInfoContentArea;
    @BindView(R.id.layout_area)
    LinearLayout layoutArea;
    @BindView(R.id.race_detial_match_info_title_kj)
    TextView raceDetialMatchInfoTitleKj;
    @BindView(R.id.race_detial_match_info_content_kj)
    TextView raceDetialMatchInfoContentKj;
    @BindView(R.id.layout_kj)
    LinearLayout layoutKj;
    @BindView(R.id.race_detial_match_info_title_slys)
    TextView raceDetialMatchInfoTitleSlys;
    @BindView(R.id.race_detial_match_info_content_slys)
    TextView raceDetialMatchInfoContentSlys;
    @BindView(R.id.layout_slys)
    LinearLayout layoutSlys;
    @BindView(R.id.race_detial_match_info_title_tq)
    TextView raceDetialMatchInfoTitleTq;
    @BindView(R.id.race_detial_match_info_content_tq)
    TextView raceDetialMatchInfoContentTq;
    @BindView(R.id.layout_tq)
    LinearLayout layoutTq;
    @BindView(R.id.race_detial_match_info_title_st)
    TextView raceDetialMatchInfoTitleSt;
    @BindView(R.id.race_detial_match_info_content_st)
    TextView raceDetialMatchInfoContentSt;
    @BindView(R.id.race_detial_match_info_title_jwd)
    TextView raceDetialMatchInfoTitleJwd;
    @BindView(R.id.race_detial_match_info_content_jwd)
    TextView raceDetialMatchInfoContentJwd;
    @BindView(R.id.layout_report_info_detial)
    LinearLayout layoutReportInfoDetial;
    @BindView(R.id.list_header_race_detial_gg)
    MarqueeTextView listHeaderRaceDetialGg;
    @BindView(R.id.layout_gg)
    LinearLayout layoutGg;
    @BindView(R.id.layout_list_table_header)
    LinearLayout layoutListTableHeader;
    private Bundle bundle;
    private Intent intent;
    private ChaZuBaoDaoDetailsAdapter mAdapter;
    private MatchInfo matchInfo;
    private String Annotation;
    private SaActionSheetDialog mSelectGroupMenuDialog;
    private Dialog loadingDialog;
    private Map<String, Object> currGroupData = null;
    private int czIndex = 0;//当前插组索引(1-24)
    private String loadType;//该页面需要加载的数据类型
    private int lastExpandItemPosition = -1;//最后一个索引

    @Override
    public int getLayoutId() {
        return R.layout.activity_racepigeons;
    }

    @Override
    public ChaZuBaoDaoDetailsPre initPresenter() {
        return new ChaZuBaoDaoDetailsPre(this);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initData();

        super.initView(savedInstanceState);

        initDetails();

    }

    /**
     * 初始化上一个页面传过来的数据
     */
    private void initData() {
        intent = getIntent();
        bundle = intent.getExtras();
        loadType = bundle.getString("loadType");
        matchInfo = (MatchInfo) bundle.getSerializable("matchinfo");
        Annotation = bundle.getString("bulletin");
        czIndex = bundle.getInt("czindex") + 1;

    }

    private void initDetails() {
        toolbar.setTitle("");
        raceDetailsMarqueetv.setText(getTitleName());
        layoutReportInfoDetial.setVisibility(View.GONE);
        if (Annotation == null || TextUtils.isEmpty(Annotation)) {
            layoutGg.setVisibility(View.GONE);
        } else {
            layoutGg.setVisibility(View.VISIBLE);
            listHeaderRaceDetialGg.setText(Annotation);
        }

        currGroupData = getData_CZTJ().get(getCzIndex() - 1);
        raceDetialInfoTextviewRacename.setText(String.format("%s(%s组%s)", matchInfo.computerBSMC(), (char) (getCzIndex() - 1 + 'A'), "报到" + currGroupData.get("gcys") + "羽"));
        raceDetialMatchInfoContentArea.setText(matchInfo.getArea());
        raceDetialMatchInfoContentJwd.setText(matchInfo.computerSFZB());
        raceDetialMatchInfoContentSt.setText(matchInfo.getSt());
        raceDetialMatchInfoContentSlys.setText(matchInfo.compuberSLYS());
        raceDetialMatchInfoContentTq.setText(matchInfo.getTq());
        raceDetialMatchInfoContentKj.setText(matchInfo.getBskj() + "KM");
        toolbar.setTitle(matchInfo.getMc());
    }

    /**
     * 获取上一个页面传过来的MatchInfo
     *
     * @return
     */
    @Override
    public MatchInfo getMatchInfo() {
        return matchInfo;
    }

    @Override
    public String getSsid() {

        return matchInfo.getSsid();
    }

    @Override
    public String getMatchType() {
        return loadType;
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
    public boolean isHascz() {
        return getMatchInfo().isMatch();
    }

    @NonNull
    @Override
    public String getTitleName() {
        String data = "";
        if(matchInfo != null){
            data =  matchInfo.getMc();
        }
        return data;
    }

    @Override
    public int getDefaultPageSize() {
        return 100;
    }

    @Override
    protected String getEmptyDataTips() {
        return "暂时没有报到数据，请稍后再试";
    }

    @Override
    public ChaZuBaoDaoDetailsAdapter getNewAdapterWithNoData() {
        mAdapter = new ChaZuBaoDaoDetailsAdapter(getMatchType());
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Object item = ((ChaZuBaoDaoDetailsAdapter) baseQuickAdapter).getData().get(i);
                Logger.d(item.getClass().getName());
                if ("xh".equals(getMatchType())) {
                    if (item instanceof ChaZuBaoDaoDetailsAdapter.MatchTitleXHItem) {
                        if (((ChaZuBaoDaoDetailsAdapter.MatchTitleXHItem) item).isExpanded()) {
                            if (lastExpandItemPosition == i) {
                                lastExpandItemPosition = -1;
                            }
                            baseQuickAdapter.collapse(i);
                        } else {
                            if (lastExpandItemPosition >= 0) {
                                baseQuickAdapter.collapse(lastExpandItemPosition);
                                if (lastExpandItemPosition > i) {//展开上面的项
                                    baseQuickAdapter.expand(i);
                                    lastExpandItemPosition = i;
                                } else if (lastExpandItemPosition < i) {//展开下面的项
                                    baseQuickAdapter.expand(i - 1);
                                    lastExpandItemPosition = i - 1;
                                }

                            } else {
                                lastExpandItemPosition = i;
                                baseQuickAdapter.expand(lastExpandItemPosition);
                            }

                        }
                    }


                } else if ("gp".equals(getMatchType())) {
                    if (item instanceof ChaZuBaoDaoDetailsAdapter.MatchTitleGPItem) {

                        if (((ChaZuBaoDaoDetailsAdapter.MatchTitleGPItem) item).isExpanded()) {
                            if (lastExpandItemPosition == i) {
                                lastExpandItemPosition = -1;
                            }
                            baseQuickAdapter.collapse(i);
                        } else {
                            if (lastExpandItemPosition >= 0) {
                                baseQuickAdapter.collapse(lastExpandItemPosition);
                                if (lastExpandItemPosition > i) {//展开上面的项
                                    baseQuickAdapter.expand(i);
                                    lastExpandItemPosition = i;
                                } else if (lastExpandItemPosition < i) {//展开下面的项
                                    baseQuickAdapter.expand(i - 1);
                                    lastExpandItemPosition = i - 1;
                                }

                            } else {
                                lastExpandItemPosition = i;
                                baseQuickAdapter.expand(lastExpandItemPosition);
                            }

                        }
                    }
                }
            }
        });
        return mAdapter;
    }

    @Override
    protected void loadDataByPresenter() {
        mPresenter.loadChaZuBaoDaoDetails();
    }

    @Override
    public int getCzIndex() {
        return czIndex;//组别
    }

    @Override
    public String getSkey() {
        return "";
    }

    @Override
    public void showMenuGroup() {
        if (mSelectGroupMenuDialog == null) {
            mSelectGroupMenuDialog = new SaActionSheetDialog(this).builder();
            mSelectGroupMenuDialog.setTitle("切换插组");
            for (int group = 0; group < getData_CZTJ().size(); group++) {
                final Map<String, Object> map = getData_CZTJ().get(group);

                mSelectGroupMenuDialog.addSheetItem(String.format("%s组%s(%d羽)", map.get("group"),
                        "报到",
                        map.get("gcys")), new SaActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        if (czIndex == which) return;
                        czIndex = which;
                        currGroupData = getData_CZTJ().get(czIndex - 1);

                        raceDetialInfoTextviewRacename.setText(String.format("%s(%s组%s(%d羽))", matchInfo.computerBSMC(),
                                map.get("group"),
                                "报到",
                                map.get("gcys")));
                        onRefresh();
                    }
                });
            }
        }
        mSelectGroupMenuDialog.show();
    }

    /**
     * 获取上一个界面传过来的插组统计
     *
     * @return
     */
    @Override
    public List<HashMap<String, Object>> getData_CZTJ() {
        return (List<HashMap<String, Object>>) bundle.getSerializable("czmap");
    }

    @OnClick({R.id.race_detial_info_match_name_layout, R.id.list_header_race_detial_gg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.race_detial_info_match_name_layout:
                if (layoutReportInfoDetial.getVisibility() == View.VISIBLE) {
                    layoutReportInfoDetial.setVisibility(View.GONE);
                    raceDetialInfoDetialShow.setRotation(0);
                } else {
                    raceDetialInfoDetialShow.setRotation(180);
                    layoutReportInfoDetial.setVisibility(View.VISIBLE);
                }
                ViewExpandAnimation expandAnimation = new ViewExpandAnimation(layoutReportInfoDetial);
                layoutReportInfoDetial.startAnimation(expandAnimation);
                break;
            case R.id.list_header_race_detial_gg:
                if (TextUtils.isEmpty(Annotation)) {
                    return;
                }
                SweetAlertDialog dialog = new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE);
                dialog.setTitleText("公告");
                dialog.setContentText(Annotation);
                dialog.setCancelable(true);
                dialog.show();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_czbd_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_change_group:
                showMenuGroup();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        lastExpandItemPosition = -1;
    }
}
