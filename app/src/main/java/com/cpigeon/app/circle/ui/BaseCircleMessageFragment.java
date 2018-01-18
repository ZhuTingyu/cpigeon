package com.cpigeon.app.circle.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cpigeon.app.R;
import com.cpigeon.app.circle.adpter.CircleMessageAdapter;
import com.cpigeon.app.circle.presenter.CircleMessageListPre;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.entity.ThumbEntity;
import com.cpigeon.app.utils.IntentBuilder;
import com.cpigeon.app.utils.Lists;
import com.cpigeon.app.utils.ScreenTool;
import com.wx.goodview.GoodView;

/**
 * Created by Zhu TingYu on 2018/1/15.
 */

public class BaseCircleMessageFragment extends BaseMVPFragment<CircleMessageListPre> {

    public static final String TYPE_ALL = "gclb";
    public static final String TYPE_FOLLOW = "gzlb";
    public static final String TYPE_MY = "wdfb";

    RecyclerView recyclerView;
    CircleMessageAdapter adapter;
    public GoodView goodView;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_recyclerview_not_white_layout;
    }

    @Override
    protected CircleMessageListPre initPresenter() {
        return new CircleMessageListPre(this);
    }

    @Override
    protected boolean isCanDettach() {
        return false;
    }

    @Override
    public void finishCreateView(Bundle state) {
        goodView = new GoodView(getContext());
        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CircleMessageAdapter(getActivity(),goodView);
        adapter.bindToRecyclerView(recyclerView);
        addItemDecorationLine(recyclerView, R.color.color_default_bg, ScreenTool.dip2px(getContext().getResources().getDimension(R.dimen.large_vertical_margin)));

        //adapter.setNewData(Lists.newArrayList(new ThumbEntity(),new ThumbEntity(), new ThumbEntity()));

        recyclerView.requestFocus();

        mPresenter.getMessageList(s -> {
            adapter.setNewData(s);
        });
    }
}
