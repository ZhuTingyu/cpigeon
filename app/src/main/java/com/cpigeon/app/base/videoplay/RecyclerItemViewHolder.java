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

    ImageView thumbImage;
    ViewGroup listItemContainer;
    ImageView play;
    TextView title;

    public RecyclerItemViewHolder(Context context, View v) {
        super(v);
        this.context = context;
        initView(v);
    }

    private void initView(View view) {
        play = view.findViewById(R.id.play);
        thumbImage = new ImageView(context);
        listItemContainer = view.findViewById(R.id.ff);
        title = view.findViewById(R.id.title);
//        play.setOnClickListener(v -> {
//            getRecyclerBaseAdapter().notifyDataSetChanged();
//        });
    }

    public void onBind(final int position, final RaceImageOrVideo item) {
        Glide.with(context).load(item.getThumburl()).into(thumbImage);

        title.setText(item.getTag());//设置标签

        smallVideoHelper.addVideoPlayer(position, thumbImage, TAG, listItemContainer, play);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRecyclerBaseAdapter().notifyDataSetChanged();
                //listVideoUtil.setLoop(true);
                smallVideoHelper.setPlayPositionAndTag(position, TAG);
                gsySmallVideoHelperBuilder.setVideoTitle(item.getTag()).setUrl(item.getUrl());
                smallVideoHelper.startPlay();
            }
        });
    }


    public void setVideoHelper(SmallVideoHelper smallVideoHelper, SmallVideoHelper.GSYSmallVideoHelperBuilder gsySmallVideoHelperBuilder) {
        this.smallVideoHelper = smallVideoHelper;
        this.gsySmallVideoHelperBuilder = gsySmallVideoHelperBuilder;
    }
}





