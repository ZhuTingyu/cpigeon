package com.cpigeon.app.circle.ui;

import android.os.Bundle;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;

/**
 * Created by Zhu TingYu on 2018/1/16.
 */

public class PushCircleMessageFragement extends BaseMVPFragment {



    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_push_circle_message_layout;
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
        findViewById(R.id.rl_user_location).setOnClickListener(v -> {

        });

        findViewById(R.id.rl_user_msg_visibility).setOnClickListener(v -> {

        });
    }
}
