package com.cpigeon.app.message.ui.order.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.message.ui.order.OderInfoViewHolder;
import com.cpigeon.app.message.ui.order.adpter.OrderPayAdapter;
import com.cpigeon.app.utils.Lists;

/**
 * Created by Zhu TingYu on 2017/12/7.
 */

public class BaseOrderPayFragment extends BaseMVPFragment{

    RecyclerView recyclerView;
    OrderPayAdapter adapter;
    OderInfoViewHolder holder;

    @Override
    public void finishCreateView(Bundle state) {
        initView();
    }

    private void initView() {
        setTitle("订单支付");
        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new OrderPayAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setNewData(Lists.newArrayList("余额支付", "微信支付"));
        adapter.addHeaderView(initHeadView());

        bindData();
    }

    private View initHeadView() {
        View head = LayoutInflater.from(getContext()).inflate(R.layout.item_order_info_head_layout, recyclerView, false);
        holder = new OderInfoViewHolder(head);
        holder.visibleBottom();

        holder.tvProtocol.setOnClickListener(v -> {

        });
        return holder.itemView;
    }


    private void bindData(){
        holder.bindData();
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_recyclerview_layout;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected boolean isCanDettach() {
        return false;
    }
}
