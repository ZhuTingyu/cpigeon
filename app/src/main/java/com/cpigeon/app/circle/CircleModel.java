package com.cpigeon.app.circle;

import android.support.annotation.Nullable;

import com.cpigeon.app.R;
import com.cpigeon.app.circle.presenter.CircleMessageListPre;
import com.cpigeon.app.entity.CircleMessageEntity;
import com.cpigeon.app.entity.NewsDetailsEntity;
import com.cpigeon.app.utils.CpigeonData;
import com.cpigeon.app.utils.databean.ApiResponse;
import com.cpigeon.app.utils.http.CPAPIHttpUtil;
import com.cpigeon.app.utils.http.HttpUtil;
import com.cpigeon.app.utils.http.PigeonHttpUtil;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Zhu TingYu on 2018/1/18.
 */

public class CircleModel {
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
}
