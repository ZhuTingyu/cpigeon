package com.cpigeon.app.modular.saigetong.view.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseQuickAdapter;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.entity.SGTDetailsInfoEntity;
import com.cpigeon.app.modular.saigetong.model.bead.SGTImgEntity;
import com.cpigeon.app.utils.DateTool;
import com.cpigeon.app.utils.DateUtils;
import com.cpigeon.app.utils.Lists;
import com.cpigeon.app.utils.StringValid;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017/12/5.
 */

public class ZHNumAdapter extends BaseQuickAdapter<SGTDetailsInfoEntity.RPImages, BaseViewHolder> {

    public ZHNumAdapter() {
        super(R.layout.item_zh_num_2, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder holder, SGTDetailsInfoEntity.RPImages item) {

        if(holder.getAdapterPosition() != 1){
            holder.setImageResource(R.id.time_line_top, R.mipmap.time_zhou_btm);
        }

        if(holder.getAdapterPosition() != mData.size()){
            holder.setImageResource(R.id.time_line_btm, R.mipmap.time_zhou_top);
        }

        long time = item.getSj();

        holder.setText(R.id.tv_day, DateTool.format(time*1000, DateTool.FORMAT_DD));
        holder.setText(R.id.tv_year, DateTool.format(time+1000, DateTool.FORMAT_YYYY_MM2));
        holder.setGlideImageViewNoRound(mContext, R.id.image, item.getImgurl());
        holder.setText(R.id.image_type,item.getTag());

        if(StringValid.isStringValid(item.getUpdatefootinfo())){
            holder.setText(R.id.remark,item.getUpdatefootinfo());
            holder.setViewVisible(R.id.remark, View.VISIBLE);
        }else {
            holder.setViewVisible(R.id.remark, View.GONE);
        }
    }

    public List<String> getImages(){
        List<String> images = Lists.newArrayList();
        for(SGTDetailsInfoEntity.RPImages rpImages : mData){
            images.add(rpImages.getImgurl());
        }
        return images;
    }
}
