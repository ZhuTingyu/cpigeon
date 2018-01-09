package com.cpigeon.app.pigeonnews.adpter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.utils.Lists;
import com.cpigeon.app.view.LinearLayoutForRecyclerView;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/1/9.
 */

public class NewsCommentAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    ReplyAdapter replyAdapter;

    public NewsCommentAdapter() {
        super(R.layout.item_news_comment_layout, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder holder, String item) {
        holder.setSimpleImageView(R.id.icon, "http://img0.imgtn.bdimg.com/it/u=1018364764,1223529536&fm=27&gp=0.jpg");
        holder.setText(R.id.name, "小朱");
        holder.setText(R.id.time, "2分钟之前");

        TextView comment = holder.getView(R.id.comment);
        TextView thumb = holder.getView(R.id.thumb);

        comment.setText("20");
        thumb.setText("0");

        holder.setViewDrawableLeft(comment, R.mipmap.ic_new_comment_select);

        holder.setText(R.id.content, "al;sdkfjasdkl;fasdjklasdfjkasdf");

        LinearLayoutForRecyclerView list = holder.getView(R.id.reply_list);

        if(replyAdapter == null){
            replyAdapter = new ReplyAdapter(mContext);
        }

        replyAdapter.setData(Lists.newArrayList("123112323412", "12314123124"));

        list.setAdapter(replyAdapter);

    }
}
