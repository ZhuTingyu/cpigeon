package com.cpigeon.app.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.utils.Lists;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

/**
 * Created by Zhu TingYu on 2017/12/29.
 */

public class HomeNewFragment extends BaseMVPFragment {

    MZBannerView banner;

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected boolean isCanDettach() {
        return false;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_new_home_layout;
    }

    @Override
    public void finishCreateView(Bundle state) {
        banner = findViewById(R.id.banner);
        
        initBanner();
    }

    private void initBanner() {
        banner.setPages(Lists.newArrayList(), new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });


    }
}

class BannerViewHolder implements MZViewHolder<Integer> {
    private ImageView mImageView;
    @Override
    public View createView(Context context) {
        // 返回页面布局
        View view = LayoutInflater.from(context).inflate(R.layout.item_banner_layou,null);
        mImageView = view.findViewById(R.id.banner_image);
        return view;
    }

    @Override
    public void onBind(Context context, int position, Integer data) {
        // 数据绑定
        //mImageView.setImageResource(data);
    }
}
