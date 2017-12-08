package com.cpigeon.app.message.ui.order.ui;

import com.cpigeon.app.message.ui.order.ui.BaseOrderPayFragment;
import com.cpigeon.app.message.ui.order.ui.presenter.MessageOrderPre;
import com.cpigeon.app.utils.RxUtils;

/**
 * Created by Zhu TingYu on 2017/12/8.
 */

public class MessageOrderFragment extends BaseOrderPayFragment<MessageOrderPre> {

    @Override
    protected MessageOrderPre initPresenter() {
        return new MessageOrderPre(getActivity());
    }

    @Override
    protected void bindHeadData() {

    }

    @Override
    protected void initView() {
        super.initView();
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            if (position == 0) {
                showPayDialog("123", "1233");
                bindUi(RxUtils.textChanges(edPassword), mPresenter.setPassword());
            } else {

            }
        });
    }
}
