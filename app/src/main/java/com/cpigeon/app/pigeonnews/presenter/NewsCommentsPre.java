package com.cpigeon.app.pigeonnews.presenter;

import android.app.Activity;

import com.cpigeon.app.commonstandard.model.dao.IBaseDao;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.entity.CommentEntity;
import com.cpigeon.app.pigeonnews.NewsModel;
import com.cpigeon.app.utils.IntentBuilder;
import com.cpigeon.app.utils.Lists;
import com.cpigeon.app.utils.http.HttpErrorException;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by Zhu TingYu on 2018/1/11.
 */

public class NewsCommentsPre extends BasePresenter {

    String newsId;
    public int page = 1;
    public String commentId;
    public String content;

    public NewsCommentsPre(Activity activity) {
        super(activity);
        newsId = activity.getIntent().getStringExtra(IntentBuilder.KEY_DATA);
    }

    @Override
    protected IBaseDao initDao() {
        return null;
    }

    public void getNewsComments(Consumer<List<CommentEntity>> consumer) {
        submitRequestThrowError(NewsModel.getNewsComments(newsId,page).map(r -> {
            if(r.isOk()){
                if(r.status){
                    return r.data;
                }else return Lists.newArrayList();
            }else throw new HttpErrorException(r);
        }),consumer);
    }

    public void replyComment(Consumer<String> consumer){
        submitRequestThrowError(NewsModel.addReplyForNews(newsId,commentId,content).map(r -> {
            if(r.status){
                return r.msg;
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
}
