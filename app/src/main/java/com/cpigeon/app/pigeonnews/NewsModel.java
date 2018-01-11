package com.cpigeon.app.pigeonnews;

import com.cpigeon.app.R;
import com.cpigeon.app.entity.CommentEntity;
import com.cpigeon.app.entity.NewsDetailsEntity;
import com.cpigeon.app.entity.NewsEntity;
import com.cpigeon.app.entity.NewsMessageEntity;
import com.cpigeon.app.utils.databean.ApiResponse;
import com.cpigeon.app.utils.http.CPAPIHttpUtil;
import com.cpigeon.app.utils.http.HttpUtil;
import com.cpigeon.app.utils.http.PigeonHttpUtil;
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

    public static Observable<ApiResponse<List<NewsMessageEntity>>> newsMessage(){
        return CPAPIHttpUtil.<ApiResponse<List<NewsMessageEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<NewsMessageEntity>>>(){}.getType())
                .setType(HttpUtil.TYPE_POST)
                .url(R.string.api_news_GetDiZhenCiBao)
                .request();
    }

    public static Observable<ApiResponse<List<CommentEntity>>> getNewsComments(String newsId, int page){
        return CPAPIHttpUtil.<ApiResponse<List<CommentEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<CommentEntity>>>(){}.getType())
                .url(R.string.api_news_comments)
                .setType(HttpUtil.TYPE_POST)
                .addBody("nid", newsId)
                .addBody("pi", String.valueOf(page))
                .addBody("ps", String.valueOf(6))
                .request();
    }

    public static Observable<ApiResponse> addNewsComments (String newsId, String content){
        return CPAPIHttpUtil.<ApiResponse>build()
                .setToJsonType(new TypeToken<ApiResponse>(){}.getType())
                .setType(HttpUtil.TYPE_POST)
                .url(R.string.api_add_news_comment)
                .addBody("nid", newsId)
                .addBody("c", content)
                .request();
    }

    public static Observable<ApiResponse> addReplyForNews (String newsId,String commentId, String content){
        return CPAPIHttpUtil.<ApiResponse>build()
                .setToJsonType(new TypeToken<ApiResponse>(){}.getType())
                .setType(HttpUtil.TYPE_POST)
                .url(R.string.api_add_reply_for_news)
                .addBody("nid", newsId)
                .addBody("cid", commentId)
                .addBody("c", content)
                .request();
    }
}
