package com.cpigeon.app.circle.adpter;

import android.support.v7.widget.AppCompatImageView;
import android.widget.LinearLayout;

import com.cpigeon.app.MyApp;
import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseQuickAdapter;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.entity.CircleMessageEntity;
import com.cpigeon.app.utils.Lists;
import com.cpigeon.app.utils.ScreenTool;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/1/15.
 */

public class CircleMessageImagesAdapter extends BaseQuickAdapter<CircleMessageEntity.PictureBean, BaseViewHolder> {

    int size;

    public CircleMessageImagesAdapter() {
        super(R.layout.item_one_image_layout, Lists.newArrayList());
        size = ((ScreenTool.getScreenWidth(MyApp.getInstance().getBaseContext()) - ScreenTool.dip2px(20)) / 3) - 10;
    }

    @Override
    protected void convert(BaseViewHolder holder, CircleMessageEntity.PictureBean item) {
        AppCompatImageView view = holder.getView(R.id.icon);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
        view.setLayoutParams(params);
        holder.setGlideImageView(mContext,R.id.icon, item.getUrl());
    }

    public List<String> getImagesUrl(){
        List<String> list = Lists.newArrayList();
        for (CircleMessageEntity.PictureBean bean : mData) {
            list.add(bean.getUrl());
        }
        return list;
    }

}
