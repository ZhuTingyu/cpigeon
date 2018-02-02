package com.cpigeon.app.message.ui.sendmessage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatImageView;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cpigeon.app.MyApp;
import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.entity.ContactsGroupEntity;
import com.cpigeon.app.entity.UserGXTEntity;
import com.cpigeon.app.event.GXTUserInfoEvent;
import com.cpigeon.app.event.PersonInfoEvent;
import com.cpigeon.app.message.ui.common.CommonMessageFragment;
import com.cpigeon.app.message.ui.contacts.SelectContactsFragment;
import com.cpigeon.app.message.ui.modifysign.ModifySignFragment;
import com.cpigeon.app.message.ui.order.ui.CreateMessageOrderFragment;
import com.cpigeon.app.utils.IntentBuilder;
import com.cpigeon.app.utils.RxUtils;
import com.cpigeon.app.utils.StringUtil;
import com.cpigeon.app.utils.StringValid;
import com.cpigeon.app.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by Zhu TingYu on 2017/11/20.
 */

public class SendMessageFragment extends BaseMVPFragment<SendMessagePre> {

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
    TextView tvSign;
    TextView tvBlanceCount;


    UserGXTEntity userGXTEntity;


    @Override
    protected SendMessagePre initPresenter() {
        return new SendMessagePre(getActivity());
    }

    @Override
    protected boolean isCanDettach() {
        return false;
    }

    @Override
    public void finishCreateView(Bundle state) {
        EventBus.getDefault().register(this);
        setTitle("发送短信");
        userGXTEntity = getActivity().getIntent().getParcelableExtra(IntentBuilder.KEY_DATA);
        toolbar.getMenu().clear();
        toolbar.getMenu().add("充值短信")
                .setOnMenuItemClickListener(item -> {
                    IntentBuilder.Builder().startParentActivity(getActivity(), CreateMessageOrderFragment.class);
                    return true;
                }).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
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
        tvSign = findViewById(R.id.text2);
        tvBlanceCount = findViewById(R.id.balance);

        tvBlanceCount.setText(getString(R.string.string_pigeon_message_balance_count, String.valueOf(userGXTEntity.syts)));
        tvSign.setText(getString(R.string.string_sign_info, userGXTEntity.qianming));

        bindUi(RxUtils.textChanges(edContent), mPresenter.setMessageContent());

        bindUi(RxUtils.click(tvPhoneNumbers), o -> {

        });

        bindUi(RxUtils.click(icContactsAdd), o -> {
            IntentBuilder.Builder()
                    .putExtra(IntentBuilder.KEY_TYPE, SelectContactsFragment.TYPE_SEND_MESSAGE)
                    .startParentActivity(getActivity(), SelectContactsFragment.class, CODE_CONTACTS_LIST);
        });

        bindUi(RxUtils.click(icRight), o -> {
            IntentBuilder.Builder()
                    .putExtra(IntentBuilder.KEY_BOOLEAN, true)
                    .startParentActivity(getActivity(), CommonMessageFragment.class, CODE_COMMON_MESSAGE);
        });

        bindUi(RxUtils.click(btnLeft), o -> {
            mPresenter.addCommonMessage(r -> {
                if (r.status) {
                    showTips(r.msg, TipType.Dialog);
                } else {
                    showTips(r.msg, TipType.DialogError);
                }
            });
        });

        bindUi(RxUtils.click(btnRight), o -> {
            showLoading();
            mPresenter.sendMessage(r -> {
                hideLoading();
                if (r.status) {
                    ToastUtil.showLongToast(MyApp.getInstance().getBaseContext(), r.msg);
                    Intent intent = new Intent();
                    intent.putExtra(IntentBuilder.KEY_BOOLEAN, true);
                    getActivity().setResult(0, intent);

                    cleanData();

                } else {
                    showTips(r.msg, TipType.DialogError);
                }
            });
        });

        bindUi(RxUtils.click(btnModifySign), o -> {
            IntentBuilder.Builder().startParentActivity(getActivity(), ModifySignFragment.class);
        });

    }

    private void cleanData() {
        mPresenter.cleanData();
        tvPhoneNumbers.setText("");
        edContent.setText("");
        contactsNumber.setVisibility(View.GONE);
        getUserInfo();
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
                String content = data.getStringExtra(IntentBuilder.KEY_DATA);
                edContent.setText(content);
                edContent.setSelection(content.length());
            }
        } else if (CODE_CONTACTS_LIST == requestCode) {
            if (data != null && data.hasExtra(IntentBuilder.KEY_DATA)) {
                List<ContactsGroupEntity> groupEntities = data.getParcelableArrayListExtra(IntentBuilder.KEY_DATA);
                contactsNumber.setVisibility(View.VISIBLE);
                contactsNumber.setText(getString(R.string.string_text_select_contacts_number
                        , String.valueOf(mPresenter.getContactsCount(groupEntities))));
                mPresenter.setGroupIds(groupEntities);
                mPresenter.getSendNumber(s -> {
                    tvPhoneNumbers.setText(s.length() > 40 ? StringUtil.getCutString(s, 0, 40) : s);
                });
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GXTUserInfoEvent event) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //do something
                getUserInfo();

            }
        }, 600);    //延时1s执行
    }

    private void getUserInfo(){
        mPresenter.getUserInfo(r -> {
            tvBlanceCount.setText(getString(R.string.string_pigeon_message_balance_count, String.valueOf(r.syts)));
        });
    }
}
