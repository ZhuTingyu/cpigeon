package com.cpigeon.app.message.ui.history;

import android.os.Bundle;
import android.widget.TextView;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.utils.DateTool;

/**
 * Created by Zhu TingYu on 2017/11/22.
 */

public class MessageDetailsFragment extends BaseMVPFragment {

    TextView date;
    TextView number;
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
        return R.layout.fragment_message_details_layout;
    }

    @Override
    public void finishCreateView(Bundle state) {
        setTitle("短信详情");

        date = findViewById(R.id.date);
        number = findViewById(R.id.number);
        content = findViewById(R.id.content);

        bindDate();
    }

    private void bindDate() {
        date.setText(DateTool.format(System.currentTimeMillis(), DateTool.FORMAT_DATETIME));
        number.setText(getString(R.string.string_text_message_addressee_number,String.valueOf(123)));
        content.setText("123123123123121231231313");
    }
}
