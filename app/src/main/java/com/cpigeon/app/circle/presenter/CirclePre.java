package com.cpigeon.app.circle.presenter;

import android.app.Activity;

import com.cpigeon.app.circle.CircleModel;
import com.cpigeon.app.commonstandard.model.dao.IBaseDao;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.entity.CircleUserInfoEntity;
import com.cpigeon.app.utils.CpigeonData;
import com.cpigeon.app.utils.http.HttpErrorException;

import io.reactivex.functions.Consumer;

/**
 * Created by Zhu TingYu on 2018/1/19.
 */

public class CirclePre extends BasePresenter {

    int userId;

    public CirclePre(Activity activity) {
        super(activity);
        userId = CpigeonData.getInstance().getUserId(activity);
    }

    @Override
    protected IBaseDao initDao() {
        return null;
    }

    public void getUserInfo(Consumer<CircleUserInfoEntity> consumer){
        submitRequestThrowError(CircleModel.getCircleInfo(userId).map(r -> {
            if(r.status){
                return r.data;
            }else throw new HttpErrorException(r);
        }),consumer);
    }

}
