package com.cpigeon.app.modular.matchlive.view.adapter;

import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cpigeon.app.R;
import com.cpigeon.app.modular.matchlive.model.bean.RaceImageOrVideo;
import com.github.vipulasri.timelineview.TimelineView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017/6/30.
 */

public class TimeLinePhotoAdapter extends BaseQuickAdapter<RaceImageOrVideo,BaseViewHolder> {
    public TimeLinePhotoAdapter(List<RaceImageOrVideo> data) {
        super(R.layout.item_car_photo,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RaceImageOrVideo item) {
        helper.setText(R.id.text_timeline_date,item.getTime());
        helper.setText(R.id.text_timeline_title,item.getTag());
        ((TimelineView)helper.getView(R.id.time_marker)).setMarker(ContextCompat.getDrawable(mContext, R.drawable.ic_marker), ContextCompat.getColor(mContext, R.color.colorPrimary));
        Picasso.with(mContext)
                .load(TextUtils.isEmpty(item.getThumburl())?item.getUrl():item.getThumburl())
                .placeholder(R.mipmap.head_image_default)
                .error(R.mipmap.head_image_default)
                .into((ImageView) helper.getView(R.id.tv_timeline_img));
    }
}
