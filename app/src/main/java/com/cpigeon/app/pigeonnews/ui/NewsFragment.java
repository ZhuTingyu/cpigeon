package com.cpigeon.app.pigeonnews.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.home.adpter.PigeonNewsAdapter;
import com.cpigeon.app.utils.IntentBuilder;
import com.cpigeon.app.utils.Lists;

/**
 * Created by Zhu TingYu on 2018/1/6.
 */

public class NewsFragment extends BaseMVPFragment {

    RecyclerView recyclerView;
    PigeonNewsAdapter adapter;

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
        return R.layout.fragment_recyclerview_not_white_layout;
    }

    @Override
    public void finishCreateView(Bundle state) {
        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PigeonNewsAdapter();
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            IntentBuilder.Builder(getActivity(), NewsDetailsActivity.class).startActivity();
        });
        recyclerView.setAdapter(adapter);
        //adapter.setNewData(Lists.newArrayList("","",""));
    }
}
