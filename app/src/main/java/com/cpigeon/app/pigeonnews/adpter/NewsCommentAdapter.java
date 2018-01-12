package com.cpigeon.app.pigeonnews.adpter;

import android.view.View;
import android.widget.TextView;

import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseQuickAdapter;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.entity.CommentEntity;
import com.cpigeon.app.utils.DateTool;
import com.cpigeon.app.utils.Lists;
import com.cpigeon.app.view.LinearLayoutForRecyclerView;

/**
 * Created by Zhu TingYu on 2018/1/9.
 */

public class NewsCommentAdapter extends BaseQuickAdapter<CommentEntity, BaseViewHolder> {

    ReplyAdapter replyAdapter;

    private OnCommunicationListener listener;

    public NewsCommentAdapter() {
        super(R.layout.item_news_comment_layout, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder holder, CommentEntity item) {
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

        holder.setViewDrawableLeft(thumb, item.dianzan == 0 ? R.mipmap.ic_thumbs_not_up : R.mipmap.ic_thumbs_up);
        holder.setViewDrawableLeft(comment,item.replycount == 0 ? R.mipmap.ic_new_comment : R.mipmap.ic_new_comment_select);

        comment.setTextColor(item.replycount == 0 ? mContext.getResources().getColor(R.color.text_color_4d4d4d) : mContext.getResources().getColor(R.color.colorPrimary));
        thumb.setTextColor(item.dianzan == 0 ? mContext.getResources().getColor(R.color.text_color_4d4d4d) : mContext.getResources().getColor(R.color.colorPrimary));

        holder.setText(R.id.content, item.content);

        LinearLayoutForRecyclerView list = holder.getView(R.id.reply_list);

        if(replyAdapter == null){
            replyAdapter = new ReplyAdapter(mContext);
        }

        if(item.reply != null && !item.reply.isEmpty()){
            list.setVisibility(View.VISIBLE);
            replyAdapter.setData(item.reply);
            list.setAdapter(replyAdapter);
        }else {
            list.setVisibility(View.GONE);
        }

    }

    @Override
    protected String getEmptyViewText() {
        return "暂时没有评论";
    }

    public interface OnCommunicationListener{
        void thumb(CommentEntity entity, int position);
        void comment(CommentEntity entity, int position);
    }

    public void setListener(OnCommunicationListener listener) {
        this.listener = listener;
    }
}
