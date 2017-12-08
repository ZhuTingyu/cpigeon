package com.cpigeon.app.message.ui.home;

import com.cpigeon.app.commonstandard.model.dao.IBaseDao;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.activity.IView;
import com.cpigeon.app.entity.OrderInfoEntity;
import com.cpigeon.app.entity.UserGXTEntity;
import com.cpigeon.app.message.ui.order.ui.OrderModel;
import com.cpigeon.app.utils.databean.ApiResponse;
import com.cpigeon.app.utils.http.HttpErrorException;

import io.reactivex.functions.Consumer;

/**
 * Created by Zhu TingYu on 2017/11/23.
 */

public class PigeonHomePre extends BasePresenter {

    public int userId;
    int sid = 22;

    public static final int STATE_NO_OPEN = 10000;
    public static final int STATE_EXAMINEING = 10010;
    public static final int STATE_NOT_PAY = 10011;

    public PigeonHomePre(IView mView) {
        super(mView);
    }

    @Override
    protected IBaseDao initDao() {
        return null;
    }

    public void getUserInfo(Consumer<ApiResponse<UserGXTEntity>> consumer) {
        submitRequestThrowError(UserGXTModel.getUserInfo(userId).map(r -> {
            if (r.errorCode == -1) {
                throw new HttpErrorException(r);
            } else {
                return r;
            }
        }), consumer);
    }

    public void greatGXTOrder(Consumer<OrderInfoEntity> consumer) {
        submitRequestThrowError(OrderModel.greatGXTOrder(userId, sid).map(r -> {
            if (r.isOk()) {
                if (r.status) {
                    return r.data;
                } else throw new HttpErrorException(r);
            } else throw new HttpErrorException(r);
        }), consumer);
    }
}
