package com.cpigeon.app.message.ui.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cpigeon.app.R;
import com.cpigeon.app.message.ui.contacts.BaseContactsListFragment;
import com.cpigeon.app.utils.IntentBuilder;

/**
 * Created by Zhu TingYu on 2017/11/21.
 */

public class SendMessageContactsListFragment extends BaseContactsListFragment {

    @Override
    public void finishCreateView(Bundle state) {
        super.finishCreateView(state);
        setTitle("选择群组");

        bottomLinearLayout.setVisibility(View.GONE);
        btn.setVisibility(View.VISIBLE);
        btn.setText("确定");
        btn.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra(IntentBuilder.KEY_DATA, "12312313112312313512341233");
            getActivity().setResult(0, intent);
            finish();
        });

        adapter.setImgChooseVisible(true);

        adapter.setOnItemClickListener((adapter1, view, position) -> {
            adapter.setMultiSelectItem(adapter.getItem(position), position);
        });

    }
}
