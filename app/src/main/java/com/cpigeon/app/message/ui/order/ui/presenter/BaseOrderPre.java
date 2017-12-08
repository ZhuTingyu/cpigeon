package com.cpigeon.app.message.ui.order.ui.presenter;

import android.app.Activity;

import com.cpigeon.app.commonstandard.model.dao.IBaseDao;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;

import io.reactivex.functions.Consumer;

/**
 * Created by Zhu TingYu on 2017/12/8.
 */

public class BaseOrderPre extends BasePresenter {

    String password;

    public BaseOrderPre(Activity activity) {
        super(activity);
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
