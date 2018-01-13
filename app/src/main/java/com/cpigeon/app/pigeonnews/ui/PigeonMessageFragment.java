package com.cpigeon.app.pigeonnews.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.utils.IntentBuilder;

/**
 * Created by Zhu TingYu on 2018/1/6.
 */

public class PigeonMessageFragment extends BaseMVPFragment {

    public static final int TYPE_EARTH_QUAKE = 1;
    public static final int TYPE_SOLAR_STORM = 2;

    int type;

    ImageView icon;
    TextView content;

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
        return R.layout.fragment_pigeon_message_layout;
    }

    @Override
    public void finishCreateView(Bundle state) {

        type = getArguments().getInt(IntentBuilder.KEY_TYPE);

        icon = findViewById(R.id.icon);
        content = findViewById(R.id.content);

        if(type == TYPE_EARTH_QUAKE){
            icon.setImageResource(R.mipmap.ic_earth_quake);
        }else {
            icon.setImageResource(R.mipmap.ic_solar_storm);
        }

    }
}
