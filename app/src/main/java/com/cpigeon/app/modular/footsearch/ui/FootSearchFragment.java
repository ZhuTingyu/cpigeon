package com.cpigeon.app.modular.footsearch.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.modular.footsearch.ui.adapter.FootSearchAdapter;
import com.cpigeon.app.modular.order.view.activity.OpenServiceActivity;
import com.cpigeon.app.utils.DateTool;
import com.cpigeon.app.utils.IntentBuilder;
import com.cpigeon.app.utils.Lists;

import java.util.List;

import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.widget.WheelView;

/**
 * Created by Zhu TingYu on 2017/12/21.
 */

public class FootSearchFragment extends BaseMVPFragment {

    private TextView year;
    private TextView searchContent;
    private TextView searchBtn;
    private TextView lookHistroy;
    private TextView open;
    private TextView btn;

    private int datePosition;

    RecyclerView recyclerView;
    FootSearchAdapter adapter;
    RelativeLayout cotent;

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
        return R.layout.fragment_foot_search_layout;
    }

    @Override
    public void finishCreateView(Bundle state) {
        toolbar.getMenu().clear();
        toolbar.getMenu().add("帮助")
                .setOnMenuItemClickListener(item -> {
                    IntentBuilder.Builder().startParentActivity(getActivity(), FootSearchHelpFragment.class);
                    return false;
                }).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        setTitle("足环查询");
        initView();
    }

    private void initView(){
        year = findViewById(R.id.tv_year);
        searchContent = findViewById(R.id.tv_search_content);
        searchBtn = findViewById(R.id.tv_search);
        lookHistroy = findViewById(R.id.tv_look_history);
        open = findViewById(R.id.tv_open);
        recyclerView = findViewById(R.id.list);
        btn = findViewById(R.id.btn);
        cotent = findViewById(R.id.rl_content);


        year.setOnClickListener(v -> {
            showPicker();
        });

        btn.setOnClickListener(v -> {
           showList(false);
        });

        lookHistroy.setOnClickListener(v -> {
            showList(true);
        });

        open.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), OpenServiceActivity.class);
            intent.putExtra(OpenServiceActivity.INTENT_DATA_KEY_SERVICENAME, "足环查询服务");
            startActivity(intent);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new FootSearchAdapter();
        recyclerView.setAdapter(adapter);

        bindData();
    }

    private void bindData(){
        adapter.setNewData(Lists.newArrayList("","",""));
    }

    private void showPicker(){
        OptionPicker picker = new OptionPicker(getSupportActivity(), getDates());
        picker.setCanceledOnTouchOutside(false);
        picker.setDividerRatio(WheelView.DividerConfig.FILL);
        picker.setSelectedIndex(datePosition);
        picker.setCycleDisable(true);
        picker.setTextSize(16);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                year.setText(item);
                datePosition = index;
            }
        });
        picker.show();
    }

    private List<String> getDates() {
        List<String> date = Lists.newArrayList();
        int len =  Integer.valueOf(DateTool.format(System.currentTimeMillis(),DateTool.FORMAT_YYYY));
        for(int i = 2016; i <= len; i++){
            date.add(String.valueOf(i));
        }
        return date;
    }

    private void showList(boolean show){
        if(show){
            recyclerView.setVisibility(View.VISIBLE);
            btn.setVisibility(View.VISIBLE);
            cotent.setVisibility(View.GONE);

        }else {
            recyclerView.setVisibility(View.GONE);
            btn.setVisibility(View.GONE);
            cotent.setVisibility(View.VISIBLE);
        }
    }
}
