package com.cpigeon.app.modular.saigetong.view.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseQuickAdapter;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.entity.SGTDetailsInfoEntity;
import com.cpigeon.app.modular.saigetong.model.bead.SGTImgEntity;
import com.cpigeon.app.utils.DateUtils;
import com.cpigeon.app.utils.Lists;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017/12/5.
 */

public class ZHNumAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ZHNumAdapter() {
        super(R.layout.item_zh_num_2, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder holder, String item) {

        if(holder.getAdapterPosition() != 1){
            holder.setImageResource(R.id.time_line_top, R.mipmap.time_zhou_btm);
        }

        if(holder.getAdapterPosition() != mData.size()){
            holder.setImageResource(R.id.time_line_btm, R.mipmap.time_zhou_top);
        }

        holder.setText(R.id.tv_day, "28");
        holder.setText(R.id.tv_year, "2017.12");
        holder.setGlideImageViewNoRound(mContext, R.id.image, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1516716619334&di=b4ee96fab16bf2b67a3c590890360d2a&imgtype=0&src=http%3A%2F%2Fi.weather.com.cn%2Fimages%2Fcn%2Flife%2F2015%2F07%2F16%2F0F5E067214B0470161EFB374DB502C9A.jpg");
        holder.setText(R.id.image_type,"入棚拍照");
        holder.setText(R.id.remark,"124151231251314131");

    }

    /*public List<String> getImages(){
        for(SGTDetailsInfoEntity rpImages : mData){

        }
    }*/
}
