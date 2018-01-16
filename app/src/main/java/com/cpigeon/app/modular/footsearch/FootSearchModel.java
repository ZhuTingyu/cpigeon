package com.cpigeon.app.modular.footsearch;

import com.cpigeon.app.R;
import com.cpigeon.app.entity.FootSearchEntity;
import com.cpigeon.app.utils.databean.ApiResponse;
import com.cpigeon.app.utils.http.CPAPIHttpUtil;
import com.cpigeon.app.utils.http.HttpUtil;
import com.google.gson.reflect.TypeToken;

import io.reactivex.Observable;

/**
 * Created by Zhu TingYu on 2018/1/16.
 */

public class FootSearchModel {
    public static Observable<ApiResponse<String>> searchFood (String keyWord, String year){
        return CPAPIHttpUtil.<ApiResponse<String>>build()
                .setToJsonType(new TypeToken<ApiResponse<String>>(){}.getType())
                .setType(HttpUtil.TYPE_POST)
                .url(R.string.api_get_search_foot)
                .addBody("s", keyWord)
                .addBody("y", year)
                .addBody("t",String.valueOf(2))
                .request();
    }
}
