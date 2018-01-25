package com.cpigeon.app.circle.adpter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseQuickAdapter;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.circle.presenter.CircleMessagePre;
import com.cpigeon.app.circle.ui.CircleMessageDetailsFragment;
import com.cpigeon.app.circle.ui.DialogHideCircleFragment;
import com.cpigeon.app.entity.CircleMessageEntity;
import com.cpigeon.app.modular.cpigeongroup.model.bean.Message;
import com.cpigeon.app.pigeonnews.ui.InputCommentDialog;
import com.cpigeon.app.utils.ChooseImageManager;
import com.cpigeon.app.utils.CpigeonData;
import com.cpigeon.app.utils.IntentBuilder;
import com.cpigeon.app.utils.Lists;
import com.cpigeon.app.utils.StringValid;
import com.cpigeon.app.utils.ToastUtil;
import com.cpigeon.app.view.ExpandTextView;
import com.cpigeon.app.view.PraiseListView;
import com.cpigeon.app.view.ShareDialogFragment;
import com.cpigeon.app.viewholder.SocialSnsViewHolder;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;
import com.stfalcon.frescoimageviewer.ImageViewer;
import com.wx.goodview.GoodView;

import org.xutils.ImageManager;

import java.util.List;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by Zhu TingYu on 2018/1/15.
 */

public class CircleMessageAdapter extends BaseQuickAdapter<CircleMessageEntity, BaseViewHolder> {
    GoodView goodView;
    Activity activity;
    CircleMessagePre mPre;
    DialogHideCircleFragment dialogHideCircleFragment;
    private ShareDialogFragment share;

    int dataType;
    private static final int TYPE_IMAGE = 0;
    private static final int TYPE_VIDEO = 1;

    public CircleMessageAdapter(Activity activity) {
        super(R.layout.item_circle_message_2_layout, Lists.newArrayList());
        this.goodView = new GoodView(activity);
        this.activity = activity;
        dialogHideCircleFragment = new DialogHideCircleFragment();
    }

