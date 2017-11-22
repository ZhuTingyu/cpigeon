package com.cpigeon.app.message.ui.history;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.message.adapter.MessageHistoryAdapter;
import com.cpigeon.app.utils.DateTool;
import com.cpigeon.app.utils.Lists;
import com.cpigeon.app.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.widget.WheelView;

/**
 * Created by Zhu TingYu on 2017/11/21.
 */

public class MessageHistoryFragment extends BaseMVPFragment{

    RecyclerView recyclerView;
    MessageHistoryAdapter adapter;
    TextView date;
    TextView searchDate;
    TextView number;
    String dateString;

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
        initView();
    }

    private void initView() {

        dateString = DateTool.format(System.currentTimeMillis(), DateTool.FORMAT_YYYY_MM);

        initHeadView();

        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MessageHistoryAdapter();
        adapter.bindToRecyclerView(recyclerView);
        bindData();
    }

    private void initHeadView() {

        date = findViewById(R.id.date);
        searchDate = findViewById(R.id.date2);
        number = findViewById(R.id.number);

        date.setText(dateString);

        searchDate.setText(getString(R.string.string_text_date_for_message_history
                ,getMonth(dateString)));

        number.setText(getString(R.string.string_text_message_addressee_number,String.valueOf(4)));

        findViewById(R.id.rl_date).setOnClickListener(v -> {
            showPicker();
        });
    }

    private void bindData() {
        adapter.setNewData(Lists.newArrayList("","","","",""));
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_message_history_layout;
    }

    private void showPicker(){
        OptionPicker picker = new OptionPicker(getSupportActivity(), getDates());
        picker.setCanceledOnTouchOutside(false);
        picker.setDividerRatio(WheelView.DividerConfig.FILL);
        picker.setShadowColor(Color.RED, 40);
        picker.setSelectedIndex(1);
        picker.setCycleDisable(true);
        picker.setTextSize(11);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {

            }
        });
        picker.show();
    }
    //以后转移到到presenter里面

    public List<String> getDates(){
        List<String> dateList = Lists.newArrayList();
        String year = DateTool.format(System.currentTimeMillis(), DateTool.FORMAT_YYYY);
        for (int i = 0; i < 12; i++) {
            dateList.add(year + i);
        }
        return dateList;
    }

    private String getMonth(String date){
        return StringUtil.getCutString(date, 5,7);
    }
}
