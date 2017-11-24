package com.cpigeon.app.message.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.activity.BaseActivity;
import com.cpigeon.app.utils.IntentBuilder;
import com.cpigeon.app.utils.StringValid;

import static android.view.KeyEvent.KEYCODE_BACK;

/**
 * Created by Zhu TingYu on 2017/11/22.
 */

public class BaseWebViewActivity extends BaseActivity {

    WebView webView;
    String url;
    String title;


    @Override
    public int getLayoutId() {
        return R.layout.activity_web_view_layout;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        url = getIntent().getStringExtra(IntentBuilder.KEY_DATA);

        title = getIntent().getStringExtra(IntentBuilder.KEY_TITLE);

        if(StringValid.isStringValid(title)){
            toolbar.setTitle(title);
        }


        webView = findViewById(R.id.web_view);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webView.setWebViewClient(new WebViewClient(){
            @Override

            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);

                //如果不需要其他对点击链接事件的处理返回true，否则返回false

                return true;

            }
        });
        //webView.loadDataWithBaseURL(null, url, "text/html", "utf-8", null);
        webView.loadUrl(url);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();

            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }

        super.onDestroy();
    }
}
