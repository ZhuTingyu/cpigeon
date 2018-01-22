package com.cpigeon.app.circle.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cpigeon.app.R;
import com.cpigeon.app.circle.adpter.CircleMessageAdapter;
import com.cpigeon.app.circle.presenter.CircleMessagePre;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.utils.ScreenTool;
import com.cpigeon.app.utils.ToastUtil;
import com.wx.goodview.GoodView;

/**
 * Created by Zhu TingYu on 2018/1/15.
 */

public class BaseCircleMessageFragment extends BaseMVPFragment<CircleMessagePre> {

    public static final String TYPE_ALL = "gclb";
    public static final String TYPE_FOLLOW = "gzlb";
    public static final String TYPE_MY = "wdfb";

    RecyclerView recyclerView;
    CircleMessageAdapter adapter;
    public GoodView goodView;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_recyclerview_not_white_no_padding_layout;
    }

    @Override
    protected CircleMessagePre initPresenter() {
        return new CircleMessagePre(this);
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
        adapter = new CircleMessageAdapter(getActivity(), goodView);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
        });
        adapter.setmPre(mPresenter);
        adapter.bindToRecyclerView(recyclerView);
        adapter.setOnLoadMoreListener(() -> {
            mPresenter.page++;
            mPresenter.getMessageList(data -> {
                if (data.isEmpty()) {
                    adapter.setLoadMore(true);
                } else {
                    adapter.setLoadMore(false);
                    adapter.addData(data);
                }
            });
        }, recyclerView);
        addItemDecorationLine(recyclerView, R.color.color_default_bg, ScreenTool.dip2px(getContext().getResources().getDimension(R.dimen.large_vertical_margin)));

        recyclerView.requestFocus();

        bindData();

        setRefreshListener(() -> {
            mPresenter.page = 1;
            bindData();
        });
    }

    private void bindData() {
        mPresenter.getMessageList(s -> {
            adapter.setNewData(s);
            hideLoading();
        });
    }
}
