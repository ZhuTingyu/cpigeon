package com.cpigeon.app.circle.ui;

import android.os.Bundle;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;

/**
 * Created by Zhu TingYu on 2018/1/4.
 */

public class FriendCircleFragment extends BaseMVPFragment{
    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected boolean isCanDettach() {
        return false;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_frends_circle_layout;
    }

    @Override
    public void finishCreateView(Bundle state) {
        setTitle("鸽迷圈");
        setToolbarNotBack();
    }
}
