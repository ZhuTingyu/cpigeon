package com.cpigeon.app.message.ui;

import android.os.Bundle;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;

/**
 * Created by Zhu TingYu on 2017/11/20.
 */

public class CommonMessageFragment extends BaseMVPFragment {
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
