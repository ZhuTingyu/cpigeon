package com.cpigeon.app.viewholder;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.cpigeon.app.MyApp;
import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.entity.HomeNewsEntity;
import com.cpigeon.app.entity.NewsEntity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/1/2.
 */

public class NewsViewHolder extends BaseViewHolder {

    private TextView tvTitle1;
    private SimpleDraweeView img1;
    private TextView tvTitle2;
    private SimpleDraweeView img2;
    private TextView tvTitle3;
    private SimpleDraweeView img3;
    private TextView tvTitle4;
    private SimpleDraweeView img4;

    public NewsViewHolder(View itemView) {
        super(itemView);
        tvTitle1 = getView(R.id.text_1);
        tvTitle2 = getView(R.id.text_2);
        tvTitle3 = getView(R.id.text_3);
        tvTitle4 = getView(R.id.text_4);

        img1 = getView(R.id.img_1);
        img2 = getView(R.id.img_2);
        img3 = getView(R.id.img_3);
        img4 = getView(R.id.img_4);
    }

    public void bindData(HomeNewsEntity entity) {

        List<NewsEntity> newList = entity.newList;

        for (int i = 0, len = newList.size(); i < len; i++) {
            NewsEntity  newsEntity = newList.get(i);
            switch (i){
                case 0:
                    tvTitle1.setText(newsEntity.title);
                    img1.setImageURI(newsEntity.imgurl);
                    break;
                case 1:
                    tvTitle2.setText(newsEntity.title);
                    img2.setImageURI(newsEntity.imgurl);
                    break;
                case 2:
                    tvTitle3.setText(newsEntity.title);
                    img3.setImageURI(newsEntity.imgurl);
                    break;
                case 3:
                    tvTitle4.setText(newsEntity.title);
                    img4.setImageURI(newsEntity.imgurl);
                    break;
            }
        }

//        tvTitle1.setText(entity.newList.get(0).title);
//        tvTitle2.setText(entity.newList.get(1).title);
//        tvTitle3.setText(entity.newList.get(2).title);
//        tvTitle4.setText(entity.newList.get(3).title);
//
//        img1.setImageURI("http://img.zcool.cn/community/01e4a2577deac20000018c1bdd823a.jpg@1280w_1l_2o_100sh.jpg");
//        img2.setImageURI("http://img.zcool.cn/community/01e4a2577deac20000018c1bdd823a.jpg@1280w_1l_2o_100sh.jpg");
//        img3.setImageURI("http://img.zcool.cn/community/01e4a2577deac20000018c1bdd823a.jpg@1280w_1l_2o_100sh.jpg");
//        img4.setImageURI("http://img.zcool.cn/community/01e4a2577deac20000018c1bdd823a.jpg@1280w_1l_2o_100sh.jpg");

    }
}
