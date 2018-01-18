package com.cpigeon.app.circle.ui;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseViewHolder;
import com.cpigeon.app.circle.adpter.CircleMessageDetailsCommentsAdapter;
import com.cpigeon.app.circle.adpter.CircleMessageImgsAdpter;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.entity.ThumbEntity;
import com.cpigeon.app.pigeonnews.ui.InputCommentDialog;
import com.cpigeon.app.utils.Lists;
import com.cpigeon.app.utils.ToastUtil;
import com.cpigeon.app.view.ExpandTextView;
import com.cpigeon.app.view.PraiseListView;
import com.cpigeon.app.viewholder.SocialSnsViewHolder;
import com.wx.goodview.GoodView;

import cn.carbs.android.expandabletextview.library.ExpandableTextView;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by Zhu TingYu on 2018/1/18.
 */

public class CircleMessageDetailsFragment extends BaseMVPFragment {

    RecyclerView recyclerView;
    CircleMessageDetailsCommentsAdapter adapter;

    GoodView goodView;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_circle_message_details;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
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

        findViewById(R.id.input).setOnClickListener(v -> {
            InputCommentDialog dialog = new InputCommentDialog();
            dialog.setHint("我的评论更精彩！");
            dialog.setPushClickListener(content -> {
                ToastUtil.showShortToast(getContext(), content.getText().toString());
            });
            dialog.show(getActivity().getFragmentManager(), "InputComment");
        });


        goodView = new GoodView(getContext());


        initMessage(new ThumbEntity());

        initComments();
    }

    private void initComments() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CircleMessageDetailsCommentsAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setNewData(Lists.newArrayList("","","",""));
    }

    private void initMessage(ThumbEntity entity) {
        BaseViewHolder holder = new BaseViewHolder(findViewById(R.id.circle_message));
        holder.setGlideImageView(getContext(), R.id.head_img, "http://img3.imgtn.bdimg.com/it/u=1611505379,380489200&fm=27&gp=0.jpg");
        holder.setText(R.id.user_name, "小朱");
        holder.setText(R.id.time,"1231-23-32");

        ExpandTextView expandTextView = holder.getView(R.id.content_text);
        expandTextView.setShowLines(100);
        expandTextView.setText("3123121235234235121241234");

        RecyclerView imgs = holder.getView(R.id.imgsList);
        JZVideoPlayerStandard videoPlayer = holder.getView(R.id.videoplayer);

        if(holder.getAdapterPosition() == 0){
            imgs.setVisibility(View.GONE);
            videoPlayer.setVisibility(View.GONE);
        }else if(holder.getAdapterPosition() == 1){
            imgs.setVisibility(View.VISIBLE);
            videoPlayer.setVisibility(View.GONE);

            imgs.setLayoutManager(new GridLayoutManager(getContext(), 3){
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });
            CircleMessageImgsAdpter adpter = new CircleMessageImgsAdpter();
            /*adpter.setNewData(imgsData);
            adpter.setOnItemClickListener((adapter, view, position) -> {
                ChooseImageManager.showImageDialog(getContext(),imgsData, position);
            });
            imgs.setAdapter(adpter);*/
            imgs.setFocusableInTouchMode(false);
        }else {
            imgs.setVisibility(View.GONE);
            videoPlayer.setVisibility(View.VISIBLE);
        }

        SocialSnsViewHolder socialSnsviewHolder = new SocialSnsViewHolder(getActivity(),holder.getView(R.id.social_sns),goodView,"回复：小朱");
        socialSnsviewHolder.setOnSocialListener(new SocialSnsViewHolder.OnSocialListener() {
            @Override
            public void thumb(View view) {
                if(entity.isThumb()){
                    entity.setCancelThumb();
                }else {
                    entity.setThumb();
                }
                socialSnsviewHolder.setThumb(entity.isThumb());

            }

            @Override
            public void comment(EditText view) {
                ToastUtil.showShortToast(getActivity(), view.getText().toString());
            }

            @Override
            public void share(View view) {

            }
        });

        ((PraiseListView) holder.getView(R.id.thumbs)).setDatas(Lists.newArrayList("","","",""));
    }
}
