package com.cpigeon.app.circle.adpter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseQuickAdapter;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.circle.ui.CircleMessageDetailsFragment;
import com.cpigeon.app.entity.CircleMessageEntity;
import com.cpigeon.app.utils.IntentBuilder;
import com.cpigeon.app.utils.Lists;
import com.cpigeon.app.utils.StringValid;
import com.cpigeon.app.utils.ToastUtil;
import com.cpigeon.app.view.ExpandTextView;
import com.cpigeon.app.view.PraiseListView;
import com.cpigeon.app.viewholder.SocialSnsViewHolder;
import com.squareup.picasso.Picasso;
import com.stfalcon.frescoimageviewer.ImageViewer;
import com.wx.goodview.GoodView;

import java.util.List;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by Zhu TingYu on 2018/1/15.
 */

public class CircleMessageAdapter extends BaseQuickAdapter<CircleMessageEntity, BaseViewHolder> {
    GoodView goodView;
    Activity activity;

    public CircleMessageAdapter(Activity activity, GoodView goodView) {
        super(R.layout.item_pigeon_circle_message_layout, Lists.newArrayList());
        this.goodView = goodView;
        this.activity = activity;
    }

    @Override
    protected void convert(BaseViewHolder holder, CircleMessageEntity item) {
        holder.setGlideImageView(mContext, R.id.head_img, item.getUserinfo().getHeadimgurl());
        holder.setText(R.id.user_name, item.getUserinfo().getNickname());
        holder.setText(R.id.time,item.getTime());

        ExpandTextView content = holder.getView(R.id.content_text);
        content.setText(item.getMsg());

        //地址
        if(StringValid.isStringValid(item.getLoabs())){
            holder.getView(R.id.ll_loaction).setVisibility(View.VISIBLE);
            holder.setText(R.id.tv_message_location,item.getLoabs());
        }else {
            holder.getView(R.id.ll_loaction).setVisibility(View.GONE);
        }

        //TODO 点赞评论

        SocialSnsViewHolder socialSnsviewHolder = new SocialSnsViewHolder(activity,holder.getView(R.id.social_sns),goodView,"回复：小朱");
        socialSnsviewHolder.setOnSocialListener(new SocialSnsViewHolder.OnSocialListener() {
            @Override
            public void thumb(View view) {

            }

            @Override
            public void comment(EditText view) {
                ToastUtil.showShortToast(activity, view.getText().toString());
            }

            @Override
            public void share(View view) {

            }
        });
        //图片
        RecyclerView imgs = holder.getView(R.id.imgsList);
        imgs.setLayoutManager(new GridLayoutManager(mContext, 3){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        CircleMessageImagesAdapter adapter;
        if(!item.getPicture().isEmpty()){
            imgs.setVisibility(View.VISIBLE);
            if(imgs.getAdapter() == null){
                adapter = new CircleMessageImagesAdapter();
            }else adapter = (CircleMessageImagesAdapter) imgs.getAdapter();
            adapter.setNewData(item.getPicture());
            adapter.setOnItemClickListener((adapter1, view, position) -> {
                showImageDialog(mContext, adapter.getImagesUrl(),position);
            });
            imgs.setAdapter(adapter);
            imgs.setFocusableInTouchMode(false);

        }else {
            imgs.setVisibility(View.GONE);
        }

        //视频
        /*JZVideoPlayerStandard videoPlayer = holder.getView(R.id.videoplayer);
        if(!item.getVideo().isEmpty()){
            videoPlayer.setVisibility(View.VISIBLE);
            videoPlayer.setUp(item.getVideo().get(0).getUrl(), JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
            Picasso.with(mContext).load(item.getVideo().get(0).getThumburl())
                    .into(((JZVideoPlayerStandard)holder.getView(R.id.videoplayer)).thumbImageView);
            JZVideoPlayer.clearSavedProgress(mContext,item.getVideo().get(0).getUrl());
        }else {
            videoPlayer.setVisibility(View.GONE);
        }*/
        PraiseListView praiseListView = holder.getView(R.id.thumbs);
        //点赞
        if(!item.getPraiseList().isEmpty()){
            praiseListView.setVisibility(View.VISIBLE);
            praiseListView.setDatas(item.getPraiseList());

        }else praiseListView.setVisibility(View.GONE);

        //评论

        RecyclerView comments = holder.getView(R.id.comments);
        comments.setLayoutManager(new LinearLayoutManager(mContext){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        if(!item.getCommentList().isEmpty()){
            comments.setVisibility(View.VISIBLE);
            MessageDetailsReplayAdapter replayAdapter;
            if(comments.getAdapter() != null){
                replayAdapter = (MessageDetailsReplayAdapter) comments.getAdapter();
            }else replayAdapter = new MessageDetailsReplayAdapter();
            comments.setAdapter(replayAdapter);
            replayAdapter.setOnItemClickListener((adapter1, view, position) -> {
                //TODO 回复
            });
            replayAdapter.setNewData(item.getCommentList());
            comments.setFocusableInTouchMode(false);
        }else {
            comments.setVisibility(View.GONE);
        }




        holder.getView(R.id.tv_details).setOnClickListener(v -> {
            IntentBuilder.Builder()
                    .startParentActivity((Activity) mContext, CircleMessageDetailsFragment.class);
        });
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

    private void showGood(View view,@DrawableRes int resId){
        goodView.setImage(resId);
        goodView.show(view);
    }

}
