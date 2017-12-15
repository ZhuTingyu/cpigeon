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
import com.cpigeon.app.entity.WeiXinPayEntity;
import com.cpigeon.app.event.WXPayResultEvent;
import com.cpigeon.app.message.ui.order.OderInfoViewHolder;
import com.cpigeon.app.message.ui.order.adpter.OrderPayAdapter;
import com.cpigeon.app.message.ui.order.ui.presenter.PayOrderPre;
import com.cpigeon.app.modular.home.view.activity.WebActivity;
import com.cpigeon.app.modular.usercenter.view.activity.SetPayPwdActivity;
import com.cpigeon.app.utils.CPigeonApiUrl;
import com.cpigeon.app.utils.CpigeonData;
import com.cpigeon.app.utils.DialogUtils;
import com.cpigeon.app.utils.IntentBuilder;
import com.cpigeon.app.utils.Lists;
import com.cpigeon.app.utils.RxUtils;
import com.cpigeon.app.utils.SendWX;
import com.cpigeon.app.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Zhu TingYu on 2017/12/7.
 */

public class OrderPayFragment extends BaseMVPFragment<PayOrderPre> {

    RecyclerView recyclerView;
    OrderPayAdapter adapter;
    OderInfoViewHolder holder;
    TextView tvPay;
    EditText edPassword;

    String type;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_recyclerview_layout;
    }


    @Override
    protected PayOrderPre initPresenter() {
        return new PayOrderPre(getActivity());
    }

    @Override
    protected boolean isCanDettach() {
        return false;
    }

    @Override
    public void finishCreateView(Bundle state) {
        EventBus.getDefault().register(this);
        type = getActivity().getIntent().getStringExtra(IntentBuilder.KEY_TYPE);
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

        adapter.setOnItemClickListener((adapter1, view, position) -> {

            if (holder.checkBox.isChecked()) {
                if (position == 0) {
                    mPresenter.getUserBalance(userBalanceEntity -> {
                        showPayDialog(String.format("%.2f", userBalanceEntity.ye), mPresenter.orderInfoEntity.price);
                        bindUi(RxUtils.textChanges(edPassword), mPresenter.setPassword());
                    });

                } else {
                    showLoading("正在创建订单");
                    mPresenter.getWXOrder(weiXinPayEntity -> {
                        hideLoading();
                        SendWX sendWX = new SendWX(getActivity());
                        sendWX.payWeiXin(weiXinPayEntity.getPayReq());
                    });
                }
            } else {
                error("请同意中鸽网支付协议");
            }

        });

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

    protected void bindHeadData() {
        holder.bindData(mPresenter.orderInfoEntity);
    }

    protected void showPayDialog(String balance, String price) {
        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        View view = View.inflate(getContext(), R.layout.dialogfragment_pay, null);

        ImageView colse = findViewById(view, R.id.iv_pay_close);
        TextView title = findViewById(view, R.id.tv_yue_prompt);
        edPassword = findViewById(view, R.id.et_paypwd);
        tvPay = findViewById(view, R.id.tv_pay);
        TextView meanPassword = findViewById(view, R.id.tv_mean_for_paypwd);
        TextView forgotPassword = findViewById(view, R.id.tv_forget_paypwd);

        bindUi(RxUtils.textChanges(edPassword), mPresenter.setPassword());

        title.setText(getString(R.string.format_pay_account_balance_tips, balance, price));

        colse.setOnClickListener(v -> {
            dialog.dismiss();
        });

        tvPay.setOnClickListener(v -> {
            payByBalance();
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

    private void payByBalance() {
        mPresenter.payOrderByBalance(r -> {
            if (r.status) {
                DialogUtils.createDialog(getContext(), r.msg, sweetAlertDialog -> {
                    sweetAlertDialog.dismiss();
                    finish();
                });
            } else {
                error(r.msg);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(WXPayResultEvent event) {
        if (WXPayResultEvent.CODE_OK == event.code) {
            DialogUtils.createDialogWithLeft(getContext(), "支付成功", sweetAlertDialog -> {
                sweetAlertDialog.dismiss();
                finish();
            });
        } else if (WXPayResultEvent.CODE_ERROR == event.code) {
            ToastUtil.showLongToast(getContext(), "支付失败");
        } else {
            ToastUtil.showLongToast(getContext(), "取消支付");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
