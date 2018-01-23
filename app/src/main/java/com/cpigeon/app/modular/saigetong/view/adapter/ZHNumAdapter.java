package com.cpigeon.app.modular.saigetong.view.adapter;

import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cpigeon.app.R;
import com.cpigeon.app.modular.saigetong.model.bead.SGTImgEntity;
import com.cpigeon.app.utils.DateUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017/12/5.
 */

public class ZHNumAdapter extends BaseQuickAdapter<SGTImgEntity, BaseViewHolder> {

    public ZHNumAdapter(List<SGTImgEntity> data) {
        super(R.layout.item_zh_num, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SGTImgEntity item) {
        helper.setText(R.id.it_sgt_time, DateUtils.getYMD(item.getSj()));
        helper.setText(R.id.it_sgt_tag, item.getTag());

        Picasso.with(mContext)
                .load(TextUtils.isEmpty(item.getSlturl()) ? "1" : item.getSlturl())
                .placeholder(R.mipmap.default_geyuntong)
                .error(R.mipmap.default_geyuntong)
                .resize(mContext.getResources().getDimensionPixelSize(R.dimen.gyt_monitor_img_wh), mContext.getResources().getDimensionPixelSize(R.dimen.gyt_monitor_img_wh))
                .centerCrop()
                .into((ImageView) helper.getView(R.id.it_sgt_img));
    }
}
