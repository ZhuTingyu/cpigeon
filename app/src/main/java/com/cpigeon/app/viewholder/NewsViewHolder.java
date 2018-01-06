package com.cpigeon.app.viewholder;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.cpigeon.app.MyApp;
import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;

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

    public void bindData(String data) {

        tvTitle1.setText("12o38129072389745102374831230-489120-934812-0");
        tvTitle2.setText("12o38129072389745102374831230-489120-934812-0");
        tvTitle3.setText("12o38129072389745102374831230-489120-934812-0");
        tvTitle4.setText("12o38129072389745102374831230-489120-934812-0");

        img1.setImageURI("http://img.zcool.cn/community/01e4a2577deac20000018c1bdd823a.jpg@1280w_1l_2o_100sh.jpg");
        img2.setImageURI("http://img.zcool.cn/community/01e4a2577deac20000018c1bdd823a.jpg@1280w_1l_2o_100sh.jpg");
        img3.setImageURI("http://img.zcool.cn/community/01e4a2577deac20000018c1bdd823a.jpg@1280w_1l_2o_100sh.jpg");
        img4.setImageURI("http://img.zcool.cn/community/01e4a2577deac20000018c1bdd823a.jpg@1280w_1l_2o_100sh.jpg");

    }
}
