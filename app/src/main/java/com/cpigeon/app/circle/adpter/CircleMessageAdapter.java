package com.cpigeon.app.circle.adpter;

import android.content.Context;
import android.os.Build;
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

    List<String> imgsData = Lists.newArrayList("http://imgsrc.baidu.com/imgad/pic/item/6d81800a19d8bc3e3bad2adf888ba61ea8d34579.jpg"
            ,"http://img0.imgtn.bdimg.com/it/u=3069472720,3661376600&fm=214&gp=0.jpg"
            , "http://imgsrc.baidu.com/imgad/pic/item/7c1ed21b0ef41bd5af9a14e85bda81cb38db3de4.jpg"
            ,"http://imgsrc.baidu.com/imgad/pic/item/5bafa40f4bfbfbed57e3ef5673f0f736afc31f65.jpg");

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
            adpter.setNewData(imgsData);
            adpter.setOnItemClickListener((adapter, view, position) -> {
                showImageDialog(mContext, imgsData,position);
            });
            imgs.setAdapter(adpter);
            imgs.setFocusableInTouchMode(false);
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
