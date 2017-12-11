package com.cpigeon.app.modular.matchlive.view.fragment;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.cpigeon.app.modular.matchlive.presenter.SearchMatchPre;
import com.cpigeon.app.modular.matchlive.view.adapter.RaceReportAdapter;
import com.cpigeon.app.modular.matchlive.view.adapter.SearchMatchAdapter;
import com.cpigeon.app.utils.IntentBuilder;

import java.util.List;

/**
 * Created by Zhu TingYu on 2017/12/11.
 */

public class SearchReportFragment extends BaseSearchResultFragment<SearchMatchPre> {

    RaceReportAdapter adapter;

    int currentPosition = -1;

    @Override
    protected SearchMatchPre initPresenter() {
        return new SearchMatchPre(getActivity());
    }

    @Override
    protected void initView() {
        super.initView();

        String type = getActivity().getIntent().getStringExtra(IntentBuilder.KEY_TYPE);

        tvTitle1.setText("暂排名次");

        adapter = new RaceReportAdapter(type);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((adapter1, view, position) -> {
            Object item = ((RaceReportAdapter) adapter).getData().get(position);
            if(item instanceof RaceReportAdapter.MatchTitleXHItem
                    || item instanceof RaceReportAdapter.MatchTitleGPItem){
                if(currentPosition == -1){ //当前没有展开项
                    adapter.expand(position);
                    currentPosition = position;

                }else {
                    if(currentPosition == position){
                        adapter.collapse(position);
                        currentPosition = -1;
                    }else if(currentPosition > position){
                        adapter.collapse(currentPosition);
                        adapter.expand(position);
                        currentPosition = position;
                    }else {
                        adapter.collapse(currentPosition);
                        int expandPosition = position - 1;
                        adapter.expand(expandPosition);
                        currentPosition = expandPosition;
                    }
                }
            }

        });


        showLoading();

        if (type.equals("xh")) {
            mPresenter.getReportXH(data -> {
                hideLoading();
                adapter.setNewData(RaceReportAdapter.getXH(data));
            });
        }else {
            mPresenter.getReportGP(data -> {
                hideLoading();
                adapter.setNewData(RaceReportAdapter.getGP(data));
            });
        }

    }
}
