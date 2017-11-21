package com.cpigeon.app.message.ui.history;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.message.adapter.MessageHistoryAdapter;
import com.cpigeon.app.utils.Lists;

/**
 * Created by Zhu TingYu on 2017/11/21.
 */

public class MessageHistoryFragment extends BaseMVPFragment{

    RecyclerView recyclerView;
    MessageHistoryAdapter adapter;

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected boolean isCanDettach() {
        return false;
    }

    @Override
    public void finishCreateView(Bundle state) {
        initView();
    }

    private void initView() {

        initHeadView();

        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MessageHistoryAdapter();
        adapter.bindToRecyclerView(recyclerView);
        bindData();
    }

    private void initHeadView() {

    }

    private void bindData() {
        adapter.setNewData(Lists.newArrayList("","","","",""));
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_message_history_layout;
    }
}
