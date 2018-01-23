package com.cpigeon.app.home.adpter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cpigeon.app.MyApp;
import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.utils.Lists;
import com.cpigeon.app.utils.ScreenTool;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/1/2.
 */

public class HomeLeadAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    List<Integer> icons = Lists.newArrayList(R.drawable.ic_home_pigeon_match_t,
            R.drawable.ic_match_control,
            R.drawable.ic_pigeon_message,
            R.drawable.ic_foot_search);

    int imgSize;


    public HomeLeadAdapter() {
        super(R.layout.item_pigeon_message_home_layout
                , Lists.newArrayList("公棚赛鸽", "比赛监控", "鸽信通", "足环查询")
        );


        imgSize = (ScreenTool.getScreenWidth(MyApp.getInstance().getBaseContext()) / 4)
                - ScreenTool.dip2px(40);
    }

    @Override
    protected void convert(BaseViewHolder holder, String item) {
        TextView title = holder.getView(R.id.title);
        title.setTextSize(12);
        title.setTextColor(mContext.getResources().getColor(R.color.black));

        holder.setText(R.id.title, item);

        holder.setViewSize(R.id.icon, imgSize, imgSize);
        holder.setIconView(R.id.icon, icons.get(holder.getAdapterPosition()));

    }
}
