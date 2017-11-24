package com.cpigeon.app.utils.http;


import android.support.annotation.StringRes;
import android.util.Log;

import com.cpigeon.app.MyApp;
import com.cpigeon.app.utils.CPigeonApiUrl;
import com.cpigeon.app.utils.CallAPI;
import com.cpigeon.app.utils.databean.ApiResponse;
import com.google.gson.reflect.TypeToken;

import org.xutils.HttpManager;
import org.xutils.common.Callback;
import org.xutils.common.util.KeyValue;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;

/**
 * Created by chenshuai on 2017/11/7.
 */

public class HttpUtil<T> {

    public static final int TYPE_GET = 0;
    public static final int TYPE_POST = 1;

    static RequestParams requestParams;
    static HttpManager manager;
    public int type;
    private Type toJsonType;

    private String headUrl;


    public static <T> HttpUtil<T> builder() {
        HttpUtil<T> httpUtil = new HttpUtil<>();
        requestParams = new RequestParams();
        requestParams.setConnectTimeout(8000);
        requestParams.setMaxRetryCount(0);
        manager = x.http();
        return httpUtil;
    }

    /**
     * post 添加url 里面的参数
     * get  添加body里面的参数
     * @param name
     * @param value
     * @return
     */
    public HttpUtil<T> addParameter(String name, Object value) {
        requestParams.addParameter(name, value);
        return this;
    }

    public HttpUtil<T> addBody(String name, String value) {
        requestParams.addBodyParameter(name, value);
        return this;
    }

    /**
     * 添加url 里面的参数
     * @param name
     * @param value
     * @return
     */
    public HttpUtil<T> addQueryString(String name, String value) {
        requestParams.addQueryStringParameter(name, value);
        return this;
    }


    public HttpUtil<T> url(@StringRes int url) {
        String stringUrl;
        stringUrl = CPigeonApiUrl.getInstance().getServer()
                + headUrl
                + MyApp.getInstance().getBaseContext().getString(url);
        requestParams.setUri(stringUrl);
        return this;
    }

    public HttpUtil<T> addHeader(String name, String value) {
        requestParams.addHeader(name, value);
        return this;
    }

    public HttpUtil<T> setUserId(String name, int value) {
        requestParams.addQueryStringParameter(name, String.valueOf(value));
        return this;
    }

    /**
     * 设置请求方式
     * @param type
     * @return
     */
    public HttpUtil<T> setType(int type) {
        if(type == TYPE_POST){
            requestParams.setMethod(HttpMethod.POST);
        }else {
            requestParams.setMethod(HttpMethod.GET);
        }
        this.type = type;
        return this;
    }

    public Observable<T> request() {
        printf();
        CallAPI.addApiSign(requestParams);
        LogUtil.print(requestParams.getUri());
        Observable<T> observable = RxNet.newRequest(this)
                .map(s -> GsonUtil.fromJson(s, toJsonType));

        observable = observable.map(e -> {
            if (e instanceof ApiResponse) {
                ApiResponse responseJson = (ApiResponse) e;
                LogUtil.print(responseJson.toJsonString());
            }
            return e;
        });
        return observable;
    }


    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public HttpUtil<T> setToJsonType(Type toJsonType) {
        this.toJsonType = toJsonType;
        return this;
    }

    private void printf() {
        List<KeyValue> keyValues = requestParams.getQueryStringParams();
        List<KeyValue> body = requestParams.getBodyParams();
        StringBuffer params = new StringBuffer();
        params.append("{" + "\n");
        for (KeyValue vale : keyValues) {
            params.append("  url    " + vale.key + ": " + vale.value + "\n");
        }
        for (KeyValue vale : body) {
            params.append("  body   " + vale.key + ": " + vale.value + "\n");
        }
        params.append("}");
        params.toString();
        Log.e("params", params.toString());
    }
}
