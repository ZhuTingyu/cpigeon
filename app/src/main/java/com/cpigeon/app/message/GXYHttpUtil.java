package com.cpigeon.app.message;

import com.cpigeon.app.MyApp;
import com.cpigeon.app.R;
import com.cpigeon.app.utils.CallAPI;
import com.cpigeon.app.utils.CommonTool;
import com.cpigeon.app.utils.databean.ApiResponse;
import com.cpigeon.app.utils.http.HttpUtil;

/**
 * Created by Zhu TingYu on 2017/11/23.
 */

public class GXYHttpUtil<T> extends HttpUtil<T> {
    public static <T> HttpUtil<T> build(){
        HttpUtil<T> httpUtil = HttpUtil.builder();
        httpUtil.setType(HttpUtil.TYPE_POST);
        httpUtil.addHeader("u", CommonTool.getUserToken(MyApp.getInstance().getBaseContext()));
        httpUtil.setHeadUrl(MyApp.getInstance().getBaseContext().getString(R.string.api_head_url));
        return httpUtil;

    }
}
