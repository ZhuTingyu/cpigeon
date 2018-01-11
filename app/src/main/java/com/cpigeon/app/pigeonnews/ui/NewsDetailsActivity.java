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
import com.cpigeon.app.utils.CommonTool;
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
        mPresenter.getNewsDetails(newsDetailsEntity -> {
            title.setText(newsDetailsEntity.title);
            introduce.setText(newsDetailsEntity.author+"  "+newsDetailsEntity.time+"  "+newsDetailsEntity.hits);
            viewHolder.bindData(newsDetailsEntity);
            String css = "<style type=\"text/css\"> </style>";
            String html = "<html><header><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no>"+css+"</header>"+"<body>"+newsDetailsEntity.content+"</body>"+"</html>";
            loadWebByHtml(html);
        });
    }
}
