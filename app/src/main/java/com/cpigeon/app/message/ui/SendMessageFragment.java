package com.cpigeon.app.message.ui;

import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.widget.EditText;
import android.widget.TextView;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.utils.RxUtils;

/**
 * Created by Zhu TingYu on 2017/11/20.
 */

public class SendMessageFragment extends BaseMVPFragment {

    TextView phoneNumbers;
    AppCompatImageView icContactsAdd;
    EditText messageContent;
    AppCompatImageView icRight;
    TextView btnLeft;
    TextView btnRight;
    TextView btnModifySign;


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
        setTitle("发送短信");
        initView();
    }

    private void initView() {
        phoneNumbers = (TextView) findViewById(R.id.phone_numbers);
        icContactsAdd = (AppCompatImageView) findViewById(R.id.ic_contacts_add);
        messageContent = (EditText) findViewById(R.id.message_content);
        icRight = (AppCompatImageView) findViewById(R.id.ic_right);
        btnLeft = (TextView) findViewById(R.id.btn_left);
        btnRight = (TextView) findViewById(R.id.btn_right);
        btnModifySign = (TextView) findViewById(R.id.btn_modify_sign);

        bindUi(RxUtils.click(phoneNumbers),o -> {

        });

        bindUi(RxUtils.click(icContactsAdd),o -> {

        });

        bindUi(RxUtils.click(icRight),o -> {

        });

        bindUi(RxUtils.click(btnLeft),o -> {

        });

        bindUi(RxUtils.click(btnRight),o -> {

        });

        bindUi(RxUtils.click(btnModifySign),o -> {

        });

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_send_message_layout;
    }
}
