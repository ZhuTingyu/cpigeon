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

    private TextView tvTitle;
    private SimpleDraweeView icon;
    private TextView tvUser;
    private TextView tvTime;
    private TextView tvThumb;
    private TextView tvComment;
    private TextView tvShare;

    public NewsViewHolder(View itemView) {
        super(itemView);

        tvTitle = getView(R.id.title);
        icon = getView(R.id.icon);
        tvUser = getView(R.id.user_name);
        tvTime = getView(R.id.time);
        tvThumb = getView(R.id.thumb);
        tvComment = getView(R.id.comment);
        tvShare = getView(R.id.share);
    }

    public void bindData(String data) {
        tvTitle.setText("关于信鸽白点和白色的区别及治疗方案");
        icon.setImageURI("http://img.zcool.cn/community/01e4a2577deac20000018c1bdd823a.jpg@1280w_1l_2o_100sh.jpg");
        tvUser.setText("大王公棚");
        tvTime.setText("2分钟前");

        tvThumb.setText("100");
        setViewDrawableLeft(tvThumb, R.drawable.ic_thumbs_up, 32, 32);

        tvComment.setText("20");
        setViewDrawableLeft(tvComment, R.drawable.ic_comment, 40, 32);

        tvShare.setText("20");
        setViewDrawableLeft(tvShare, R.drawable.ic_share, 40, 32);
    }
}
