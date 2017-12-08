package com.cpigeon.app.message.ui.order.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.message.ui.order.OderInfoViewHolder;
import com.cpigeon.app.message.ui.order.adpter.OrderPayAdapter;
import com.cpigeon.app.modular.home.view.activity.WebActivity;
import com.cpigeon.app.modular.usercenter.view.activity.SetPayPwdActivity;
import com.cpigeon.app.utils.CPigeonApiUrl;
import com.cpigeon.app.utils.Lists;

/**
 * Created by Zhu TingYu on 2017/12/7.
 */

public abstract class BaseOrderPayFragment<P extends  BasePresenter> extends BaseMVPFragment<P>{

    RecyclerView recyclerView;
    OrderPayAdapter adapter;
    OderInfoViewHolder holder;
    TextView tvPay;
    EditText edPassword;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_recyclerview_layout;
    }


    @Override
    protected boolean isCanDettach() {
        return false;
    }

    @Override
    public void finishCreateView(Bundle state) {
        initView();
    }

    protected void initView() {
        setTitle("订单支付");
        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new OrderPayAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setNewData(Lists.newArrayList("余额支付", "微信支付"));
        adapter.addHeaderView(initHeadView());

        bindHeadData();
    }

    private View initHeadView() {
        View head = LayoutInflater.from(getContext()).inflate(R.layout.item_order_info_head_layout, recyclerView, false);
        holder = new OderInfoViewHolder(head);
        holder.visibleBottom();

        holder.tvProtocol.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), WebActivity.class);
            intent.putExtra(WebActivity.INTENT_DATA_KEY_URL, CPigeonApiUrl.getInstance().getServer() + "/APP/Protocol?type=pay");
            intent.putExtra(WebActivity.INTENT_DATA_KEY_BACKNAME, "订单支付");
            startActivity(intent);
        });
        return holder.itemView;
    }

    protected abstract void bindHeadData();

    protected void showPayDialog(String balance, String price){
        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        View view = View.inflate(getContext(),R.layout.dialogfragment_pay,null);

        ImageView colse = findViewById(view, R.id.iv_pay_close);
        TextView title = findViewById(view, R.id.tv_yue_prompt);
        edPassword = findViewById(view, R.id.et_paypwd);
        tvPay = findViewById(view, R.id.tv_pay);
        TextView meanPassword = findViewById(view, R.id.tv_mean_for_paypwd);
        TextView forgotPassword = findViewById(view, R.id.tv_forget_paypwd);

        title.setText(getString(R.string.format_pay_account_balance_tips,balance,price));

        colse.setOnClickListener(v -> {
            dialog.dismiss();
        });

        meanPassword.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), WebActivity.class);
            String url = CPigeonApiUrl.getInstance().getServer() + "/APP/Help?type=help&id=172";
            intent.putExtra(WebActivity.INTENT_DATA_KEY_URL, url);
            intent.putExtra(WebActivity.INTENT_DATA_KEY_BACKNAME, "付款");
            startActivity(intent);
        });

        forgotPassword.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), SetPayPwdActivity.class));
        });



        dialog.setContentView(view);
        dialog.show();
    }

}
