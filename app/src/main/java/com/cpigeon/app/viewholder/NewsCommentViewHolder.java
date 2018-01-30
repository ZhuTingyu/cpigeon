package com.cpigeon.app.viewholder;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cpigeon.app.MyApp;
import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.entity.NewsDetailsEntity;
import com.cpigeon.app.pigeonnews.ui.InputCommentDialog;
import com.cpigeon.app.utils.CommonTool;
import com.cpigeon.app.utils.IntentBuilder;
import com.cpigeon.app.utils.http.CommonUitls;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

/**
 * Created by Zhu TingYu on 2018/1/8.
 */

public class NewsCommentViewHolder extends BaseViewHolder {

    Activity activity;

    TextView input;
    TextView thumb;
    TextView comment;

    ImageView imgThumb;


    private OnViewClickListener listener;
    public InputCommentDialog dialog;
    Animation animation;



    public NewsCommentViewHolder(View itemView, Activity activity) {
        super(itemView);
        input = getView(R.id.input);
        thumb = getView(R.id.thumb);
        comment = getView(R.id.comment);
        imgThumb = getView(R.id.image_thumb);
        this.activity = activity;
        animation = AnimationUtils.loadAnimation(MyApp.getInstance().getBaseContext(), R.anim.anim_sign_box_rock);
        bindUi();
    }

    private void bindUi() {
        input.setOnClickListener(v -> {
            dialog = new InputCommentDialog();
            dialog.setHint("我的评论更精彩！");
            dialog.setPushClickListener(content -> {
                listener.commentPushClick(content);
            });
            dialog.show(activity.getFragmentManager(), "InputComment");
        });

        getView(R.id.ll_thumb).setOnClickListener(v -> {
            listener.thumbClick();
        });

        comment.setOnClickListener(v -> {
            listener.commentClick();
        });

    }

    public void bindData(NewsDetailsEntity entity, boolean isFirst) {
        thumb.setText(String.valueOf(entity.priase));
        comment.setText(String.valueOf(entity.count));

        if (entity.ispl) {
            setViewDrawableLeft(comment, R.mipmap.ic_new_comment_select);
            comment.setTextColor(activity.getResources().getColor(R.color.colorPrimary));
        }else {
            setViewDrawableLeft(comment, R.mipmap.ic_new_comment);
            comment.setTextColor(activity.getResources().getColor(R.color.text_color_4d4d4d));
        }

        if(entity.isThumb()){
            thumb.setTextColor(activity.getResources().getColor(R.color.colorPrimary));
            imgThumb.setImageResource(R.mipmap.ic_thumbs_up);
            if(!isFirst){
                imgThumb.startAnimation(animation);
            }
        }else {
            thumb.setTextColor(activity.getResources().getColor(R.color.text_color_4d4d4d));
            imgThumb.setImageResource(R.mipmap.ic_thumbs_not_up);
        }

        if(!entity.iscomment){
            onlyComment();
        }


    }


    public void onlyComment() {
        thumb.setVisibility(View.GONE);
        imgThumb.setVisibility(View.GONE);
        comment.setVisibility(View.GONE);
    }

    public interface OnViewClickListener {
        void commentPushClick(EditText content);

        void thumbClick();

        void commentClick();

    }

    public void setListener(OnViewClickListener listener) {
        this.listener = listener;
    }
}
