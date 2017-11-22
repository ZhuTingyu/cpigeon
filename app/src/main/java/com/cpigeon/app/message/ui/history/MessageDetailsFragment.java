package com.cpigeon.app.message.ui.history;

import android.os.Bundle;

import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;

/**
 * Created by Zhu TingYu on 2017/11/22.
 */

public class MessageDetailsFragment extends BaseMVPFragment {
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
