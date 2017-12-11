package com.cpigeon.app.modular.matchlive.view.fragment;

import com.cpigeon.app.modular.matchlive.presenter.SearchMatchPre;
import com.cpigeon.app.modular.matchlive.view.adapter.SearchMatchAdapter;

/**
 * Created by Zhu TingYu on 2017/12/11.
 */

public class SearchReportFragment extends BaseSearchResultFragment<SearchMatchPre>{

    SearchMatchAdapter adapter;

    @Override
    protected SearchMatchPre initPresenter() {
        return new SearchMatchPre(getActivity());
    }

    @Override
    protected void initView() {
        super.initView();
        tvTitle1.setText("暂排名次");

        adapter = new SearchMatchAdapter();
        recyclerView.setAdapter(adapter);

        mPresenter.getReport(data -> {
            adapter.setNewData(data);
        });
    }
}