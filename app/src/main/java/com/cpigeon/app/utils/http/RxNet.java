package com.cpigeon.app.utils.http;

import com.cpigeon.app.utils.CallAPI;
import com.cpigeon.app.utils.StringValid;
import com.cpigeon.app.utils.cache.CacheManager;
import com.cpigeon.app.utils.databean.ApiResponse;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;

/**
 * Created by Zhu TingYu on 2017/11/23.
 */

public class RxNet {
    public static Observable<String> newRequest(HttpUtil httpUtil) {
        return Observable.create(observableEmitter -> {
            if (httpUtil.type == HttpUtil.TYPE_GET) {
                if(httpUtil.isHaveCache()){
                    String data = CacheManager.getCache(httpUtil.getCacheKey());
                    if(StringValid.isStringValid(data)){
                        observableEmitter.onNext(data);
                    }else {
                        httpUtil.manager.get(httpUtil.requestParams, setCacheCallback(observableEmitter));
                    }
                }else {
                    httpUtil.manager.get(httpUtil.requestParams, setCallback(observableEmitter));
                }
            } else {
                if(httpUtil.isHaveCache()){
                    String data = CacheManager.getCache(httpUtil.getCacheKey());
                    if (StringValid.isStringValid(data)){
                        observableEmitter.onNext(data);
                    }else {
                        httpUtil.manager.post(httpUtil.requestParams, setCacheCallback(observableEmitter));
                    }
                }else {
                    httpUtil.manager.post(httpUtil.requestParams, setCallback(observableEmitter));
                }
            }
        });
    }

    private static Callback.CommonCallback<String> setCallback(ObservableEmitter<String> subscribe) {
        return new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                subscribe.onNext(s);
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                ApiResponse<String> apiResponse = new ApiResponse<>();
                apiResponse.errorCode = -1;
                apiResponse.msg = "网络不给力，请稍后重试";
                apiResponse.status = false;
                subscribe.onNext(GsonUtil.toJson(apiResponse));
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

    private static Callback.CacheCallback<String> setCacheCallback(ObservableEmitter<String> subscribe) {
        return new Callback.CacheCallback<String>() {

            @Override
            public boolean onCache(String s) {
                subscribe.onNext(s);
                return true;
            }

            @Override
            public void onSuccess(String s) {
                subscribe.onNext(s);
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                ApiResponse<String> apiResponse = new ApiResponse<>();
                apiResponse.errorCode = -1;
                apiResponse.msg = "网络不给力，请稍后重试";
                apiResponse.status = false;
                subscribe.onNext(GsonUtil.toJson(apiResponse));
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
