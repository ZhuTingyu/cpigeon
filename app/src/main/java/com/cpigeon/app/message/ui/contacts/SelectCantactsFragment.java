package com.cpigeon.app.message.ui.contacts;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cpigeon.app.R;

/**
 * Created by Zhu TingYu on 2017/11/21.
 */

public class SelectCantactsFragment extends BaseContactsListFragment {

    @Override
    public void finishCreateView(Bundle state) {
        super.finishCreateView(state);

        setTitle("选择群组");

        bottomLinearLayout.setVisibility(View.GONE);
        btn.setVisibility(View.VISIBLE);
        btn.setText("确定");
        btn.setOnClickListener(v -> {

        });

        adapter.setOnItemClickListener((adapter1, view, position) -> {
            adapter.setSingleItem(adapter.getItem(position), position);
        });

        adapter.addFooterView(initFoodView());

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
}
