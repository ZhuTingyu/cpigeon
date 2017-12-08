package com.cpigeon.app.message.ui.order.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.message.ui.order.ui.presenter.MessageCreateOrderPre;
import com.cpigeon.app.utils.IntentBuilder;
import com.cpigeon.app.utils.Lists;

import java.util.List;

/**
 * Created by Zhu TingYu on 2017/12/7.
 */

public class CreateMessageOrderFragment extends BaseMVPFragment<MessageCreateOrderPre>{

    AppCompatTextView tvExplain;
    AppCompatTextView tvPrice;
    AppCompatButton btnLook;
    AppCompatEditText edCount;

    List<AppCompatTextView> selectTvs;

    List<Integer> tvIds;

    @Override
    protected MessageCreateOrderPre initPresenter() {
        return new MessageCreateOrderPre(getActivity());
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

        setTitle("短信充值");

        toolbar.getMenu().add("充值记录")
                .setOnMenuItemClickListener(item -> {
                    IntentBuilder.Builder().startParentActivity(getActivity(), RechargeHistoryFragment.class);
                    return false;
                }).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        tvIds = Lists.newArrayList(R.id.tv_select1,R.id.tv_select2,R.id.tv_select3
                ,R.id.tv_select4,R.id.tv_select5);
        selectTvs = Lists.newArrayList();

        tvExplain = findViewById(R.id.tv_explain);
        tvPrice = findViewById(R.id.order_price);

        edCount = findViewById(R.id.ed_count);

        tvExplain.setText(getString(R.string.string_text_create_message_order_explain,"1231","123"));


        for (int i = 0, len = tvIds.size(); i < len; i++) {
            AppCompatTextView textView = findViewById(tvIds.get(i));
            selectTvs.add(textView);
        }

        for (int i = 0, len = selectTvs.size(); i < len; i++) {
            int finalI = i;
            selectTvs.get(i).setOnClickListener(v -> {
                setTvExplainListener(finalI);
            });
        }

    }

    private void setTvExplainListener(int position){

        if(position == 4){
            edCount.setEnabled(true);
        }else edCount.setEnabled(false);

        for (int i = 0, len = selectTvs.size(); i < len; i++) {
            AppCompatTextView textView = selectTvs.get(i);

            if(position == i){
                Drawable drawable = getResources().getDrawable(R.drawable.ic_blue_hook);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                textView.setCompoundDrawables(drawable,null,null,null);

                textView.setSelected(true);
            }else {
                textView.setCompoundDrawables(null,null,null,null);
                textView.setSelected(false);
            }
        }
    }
}
