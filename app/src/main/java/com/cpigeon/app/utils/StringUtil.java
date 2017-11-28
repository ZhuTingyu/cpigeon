package com.cpigeon.app.utils;

import android.support.annotation.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Zhu TingYu on 2017/11/22.
 */

public class StringUtil {

    public static String getCutString(String s, int start, int end){
        return s.substring(start, end);
    }

    public static String removeAllSpace(String s){
        return s.replaceAll(" +","");
    }

    public static boolean stringIsNumber(String s){
        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(s);
        return m.matches();
    }

}
