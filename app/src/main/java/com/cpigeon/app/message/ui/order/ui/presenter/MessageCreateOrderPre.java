package com.cpigeon.app.message.ui.order.ui.presenter;

import android.app.Activity;

import com.cpigeon.app.commonstandard.model.dao.IBaseDao;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.entity.OrderInfoEntity;
import com.cpigeon.app.message.ui.order.ui.OrderModel;
import com.cpigeon.app.utils.CpigeonData;
import com.cpigeon.app.utils.http.HttpErrorException;

import java.util.function.Consumer;

/**
 * Created by Zhu TingYu on 2017/12/8.
 */

public class MessageCreateOrderPre extends BasePresenter {

    public final static int SID_MESSAGE = 23;
    int userId;

    public MessageCreateOrderPre(Activity activity) {
        super(activity);
        userId = CpigeonData.getInstance().getUserId(activity);
    }

    @Override
    protected IBaseDao initDao() {
        return null;
    }

    public void greatOrder(io.reactivex.functions.Consumer<OrderInfoEntity> consumer) {

    }

}
