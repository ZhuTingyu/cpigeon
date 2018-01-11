package com.cpigeon.app.pigeonnews.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.entity.CommentEntity;
import com.cpigeon.app.pigeonnews.adpter.NewsCommentAdapter;
import com.cpigeon.app.pigeonnews.presenter.NewsCommentsPre;
import com.cpigeon.app.utils.Lists;
import com.cpigeon.app.utils.ToastUtil;
import com.cpigeon.app.viewholder.NewsCommentViewHolder;

/**
 * Created by Zhu TingYu on 2018/1/9.
 */

public class NewsCommentsFragment extends BaseMVPFragment<NewsCommentsPre> {

    RecyclerView recyclerView;
    NewsCommentAdapter adapter;

    @Override
    protected NewsCommentsPre initPresenter() {
        return new NewsCommentsPre(getActivity());
    }

    @Override
    protected boolean isCanDettach() {
        return false;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_news_details_comment_layout;
    }

    @Override
    public void finishCreateView(Bundle state) {
        setTitle("全部评论");
        NewsCommentViewHolder viewHolder = new NewsCommentViewHolder(findViewById(R.id.bottom_comment), getActivity());
        viewHolder.onlyComment();
        viewHolder.setListener(new NewsCommentViewHolder.OnViewClickListener() {
            @Override
            public void commentPushClick(EditText editText) {
                mPresenter.content = editText.getText().toString();
                mPresenter.addNewsComment(msg -> {
                    ToastUtil.showShortToast(getActivity(),msg);
                    viewHolder.dialog.closeDialog();
                    bindData();
                });
            }

            @Override
            public void thumbClick() {

            }

            @Override
            public void commentClick() {

            }
        });

        recyclerView = findViewById(R.id.list);
        addItemDecorationLine(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NewsCommentAdapter();
        adapter.setListener(new NewsCommentAdapter.OnCommunicationListener() {
            @Override
            public void thumb(CommentEntity entity, int position) {

            }

            @Override
            public void comment(CommentEntity entity, int position) {
                mPresenter.commentId = entity.id;
                InputCommentDialog dialog = new InputCommentDialog();
                dialog.setPushClickListener(content -> {
                    mPresenter.content = content.getText().toString();
                    mPresenter.replyComment(s -> {
                        bindData();
                        dialog.closeDialog();
                    });
                });
                dialog.show(getActivity().getFragmentManager(), "InputComment");
            }
        });
        adapter.setOnLoadMoreListener(() -> {
            mPresenter.page++;
            mPresenter.getNewsComments(data -> {
                if (data.isEmpty()) {
                    adapter.loadMoreEnd();
                } else {
                    adapter.addData(data);
                    adapter.loadMoreComplete();
                }
            });
        }, recyclerView);
        recyclerView.setAdapter(adapter);


        bindData();
    }

    private void bindData() {
        showLoading();
        mPresenter.getNewsComments(data -> {
            hideLoading();
            adapter.setNewData(data);
        });
    }
}
