package com.cpigeon.app.pigeonnews.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.pigeonnews.presenter.PigeonMessagePre;
import com.cpigeon.app.utils.IntentBuilder;
import com.cpigeon.app.utils.ToastUtil;

/**
 * Created by Zhu TingYu on 2018/1/6.
 */

public class PigeonMessageFragment extends BaseMVPFragment<PigeonMessagePre> {

    public static final int TYPE_EARTH_QUAKE = 1;
    public static final int TYPE_SOLAR_STORM = 2;


    ImageView icon;
    TextView content;

    @Override
    protected PigeonMessagePre initPresenter() {
        return new PigeonMessagePre(getActivity());
    }

    @Override
    protected boolean isCanDettach() {
        return false;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_pigeon_message_layout;
    }

    @Override
    public void finishCreateView(Bundle state) {

        mPresenter.type = getArguments().getInt(IntentBuilder.KEY_TYPE);

        icon = findViewById(R.id.icon);
        content = findViewById(R.id.content);

        if(mPresenter.type == TYPE_EARTH_QUAKE){
            icon.setImageResource(R.mipmap.ic_earth_quake);
        }else {
            icon.setImageResource(R.mipmap.ic_solar_storm);
        }

        bindData();

    }

    private void bindData() {
        mPresenter.getMessage(data -> {
            content.setText(data.content);
        });
    }
}
