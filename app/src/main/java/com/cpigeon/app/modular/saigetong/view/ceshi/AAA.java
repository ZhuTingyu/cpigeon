package com.cpigeon.app.modular.saigetong.view.ceshi;

import android.util.Log;

import com.cpigeon.app.MyApp;
import com.cpigeon.app.utils.CommonTool;
import com.cpigeon.app.utils.Const;
import com.cpigeon.app.utils.CpigeonData;
import com.cpigeon.app.utils.EncryptionTool;
import com.cpigeon.app.utils.StringValid;
import com.cpigeon.app.utils.http.RetrofitHelper;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static android.R.attr.key;

/**
 * Created by Administrator on 2018/1/22.
 */

public class AAA {


    public static void start1() {
        Map<String, String> postParams = new HashMap<>();
        long timestamp;
        timestamp = System.currentTimeMillis() / 1000;//时间戳

        postParams.clear();//清空之前集合中保存的数据

        postParams.put("u", CommonTool.getUserToken(MyApp.getInstance().getBaseContext()));
        postParams.put("guid", "21819");
        postParams.put("id", "292959");
        postParams.put("pi", String.valueOf(1));
        postParams.put("ps", String.valueOf(90));

        RetrofitHelper.getApi()
                .getSGTGeZhu(CommonTool.getUserToken(MyApp.getInstance().getBaseContext()),
                        postParams,
                        CpigeonData.getInstance().getUserId(MyApp.getInstance().getBaseContext()),
                        AAA.getApiSign2(postParams))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    Log.d("xiaohl", "start1: " + data.string().toString());
                });
    }

    /**
     * 自动计算并添加API签名信息
     *
     * @param map
     * @return
     */
    public static String getApiSign2(Map<String, String> map) {

        Map<String, String> postParams = new HashMap<>();
        for (int i = 0; i < map.keySet().size(); i++) {
            if (StringValid.isStringValid(map.get(key))) {
                postParams.put("post_" + key, map.get(key));
            }
        }

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < map.keySet().size(); i++) {
            stringBuilder.append(key + "=" + map.get(key) + "&");
        }
        stringBuilder.append("key=" + Const.KEY_API_SIGN);
        String result = stringBuilder.toString();
        Logger.d(result);
        result = EncryptionTool.MD5(result);
        return result;
    }
}
