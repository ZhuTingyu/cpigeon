package com.cpigeon.app.pigeonnews.ui;

import android.os.Bundle;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;

/**
 * Created by Zhu TingYu on 2018/1/6.
 */

public class NewsFragment extends BaseMVPFragment {
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

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_recyclerview_layout;
    }
}
