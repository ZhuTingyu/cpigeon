package com.cpigeon.app.pigeonnews.ui;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

import com.cpigeon.app.R;
import com.cpigeon.app.base.BaseWebViewActivity;
import com.cpigeon.app.pigeonnews.presenter.NewsDetailsPre;
import com.cpigeon.app.utils.IntentBuilder;
import com.cpigeon.app.utils.ToastUtil;
import com.cpigeon.app.utils.http.CommonUitls;
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
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webView.loadUrl("javascript:(function(){" +
                        "var objs = document.getElementsByTagName('img'); " +
                        "for(var i=0;i<objs.length;i++)  " +
                        "{"
                        + "var img = objs[i];   " +
                        " img.style.maxWidth = '100%';img.style.height='auto';" +
                        "}" +
                        "})()");
            }
        });
        bindData();
    }

    private void initBottomToolbar() {

        View view = findViewById(R.id.bottom_comment);
        viewHolder = new NewsCommentViewHolder(view, this);
        viewHolder.setListener(new NewsCommentViewHolder.OnViewClickListener() {
            @Override
            public void commentPushClick(EditText editText) {
                mPresenter.content = editText.getText().toString();
                mPresenter.addNewsComment(msg -> {
                    ToastUtil.showShortToast(getActivity(),msg);
                    viewHolder.dialog.closeDialog();
                    mPresenter.newsDetailsEntity.count += 1;
                    viewHolder.bindData(mPresenter.newsDetailsEntity);
                });

            }

            @Override
            public void thumbClick() {
                showLoading();
                mPresenter.thumbNews(data -> {
                    hideLoading();
                    if(data.isThumb()){
                        ToastUtil.showShortToast(getActivity(), "点赞成功");
                        mPresenter.newsDetailsEntity.priase += 1;
                        mPresenter.newsDetailsEntity.setThumb();
                    }else {
                        ToastUtil.showShortToast(getActivity(), "取消点赞");
                        mPresenter.newsDetailsEntity.priase -= 1;
                        mPresenter.newsDetailsEntity.setCancelThumb();
                    }
                    viewHolder.bindData(mPresenter.newsDetailsEntity);
                });
            }

            @Override
            public void commentClick() {
                IntentBuilder.Builder()
                        .putExtra(IntentBuilder.KEY_DATA, mPresenter.newsId)
                        .startParentActivity(getActivity(), NewsCommentsFragment.class);
            }
        });


    }

    private void bindData() {
        mPresenter.getNewsDetails(newsDetailsEntity -> {
            title.setText(newsDetailsEntity.title);
            introduce.setText(newsDetailsEntity.author+"  "+newsDetailsEntity.time+"  "+
                    "浏览"+newsDetailsEntity.hits+"次");
            viewHolder.bindData(newsDetailsEntity);
            String css = "<style type=\"text/css\"> </style>";
            String html = "<html><header><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no>"+css+"</header>"+"<body>"+newsDetailsEntity.content+"</body>"+"</html>";
            loadWebByHtml(html);
        });
    }
}
