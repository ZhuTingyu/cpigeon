package com.cpigeon.app.circle.adpter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.cpigeon.app.MyApp;
import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.entity.ChooseImageEntity;
import com.cpigeon.app.utils.ChooseImageManager;
import com.cpigeon.app.utils.Lists;
import com.cpigeon.app.utils.ScreenTool;
import com.cpigeon.app.utils.StringValid;
import com.cpigeon.app.view.SingleSelectCenterDialog;
import com.luck.picture.lib.config.PictureMimeType;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/1/16.
 */

public class ChooseImageAdapter extends BaseMultiItemQuickAdapter<ChooseImageEntity, BaseViewHolder> {

    private int maxChoose = 9;

    public static final int TYPE_VIDEO = 1;
    public static final int TYPE_PICTURE = 2;

    int type;
    SingleSelectCenterDialog publishTypeDialog;
    int size;


    public ChooseImageAdapter(Activity activity) {
        super(Lists.newArrayList());
        addItemType(ChooseImageEntity.TYPE_IMG, R.layout.item_choose_image_layout);
        addItemType(ChooseImageEntity.TYPE_ADD, R.layout.item_choose_image_layout);
        SingleSelectCenterDialog.OnItemClickListener onItemClickListener = new SingleSelectCenterDialog.OnItemClickListener() {
            @Override
            public void onItemClick(SingleSelectCenterDialog dialog, SingleSelectCenterDialog.SelectItem item) {
                if (item != null) {
                    if (item.getText().equals("选择图片")) {
                        ChooseImageManager.showChooseImage(activity, PictureMimeType.ofImage(), maxChoose - getImgs().size());
                        setType(TYPE_PICTURE);
                        maxChoose = 9;
                    } else {
                        ChooseImageManager.showChooseImage(activity, PictureMimeType.ofVideo(), maxChoose - getImgs().size());
                        setType(TYPE_VIDEO);
                        maxChoose = 1;
                    }
                    dialog.dismiss();

                }
            }
        };
        publishTypeDialog = new SingleSelectCenterDialog
                .Builder(activity)
                .addSelectItem("选择图片", onItemClickListener)
                .addSelectItem("选择视频", onItemClickListener)
                .create();

        size = ((ScreenTool.getScreenWidth(MyApp.getInstance().getBaseContext()) - ScreenTool.dip2px(20)) / 3) - 10;
    }

    @Override
    protected void convert(BaseViewHolder holder, ChooseImageEntity item) {

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(size, size);
        holder.itemView.setLayoutParams(params);

        switch (holder.getItemViewType()) {

            case ChooseImageEntity.TYPE_IMG:

                if(type == TYPE_PICTURE){
                    holder.setGlideImageView(mContext,R.id.image, item.url);
                }else {
                    Glide.with(mContext).load(item.url).thumbnail(0.5f).into((ImageView) holder.getView(R.id.image));
                }

                holder.getView(R.id.ll_del).setOnClickListener(v -> {
                    remove(holder.getAdapterPosition());
                    if(mData.get(mData.size() - 1).getItemType() != ChooseImageEntity.TYPE_ADD){
                        setAddImage();
                    }
                });
                break;

            case ChooseImageEntity.TYPE_ADD:
                holder.setViewVisible(R.id.ll_del, View.GONE);
                holder.setImageResource(R.id.image, R.mipmap.ic_add_img);
                holder.itemView.setOnClickListener(v -> {
                    publishTypeDialog.show();
                });
                break;
        }
    }

    public List<String> getImgs(){
        List<String> imgs = Lists.newArrayList();
        for(ChooseImageEntity entity : mData){
            if(StringValid.isStringValid(entity.url)){
                imgs.add(entity.url);
            }
        }
        return imgs;
    }


    @Override
    public void setNewData(List<ChooseImageEntity> data) {

        for (ChooseImageEntity entity : data) {
            entity.setType(ChooseImageEntity.TYPE_IMG);
        }

        if (data.size() != maxChoose) {
            ChooseImageEntity entity = new ChooseImageEntity();
            entity.setType(ChooseImageEntity.TYPE_ADD);
            data.add(entity);
        }
        super.setNewData(data);
    }

    @Override
    public void addData(List<ChooseImageEntity> newData) {
        for (ChooseImageEntity entity : newData) {
            entity.setType(ChooseImageEntity.TYPE_IMG);
        }

        super.addData(mData.size() - 1,newData);
        if(mData.size() == maxChoose + 1){
            removeAddImage();
        }

    }

    public void setMaxChoose(int maxChoose) {
        this.maxChoose = maxChoose;
    }

    public void setType(int type) {
        this.type = type;
    }

    private void setAddImage(){
        ChooseImageEntity entity = new ChooseImageEntity();
        entity.setType(ChooseImageEntity.TYPE_ADD);
        mData.add(entity);
        notifyDataSetChanged();
    }
    private void removeAddImage(){
        mData.remove(mData.size() -1 );
        notifyDataSetChanged();
    }

}


