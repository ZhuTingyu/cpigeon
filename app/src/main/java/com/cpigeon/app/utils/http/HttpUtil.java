package com.cpigeon.app.utils.http;


import com.cpigeon.app.utils.CPigeonApiUrl;
import com.cpigeon.app.utils.databean.ApiResponse;
import com.google.gson.reflect.TypeToken;

import org.xutils.HttpManager;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

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
    int type;


    public static HttpUtil builder() {
        HttpUtil httpUtil = new HttpUtil();
        requestParams = new RequestParams();
        requestParams.setConnectTimeout(8000);
        requestParams.setMaxRetryCount(0);
        manager = x.http();
        return httpUtil;
    }


    public HttpUtil addParameter(String name, Object value) {
        requestParams.addParameter(name, value);
        return this;
    }

    public HttpUtil url(String url, String head) {
        String stringUrl;
        stringUrl = CPigeonApiUrl.getInstance().getServer() + head + url;
        requestParams.setUri(stringUrl);
        return this;
    }

    public HttpUtil addHeader(String name, String value) {
        requestParams.addHeader(name, value);
        return this;
    }

    public HttpUtil setType(int type) {
        this.type = type;
        return this;
    }

    public Observable<ApiResponse<T>> request() {

        return Observable.create(subscribe -> {

            LogUtil.print(requestParams.getBodyParams());

            if (type == TYPE_GET) {
                manager.get(requestParams, setCallback(subscribe));
            } else {
                manager.post(requestParams, setCallback(subscribe));
            }
        });

    }

    private Callback.CommonCallback<String> setCallback(ObservableEmitter<ApiResponse<T>> subscribe) {
        return new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                subscribe.onNext(GsonUtil.fromJson(s, new TypeToken<ApiResponse<T>>() {
                }.getType()));
                LogUtil.print(GsonUtil.fromJson(s, new TypeToken<ApiResponse<T>>() {
                }.getType()));
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                ApiResponse apiResponse = new ApiResponse();
                apiResponse.errorCode = -1;
                apiResponse.msg = "网络不给力，请稍后重试";
                subscribe.onNext(apiResponse);
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {
                subscribe.onComplete();
            }
        };
    }


}
