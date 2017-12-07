package com.cpigeon.app.message.ui.order.ui;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.widget.TextView;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.utils.Lists;

import java.util.List;

/**
 * Created by Zhu TingYu on 2017/12/7.
 */

public class CreateMessageOrderFragment extends BaseMVPFragment{

    AppCompatTextView tvExplain;
    AppCompatTextView tvPrice;
    AppCompatButton btnLook;

    TextView btn;

    List<AppCompatTextView> selectTvs;

    List<Integer> tvIds;

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



    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_creat_message_order_layout;
    }

    private void initView() {
        tvIds = Lists.newArrayList(R.id.tv_select1,R.id.tv_select2,R.id.tv_select3
                ,R.id.tv_select4,R.id.tv_select5);

        tvExplain = findViewById(R.id.tv_explain);
        tvPrice = findViewById(R.id.order_price);
        btnLook = findViewById(R.id.btn_1);

        btn = findViewById(R.id.text_btn);

        for (Integer id : tvIds) {
            AppCompatTextView textView = findViewById(tvIds.get(id));
            textView.setOnClickListener(v -> {

            });
            selectTvs.add(textView);
        }


    }
}
