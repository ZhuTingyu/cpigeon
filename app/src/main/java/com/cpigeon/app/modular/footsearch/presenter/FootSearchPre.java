package com.cpigeon.app.modular.footsearch.presenter;

import android.app.Activity;
import android.os.Handler;

import com.cpigeon.app.commonstandard.model.dao.IBaseDao;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.activity.IView;
import com.cpigeon.app.entity.FootInfoEntity;
import com.cpigeon.app.modular.footsearch.FootSearchModel;
import com.cpigeon.app.modular.footsearch.view.fragment.IFootSearchView;
import com.cpigeon.app.modular.footsearch.model.dao.ICpigeonServicesInfo;
import com.cpigeon.app.modular.footsearch.model.daoimpl.CpigeonServicesInfoImpl;
import com.cpigeon.app.modular.usercenter.model.bean.CpigeonUserServiceInfo;
import com.cpigeon.app.utils.CpigeonData;
import com.cpigeon.app.utils.http.HttpErrorException;

import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2017/4/8.
 */

public class FootSearchPre extends BasePresenter {

    String keyWord;
    public String year;
    int userId;


    public FootSearchPre(Activity activity) {
        super(activity);
        userId = CpigeonData.getInstance().getUserId(activity);
    }

    @Override
    protected IBaseDao initDao() {
        return null;
    }

    public void searchFoot(Consumer<List<FootInfoEntity>> consumer){
        submitRequestThrowError(FootSearchModel.searchFoot(keyWord,year,userId).map(r -> {
            if(r.status){
                return r.data;
            }else throw new HttpErrorException(r);
        }),consumer);
    }


    public Consumer<String> setKeyWord(){
        return s -> {
          keyWord = s;
        };
    }

}
