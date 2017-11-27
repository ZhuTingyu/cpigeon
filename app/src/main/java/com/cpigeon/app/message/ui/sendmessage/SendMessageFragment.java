package com.cpigeon.app.message.ui.sendmessage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.message.ui.common.CommonMessageFragment;
import com.cpigeon.app.message.ui.contacts.SelectContactsFragment;
import com.cpigeon.app.message.ui.contacts.SendMessageContactsListFragment;
import com.cpigeon.app.message.ui.modifysign.ModifySignFragment;
import com.cpigeon.app.utils.IntentBuilder;
import com.cpigeon.app.utils.RxUtils;
import com.cpigeon.app.utils.StringValid;

/**
 * Created by Zhu TingYu on 2017/11/20.
 */

public class SendMessageFragment extends BaseMVPFragment {

    public static final int CODE_COMMON_MESSAGE = 0x123;
    public static final int CODE_CONTACTS_LIST = 0x234;

    TextView tvPhoneNumbers;
    AppCompatImageView icContactsAdd;
    EditText edContent;
    AppCompatImageView icRight;
    TextView btnLeft;
    TextView btnRight;
    TextView btnModifySign;
    TextView contactsNumber;


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
        tvPhoneNumbers = (TextView) findViewById(R.id.phone_numbers);
        icContactsAdd = (AppCompatImageView) findViewById(R.id.ic_contacts_add);
        edContent = (EditText) findViewById(R.id.message_content);
        icRight = (AppCompatImageView) findViewById(R.id.ic_right);
        btnLeft = (TextView) findViewById(R.id.btn_left);
        btnRight = (TextView) findViewById(R.id.btn_right);
        btnModifySign = (TextView) findViewById(R.id.btn_modify_sign);
        contactsNumber = findViewById(R.id.number);

        bindUi(RxUtils.click(tvPhoneNumbers), o -> {

        });

        bindUi(RxUtils.click(icContactsAdd), o -> {
            IntentBuilder.Builder()
                    .putExtra(IntentBuilder.KEY_TYPE, SelectContactsFragment.TYPE_CONTACTS_ADD)
                    .startParentActivity(getActivity(), SelectContactsFragment.class, CODE_CONTACTS_LIST);
        });

        bindUi(RxUtils.click(icRight), o -> {
            IntentBuilder.Builder()
                    .putExtra(IntentBuilder.KEY_BOOLEAN, true)
                    .startParentActivity(getActivity(), CommonMessageFragment.class, CODE_COMMON_MESSAGE);
        });

        bindUi(RxUtils.click(btnLeft), o -> {

        });

        bindUi(RxUtils.click(btnRight), o -> {

        });

        bindUi(RxUtils.click(btnModifySign), o -> {
            IntentBuilder.Builder().startParentActivity(getActivity(), ModifySignFragment.class);
        });

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_send_message_layout;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (CODE_COMMON_MESSAGE == requestCode) {
            if (data != null && StringValid.isStringValid(data.getStringExtra(IntentBuilder.KEY_DATA))) {
                edContent.setText(data.getStringExtra(IntentBuilder.KEY_DATA));
            }
        }else if (CODE_CONTACTS_LIST == requestCode){
            if(data != null){
                contactsNumber.setVisibility(View.VISIBLE);
            }
        }
    }
}
