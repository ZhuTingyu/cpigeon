package com.cpigeon.app.modular.footsearch.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.modular.footsearch.ui.adapter.FootSearchAdapter;
import com.cpigeon.app.utils.IntentBuilder;
import com.cpigeon.app.utils.Lists;

/**
 * Created by Zhu TingYu on 2017/12/21.
 */

public class FootSearchHistoryFragment extends BaseMVPFragment {

    RecyclerView recyclerView;
    FootSearchAdapter adapter;

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
        return R.layout.fragment_foot_search_help_layout;
    }

    @Override
    public void finishCreateView(Bundle state) {

        setTitle("足环查询");

        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        addItemDecorationLine(recyclerView);
        adapter = new FootSearchAdapter();
        recyclerView.setAdapter(adapter);


        adapter.setNewData(Lists.newArrayList("","",""));

        adapter.setOnItemClickListener((adapter1, view, position) -> {
            IntentBuilder.Builder().startParentActivity(getActivity(), FootSearchResultFragment.class);
        });

    }
}
