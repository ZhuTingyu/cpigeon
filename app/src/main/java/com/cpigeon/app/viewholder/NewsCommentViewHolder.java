package com.cpigeon.app.viewholder;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.entity.NewsDetailsEntity;
import com.cpigeon.app.pigeonnews.ui.InputCommentDialog;

/**
 * Created by Zhu TingYu on 2018/1/8.
 */

public class NewsCommentViewHolder extends BaseViewHolder {

    Activity activity;

    TextView input;
    TextView thumb;
    TextView comment;
    ImageView anonymous;

    public boolean isAnonymous = false;

    private OnViewClickListener listener;


    public NewsCommentViewHolder(View itemView,Activity activity) {
        super(itemView);
        input = getView(R.id.input);
        thumb = getView(R.id.thumb);
        comment = getView(R.id.comment);
        anonymous = getView(R.id.anonymous);
        this.activity = activity;
        bindUi();
    }

    private void bindUi() {
        input.setOnClickListener(v -> {
            InputCommentDialog dialog = new InputCommentDialog();
            dialog.setListener(content -> {
                listener.commentPushClick(content);
            });
            dialog.show(activity.getFragmentManager(),"InputComment");
        });

        thumb.setOnClickListener(v -> {
            listener.thumbClick();
        });

        comment.setOnClickListener(v -> {
            listener.commentClick();
        });

        anonymous.setOnClickListener(v -> {
            isAnonymous = !isAnonymous;
            anonymous.setImageResource(isAnonymous ? R.drawable.ic_anonymous_selected : R.drawable.ic_anonymous);
        });
    }

    public void bindData(NewsDetailsEntity entity) {
        thumb.setText(entity.priase);
        comment.setText("3");

        if(Integer.valueOf(thumb.getText().toString()) != 0){
            setViewDrawableLeft(thumb, R.mipmap.ic_thumbs_up);
        }

        if(Integer.valueOf(comment.getText().toString()) != 0){
            setViewDrawableLeft(comment, R.mipmap.ic_new_comment_select);
        }

    }

    public void onlyComment(){
        thumb.setVisibility(View.GONE);
        comment.setVisibility(View.GONE);
    }

    public interface OnViewClickListener {
        void commentPushClick(String content);

        void thumbClick();

        void commentClick();
    }

    public void setListener(OnViewClickListener listener) {
        this.listener = listener;
    }
}
