package com.cpigeon.app.message.ui.order.ui.presenter;

import android.app.Activity;

import com.cpigeon.app.commonstandard.model.dao.IBaseDao;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.entity.OrderInfoEntity;
import com.cpigeon.app.utils.CpigeonData;
import com.cpigeon.app.utils.IntentBuilder;

import io.reactivex.functions.Consumer;

/**
 * Created by Zhu TingYu on 2017/12/8.
 */

public class PayOrderPre extends BasePresenter {

    String password;
    int userId;
    public OrderInfoEntity orderInfoEntity;

    public PayOrderPre(Activity activity) {
        super(activity);
        userId = CpigeonData.getInstance().getUserId(activity);
        orderInfoEntity = getActivity().getIntent().getParcelableExtra(IntentBuilder.KEY_DATA);
    }

    @Override
    protected IBaseDao initDao() {
        return null;
    }

    public Consumer<String> setPassword(){
        return s -> {
            password = s;
        };
    }

}
