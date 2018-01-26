package com.cpigeon.app.circle.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cpigeon.app.R;
import com.cpigeon.app.circle.adpter.CircleMessageAdapter;
import com.cpigeon.app.circle.presenter.CircleMessagePre;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.utils.ScreenTool;
import com.cpigeon.app.utils.ToastUtil;
import com.cpigeon.app.view.ShareDialogFragment;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
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
    ShareDialogFragment shareDialogFragment;

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

        shareDialogFragment = new ShareDialogFragment();

        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CircleMessageAdapter(getActivity());
        adapter.setShare(shareDialogFragment);
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
