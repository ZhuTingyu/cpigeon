package com.cpigeon.app.home.adpter;

import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.entity.BaseDynamicEntity;
import com.cpigeon.app.utils.Lists;

/**
 * Created by Zhu TingYu on 2018/1/2.
 */

public class CircleDynamicAdapter extends BaseMultiItemQuickAdapter<BaseDynamicEntity, BaseViewHolder> {

    public CircleDynamicAdapter() {
        super(Lists.newArrayList());
        addItemType(BaseDynamicEntity.IMAGE_0, R.layout.item_dynamic_1_img_layout);
        addItemType(BaseDynamicEntity.IMAGE_1, R.layout.item_dynamic_1_img_layout);
        addItemType(BaseDynamicEntity.IMAGE_2, R.layout.item_dynamic_2_img_layout);
        addItemType(BaseDynamicEntity.IMAGE_3, R.layout.item_dynamic_3_img_layout);
    }

    @Override
    protected void convert(BaseViewHolder holder, BaseDynamicEntity item) {
        switch (holder.getItemViewType()){
            case BaseDynamicEntity.IMAGE_0:
                holder.setViewVisible(R.id.content_img, View.GONE);
                holder.setSimpleImageView(R.id.user_icon, "http://img0.imgtn.bdimg.com/it/u=1018364764,1223529536&fm=27&gp=0.jpg");
                holder.setText(R.id.user_name,"小朱");
                holder.setText(R.id.content_text, "1241312312312131312313123123123131231");
                holder.setImageResource(R.id.icon, R.mipmap.ic_home_follow);
                break;
            case BaseDynamicEntity.IMAGE_1:
                holder.setSimpleImageView(R.id.content_img, "http://img.zcool.cn/community/01e4a2577deac20000018c1bdd823a.jpg@1280w_1l_2o_100sh.jpg");
                holder.setSimpleImageView(R.id.user_icon, "http://img0.imgtn.bdimg.com/it/u=1018364764,1223529536&fm=27&gp=0.jpg");
                holder.setText(R.id.user_name,"小朱");
                holder.setText(R.id.content_text, "1241312312312131312313123123123131231");
                holder.setImageResource(R.id.icon, R.mipmap.ic_home_followed);
                break;
            case BaseDynamicEntity.IMAGE_2:
                holder.setSimpleImageView(R.id.content_img1, "http://img.zcool.cn/community/01e4a2577deac20000018c1bdd823a.jpg@1280w_1l_2o_100sh.jpg");
                holder.setSimpleImageView(R.id.content_img2, "http://img.zcool.cn/community/01e4a2577deac20000018c1bdd823a.jpg@1280w_1l_2o_100sh.jpg");
                holder.setSimpleImageView(R.id.user_icon, "http://img0.imgtn.bdimg.com/it/u=1018364764,1223529536&fm=27&gp=0.jpg");
                holder.setText(R.id.user_name,"小朱");
                holder.setText(R.id.content_text, "1241312312312131312313123123123131231");
                holder.setImageResource(R.id.icon, R.mipmap.ic_home_followed);
                break;
            case BaseDynamicEntity.IMAGE_3:
                holder.setSimpleImageView(R.id.content_img1, "http://img.zcool.cn/community/01e4a2577deac20000018c1bdd823a.jpg@1280w_1l_2o_100sh.jpg");
                holder.setSimpleImageView(R.id.content_img2, "http://img.zcool.cn/community/01e4a2577deac20000018c1bdd823a.jpg@1280w_1l_2o_100sh.jpg");
                holder.setSimpleImageView(R.id.content_img3, "http://img.zcool.cn/community/01e4a2577deac20000018c1bdd823a.jpg@1280w_1l_2o_100sh.jpg");
                holder.setSimpleImageView(R.id.user_icon, "http://img0.imgtn.bdimg.com/it/u=1018364764,1223529536&fm=27&gp=0.jpg");
                holder.setText(R.id.user_name,"小朱");
                holder.setText(R.id.content_text, "1241312312312131312313123123123131231");
                holder.setImageResource(R.id.icon, R.mipmap.ic_home_followed);
                break;

        }
    }
}
