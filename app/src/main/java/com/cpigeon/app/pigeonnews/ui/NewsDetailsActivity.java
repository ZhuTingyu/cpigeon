package com.cpigeon.app.pigeonnews.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.message.ui.BaseWebViewActivity;
import com.cpigeon.app.pigeonnews.presenter.NewsDetailsPre;
import com.cpigeon.app.utils.IntentBuilder;
import com.cpigeon.app.utils.ToastUtil;
import com.cpigeon.app.viewholder.NewsCommentViewHolder;

/**
 * Created by Zhu TingYu on 2018/1/6.
 */

public class NewsDetailsActivity extends BaseWebViewActivity<NewsDetailsPre>{


    TextView title;
    TextView introduce;
    NewsCommentViewHolder viewHolder;

    @Override
    public int getLayoutId() {
        return R.layout.activity_news_details_layout;
    }

    @Override
    public NewsDetailsPre initPresenter() {
        return new NewsDetailsPre(this);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setTitle("新闻详情");
        title = findViewById(R.id.title);
        introduce = findViewById(R.id.introduce);
        initBottomToolbar();
        super.initView(savedInstanceState);
        bindData(savedInstanceState);
    }

    private void initBottomToolbar() {

        View view = findViewById(R.id.bottom_comment);
        viewHolder = new NewsCommentViewHolder(view, this);
        viewHolder.setListener(new NewsCommentViewHolder.OnViewClickListener() {
            @Override
            public void commentPushClick(String content) {
                ToastUtil.showShortToast(getApplicationContext(), content);
            }

            @Override
            public void thumbClick() {

            }

            @Override
            public void commentClick() {
                IntentBuilder.Builder().startParentActivity(getActivity(), NewsCommentFragment.class);
            }
        });


    }

    private void bindData(Bundle savedInstanceState) {
        title.setText("aldjf;alsfjasl;");
        introduce.setText("`1231231"+"  "+"`1231231"+"  "+"`1231231");

        mPresenter.getNewsDetails(newsDetailsEntity -> {
            viewHolder.bindData(newsDetailsEntity);
            loadWebByHtml(newsDetailsEntity.content);
        });
    }
}
