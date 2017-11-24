package com.cpigeon.app.message.ui.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.entity.MultiSelectEntity;
import com.cpigeon.app.utils.IntentBuilder;
import com.cpigeon.app.utils.Lists;
import com.cpigeon.app.view.indexrecyclerview.SelectPhoneActivity;

import java.util.ArrayList;

/**
 * Created by Zhu TingYu on 2017/11/21.
 */

public class SelectContactsFragment extends BaseContactsListFragment {

    public static final int TYPE_CONTACTS_ADD = 1;

    public static final int TYPE_PHONE_SELECT = 2;


    private int type;

    @Override
    public void finishCreateView(Bundle state) {
        super.finishCreateView(state);
        type = getActivity().getIntent().getIntExtra(IntentBuilder.KEY_TYPE, 0);
        setTitle("选择群组");

        bottomLinearLayout.setVisibility(View.GONE);
        btn.setVisibility(View.VISIBLE);
        btn.setText("确定");
        btn.setOnClickListener(v -> {
            if(type == TYPE_CONTACTS_ADD){
                Intent intent = new Intent();
                intent.putExtra(IntentBuilder.KEY_DATA,"12312312");
                getActivity().setResult(0, intent);
                finish();
            }else if(type == TYPE_PHONE_SELECT) {
                IntentBuilder.Builder(getSupportActivity(), SelectPhoneActivity.class)
                        .startActivity();
            }
        });

        adapter.setOnItemClickListener((adapter1, view, position) -> {
            adapter.setSingleItem(adapter.getItem(position), position);
        });

        adapter.addFooterView(initFoodView());

    }

    @Override
    protected void bindData() {
        ArrayList<MultiSelectEntity> data = Lists.newArrayList();
        for (int i = 0; i < 5; i++) {
            data.add(new MultiSelectEntity());
        }
        //adapter.setNewData(data);
        adapter.setImgChooseVisible(true);
    }

    public View initFoodView() {
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.item_select_cantacts_foot_layout, recyclerView, false);
        view.setOnClickListener(v -> {
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

        btnLeft.setOnClickListener(v -> {
            dialogPrompt.dismiss();
        });

        btnRight.setOnClickListener(v -> {

        });

        dialogPrompt.setView(view);
        dialogPrompt.show();
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }
}
