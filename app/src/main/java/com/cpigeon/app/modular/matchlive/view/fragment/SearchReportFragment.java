package com.cpigeon.app.modular.matchlive.view.fragment;

import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.modular.matchlive.presenter.SearchReportPre;
import com.cpigeon.app.modular.matchlive.view.adapter.SearchReportAdapter;

/**
 * Created by Zhu TingYu on 2017/12/11.
 */

public class SearchReportFragment extends BaseSearchResultFragment<SearchReportPre>{

    SearchReportAdapter adapter;

    @Override
    protected SearchReportPre initPresenter() {
        return new SearchReportPre(this);
    }

    @Override
    protected void initView() {
        super.initView();

        adapter = new SearchReportAdapter();
        recyclerView.setAdapter(adapter);

        mPresenter.getReport(data -> {
            adapter.setNewData(data);
        });
    }
}
