package com.cpigeon.app.circle;

import android.support.annotation.Nullable;

import com.cpigeon.app.R;
import com.cpigeon.app.entity.CircleMessageEntity;
import com.cpigeon.app.entity.CircleUserInfoEntity;
import com.cpigeon.app.utils.databean.ApiResponse;
import com.cpigeon.app.utils.http.HttpUtil;
import com.cpigeon.app.utils.http.PigeonHttpUtil;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Zhu TingYu on 2018/1/18.
 */

public class CircleModel {

    public static Observable<ApiResponse<CircleUserInfoEntity>> getCircleInfo(int userId){
        return PigeonHttpUtil.<ApiResponse<CircleUserInfoEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<CircleUserInfoEntity>>(){}.getType())
                .setType(HttpUtil.TYPE_POST)
                .url(R.string.api_get_circle_user_info)
                .addBody("u", String.valueOf(userId))
                .request();
    }


    public static Observable<ApiResponse<List<CircleMessageEntity>>> circleMessage(int userId, String type, @Nullable String messageId, int page, int count){
        return PigeonHttpUtil.<ApiResponse<List<CircleMessageEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<CircleMessageEntity>>>(){}.getType())
                .setType(HttpUtil.TYPE_POST)
                .url(R.string.api_circle_message_list)
                .addBody("u", String.valueOf(userId))
                .addBody("lt", type)
                .addBody("mid", messageId)
                .addBody("pi", String.valueOf(page))
                .addBody("ps", String.valueOf(count))
                .request();
    }

    public static Observable<ApiResponse> circleFollow(int userId, int followId, int isFollow){
        return PigeonHttpUtil.<ApiResponse>build()
                .setToJsonType(new TypeToken<ApiResponse>(){}.getType())
                .setType(HttpUtil.TYPE_POST)
                .url(R.string.api_circle_user_follow)
                .addBody("u", String.valueOf(userId))
                .addBody("auid", String.valueOf(followId))
                .addBody("isa", String.valueOf(isFollow))
                .request();
    }
}
