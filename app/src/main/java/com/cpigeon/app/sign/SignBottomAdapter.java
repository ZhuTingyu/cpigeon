package com.cpigeon.app.sign;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cpigeon.app.MyApp;
import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.entity.MultiSelectEntity;
import com.cpigeon.app.utils.Lists;
import com.cpigeon.app.utils.ScreenTool;

import java.util.List;

import pl.droidsonroids.gif.GifImageView;

/**
 * Created by Zhu TingYu on 2017/12/28.
 */

public class SignBottomAdapter extends BaseQuickAdapter<MultiSelectEntity, BaseViewHolder> {

    List<Integer> icons;
    int size;
    private static final int GIF_SIZE = 4;
    Animation animation;

    public SignBottomAdapter() {
        super(R.layout.item_sign_bottom_layout, Lists.newArrayList());
        icons = Lists.newArrayList(R.mipmap.ic_gray_box_1
                , R.mipmap.ic_gray_box_2
                , R.mipmap.ic_gray_box_3
                , R.mipmap.ic_gray_box_4);
        size = ScreenTool.getScreenWidth(MyApp.getInstance().getBaseContext()) / 4;
        animation = AnimationUtils.loadAnimation(MyApp.getInstance().getBaseContext(), R.anim.anim_sign_box_rock);

    }

    @Override
    protected void convert(BaseViewHolder holder, MultiSelectEntity item) {

        ImageView imageView = holder.getView(R.id.img);

        holder.itemView.setLayoutParams(new RelativeLayout.LayoutParams(size, ViewGroup.LayoutParams.MATCH_PARENT));
        int ImgSize = 0;
        int imgId = 0;
        switch (holder.getAdapterPosition()) {
            case 0:
                imgId = R.mipmap.ic_blue_box_1;
                ImgSize = 20 + GIF_SIZE;
                break;
            case 1:
                imgId = R.mipmap.ic_blue_box_2;
                ImgSize = 24 + GIF_SIZE;
                break;
            case 2:
                imgId = R.mipmap.ic_blue_box_3;
                ImgSize = 28 + GIF_SIZE;
                break;
            case 3:
                imgId = R.mipmap.ic_blue_box_4;
                ImgSize = 32 + GIF_SIZE;
                break;
        }
        imageView.setLayoutParams(new RelativeLayout.LayoutParams(ScreenTool.dip2px(ImgSize),ScreenTool.dip2px(ImgSize)));
        if(item.isChoose){
            holder.setImageResource(R.id.img, imgId);
            holder.itemView.setOnClickListener(v -> {
                imageView.startAnimation(animation);
            });
        }else {
            holder.setImageResource(R.id.img, icons.get(holder.getAdapterPosition()));
        }
    }
}

