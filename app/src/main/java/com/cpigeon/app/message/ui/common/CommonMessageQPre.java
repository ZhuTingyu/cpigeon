package com.cpigeon.app.message.ui.common;

import com.cpigeon.app.commonstandard.model.dao.IBaseDao;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.activity.IView;
import com.cpigeon.app.entity.CommonEntity;
import com.cpigeon.app.utils.databean.ApiResponse;
import com.cpigeon.app.utils.http.HttpErrorException;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Zhu TingYu on 2017/11/23.
 */

public class CommonMessageQPre extends BasePresenter{

    int userId = 123;

    public CommonMessageQPre(IView mView) {
        super(mView);
    }

    @Override
    protected IBaseDao initDao() {
        return null;
    }

    public void getCommonList(Consumer<List<CommonEntity>> consumer){
        submitRequestThrowError(CommonModel.getCommons(userId).map(r -> {
            if (r.isOk()){
                return r.data;
            }else throw new HttpErrorException(r);
        }),consumer);
    }
}
