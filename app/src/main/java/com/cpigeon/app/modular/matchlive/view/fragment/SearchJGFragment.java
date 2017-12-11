package com.cpigeon.app.modular.matchlive.view.fragment;

import android.os.Bundle;

import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.modular.matchlive.presenter.SearchMatchPre;
import com.cpigeon.app.modular.matchlive.view.adapter.SearchJGAdapter;
import com.cpigeon.app.modular.matchlive.view.adapter.SearchMatchAdapter;

/**
 * Created by Zhu TingYu on 2017/12/11.
 */

public class SearchJGFragment extends BaseSearchResultFragment<SearchMatchPre> {

     SearchJGAdapter adapter;

    @Override
    protected SearchMatchPre initPresenter() {
        return new SearchMatchPre(getActivity());
    }

    @Override
    protected void initView() {
        super.initView();
        tvTitle1.setText("序号");

        adapter = new SearchJGAdapter();
        recyclerView.setAdapter(adapter);

        mPresenter.getJGMessage(data -> {
            adapter.setNewData(data);
        });
    }
}
