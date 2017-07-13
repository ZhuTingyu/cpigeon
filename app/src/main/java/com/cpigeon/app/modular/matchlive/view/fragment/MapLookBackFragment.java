package com.cpigeon.app.modular.matchlive.view.fragment;

import android.os.Bundle;

import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;

/**
 * 回看界面
 * Created by Administrator on 2017/7/12.
 */

public class MapLookBackFragment extends BaseMVPFragment{
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