    @Override
    protected void convert(BaseViewHolder holder, CircleMessageEntity item) {

        holder.getView(R.id.ll1).setOnClickListener(v -> {
            IntentBuilder.Builder()
                    .putExtra(IntentBuilder.KEY_DATA, item.getMid())
                    .startParentActivity(activity, CircleMessageDetailsFragment.class);
        });

        holder.setGlideImageView(mContext, R.id.head_img, item.getUserinfo().getHeadimgurl());
        holder.setText(R.id.user_name, item.getUserinfo().getNickname());
        holder.setText(R.id.time,item.getTime());

        ExpandTextView content = holder.getView(R.id.content_text);
        content.setText(item.getMsg());

        /**
         * 屏蔽取消
         */
        holder.getView(R.id.img_expand).setOnClickListener(v -> {
            dialogHideCircleFragment.setCircleMessageEntity(item);
            dialogHideCircleFragment.setListener(new DialogHideCircleFragment.OnDialogClickListener() {
                @Override
                public void hideMessage() {
                    dialogHideCircleFragment.dismiss();
                    remove(holder.getAdapterPosition());
                }

                @Override
                public void hideHisMessage() {
                    dialogHideCircleFragment.dismiss();
                    remove(holder.getAdapterPosition());
                }

                @Override
                public void black() {
                    dialogHideCircleFragment.dismiss();
                    remove(holder.getAdapterPosition());
                }

                @Override
                public void report() {
                }
            });
            dialogHideCircleFragment.show(activity.getFragmentManager(),"DialogHideCircleFragment");
        });

        /**
         * 地址
         */
        if(StringValid.isStringValid(item.getLoabs())){
            holder.getView(R.id.ll_loaction).setVisibility(View.VISIBLE);
            holder.setText(R.id.tv_message_location,item.getLoabs());
        }else {
            holder.getView(R.id.ll_loaction).setVisibility(View.GONE);
        }
        /**
         * 关注
         */
        ImageView follow = holder.getView(R.id.follow);
        if(item.isIsAttention() || item.getUserinfo().getUid() == CpigeonData.getInstance().getUserId(mContext)){
            follow.setVisibility(View.GONE);

        }else {
            follow.setVisibility(View.VISIBLE);
            follow.setOnClickListener(v -> {
                mPre.followId = item.getUserinfo().getUid();
                mPre.setIsFollow(true);
                mPre.setFollow(s -> {
                    item.setIsAttention(true);
                    notifyItemChanged(holder.getAdapterPosition());
                    ToastUtil.showLongToast(mContext, s);
                });
            });
        }


        /**
         * 图片
         */
        RecyclerView imgs = holder.getView(R.id.imgsList);
        imgs.setLayoutManager(new GridLayoutManager(mContext, 3){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        CircleMessageImagesAdapter imagesAdapter = null;
        if(!item.getPicture().isEmpty()){
            imgs.setVisibility(View.VISIBLE);
            if(imgs.getAdapter() == null){
                imagesAdapter = new CircleMessageImagesAdapter();
            }else imagesAdapter = (CircleMessageImagesAdapter) imgs.getAdapter();
            imagesAdapter.setNewData(item.getPicture());
            CircleMessageImagesAdapter finalImagesAdapter = imagesAdapter;
            imagesAdapter.setOnItemClickListener((adapter1, view, position) -> {
                ChooseImageManager.showImageDialog(mContext, finalImagesAdapter.getImagesUrl(),position);
            });
            imgs.setAdapter(imagesAdapter);
            imgs.setFocusableInTouchMode(false);
            dataType = TYPE_IMAGE;
        }else {
            imgs.setVisibility(View.GONE);
        }

        /**
         * 视频
         */
        JZVideoPlayerStandard videoPlayer = holder.getView(R.id.videoplayer);
        if(!item.getVideo().isEmpty()){
            videoPlayer.setVisibility(View.VISIBLE);
            videoPlayer.setUp(item.getVideo().get(0).getUrl(), JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
            Picasso.with(mContext).load(item.getVideo().get(0).getThumburl())
                    .into(((JZVideoPlayerStandard)holder.getView(R.id.videoplayer)).thumbImageView);
            JZVideoPlayer.clearSavedProgress(mContext,item.getVideo().get(0).getUrl());
            dataType = TYPE_VIDEO;
        }else {
            videoPlayer.setVisibility(View.GONE);
        }
        PraiseListView praiseListView = holder.getView(R.id.thumbs);
        /**
         * 点赞
         */
        if(!item.getPraiseList().isEmpty()){
            praiseListView.setVisibility(View.VISIBLE);
            praiseListView.setDatas(item.getPraiseList());

        }else praiseListView.setVisibility(View.GONE);
        /**
         * 评论
         */
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
                CircleMessageEntity.CommentListBean  comment =replayAdapter.getItem(position);
                InputCommentDialog dialog = new InputCommentDialog();
                dialog.setHint(CpigeonData.getInstance().getUserInfo().getNickname()
                        + " 回复 " + comment.getUser().getNickname());
                dialog.setPushClickListener(editText -> {
                    mPre.messageId = item.getMid();
                    mPre.commentId =comment.getId();
                    mPre.commentContent = editText.getText().toString();
                    mPre.addComment(newComment -> {
                        item.getCommentList().add(position,newComment);
                        dialog.dismiss();
                        notifyItemChanged(holder.getAdapterPosition());
                    });
                });
                dialog.show(activity.getFragmentManager(),"InputCommentDialog");
            });
            replayAdapter.setNewData(item.getCommentList());
            comments.setFocusableInTouchMode(false);
        }else {
            comments.setVisibility(View.GONE);
        }

        /*holder.getView(R.id.tv_details).setOnClickListener(v -> {
            IntentBuilder.Builder()
                    .putExtra(IntentBuilder.KEY_DATA, item.getMid())
                    .startParentActivity(activity, CircleMessageDetailsFragment.class);
        });*/

        /**
         * 点赞评论
         */
        SocialSnsViewHolder socialSnsviewHolder = new SocialSnsViewHolder(activity,holder.getView(R.id.social_sns),goodView,"回复:"+item.getUserinfo().getNickname());
        CircleMessageImagesAdapter finalImagesAdapter1 = imagesAdapter;
        socialSnsviewHolder.setOnSocialListener(new SocialSnsViewHolder.OnSocialListener() {
            @Override
            public void thumb(View view) {
                mPre.messageId = item.getMid();
                mPre.setIsThumb(!item.isThumb());
                getBaseActivity().showLoading();
                mPre.setThumb(s -> {
                    getBaseActivity().hideLoading();
                    if(item.isThumb()){
                        int position = mPre.getUserThumbPosition(item.getPraiseList(),CpigeonData.getInstance().getUserId(mContext));
                        if(position != -1){
                            item.getPraiseList().remove(position);
                        }
                        socialSnsviewHolder.setThumb(false);
                        socialSnsviewHolder.setThumbAnimation(false);
                    }else {
                        CircleMessageEntity.PraiseListBean bean = new CircleMessageEntity.PraiseListBean();
                        bean.setIsPraise(1);
                        bean.setUid(CpigeonData.getInstance().getUserId(mContext));
                        bean.setNickname(CpigeonData.getInstance().getUserInfo().getNickname());
                        item.getPraiseList().add(0,bean);
                        socialSnsviewHolder.setThumb(true);
                        socialSnsviewHolder.setThumbAnimation(true);
                    }
                    notifyItemChanged(holder.getAdapterPosition());
                });
            }

            @Override
            public void comment(EditText view, InputCommentDialog dialog) {
                mPre.messageId = item.getMid();
                mPre.commentContent = view.getText().toString();
                mPre.addComment(newComment -> {
                    item.getCommentList().add(0, newComment);
                    dialog.closeDialog();
                    notifyItemChanged(holder.getAdapterPosition());
                });
            }

            @Override
            public void share(View view) {
                share.setDescription(item.getMsg());
                if(finalImagesAdapter1 != null){
                    share.setShareType(ShareDialogFragment.TYPE_IMAGE_URL);
                    share.setShareContent(finalImagesAdapter1.getImagesUrl().get(0));
                    share.show(activity.getFragmentManager(),"share");
                }

            }
        });

        if (item.getPraiseList() != null && item.getPraiseList().size() > 0) {

            for (CircleMessageEntity.PraiseListBean praiseListBean : item.getPraiseList()) {

                if (praiseListBean.getUid() == CpigeonData.getInstance().getUserId(mContext) && praiseListBean.getIsPraise() == 1) {
                    socialSnsviewHolder.setThumb(true);
                    item.setThumb();
                    break;
                } else {
                    socialSnsviewHolder.setThumb(false);
                    item.setCancelThumb();
                }
            }
        }else {
            socialSnsviewHolder.setThumb(false);
            item.setCancelThumb();
        }
    }


    public void setmPre(CircleMessagePre mPre) {
        this.mPre = mPre;
    }

    public void setShare(ShareDialogFragment share) {
        this.share = share;
    }

    public ShareDialogFragment getShare() {
        return share;
    }
}
