package com.cpigeon.app.modular.matchlive.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;

/**
 * Created by Zhu TingYu on 2017/12/11.
 */

public abstract class BaseSearchResultFragment<P extends BasePresenter> extends BaseMVPFragment<P> {

    RecyclerView recyclerView;

    TextView tvTitle1;
    TextView tvTitle2;
    TextView tvTitle3;




    @Override
    protected boolean isCanDettach() {
        return false;
    }

    @Override
    public void finishCreateView(Bundle state) {
        initView();
    }

    protected void initView() {

        tvTitle1 = findViewById(R.id.tv_title1);
        tvTitle1 = findViewById(R.id.tv_title2);
        tvTitle1 = findViewById(R.id.tv_title3);

        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_search_result_layout;
    }
}
