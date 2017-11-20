package com.cpigeon.app.utils;

import android.view.View;

import io.reactivex.Observable;

/**
 * Created by Zhu TingYu on 2017/11/20.
 */

public class RxUtils {
    public static Observable<Object> click(View view) {
        return Observable.create(subscriber -> {
            view.setOnClickListener(v -> {
                v.setEnabled(false);
                v.postDelayed(() -> {
                    v.setEnabled(true);
                }, 350);
                subscriber.onNext(new Object());
            });
        });
    }
}
