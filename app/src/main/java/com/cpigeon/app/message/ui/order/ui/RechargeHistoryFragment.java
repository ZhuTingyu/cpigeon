package com.cpigeon.app.message.ui.order.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.message.ui.order.adpter.RechargeHistoryAdapter;
import com.cpigeon.app.utils.DateTool;
import com.cpigeon.app.utils.Lists;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.DateTimePicker;
import cn.qqtheme.framework.util.ConvertUtils;

/**
 * Created by Zhu TingYu on 2017/12/7.
 */

public class RechargeHistoryFragment extends BaseMVPFragment {

    RecyclerView recyclerView;
    RechargeHistoryAdapter adapter;

    TextView tvDateLeft;
    TextView tvDataRight;

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

        setTitle("充值记录");
        initView();
    }

    private void initView() {
        tvDateLeft = findViewById(R.id.date_left);
        tvDataRight = findViewById(R.id.date_right);

        tvDateLeft.setText(DateTool.format(System.currentTimeMillis(),DateTool.FORMAT_DATE));
        tvDataRight.setText(DateTool.format(System.currentTimeMillis(),DateTool.FORMAT_DATE));


        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RechargeHistoryAdapter();
        recyclerView.setAdapter(adapter);

        tvDateLeft.setOnClickListener(v -> {
            showTimePicker(tvDateLeft);
        });

        tvDataRight.setOnClickListener(v -> {
            showTimePicker(tvDataRight);
        });

        bindData();
    }

    private void bindData(){
        adapter.setNewData(Lists.newArrayList("","","",""));
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_recharge_history_layout;
    }

    public void showTimePicker(TextView textView) {

        int endY = Integer.parseInt(DateTool.format(System.currentTimeMillis(),DateTool.FORMAT_YYYY));
        int endM = Integer.parseInt(DateTool.format(System.currentTimeMillis(),DateTool.FORMAT_MM));
        int endD = Integer.parseInt(DateTool.format(System.currentTimeMillis(),DateTool.FORMAT_DD));

        final DatePicker picker = new DatePicker(getActivity());
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setTopPadding(ConvertUtils.toPx(getContext(), 10));
        picker.setRangeEnd(endY, endM, endD);
        picker.setRangeStart(2017, 1, 1);
        picker.setSelectedItem(endY, endM, endD);
        picker.setResetWhileWheel(false);
        picker.setTopLineColor(getResources().getColor(R.color.colorPrimary));
        picker.setLabelTextColor(getResources().getColor(R.color.colorPrimary));
        picker.setDividerColor(getResources().getColor(R.color.colorPrimary));
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                textView.setText(year + "-" + month + "-" + day);
            }
        });
        picker.show();
    }
}
