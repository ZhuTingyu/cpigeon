package com.cpigeon.app.pigeonnews.adpter;

import android.view.View;
import android.widget.TextView;

import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseQuickAdapter;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.entity.NewsCommentEntity;
import com.cpigeon.app.pigeonnews.presenter.NewsCommentsPre;
import com.cpigeon.app.utils.DateTool;
import com.cpigeon.app.utils.Lists;
import com.cpigeon.app.view.LinearLayoutForRecyclerView;

/**
 * Created by Zhu TingYu on 2018/1/9.
 */

public class NewsCommentAdapter extends BaseQuickAdapter<NewsCommentEntity, BaseViewHolder> {


    private OnCommunicationListener listener;

    NewsCommentsPre mPresenter;

    public NewsCommentAdapter(NewsCommentsPre commentsPre) {
        super(R.layout.item_news_comment_layout, Lists.newArrayList());
        mPresenter = commentsPre;
    }

    @Override
    protected void convert(BaseViewHolder holder, NewsCommentEntity item) {
        holder.setSimpleImageView(R.id.icon, item.headurl);
        holder.setText(R.id.name, item.nicheng);
        holder.setText(R.id.time, DateTool.format(item.time, DateTool.FORMAT_DATETIME));

        TextView comment = holder.getView(R.id.comment);
        TextView thumb = holder.getView(R.id.thumb);

        comment.setOnClickListener(v -> {
            listener.comment(item, holder.getAdapterPosition());
        });

        thumb.setOnClickListener(v -> {
            listener.thumb(item, holder.getAdapterPosition());
        });

        comment.setText(String.valueOf(item.replycount));
        thumb.setText(String.valueOf(item.dianzan));

        if (item.isreply) {
            holder.setViewDrawableLeft(comment, R.mipmap.ic_new_comment_select);
            comment.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        }else {
            holder.setViewDrawableLeft(comment, R.mipmap.ic_new_comment);
            comment.setTextColor(mContext.getResources().getColor(R.color.text_color_4d4d4d));
        }

        if(item.isThumb()){
            holder.setViewDrawableLeft(thumb, R.mipmap.ic_thumbs_up);
            thumb.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        }else {
            thumb.setTextColor(mContext.getResources().getColor(R.color.text_color_4d4d4d));
            holder.setViewDrawableLeft(thumb, R.mipmap.ic_thumbs_not_up);
        }

        holder.setText(R.id.content, item.content);

        LinearLayoutForRecyclerView list = holder.getView(R.id.reply_list);

        ReplyAdapter replyAdapter = new ReplyAdapter(mContext);

        if(item.reply != null && !item.reply.isEmpty()){
            holder.setViewVisible(R.id.img1,View.VISIBLE);
            list.setVisibility(View.VISIBLE);
            replyAdapter.setData(item.reply);
            replyAdapter.setOnItemReplyClickListenerListener((entity, position, content,dialog) -> {
                mPresenter.commentId = item.id;
                mPresenter.content = content;
                mPresenter.replyId = entity.cid;
                mPresenter.replyComment(s -> {
                    item.reply.add(position + 1, replyAdapter.getNewEntity(position, content));
                    dialog.closeDialog();
                    item.replycount += 1;
                    notifyItemChanged(holder.getAdapterPosition());
                });
            });
            list.setAdapter(replyAdapter);
        }else {
            holder.setViewVisible(R.id.img1,View.GONE);
            list.setVisibility(View.GONE);
        }

    }

    @Override
    protected String getEmptyViewText() {
        return "暂时没有评论";
    }

    public interface OnCommunicationListener{
        void thumb(NewsCommentEntity entity, int position);
        void comment(NewsCommentEntity entity, int position);
    }

    public void setListener(OnCommunicationListener listener) {
        this.listener = listener;
    }
}
