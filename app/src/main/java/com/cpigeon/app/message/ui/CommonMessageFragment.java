package com.cpigeon.app.message.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.entity.MultiSelectEntity;
import com.cpigeon.app.message.adapter.CommonMessageAdapter;
import com.cpigeon.app.utils.DialogUtils;
import com.cpigeon.app.utils.IntentBuilder;
import com.cpigeon.app.utils.Lists;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Zhu TingYu on 2017/11/20.
 */

public class CommonMessageFragment extends BaseMVPFragment {

    boolean isSendMessage;

    RelativeLayout bottomRelativeLayout;
    LinearLayout bottomLinearLayout;

    CommonMessageAdapter adapter;
    RecyclerView recyclerView;

    AppCompatImageView bottomIcon;
    TextView bottomText;

    TextView bottomText2;

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
        return R.layout.fragment_recyclerview_with_button_layout;
    }

    @Override
    public void finishCreateView(Bundle state) {
        isSendMessage = getActivity().getIntent().getBooleanExtra(IntentBuilder.KEY_BOOLEAN,false);
        setTitle("短语库");
        initView();
        setToolbarChooseMenu();
        if(!isSendMessage){
            setBottomViewAdd();
        }else {
            initSelectMessage();
        }
    }

    private void initSelectMessage() {
        bottomLinearLayout.setVisibility(View.GONE);
        bottomText2.setVisibility(View.VISIBLE);
        bottomText2.setText("确定");
        bottomText2.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra(IntentBuilder.KEY_DATA, "sdfasdfasdfasdfas");
            getActivity().setIntent(intent);
            finish();
        });
    }

    private void setToolbarChooseMenu(){
        toolbar.getMenu().clear();
        toolbar.getMenu().add("选择")
                .setOnMenuItemClickListener(item -> {
                    setToolbarCancelMenu();
                    adapter.SetImgChooseVisible(true);
                    if(!isSendMessage){
                        setBottomViewDelete();
                        adapter.setOnItemClickListener((adapter1, view, position) -> {
                            adapter.setMultiSelectItem(adapter.getItem(position),position);
                        });
                    }else {
                        adapter.setOnItemClickListener((adapter1, view, position) -> {
                            adapter.setSingleItem(adapter.getItem(position), position);
                        });
                    }
                    return false;
                })
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

    }

    private void setToolbarCancelMenu(){
        toolbar.getMenu().clear();
        toolbar.getMenu().add("取消")
                .setOnMenuItemClickListener(item -> {
                    setToolbarChooseMenu();
                    if(!isSendMessage){
                        setBottomViewAdd();
                    }else {

                    }
                    adapter.SetImgChooseVisible(false);
                    adapter.setOnItemClickListener((adapter1, view, position) -> showMessageContent(adapter1,position));
                    return false;
                })
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    private void initView() {
        recyclerView = findViewById(R.id.list);
        adapter = new CommonMessageAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        if(isSendMessage){
            adapter.SetImgChooseVisible(true);
        }

        adapter.setOnItemClickListener((adapter1, view, position) -> showMessageContent(adapter1,position));

        bottomRelativeLayout = findViewById(R.id.rl1);
        bottomLinearLayout = findViewById(R.id.ll1);

        bottomIcon = findViewById(R.id.icon);
        bottomText = findViewById(R.id.title);
        bottomText2 = findViewById(R.id.text_btn);

        bindData();

    }

    private void showMessageContent(BaseQuickAdapter adapter, int position){
        DialogUtils.createDialog(getContext(),"详细内容"
                ,"fadfadfadfa", "确定");
    }

    private void bindData() {
        ArrayList<MultiSelectEntity> data = Lists.newArrayList();
        for (int i = 0; i < 5; i++) {
            data.add(new MultiSelectEntity());
        }
        adapter.setNewData(data);
    }

    private void setBottomViewAdd(){
        bottomIcon.setBackgroundResource(R.drawable.ic_message_add);
        bottomText.setText("添加短语");
        bottomRelativeLayout.setOnClickListener(v -> {
            showAddMessageDialog();
        });
    }

    private void setBottomViewDelete(){
        bottomIcon.setBackgroundResource(R.drawable.ic_message_delete);
        bottomText.setText("删除短语");
        bottomRelativeLayout.setOnClickListener(v -> {
            adapter.deleteChoose();
        });

    }

    private void showAddMessageDialog(){
        AlertDialog dialogPrompt = new AlertDialog.Builder(getContext()).create();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_message_layout,null);

        TextView title = findViewById(view, R.id.title);
        EditText content = findViewById(view, R.id.content);

        TextView btnLeft = findViewById(view, R.id.btn_left);
        TextView btnRight = findViewById(view, R.id.btn_right);

        title.setText("新建短语");

        btnLeft.setOnClickListener(v -> {
            dialogPrompt.dismiss();
        });

        btnRight.setOnClickListener(v -> {

        });

        dialogPrompt.setView(view);
        dialogPrompt.show();

    }

}
