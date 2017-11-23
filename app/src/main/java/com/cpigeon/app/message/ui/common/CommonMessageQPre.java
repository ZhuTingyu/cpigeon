package com.cpigeon.app.message.ui.common;

import com.cpigeon.app.commonstandard.model.dao.IBaseDao;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.activity.IView;
import com.cpigeon.app.entity.CommonEntity;
import com.cpigeon.app.utils.Lists;
import com.cpigeon.app.utils.databean.ApiResponse;
import com.cpigeon.app.utils.http.HttpErrorException;

import java.util.List;


import io.reactivex.functions.Consumer;

/**
 * Created by Zhu TingYu on 2017/11/23.
 */

public class CommonMessageQPre extends BasePresenter{

    public int userId;

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
                if(r.isNotDate()){
                    return Lists.newArrayList();
                }else {
                    return r.data;
                }
            }else throw new HttpErrorException(r);
        }),consumer);
    }
}
