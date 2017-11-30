package com.cpigeon.app.message.ui.modifysign;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.message.adapter.PersonImageInfoAdapter;
import com.cpigeon.app.message.ui.idCard.IdCardCameraActivity;
import com.cpigeon.app.utils.IntentBuilder;
import com.cpigeon.app.utils.Lists;

/**
 * Created by Zhu TingYu on 2017/11/22.
 */

public class ModifySignFragment extends BaseMVPFragment {

    RecyclerView recyclerView;
    PersonImageInfoAdapter adapter;

    TextView btn;

    EditText edSign;

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
        setTitle("修改签名");
        hideSoftInput();
        initView();
    }

    private void initView() {
        findViewById(R.id.ll1).setVisibility(View.GONE);
        btn = findViewById(R.id.text_btn);
        btn.setVisibility(View.VISIBLE);
        btn.setText("提交签名");

        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new PersonImageInfoAdapter(getContext());
        adapter.bindToRecyclerView(recyclerView);
        adapter.addHeaderView(initHeadView());
        adapter.setNewData(Lists.newArrayList("", "", ""));
        recyclerView.requestFocus();

        adapter.setOnItemClickListener((adapter1, view, position) -> {
            if (position == 0) {//身份证正面
                IntentBuilder.Builder(getActivity(), IdCardCameraActivity.class)
                        .startActivity();
            } else if (position == 1) {//身份中反面

            } else if (position == 2) {//营业执照

            }
        });

    }

    private View initHeadView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_modify_sign_head_layout, recyclerView, false);
        edSign = findViewById(view, R.id.sign);
        edSign.clearFocus();
        return view;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_recyclerview_with_button_layout;
    }
}
