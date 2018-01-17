package com.cpigeon.app.circle.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;

/**
 * Created by Zhu TingYu on 2018/1/17.
 */

public class ChooseLocationFragment extends BaseMVPFragment {

    RecyclerView recyclerView;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_choose_location_layout;
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

    }
}
