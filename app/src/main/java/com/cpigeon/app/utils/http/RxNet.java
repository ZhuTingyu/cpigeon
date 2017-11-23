package com.cpigeon.app.utils.http;

import com.cpigeon.app.utils.databean.ApiResponse;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;

/**
 * Created by Zhu TingYu on 2017/11/23.
 */

public class RxNet {
    public static Observable<String> newRequest(HttpUtil httpUtil) {
        return Observable.create(observableEmitter -> {
            if (httpUtil.type == HttpUtil.TYPE_GET) {
                httpUtil.manager.get(httpUtil.requestParams, setCallback(observableEmitter));
            } else {
                httpUtil.manager.post(httpUtil.requestParams, setCallback(observableEmitter));
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
