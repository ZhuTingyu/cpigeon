package com.cpigeon.app.message.ui.home;

import com.cpigeon.app.commonstandard.model.dao.IBaseDao;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.activity.IView;
import com.cpigeon.app.entity.UserGXTEntity;
import com.cpigeon.app.utils.databean.ApiResponse;
import com.cpigeon.app.utils.http.HttpErrorException;

import io.reactivex.functions.Consumer;

/**
 * Created by Zhu TingYu on 2017/11/23.
 */

public class PigeonHomePre extends BasePresenter {

    public int userId;

    public PigeonHomePre(IView mView) {
        super(mView);
    }

    @Override
    protected IBaseDao initDao() {
        return null;
    }

    public void getUserInfo(Consumer<ApiResponse<UserGXTEntity>> consumer){
        submitRequestThrowError(UserGXTModel.getUserInfo(userId).map(r -> {
            if(r.errorCode == -1){
                throw new HttpErrorException(r);
            }else {
                return r;
            }
        }),consumer);
    }

}