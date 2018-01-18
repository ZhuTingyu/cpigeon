package com.cpigeon.app.circle.adpter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseQuickAdapter;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.utils.Lists;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/1/18.
 */

public class CircleMessageDetailsCommentsAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public CircleMessageDetailsCommentsAdapter() {
        super(R.layout.item_circle_message_details_comment_layout, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder holder, String item) {
        holder.setGlideImageView(mContext, R.id.head_img, "http://img3.imgtn.bdimg.com/it/u=1611505379,380489200&fm=27&gp=0.jpg");
        holder.setText(R.id.user_name, "小朱");
        holder.setText(R.id.time,"1231-23-32");
        holder.setText(R.id.content, "123123123123");

        RecyclerView recyclerView = holder.getView(R.id.reply_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        MessageDetailsReplayAdapter adapter;
        if(recyclerView.getAdapter() != null){
            adapter = (MessageDetailsReplayAdapter) recyclerView.getAdapter();
        }else {
            adapter = new MessageDetailsReplayAdapter();
            recyclerView.setAdapter(adapter);
        }



    }
}
