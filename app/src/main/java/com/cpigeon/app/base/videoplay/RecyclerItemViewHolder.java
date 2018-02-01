package com.cpigeon.app.base.videoplay;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cpigeon.app.R;
import com.cpigeon.app.entity.VideoEntity;
import com.cpigeon.app.modular.matchlive.model.bean.RaceImageOrVideo;
import com.cpigeon.app.view.ShareDialogFragment;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by GUO on 2015/12/3.
 */
public class RecyclerItemViewHolder extends RecyclerItemBaseHolder {

    public final static String TAG = "RecyclerView2List";

    protected Context context = null;


    private SmallVideoHelper smallVideoHelper;
    private SmallVideoHelper.GSYSmallVideoHelperBuilder gsySmallVideoHelperBuilder;

    private ShareDialogFragment dialogFragment;//分享显示视图
    ImageView thumbImage;
    ViewGroup listItemContainer;
    ImageView play;

    public RecyclerItemViewHolder(Context context, View v) {
        super(v);
        this.context = context;
        dialogFragment = new ShareDialogFragment();
        initView(v);
    }

    private void initView(View view) {
        play = view.findViewById(R.id.play);
        thumbImage = new ImageView(context);
        listItemContainer = view.findViewById(R.id.ff);


        play.setOnClickListener(v -> {
            getRecyclerBaseAdapter().notifyDataSetChanged();
        });
    }

    public void onBind(final int position, final RaceImageOrVideo item) {

        smallVideoHelper.addVideoPlayer(position, thumbImage, TAG, listItemContainer, play);
        smallVideoHelper.setPlayPositionAndTag(position, TAG);
        gsySmallVideoHelperBuilder.setVideoTitle(item.getTag()).setUrl(item.getUrl());
        smallVideoHelper.startPlay();


        //增加封面
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setImageResource(R.mipmap.xxx1);

        /*Glide.with(context).load(item.getThumburl()).into(imageView);


        listItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRecyclerBaseAdapter().notifyDataSetChanged();
                //listVideoUtil.setLoop(true);


                it_video_lable.setText(item.getTag());//设置标签
                it_video_time.setText(item.getTime()); //设置时间
                it_video_water.setText(item.getWeartherName() + " " + item.getTemperature() + "° " + item.getWindDir()); //设置天气
                it_video_lola.setText("坐标：" + item.getLongitude() + "/" + item.getLatitude());//设置经纬度

                imgBtn_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialogFragment != null) {
                            dialogFragment.setShareContent(ApiConstants.VIDEO_SHARE + item.getFid());
                            dialogFragment.setShareListener(ViewControlShare.getShareResultsDown(context, dialogFragment, "sp"));
                            dialogFragment.setShareType(1);
                            dialogFragment.show(((Activity) context).getFragmentManager(), "share");
                        }
                    }
                });
            }
        });*/
    }


    public void setVideoHelper(SmallVideoHelper smallVideoHelper, SmallVideoHelper.GSYSmallVideoHelperBuilder gsySmallVideoHelperBuilder) {
        this.smallVideoHelper = smallVideoHelper;
        this.gsySmallVideoHelperBuilder = gsySmallVideoHelperBuilder;
    }
}





