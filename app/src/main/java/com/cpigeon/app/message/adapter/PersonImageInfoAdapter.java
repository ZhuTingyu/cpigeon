package com.cpigeon.app.message.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.utils.Lists;
import com.cpigeon.app.utils.ScreenTool;

import java.util.List;

/**
 * Created by Zhu TingYu on 2017/11/22.
 */

public class PersonImageInfoAdapter extends BaseQuickAdapter<String, BaseViewHolder>{

    List<Integer> icList = Lists.newArrayList(R.mipmap.ic_positive
            , R.mipmap.ic_positive_wrong_side, R.mipmap.ic_business_license);

    public int size;

    public PersonImageInfoAdapter(Context context) {
        super(R.layout.item_pigeon_message_home_layout, Lists.newArrayList());
        size = ScreenTool.getScreenWidth(context) / 2 - ScreenTool.dip2px(32);
    }

    @Override
    protected void convert(BaseViewHolder holder, String item) {
        holder.findViewById(R.id.title).setVisibility(View.GONE);
        AppCompatImageView imageView = holder.findViewById(R.id.icon);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, (int) (size * 0.6));
        imageView.setLayoutParams(params);
        imageView.setBackgroundResource(icList.get(holder.getAdapterPosition() - 1));
    }
}
