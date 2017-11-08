package com.cpigeon.app.commonstandard.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

/**
 * Created by chenshuai on 2017/4/15.
 */

public abstract class BaseMVPFragment<Pre extends BasePresenter> extends BaseFragment {

    protected Pre mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mPresenter = this.initPresenter();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected abstract Pre initPresenter();

    /**
     * 是否可以释放Presenter与View 的绑定
     *
     * @return
     */
    protected abstract boolean isCanDettach();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (isCanDettach() && mPresenter != null && mPresenter.isAttached())
            mPresenter.detach();
    }

    protected void addItemDecorationLine(RecyclerView recyclerView){
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(recyclerView.getContext())
                .colorResId(R.color.line_color).size(1)
                .showLastDivider().build());
    }
}
