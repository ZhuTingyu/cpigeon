package com.cpigeon.app.utils;

import android.support.annotation.ColorRes;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.View;

import com.cpigeon.app.MyApp;

/**
 * Created by Zhu TingYu on 2018/1/18.
 */

public class StringSpanUtil {
    public static SpannableString setClickableSpan(String textStr, @ColorRes int resId) {
        SpannableString subjectSpanText = new SpannableString(textStr);
        subjectSpanText.setSpan(new SpannableClickable(MyApp.getInstance().getBaseContext().getResources().getColor(resId)){
                                    @Override
                                    public void onClick(View widget) {

                                    }
                                }, 0, subjectSpanText.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return subjectSpanText;
    }
}
