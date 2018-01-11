package com.cpigeon.app.pigeonnews.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.pigeonnews.adpter.NewsCommentAdapter;
import com.cpigeon.app.utils.Lists;
import com.cpigeon.app.utils.ToastUtil;
import com.cpigeon.app.viewholder.NewsCommentViewHolder;

/**
 * Created by Zhu TingYu on 2018/1/9.
 */

public class NewsCommentFragment extends BaseMVPFragment {

    RecyclerView recyclerView;
    NewsCommentAdapter adapter;

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
        return R.layout.fragment_news_details_comment_layout;
    }

    @Override
    public void finishCreateView(Bundle state) {
        setTitle("全部评论");

        recyclerView = findViewById(R.id.list);
        addItemDecorationLine(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NewsCommentAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setNewData(Lists.newArrayList("","","","","","","",""));


        NewsCommentViewHolder holder = new NewsCommentViewHolder(findViewById(R.id.bottom_comment),getActivity());
        holder.onlyComment();

        holder.setListener(new NewsCommentViewHolder.OnViewClickListener() {
            @Override
            public void commentPushClick(String content) {
                ToastUtil.showShortToast(getContext(),content);
            }

            @Override
            public void thumbClick() {

            }

            @Override
            public void commentClick() {

            }
        });

    }
}
