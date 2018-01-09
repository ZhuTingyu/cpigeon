package com.cpigeon.app.home;

import android.app.Activity;

import com.cpigeon.app.commonstandard.model.dao.IBaseDao;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.activity.IView;
import com.cpigeon.app.entity.HomeAdEntity;
import com.cpigeon.app.entity.NewsEntity;
import com.cpigeon.app.modular.home.model.bean.HomeAd;
import com.cpigeon.app.utils.http.HttpErrorException;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by Zhu TingYu on 2018/1/9.
 */

public class HomePre extends BasePresenter {
    public HomePre(Activity activity) {
        super(activity);
    }

    @Override
    protected IBaseDao initDao() {
        return null;
    }

    public void getHomeAd(Consumer<List<HomeAd>> consumer){
        submitRequest(HomeModel.getAd().map(homeAds -> {
            return homeAds;
        }),consumer,throwable -> {
            error.onNext(getErrorString(throwable.toString()));
        });
    }

    public void getHomeSpeedNews(Consumer<List<NewsEntity>> consumer){
        submitRequestThrowError(HomeModel.homeSpeedNews().map(r -> {
            if(r.status){
                return r.data;
            }else throw new HttpErrorException(r);
        }),consumer);
    }

}
