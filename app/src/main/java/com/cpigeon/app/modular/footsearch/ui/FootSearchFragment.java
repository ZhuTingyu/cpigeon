package com.cpigeon.app.modular.footsearch.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.modular.footsearch.presenter.FootSearchPre;
import com.cpigeon.app.modular.order.view.activity.OpenServiceActivity;
import com.cpigeon.app.utils.DateTool;
import com.cpigeon.app.utils.IntentBuilder;
import com.cpigeon.app.utils.Lists;
import com.cpigeon.app.utils.RxUtils;
import com.cpigeon.app.utils.ScreenTool;

import java.util.List;

import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.widget.WheelView;

/**
 * Created by Zhu TingYu on 2017/12/21.
 */

public class FootSearchFragment extends BaseMVPFragment<FootSearchPre> {

    private TextView year;
    private TextView searchContent;
    private TextView searchBtn;
    private TextView lookHistroy;
    private TextView open;

    private int datePosition;

    RelativeLayout cotent;

    @Override
    protected FootSearchPre initPresenter() {
        return new FootSearchPre(getActivity());
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
        cotent = findViewById(R.id.rl_content);

        bindUi(RxUtils.textChanges(searchContent), mPresenter.setKeyWord());

        year.setOnClickListener(v -> {
            showPicker();
        });



        lookHistroy.setOnClickListener(v -> {
            IntentBuilder.Builder().startParentActivity(getActivity(), FootSearchHistoryFragment.class);
        });

        open.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), OpenServiceActivity.class);
            intent.putExtra(OpenServiceActivity.INTENT_DATA_KEY_SERVICENAME, "足环查询服务");
            startActivity(intent);
        });

        searchBtn.setOnClickListener(v -> {
            mPresenter.searchFoot(s -> {

            });
        });

        initTopImg();

    }

    private void initTopImg() {
        AppCompatImageView imageView = findViewById(R.id.icon_title);

        int w = ScreenTool.getScreenWidth(getContext());
        int h = (int) (w * 0.46f);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(w,h);
        imageView.setLayoutParams(layoutParams);
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
                mPresenter.year = item;
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

}
