package com.cpigeon.app.modular.usercenter.view.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.TextView;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.view.fragment.BaseFragment;
import com.cpigeon.app.utils.customview.CustomEmptyView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/2/16.
 */

public class MessageFragment extends BaseFragment {
    @BindView(R.id.recyclerview)
    protected RecyclerView mRecyclerView;
    @BindView(R.id.empty_layout)
    CustomEmptyView mCustomEmptyView;
    @Override
    public void finishCreateView(Bundle state) {
        showTips("暂无通知", TipType.View);
    }
    @Override
    protected int getLayoutResource() {
        return R.layout.layout_recyclerview;
    }

    @Override
    public boolean showTips(String tip, TipType tipType) {
        if (tipType == TipType.View) {
            mCustomEmptyView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
            mCustomEmptyView.setEmptyImage(R.drawable.ic_empty);
            mCustomEmptyView.setEmptyText(tip);
            return true;
        }
        return super.showTips(tip, tipType);
    }
}
