package com.cpigeon.app.circle.adpter;

import android.widget.LinearLayout;

import com.cpigeon.app.MyApp;
import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseQuickAdapter;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.utils.Lists;
import com.cpigeon.app.utils.ScreenTool;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by Zhu TingYu on 2018/1/15.
 */

public class CircleMessageImgsAdpter extends BaseQuickAdapter<String, BaseViewHolder> {

    int size;

    public CircleMessageImgsAdpter() {
        super(R.layout.item_one_image_layout, Lists.newArrayList());
        size = ((ScreenTool.getScreenWidth(MyApp.getInstance().getBaseContext()) - ScreenTool.dip2px(20)) / 3) - 10;
    }

    @Override
    protected void convert(BaseViewHolder holder, String item) {
        SimpleDraweeView view = holder.getView(R.id.icon);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
        view.setLayoutParams(params);
        view.setImageURI("http://e.hiphotos.baidu.com/image/h%3D300/sign=8d3a9ea62c7f9e2f6f351b082f31e962/500fd9f9d72a6059099ccd5a2334349b023bbae5.jpg");
    }
}
