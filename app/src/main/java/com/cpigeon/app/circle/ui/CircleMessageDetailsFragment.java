package com.cpigeon.app.circle.ui;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.circle.adpter.CircleMessageDetailsCommentsAdapter;
import com.cpigeon.app.circle.adpter.CircleMessageImagesAdapter;
import com.cpigeon.app.circle.adpter.MessageDetailsReplayAdapter;
import com.cpigeon.app.circle.presenter.CircleMessagePre;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.entity.CircleMessageEntity;
import com.cpigeon.app.entity.SnsEntity;
import com.cpigeon.app.pigeonnews.ui.InputCommentDialog;
import com.cpigeon.app.utils.ChooseImageManager;
import com.cpigeon.app.utils.CpigeonData;
import com.cpigeon.app.utils.Lists;
import com.cpigeon.app.utils.StringValid;
import com.cpigeon.app.utils.ToastUtil;
import com.cpigeon.app.view.ExpandTextView;
import com.cpigeon.app.view.PraiseListView;
import com.cpigeon.app.viewholder.SocialSnsViewHolder;
import com.squareup.picasso.Picasso;
import com.wx.goodview.GoodView;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by Zhu TingYu on 2018/1/18.
 */

public class CircleMessageDetailsFragment extends BaseMVPFragment<CircleMessagePre> {

    RecyclerView recyclerView;
    CircleMessageDetailsCommentsAdapter adapter;

