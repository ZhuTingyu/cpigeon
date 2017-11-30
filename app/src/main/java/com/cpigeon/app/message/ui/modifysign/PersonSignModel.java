package com.cpigeon.app.message.ui.modifysign;

import com.cpigeon.app.R;
import com.cpigeon.app.message.GXYHttpUtil;
import com.cpigeon.app.utils.StringValid;
import com.cpigeon.app.utils.databean.ApiResponse;
import com.cpigeon.app.utils.http.GsonUtil;
import com.cpigeon.app.utils.http.HttpUtil;
import com.google.gson.reflect.TypeToken;

import java.io.File;

import io.reactivex.Observable;

/**
 * Created by Zhu TingYu on 2017/11/30.
 */

public class PersonSignModel {
    public static Observable<ApiResponse> modifySign(int userId, String sign, String IdCardP, String IdCardN
            ,String license, String name, String sex, String familyName, String address, String idCardNumber
            ,String organization, String idCardDate ) {
        return GXYHttpUtil.<ApiResponse>build()
                .setToJsonType(new TypeToken<ApiResponse>() {
                }.getType())
                .setType(HttpUtil.TYPE_POST)
                .url(R.string.api_modify_sign)
                .addQueryString("u", String.valueOf(userId))
                .addBody("qm", sign)
                .addFileBody("sfzzm",IdCardP)
                .addFileBody("sfzbm",IdCardN)
                .addFileBody("gswj",license)
                .addBody("xingming",name)
                .addBody("xingbie",sex)
                .addBody("minzu",familyName)
                .addBody("zhuzhi",address)
                .addBody("haoma",idCardNumber)
                .addBody("qianfajiguan",organization)
                .addBody("youxiaoqi",idCardDate)
                .request();
    }
}
