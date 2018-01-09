package com.cpigeon.app.pigeonnews;

import com.cpigeon.app.R;
import com.cpigeon.app.entity.NewsDetailsEntity;
import com.cpigeon.app.entity.NewsEntity;
import com.cpigeon.app.utils.databean.ApiResponse;
import com.cpigeon.app.utils.http.CPAPIHttpUtil;
import com.cpigeon.app.utils.http.HttpUtil;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Zhu TingYu on 2018/1/9.
 */

public class NewsModel {
    public static Observable<ApiResponse<NewsDetailsEntity>> newsDetails(String newsId){
        return CPAPIHttpUtil.<ApiResponse<NewsDetailsEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<NewsDetailsEntity>>(){}.getType())
                .setType(HttpUtil.TYPE_POST)
                .url(R.string.api_news_details)
                .addBody("nid",newsId)
                .request();
    }
}
