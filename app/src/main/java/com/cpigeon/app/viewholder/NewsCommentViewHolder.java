package com.cpigeon.app.viewholder;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.pigeonnews.ui.InputCommentDialog;

/**
 * Created by Zhu TingYu on 2018/1/8.
 */

public class NewsCommentViewHolder extends BaseViewHolder {

    TextView input;
    TextView thumb;
    TextView comment;
    TextView anonymous;

    public boolean isAnonymous = false;

    private OnViewClickListener listener;


    public NewsCommentViewHolder(View itemView, Activity activity) {
        super(itemView);
        input = getView(R.id.input);
        thumb = getView(R.id.thumb);
        comment = getView(R.id.comment);
        anonymous = getView(R.id.anonymous);
        bindUi();
    }

    private void bindUi() {
        input.setOnClickListener(v -> {
            listener.inputClick();
        });

        thumb.setOnClickListener(v -> {
            listener.thumbClick();
        });

        comment.setOnClickListener(v -> {
            listener.commentClick();
        });

        anonymous.setOnClickListener(v -> {
            isAnonymous = !isAnonymous;
            setViewDrawableLeft(anonymous, isAnonymous ? R.drawable.ic_anonymous_selected : R.drawable.ic_anonymous);
            listener.anonymousClick();
        });
    }

    private void bindData() {
        thumb.setText("32");
        comment.setText("0");

        if(Integer.valueOf(thumb.getText().toString()) != 0){
            setViewDrawableLeft(thumb, R.mipmap.ic_thumbs_up);
        }

        if(Integer.valueOf(comment.getText().toString()) != 0){
            setViewDrawableLeft(comment, R.mipmap.ic_new_comment_select);
        }

    }

    public interface OnViewClickListener {
        void inputClick();

        void thumbClick();

        void commentClick();

        void anonymousClick();
    }

}
