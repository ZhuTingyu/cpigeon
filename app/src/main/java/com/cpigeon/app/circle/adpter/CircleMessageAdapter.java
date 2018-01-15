package com.cpigeon.app.circle.adpter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseQuickAdapter;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.utils.Lists;
import com.cpigeon.app.view.ExpandTextView;
import com.cpigeon.app.view.PraiseListView;
import com.orhanobut.logger.Logger;
import com.stfalcon.frescoimageviewer.ImageViewer;

import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by Zhu TingYu on 2018/1/15.
 */

public class CircleMessageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public CircleMessageAdapter() {
        super(R.layout.item_pigeon_circle_message_layout, Lists.newArrayList());
    }

    @Override
    protected void convert(BaseViewHolder holder, String item) {
        holder.setGlideImageView(mContext, R.id.head_img, "http://img3.imgtn.bdimg.com/it/u=1611505379,380489200&fm=27&gp=0.jpg");
        holder.setText(R.id.user_name, "小朱");
        holder.setText(R.id.time,"1231-23-32");

        ExpandTextView content = holder.getView(R.id.content_text);
        RecyclerView imgs = holder.getView(R.id.imgsList);
        JZVideoPlayerStandard videoPlayer = holder.getView(R.id.videoplayer);

        if(holder.getAdapterPosition() == 0){
            content.setVisibility(View.VISIBLE);
            imgs.setVisibility(View.GONE);
            videoPlayer.setVisibility(View.GONE);

            content.setText("12312312312312312312q123123123" +
                    "123123123123" +
                    "12312312333333333333333333333333333333333333333333333333333");
        }else if(holder.getAdapterPosition() == 1){
            content.setVisibility(View.GONE);
            imgs.setVisibility(View.VISIBLE);
            videoPlayer.setVisibility(View.GONE);

            imgs.setLayoutManager(new GridLayoutManager(mContext, 3){
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            CircleMessageImgsAdpter adpter = new CircleMessageImgsAdpter();
            adpter.setNewData(Lists.newArrayList("", "",""));
            adpter.setOnItemClickListener((adapter, view, position) -> {
                showImageDialog(mContext, Lists.newArrayList("http://e.hiphotos.baidu.com/image/h%3D300/sign=8d3a9ea62c7f9e2f6f351b082f31e962/500fd9f9d72a6059099ccd5a2334349b023bbae5.jpg",
                        "http://e.hiphotos.baidu.com/image/h%3D300/sign=8d3a9ea62c7f9e2f6f351b082f31e962/500fd9f9d72a6059099ccd5a2334349b023bbae5.jpg",
                        "http://e.hiphotos.baidu.com/image/h%3D300/sign=8d3a9ea62c7f9e2f6f351b082f31e962/500fd9f9d72a6059099ccd5a2334349b023bbae5.jpg"),position);
            });
            imgs.setAdapter(adpter);

        }else {
            content.setVisibility(View.GONE);
            imgs.setVisibility(View.GONE);
            videoPlayer.setVisibility(View.VISIBLE);
        }

        ((PraiseListView) holder.getView(R.id.thumbs)).setDatas(Lists.newArrayList("","","",""));
    }

    public static void showImageDialog(Context context, List<String> list, int startPosition) {
        new ImageViewer.Builder<String>(context, list)
                .setStartPosition(startPosition)
                .hideStatusBar(false)
                .allowZooming(true)
                .allowSwipeToDismiss(true)
                //.setBackgroundColorRes(colorRes)
                //.setBackgroundColor(color)
                //.setImageMargin(margin)
                //.setImageMarginPx(marginPx)
                //.setContainerPadding(this, dimen)
                //.setContainerPadding(this, dimenStart, dimenTop, dimenEnd, dimenBottom)
                //.setContainerPaddingPx(padding)
                //.setContainerPaddingPx(start, top, end, bottom)
//                        .setCustomImageRequestBuilder(imageRequestBuilder)
//                        .setCustomDraweeHierarchyBuilder(draweeHierarchyBuilder)
//                        .setImageChangeListener(imageChangeListener)
//                        .setOnDismissListener(onDismissListener)
//                        .setOverlayView(overlayView)
                .show();
    }

}
