package com.cpigeon.app.modular.matchlive.view.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cpigeon.app.R;
import com.cpigeon.app.modular.matchlive.model.bean.RaceImageOrVideo;
import com.squareup.picasso.Picasso;

import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;


/**
 * 鸽运通视频
 * Created by Administrator on 2017/6/16.
 */

public class CarVideoAdapter extends BaseQuickAdapter<RaceImageOrVideo,BaseViewHolder> {
    public CarVideoAdapter(List<RaceImageOrVideo> data) {
        super(R.layout.item_car_video,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RaceImageOrVideo item) {
        ((JZVideoPlayerStandard)helper.getView(R.id.videoplayer))
                .setUp(item.getUrl(), JZVideoPlayerStandard.SCREEN_STATE_OFF, item.getTag());
        Picasso.with(mContext).load(item.getThumburl())
                .into(((JZVideoPlayerStandard)helper.getView(R.id.videoplayer)).thumbImageView);
    }
}
