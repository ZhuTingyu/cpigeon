package com.cpigeon.app.pigeonnews.adpter;

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

        comment.setText(item.replycount);
        thumb.setText(item.dianzan);

        holder.setViewDrawableLeft(thumb, Integer.valueOf(item.dianzan) == 0 ? R.mipmap.ic_thumbs_not_up : R.mipmap.ic_thumbs_up);
        holder.setViewDrawableLeft(comment,Integer.valueOf(item.replycount) == 0 ? R.mipmap.ic_new_comment : R.mipmap.ic_new_comment_select);

        holder.setText(R.id.content, item.content);

        LinearLayoutForRecyclerView list = holder.getView(R.id.reply_list);

        if(replyAdapter == null){
            replyAdapter = new ReplyAdapter(mContext);
        }

        if(item.reply != null && !item.reply.isEmpty()){
            replyAdapter.setData(item.reply);
            list.setAdapter(replyAdapter);
        }

    }

    @Override
    protected String getEmptyViewText() {
        return "暂时没有评论";
    }
}
