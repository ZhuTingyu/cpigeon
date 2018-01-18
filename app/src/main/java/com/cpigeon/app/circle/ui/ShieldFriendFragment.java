package com.cpigeon.app.circle.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cpigeon.app.R;
import com.cpigeon.app.circle.adpter.ShieldFriendAdapter;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.utils.IntentBuilder;
import com.cpigeon.app.utils.Lists;

/**
 * Created by Zhu TingYu on 2018/1/18.
 */

public class ShieldFriendFragment extends BaseMVPFragment{

    public static final String TYPE_SHIELD = "TYPE_SHIELD";
    public static final String TYPE_BLACKLIST = "TYPE_BLACKLIST";

    String type;

    ShieldFriendAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_recyclerview_not_white_layout;
    }

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
        type = getArguments().getString(IntentBuilder.KEY_TYPE);

        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ShieldFriendAdapter(type);
        recyclerView.setAdapter(adapter);
        adapter.setNewData(Lists.newArrayList("", "", ""));
    }
}
