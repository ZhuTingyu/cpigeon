package com.cpigeon.app.message.ui.contacts;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cpigeon.app.R;
import com.cpigeon.app.entity.MultiSelectEntity;
import com.cpigeon.app.message.ui.contacts.presenter.TelephoneBookPre;
import com.cpigeon.app.utils.CpigeonData;
import com.cpigeon.app.utils.IntentBuilder;
import com.cpigeon.app.utils.Lists;
import com.cpigeon.app.utils.RxUtils;
import com.cpigeon.app.utils.ToastUtil;

import java.util.ArrayList;

/**
 * Created by Zhu TingYu on 2017/11/21.
 */

public class TelephoneBookFragment extends BaseContactsListFragment<TelephoneBookPre> {

    @Override
    protected TelephoneBookPre initPresenter() {
        return new TelephoneBookPre(this);
    }

    @Override
    public void finishCreateView(Bundle state) {
        super.finishCreateView(state);
        setTitle("电话薄");

        icon.setBackgroundResource(R.mipmap.ic_contacts_add);
        title.setText("添加联系人");

        bottomLinearLayout.setOnClickListener(v -> {
            IntentBuilder.Builder()
                    .putExtra(IntentBuilder.KEY_TYPE, ContactsInfoFragment.TYPE_EDIT)
                    .startParentActivity(getActivity(), ContactsInfoFragment.class);
        });

        adapter.addHeaderView(initHeadView());

        adapter.setOnItemClickListener((adapter1, view, position) -> {
            IntentBuilder.Builder()
                    .putExtra(IntentBuilder.KEY_DATA,"川南地区")
                    .startParentActivity(getActivity(), ContactsListFragment.class);
        });



    }

    @Override
    protected void bindData() {
        mPresenter.getContactsGroups(data -> {
            adapter.setNewData(data);
            adapter.setImgChooseVisible(false);
        });

    }

    private View initHeadView(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_telephone_book_fagment_head_layout, recyclerView,false);
        findViewById(view, R.id.rl1).setOnClickListener(v -> {
            IntentBuilder.Builder()
                    .putExtra(IntentBuilder.KEY_TYPE, SelectContactsFragment.TYPE_PHONE_SELECT)
                    .startParentActivity(getActivity(), SelectContactsFragment.class);
        });

        findViewById(view, R.id.rl2).setOnClickListener(v -> {
            showAddMessageDialog();
        });
        return view;
    }

    private void showAddMessageDialog() {
        AlertDialog dialogPrompt = new AlertDialog.Builder(getContext()).create();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_message_layout, null);

        TextView title = findViewById(view, R.id.title);
        EditText content = findViewById(view, R.id.content);

        TextView btnLeft = findViewById(view, R.id.btn_left);
        TextView btnRight = findViewById(view, R.id.btn_right);

        title.setText("新建群组");

        bindUi(RxUtils.textChanges(content), mPresenter.setGroupName());

        btnLeft.setOnClickListener(v -> {
            dialogPrompt.dismiss();
        });

        btnRight.setOnClickListener(v -> {

            if(mPresenter.groupName.length() < 3){
                ToastUtil.showLongToast(getContext(),"分组名称长度不能小于3位");
                return;
            }

            mPresenter.addContactsGroups(r ->{
                dialogPrompt.dismiss();
                if(r.status){
                    bindData();
                    showTips("添加成功", TipType.Dialog);
                }else {
                    error(r.msg);
                }
            });
        });

        dialogPrompt.setView(view);
        dialogPrompt.show();
    }
}
