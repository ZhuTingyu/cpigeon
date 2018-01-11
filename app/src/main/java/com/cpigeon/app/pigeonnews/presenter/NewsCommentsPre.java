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

    String newId;
    public int page = 1;

    public NewsCommentsPre(Activity activity) {
        super(activity);
        newId = activity.getIntent().getStringExtra(IntentBuilder.KEY_DATA);
    }

    @Override
    protected IBaseDao initDao() {
        return null;
    }

    public void getNewsComments(Consumer<List<CommentEntity>> consumer) {
        submitRequestThrowError(NewsModel.getNewsComments(newId,page).map(r -> {
            if(r.isOk()){
                if(r.status){
                    return r.data;
                }else return Lists.newArrayList();
            }else throw new HttpErrorException(r);
        }),consumer);
    }
}
