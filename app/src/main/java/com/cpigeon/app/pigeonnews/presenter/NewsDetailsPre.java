package com.cpigeon.app.pigeonnews.presenter;

import android.app.Activity;

import com.cpigeon.app.commonstandard.model.dao.IBaseDao;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.entity.NewsDetailsEntity;
import com.cpigeon.app.entity.ThumbEntity;
import com.cpigeon.app.pigeonnews.NewsModel;
import com.cpigeon.app.utils.IntentBuilder;
import com.cpigeon.app.utils.http.HttpErrorException;


import io.reactivex.functions.Consumer;

/**
 * Created by Zhu TingYu on 2018/1/9.
 */

public class NewsDetailsPre extends BasePresenter {

    public String newsId;
    public String content;
    public NewsDetailsEntity newsDetailsEntity;

    public NewsDetailsPre(Activity activity) {
        super(activity);
        newsId = activity.getIntent().getStringExtra(IntentBuilder.KEY_DATA);
    }

    @Override
    protected IBaseDao initDao() {
        return null;
    }

    public void getNewsDetails(Consumer<NewsDetailsEntity> consumer){
        submitRequestThrowError(NewsModel.newsDetails(newsId).map(r -> {
            if(r.status){
                newsDetailsEntity = r.data;
                return newsDetailsEntity;
            }else throw new HttpErrorException(r);
        }),consumer);
    }

    public void addNewsComment(Consumer<String> consumer){
        submitRequestThrowError(NewsModel.addNewsComments(newsId, content).map(r -> {
            if(r.status){
                return r.msg;
            }else throw new HttpErrorException(r);
        }),consumer);
    }

    public void thumbNews(Consumer<ThumbEntity> consumer){
        submitRequestThrowError(NewsModel.newsThumb(newsId).map(r -> {
            if(r.status){
                return r.data;
            }else throw new HttpErrorException(r);
        }),consumer);
    }

}
