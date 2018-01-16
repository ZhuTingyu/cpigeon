package com.cpigeon.app.viewholder;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.EditText;

import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.pigeonnews.ui.InputCommentDialog;
import com.wx.goodview.GoodView;

/**
 * Created by Zhu TingYu on 2018/1/16.
 */

public class SocialSnsViewHolder extends BaseViewHolder{

    AppCompatImageView thumb;
    AppCompatImageView comment;
    AppCompatImageView share;

    OnSocialListener listener;

    GoodView goodView;

    Activity activity;

    String hint;

    public interface OnSocialListener{
        void thumb(View imageView);
        void comment(EditText editText);
        void share(View imageView);
    }

    public SocialSnsViewHolder(Activity activity, View itemView, GoodView goodView, String hint) {
        super(itemView);
        this.activity = activity;
        this.goodView = goodView;
        this.hint = hint;
        thumb = getView(R.id.thumb);
        comment = getView(R.id.comment);
        share = getView(R.id.share);
        bindUi();
    }

    private void bindUi() {
        thumb.setOnClickListener(v -> {
            listener.thumb(v);
        });

        comment.setOnClickListener(v -> {
            InputCommentDialog dialog = new InputCommentDialog();
            dialog.setHint(hint);
            dialog.setPushClickListener(content -> {
                listener.comment(content);
            });
            dialog.show(activity.getFragmentManager(), "InputComment");
        });

        share.setOnClickListener(v -> {
            listener.share(v);
        });
    }

    public void setOnSocialListener(OnSocialListener listener) {
        this.listener = listener;
    }

    public void setThumb(boolean isThumb){

        setImageDrawable(R.id.thumb, isThumb ? getDrawable(R.mipmap.ic_thumbs_up) : getDrawable(R.mipmap.ic_thumbs_not_up));

        if(isThumb){
            goodView.setImage(getDrawable(R.mipmap.ic_thumbs_up));
        }else {
            goodView.setImage(getDrawable(R.mipmap.ic_thumbs_not_up));
        }
        goodView.show(thumb);
    }

    public void setComment(boolean isComment){
        setImageDrawable(R.id.thumb, isComment ? getDrawable(R.mipmap.ic_new_comment_select) : getDrawable(R.mipmap.ic_new_comment));
    }
}