    GoodView goodView;
    DialogHideCircleFragment dialogHideCircleFragment;
    CircleMessageEntity entity;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_circle_message_details;
    }

    @Override
    protected CircleMessagePre initPresenter() {
        return new CircleMessagePre(this);
    }

    @Override
    protected boolean isCanDettach() {
        return false;
    }

    @Override
    public void finishCreateView(Bundle state) {

        setTitle("详情");

        recyclerView = findViewById(R.id.list);

        View view  = findViewById(R.id.input_comment);
        findViewById(view, R.id.thumb).setVisibility(View.GONE);
        findViewById(view, R.id.comment).setVisibility(View.GONE);
        findViewById(R.id.comments).setVisibility(View.GONE);
        findViewById(R.id.tv_details).setVisibility(View.GONE);

        findViewById(R.id.input).setOnClickListener(v -> {
            InputCommentDialog dialog = new InputCommentDialog();
            dialog.setHint("回复 " + entity.getUserinfo().getNickname());
            dialog.setPushClickListener(editText -> {
                mPresenter.messageId = entity.getMid();
                mPresenter.commentContent = editText.getText().toString();
                mPresenter.addComment(newComment -> {
                    entity.getCommentList().add(0,newComment);
                    dialog.dismiss();
                    adapter.notifyDataSetChanged();
                });
            });
            dialog.show(getActivity().getFragmentManager(), "InputComment");
        });


        goodView = new GoodView(getContext());

        mPresenter.type = "xxxx";
        mPresenter.getMessageList(data -> {
            entity = data.get(0);
            initMessage();
            initComments();
        });

    }

    private void initComments() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CircleMessageDetailsCommentsAdapter();
        recyclerView.setAdapter(adapter);

        if(!entity.getCommentList().isEmpty()){
            recyclerView.setVisibility(View.VISIBLE);
            adapter.setOnItemClickListener((adapter1, view, position) -> {
                CircleMessageEntity.CommentListBean  comment = entity.getCommentList().get(position);
                InputCommentDialog dialog = new InputCommentDialog();
                dialog.setHint(CpigeonData.getInstance().getUserInfo().getNickname()
                        + " 回复 " + comment.getUser().getNickname());
                dialog.setPushClickListener(editText -> {
                    mPresenter.messageId = entity.getMid();
                    mPresenter.commentId =comment.getId();
                    mPresenter.commentContent = editText.getText().toString();
                    mPresenter.addComment(newComment -> {
                        entity.getCommentList().add(position,newComment);
                        dialog.dismiss();
                        adapter.notifyDataSetChanged();
                    });
                });
                dialog.show(getActivity().getFragmentManager(),"InputCommentDialog");
            });
            adapter.setNewData(entity.getCommentList());
            recyclerView.setFocusableInTouchMode(false);
        }else {
            recyclerView.setVisibility(View.GONE);
            findViewById(R.id.text_all_comment).setVisibility(View.GONE);
        }
    }

    private void initMessage() {
        BaseViewHolder holder = new BaseViewHolder(findViewById(R.id.circle_message));
        holder.setGlideImageView(getContext(), R.id.head_img, entity.getUserinfo().getHeadimgurl());
        holder.setText(R.id.user_name, entity.getUserinfo().getNickname());
        holder.setText(R.id.time,entity.getTime());

        ExpandTextView content = holder.getView(R.id.content_text);
        content.setText(entity.getMsg());

        /**
         * 屏蔽取消
         */
        holder.getView(R.id.img_expand).setOnClickListener(v -> {
            dialogHideCircleFragment.setCircleMessageEntity(entity);
            dialogHideCircleFragment.setListener(new DialogHideCircleFragment.OnDialogClickListener() {
                @Override
                public void hideMessage() {
                    dialogHideCircleFragment.dismiss();
                }

                @Override
                public void hideHisMessage() {
                    dialogHideCircleFragment.dismiss();
                }

                @Override
                public void black() {
                    dialogHideCircleFragment.dismiss();
                }

                @Override
                public void report() {
                }
            });
            dialogHideCircleFragment.show(getActivity().getFragmentManager(),"DialogHideCircleFragment");
        });

        /**
         * 地址
         */
        if(StringValid.isStringValid(entity.getLoabs())){
            holder.getView(R.id.ll_loaction).setVisibility(View.VISIBLE);
            holder.setText(R.id.tv_message_location,entity.getLoabs());
        }else {
            holder.getView(R.id.ll_loaction).setVisibility(View.GONE);
        }
        /**
         * 关注
         */
        ImageView follow = holder.getView(R.id.follow);
        if(entity.isIsAttention() || entity.getUserinfo().getUid() == CpigeonData.getInstance().getUserId(getContext())){
            follow.setVisibility(View.GONE);
        }else {
            follow.setVisibility(View.VISIBLE);
            follow.setOnClickListener(v -> {
                mPresenter.followId = entity.getUserinfo().getUid();
                mPresenter.setIsFollow(true);
                mPresenter.setFollow(s -> {
                    entity.setIsAttention(true);
                    follow.setVisibility(View.GONE);
                    ToastUtil.showLongToast(getContext(), s);
                });
            });
        }

        /**
         * 点赞评论
         */
        SocialSnsViewHolder socialSnsviewHolder = new SocialSnsViewHolder(getActivity(),holder.getView(R.id.social_sns),goodView,"回复:"+entity.getUserinfo().getNickname());
        socialSnsviewHolder.setOnSocialListener(new SocialSnsViewHolder.OnSocialListener() {
            @Override
            public void thumb(View view) {
                mPresenter.messageId = entity.getMid();
                mPresenter.setIsThumb(!entity.isThumb());
                mPresenter.setThumb(s -> {
                    if(entity.isThumb()){
                        int position = mPresenter.getUserThumbPosition(entity.getPraiseList(),CpigeonData.getInstance().getUserId(getContext()));
                        if(position != -1){
                            entity.getPraiseList().remove(position);
                        }
                        socialSnsviewHolder.setThumb(false);
                        socialSnsviewHolder.setThumbAnimation(false);
                    }else {
                        CircleMessageEntity.PraiseListBean bean = new CircleMessageEntity.PraiseListBean();
                        bean.setIsPraise(1);
                        bean.setUid(CpigeonData.getInstance().getUserId(getContext()));
                        bean.setNickname(CpigeonData.getInstance().getUserInfo().getNickname());
                        entity.getPraiseList().add(0,bean);
                        socialSnsviewHolder.setThumb(true);
                        socialSnsviewHolder.setThumbAnimation(true);
                    }
                   initMessage();
                });
            }

            @Override
            public void comment(EditText view, InputCommentDialog dialog) {
                mPresenter.messageId = entity.getMid();
                mPresenter.commentContent = view.getText().toString();
                mPresenter.addComment(newComment -> {
                    entity.getCommentList().add(0, newComment);
                    dialog.closeDialog();

                });
            }

            @Override
            public void share(View view) {

            }
        });

        if (entity.getPraiseList() != null && entity.getPraiseList().size() > 0) {

            for (CircleMessageEntity.PraiseListBean praiseListBean : entity.getPraiseList()) {

                if (praiseListBean.getUid() == CpigeonData.getInstance().getUserId(getContext()) && praiseListBean.getIsPraise() == 1) {
                    socialSnsviewHolder.setThumb(true);
                    entity.setThumb();
                    break;
                } else {
                    socialSnsviewHolder.setThumb(false);
                    entity.setCancelThumb();
                }
            }
        }else socialSnsviewHolder.setThumb(false);
        /**
         * 图片
         */
        RecyclerView imgs = holder.getView(R.id.imgsList);
        imgs.setLayoutManager(new GridLayoutManager(getContext(), 3){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        CircleMessageImagesAdapter adapter;
        if(!entity.getPicture().isEmpty()){
            imgs.setVisibility(View.VISIBLE);
            if(imgs.getAdapter() == null){
                adapter = new CircleMessageImagesAdapter();
            }else adapter = (CircleMessageImagesAdapter) imgs.getAdapter();
            adapter.setNewData(entity.getPicture());
            adapter.setOnItemClickListener((adapter1, view, position) -> {
                ChooseImageManager.showImageDialog(getContext(), adapter.getImagesUrl(),position);
            });
            imgs.setAdapter(adapter);
            imgs.setFocusableInTouchMode(false);

        }else {
            imgs.setVisibility(View.GONE);
        }

        /**
         * 视频
         */
        JZVideoPlayerStandard videoPlayer = holder.getView(R.id.videoplayer);
        if(!entity.getVideo().isEmpty()){
            videoPlayer.setVisibility(View.VISIBLE);
            videoPlayer.setUp(entity.getVideo().get(0).getUrl(), JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
            Picasso.with(getContext()).load(entity.getVideo().get(0).getThumburl())
                    .into(((JZVideoPlayerStandard)holder.getView(R.id.videoplayer)).thumbImageView);
            JZVideoPlayer.clearSavedProgress(getContext(),entity.getVideo().get(0).getUrl());
        }else {
            videoPlayer.setVisibility(View.GONE);
        }
        PraiseListView praiseListView = holder.getView(R.id.thumbs);
        /**
         * 点赞
         */
        if(!entity.getPraiseList().isEmpty()){
            praiseListView.setVisibility(View.VISIBLE);
            praiseListView.setDatas(entity.getPraiseList());

        }else praiseListView.setVisibility(View.GONE);

    }
}