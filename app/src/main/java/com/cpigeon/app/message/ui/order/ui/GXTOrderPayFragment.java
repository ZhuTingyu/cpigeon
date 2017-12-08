package com.cpigeon.app.message.ui.order.ui;

import com.cpigeon.app.message.ui.order.ui.presenter.GXTOrderPre;
import com.cpigeon.app.utils.RxUtils;

/**
 * Created by Zhu TingYu on 2017/12/8.
 */

public class GXTOrderPayFragment extends BaseOrderPayFragment<GXTOrderPre> {
    @Override
    protected GXTOrderPre initPresenter() {
        return new GXTOrderPre(getActivity());
    }


    @Override
    protected void bindHeadData() {

    }

    @Override
    protected void initView() {
        super.initView();
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            if(position == 0){
                showPayDialog("123","1233");
                bindUi(RxUtils.textChanges(edPassword),mPresenter.setPassword());
            }else {

            }
        });
    }
}
