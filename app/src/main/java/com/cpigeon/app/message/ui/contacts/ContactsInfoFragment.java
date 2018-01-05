package com.cpigeon.app.message.ui.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.entity.ContactsEntity;
import com.cpigeon.app.entity.ContactsGroupEntity;
import com.cpigeon.app.event.ContactsEvent;
import com.cpigeon.app.message.ui.contacts.presenter.ContactsInfoPre;
import com.cpigeon.app.utils.IntentBuilder;
import com.cpigeon.app.utils.RxUtils;
import com.cpigeon.app.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Zhu TingYu on 2017/11/21.
 */

public class ContactsInfoFragment extends BaseMVPFragment<ContactsInfoPre> {

    public static final int CODE_SELECTE = 0x123;

    public static final int TYPE_LOOK = 0;
    public static final int TYPE_EDIT = 1;

    EditText editText1;
    EditText editText2;
    EditText editText3;
    TextView editText4;

    TextView textView4;


    TextView btn;

    ContactsEntity contactsEntity;

    String groupName;

    int type;

    RelativeLayout rlSelectGroup;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragent_cantacts_info_layout;
    }

    @Override
    protected ContactsInfoPre initPresenter() {
        return new ContactsInfoPre(this.getActivity());
    }

    @Override
    protected boolean isCanDettach() {
        return false;
    }

    @Override
    public void finishCreateView(Bundle state) {
        type = getActivity().getIntent().getIntExtra(IntentBuilder.KEY_TYPE,0);
        contactsEntity = mPresenter.contactsEntity;
        groupName = getActivity().getIntent().getStringExtra(IntentBuilder.KEY_TITLE);
        initView();
    }

    private void initView() {
        editText1 = findViewById(R.id.ed1);
        editText2 = findViewById(R.id.ed2);
        editText3 = findViewById(R.id.ed3);
        editText4 = findViewById(R.id.ed4);

        textView4 = findViewById(R.id.text4);

        rlSelectGroup = findViewById(R.id.rl2);
        btn = findViewById(R.id.text_btn);

        bindUi(RxUtils.textChanges(editText1), mPresenter.setName());
        bindUi(RxUtils.textChanges(editText2), mPresenter.setPhoneNumer());
        bindUi(RxUtils.textChanges(editText3), mPresenter.setRemarks());

        if(TYPE_EDIT == type){
            setTitle("添加联系人");

            rlSelectGroup.setOnClickListener(v -> {
                IntentBuilder.Builder()
                        .putExtra(IntentBuilder.KEY_TYPE, SelectContactsFragment.TYPE_CONTACTS_ADD)
                        .startParentActivity(getActivity(), SelectContactsFragment.class, CODE_SELECTE);
            });


            btn.setOnClickListener(v -> {
                mPresenter.addContacts(r -> {
                    if(r.status){
                        ToastUtil.showLongToast(getContext(), r.msg);
                        EventBus.getDefault().post(new ContactsEvent());
                        finish();
                    }else {
                        error(r.msg);
                    }
                });
            });

        }else {

            setTitle("联系人详情");
            if(contactsEntity != null){
                editText1.setText(contactsEntity.xingming);
                editText2.setText(contactsEntity.sjhm);
                editText3.setText(contactsEntity.beizhu);
                editText4.setText(groupName);
            }
            textView4.setText("分组");

            setToolBarEdit();

        }

    }

    private void setToolBarEdit(){
        setUiCanEdit(false);
        toolbar.getMenu().clear();
        toolbar.getMenu().add("编辑").setOnMenuItemClickListener(item -> {
            setToolBarCancel();
            return false;
        }).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    private void setToolBarCancel(){
        setUiCanEdit(true);
        toolbar.getMenu().clear();
        toolbar.getMenu().add("取消").setOnMenuItemClickListener(item -> {
            setToolBarEdit();
            return false;
        }).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    private void setUiCanEdit(boolean icCanEdit){
        if(icCanEdit){
            btn.setVisibility(View.VISIBLE);
            findViewById(R.id.icon).setVisibility(View.VISIBLE);
            editText1.setFocusable(true);
            editText2.setFocusable(true);
            editText3.setFocusable(true);
            rlSelectGroup.setOnClickListener(v -> {
                IntentBuilder.Builder()
                        .putExtra(IntentBuilder.KEY_TYPE, SelectContactsFragment.TYPE_CONTACTS_ADD)
                        .startParentActivity(getActivity(), SelectContactsFragment.class, CODE_SELECTE);
            });
            btn.setOnClickListener(v -> {
                mPresenter.modifyContacts(s -> {
                    ToastUtil.showLongToast(getContext(), s);
                    EventBus.getDefault().post(new ContactsEvent());
                    finish();
                });
            });
        }else {
            btn.setVisibility(View.GONE);
            findViewById(R.id.icon).setVisibility(View.GONE);
            editText1.setFocusable(false);
            editText2.setFocusable(false);
            editText3.setFocusable(false);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(CODE_SELECTE == requestCode){
            if(data != null && data.hasExtra(IntentBuilder.KEY_DATA)){
                ContactsGroupEntity groupEntity = data.getParcelableExtra(IntentBuilder.KEY_DATA);
                editText4.setText(groupEntity.fzmc);
                mPresenter.groupId = groupEntity.fzid;
            }
        }
    }
}
