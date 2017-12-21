package com.cpigeon.app.modular.footsearch;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.utils.DateTool;
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

    private int datePosition;
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
        toolbar.getMenu().add("帮助")
                .setOnMenuItemClickListener(item -> {
                    return false;
                }).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        initView();
    }

    private void initView(){
        year = findViewById(R.id.tv_year);
        searchContent = findViewById(R.id.tv_search_content);
        searchBtn = findViewById(R.id.tv_search);
        lookHistroy = findViewById(R.id.tv_look_history);
        open = findViewById(R.id.tv_open);


        year.setOnClickListener(v -> {
            showPicker();
        });


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
        for(int i = 2016; i < len; i++){
            date.add(String.valueOf(i));
        }
        return date;
    }
}
