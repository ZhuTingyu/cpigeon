package com.cpigeon.app.circle.adpter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;

import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseQuickAdapter;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.entity.CircleMessageEntity;
import com.cpigeon.app.utils.Lists;
import com.cpigeon.app.utils.StringSpanUtil;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/1/18.
 */

public class CircleMessageDetailsCommentsAdapter extends BaseQuickAdapter<CircleMessageEntity.CommentListBean, BaseViewHolder> {

    public CircleMessageDetailsCommentsAdapter() {
        super(R.layout.item_circle_message_details_comment_layout, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder holder, CircleMessageEntity.CommentListBean item) {
        holder.setGlideImageView(mContext, R.id.head_img, item.getUser().getHeadimgurl());
        holder.setText(R.id.user_name, item.getUser().getNickname());
        holder.setText(R.id.time,item.getTime());

        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(StringSpanUtil.setClickableSpan(item.getUser().getNickname(), R.color.colorPrimary));

        if(item.getTouser() == null){
            builder.append(":");
        }else {
            builder.append(" 回复 ");
            builder.append(StringSpanUtil.setClickableSpan(item.getTouser().getNickname(), R.color.colorPrimary));
            builder.append(":");
        }
        builder.append(item.getContent());



        holder.setText(R.id.content, builder);



    }
}
