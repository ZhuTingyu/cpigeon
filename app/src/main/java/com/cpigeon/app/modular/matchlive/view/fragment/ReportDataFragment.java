package com.cpigeon.app.modular.matchlive.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.view.fragment.BasePageTurnFragment;
import com.cpigeon.app.modular.matchlive.model.bean.GeCheJianKongRace;
import com.cpigeon.app.modular.matchlive.model.bean.MatchInfo;
import com.cpigeon.app.modular.matchlive.model.bean.MatchReportGP;
import com.cpigeon.app.modular.matchlive.model.bean.MatchReportXH;
import com.cpigeon.app.modular.matchlive.presenter.RacePre;
import com.cpigeon.app.modular.matchlive.view.activity.RaceReportActivity;
import com.cpigeon.app.modular.matchlive.view.adapter.RaceReportAdapter;
import com.cpigeon.app.modular.matchlive.view.fragment.viewdao.IReportData;
import com.cpigeon.app.utils.customview.SaActionSheetDialog;
import com.cpigeon.app.utils.customview.SearchEditText;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/4/15.
 */

public class ReportDataFragment extends BasePageTurnFragment<RacePre, RaceReportAdapter, MultiItemEntity> implements IReportData {

    @BindView(R.id.list_header_race_detial_table_header_1)
    TextView listHeaderRaceDetialTableHeader1;
    @BindView(R.id.list_header_race_detial_table_header_2)
    TextView listHeaderRaceDetialTableHeader2;
    @BindView(R.id.list_header_race_detial_table_header_3)
    TextView listHeaderRaceDetialTableHeader3;
    @BindView(R.id.layout_list_table_header)
    LinearLayout layoutListTableHeader;
    @BindView(R.id.searchEditText)
    SearchEditText searchEditText;
    private MatchInfo matchInfo;
    private String sKey = "";//当前搜索关键字

    private int lastExpandItemPosition = -1;//最后一个索引

    @Override
    public void onRefresh() {
        super.onRefresh();
        lastExpandItemPosition = -1;
        if (searchEditText != null) {
            searchEditText.setText(this.sKey);
            searchEditText.setSelection(this.sKey.length());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initMatchinfo();
    }

    private void initMatchinfo() {
        if (matchInfo == null)
            this.matchInfo = ((RaceReportActivity) getActivity()).getMatchInfo();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_race_data;
    }

    @Override
    protected RacePre initPresenter() {
        return new RacePre(this);
    }

    @Override
    protected boolean isCanDettach() {
        return true;
    }


    private void initSearch() {
        searchEditText.setOnSearchClickListener(new SearchEditText.OnSearchClickListener() {
            @Override
            public void onSearchClick(View view, String keyword) {
                search(keyword);
                searchEditText.setText(keyword);
            }
        });
    }

    @Override
    public String getMatchType() {
        initMatchinfo();
        return matchInfo.getLx();
    }

    @Override
    public String getSsid() {
        initMatchinfo();
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
        return true;
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
    public MatchInfo getMatchInfo() {
        initMatchinfo();
        return matchInfo;
    }

    @Override
    public void showDefaultGCJKInfo(GeCheJianKongRace geCheJianKongRace) {

    }

    @Override
    public void refreshBoomMnue() {

    }

    @Override
    protected int getDefaultPageSize() {
        return 100;
    }

    @Override
    protected String getEmptyDataTips() {
        return "暂时没有报到数据";
    }

    @Override
    public RaceReportAdapter getNewAdapterWithNoData() {
        RaceReportAdapter adapter = new RaceReportAdapter(getMatchType());

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                Object item = ((RaceReportAdapter) adapter).getData().get(position);
                Logger.d(item.getClass().getName());
                if ("xh".equals(getMatchType())) {
                    if (item instanceof RaceReportAdapter.MatchTitleXHItem) {
                        if (((RaceReportAdapter.MatchTitleXHItem) item).isExpanded()) {
                            if (lastExpandItemPosition == position) {
                                lastExpandItemPosition = -1;
                            }
                            adapter.collapse(position);
                        } else {
                            if (lastExpandItemPosition >= 0) {
                                adapter.collapse(lastExpandItemPosition);
                                Logger.e("上一个关闭的项的postion" + lastExpandItemPosition);
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
                                Logger.e("当前被展开的项的lastExpandItemPosition" + lastExpandItemPosition);
                            }
                        }
                    }
                } else if ("gp".equals(getMatchType())) {
                    if (item instanceof RaceReportAdapter.MatchTitleGPItem) {
                        if (((RaceReportAdapter.MatchTitleGPItem) item).isExpanded()) {
                            if (lastExpandItemPosition == position) {
                                lastExpandItemPosition = -1;
                            }
                            adapter.collapse(position);
                        } else {
                            if (lastExpandItemPosition >= 0) {
                                adapter.collapse(lastExpandItemPosition);
                                Logger.e("上一个关闭的项的postion" + lastExpandItemPosition);
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
                                Logger.e("当前被展开的项的lastExpandItemPosition" + lastExpandItemPosition);
                            }
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
                    new SaActionSheetDialog(getActivity())
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
                    if ("xh".equals(getMatchType())) {
                        Object item = ((RaceReportAdapter) baseQuickAdapter).getData().get(i);
                        if (item instanceof RaceReportAdapter.MatchTitleXHItem) {
                            key = ((RaceReportAdapter.MatchTitleXHItem) item).getMatchReportXH().getName();
                            show = true;
                        }
                    } else if ("gp".equals(getMatchType())) {
                        Object item = ((RaceReportAdapter) baseQuickAdapter).getData().get(i);
                        if (item instanceof RaceReportAdapter.MatchTitleGPItem) {
                            key = ((RaceReportAdapter.MatchTitleGPItem) item).getMatchReportGP().getName();
                            show = true;
                        }
                    }
                    final String finalKey = key;
                    if (show)
                        new SaActionSheetDialog(getActivity())
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
        ((RaceReportActivity) getActivity()).initBulletin();
        mPresenter.loadRaceData(0);
    }

    public void search(String keyword) {
        this.sKey = keyword;
        onRefresh();
    }

    @Override
    public void finishCreateView(Bundle state) {
        super.finishCreateView(state);
        initSearch();
        if ("gp".equals(getMatchType())) {
            listHeaderRaceDetialTableHeader1.setText("名次");
        }
    }
}
