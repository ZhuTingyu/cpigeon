package com.cpigeon.app.circle.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.cpigeon.app.R;
import com.cpigeon.app.circle.adpter.ChooseImageAdapter;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.view.SingleSelectCenterDialog;
import com.luck.picture.lib.model.LocalMediaLoader;

/**
 * Created by Zhu TingYu on 2018/1/16.
 */

public class PushCircleMessageFragement extends BaseMVPFragment {

    RecyclerView recyclerView;
    ChooseImageAdapter adapter;
    SingleSelectCenterDialog publishTypeDialog;
    SingleSelectCenterDialog.OnItemClickListener onItemClickListener = new SingleSelectCenterDialog.OnItemClickListener() {
        @Override
        public void onItemClick(SingleSelectCenterDialog dialog, SingleSelectCenterDialog.SelectItem item) {
            if (item != null) {
                if (item.getText().equals("选择视频")) {

                } else {

                }
                dialog.dismiss();

            }
        }
    };


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_push_circle_message_layout;
    }

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

        SingleSelectCenterDialog publishTypeDialog = new SingleSelectCenterDialog
                .Builder(getContext())
                .addSelectItem("选择图片", onItemClickListener)
                .addSelectItem("选择视频", onItemClickListener)
                .create();

        findViewById(R.id.rl_user_location).setOnClickListener(v -> {

        });

        findViewById(R.id.rl_user_msg_visibility).setOnClickListener(v -> {

        });

        recyclerView = findViewById(R.id.list);
        adapter = new ChooseImageAdapter();
        recyclerView.setAdapter(adapter);
    }
}
