package com.cpigeon.app.circle.presenter;

import android.app.Activity;

import com.cpigeon.app.circle.CircleModel;
import com.cpigeon.app.commonstandard.model.dao.IBaseDao;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseFragment;
import com.cpigeon.app.entity.CircleMessageEntity;
import com.cpigeon.app.utils.CpigeonData;
import com.cpigeon.app.utils.IntentBuilder;
import com.cpigeon.app.utils.Lists;
import com.cpigeon.app.utils.http.HttpErrorException;

import java.util.List;

import io.reactivex.functions.Consumer;


/**
 * Created by Zhu TingYu on 2018/1/18.
 */

public class CircleMessagePre extends BasePresenter {

    int userId;
    public int page = 1;
    public String type;
    public int followId;
    private int isFollow; //1关注，0取消关注
    private int isThumb; //1点赞，0取消点赞
    public int messageId;
    public String commentContent;
    public int commentId = 0;

    public CircleMessagePre(BaseFragment fragment) {
        super(fragment);
        userId = CpigeonData.getInstance().getUserId(fragment.getActivity());
        if(fragment.getArguments() != null){
            type = fragment.getArguments().getString(IntentBuilder.KEY_TYPE);
        }
        messageId = fragment.getActivity().getIntent().getIntExtra(IntentBuilder.KEY_DATA, 0);
    }

    @Override
    protected IBaseDao initDao() {
        return null;
    }

    public void getMessageList(Consumer<List<CircleMessageEntity>> consumer){
        submitRequestThrowError(CircleModel.circleMessage(userId, type, messageId, page,10).map(r -> {
            if(r.isOk()){
                if(r.status){
                    return r.data;
                }else return Lists.newArrayList();
            }else throw new HttpErrorException(r);
        }),consumer);
    }

    public void setFollow(Consumer<String> consumer){
        submitRequestThrowError(CircleModel.circleFollow(userId, followId, isFollow).map(r -> {
            if(r.status){
                return r.msg;
            }else throw new HttpErrorException(r);
        }),consumer);
    }

    public void setThumb(Consumer<String> consumer){
        submitRequestThrowError(CircleModel.circleMessageThumbUp(userId, messageId, isThumb).map(r -> {
            if(r.status){
                return r.msg;
            }else throw new HttpErrorException(r);
        }),consumer);
    }

    public void addComment(Consumer<CircleMessageEntity.CommentListBean> consumer){
        submitRequestThrowError(CircleModel.addCircleMessageComment(userId, messageId, commentContent,commentId).map(r -> {
            if(r.status){
                return r.data;
            }else throw new HttpErrorException(r);
        }),consumer);
    }

    public void setIsFollow(boolean isFollow) {
        this.isFollow = isFollow ? 1 : 0;
    }

    public void setIsThumb(boolean isThumb) {
        this.isThumb = isThumb ? 1 : 0;
    }

    public int getUserThumbPosition(List<CircleMessageEntity.PraiseListBean> list, int userId){
        int position = -1;
        for(int i = 0,len = list.size(); i < len; i++){
            CircleMessageEntity.PraiseListBean praiseListBean = list.get(i);
            if(praiseListBean.getUid() == userId){
                position = i;
                break;
            }
        }
        return position;
    }

}
