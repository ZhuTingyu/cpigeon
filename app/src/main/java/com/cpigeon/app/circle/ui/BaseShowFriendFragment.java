package com.cpigeon.app.circle.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cpigeon.app.R;
import com.cpigeon.app.circle.adpter.ShowFriendAdapter;
import com.cpigeon.app.circle.presenter.FriendPre;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.utils.Lists;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/1/18.
 */

public class BaseShowFriendFragment extends BaseMVPFragment<FriendPre> {

    public static final String TYPE_FANS = "fs";
    public static final String TYPE_FOLLOW = "gz";

    RecyclerView recyclerView;
    ShowFriendAdapter adapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_recyclerview_not_white_layout;
    }

    @Override
    protected FriendPre initPresenter() {
        return new FriendPre(this);
    }

    @Override
    protected boolean isCanDettach() {
        return false;
    }

    @Override
    public void finishCreateView(Bundle state) {
        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ShowFriendAdapter();
        recyclerView.setAdapter(adapter);
        bindData();

    }

    private void bindData() {
        showLoading();
        mPresenter.getFriends(data -> {
            hideLoading();
            adapter.setNewData(data);
        });
    }
}
