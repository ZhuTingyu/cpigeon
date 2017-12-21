package com.cpigeon.app.modular.footsearch.ui;

import android.os.Bundle;

import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;

/**
 * Created by Zhu TingYu on 2017/12/21.
 */

public class FootSearchHistroyFragment extends BaseMVPFragment {
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
        return 0;
    }
}
