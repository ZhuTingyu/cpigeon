package com.cpigeon.app.message.ui.sendmessage;

import com.cpigeon.app.R;
import com.cpigeon.app.entity.MessageEntity;
import com.cpigeon.app.message.GXYHttpUtil;
import com.cpigeon.app.utils.databean.ApiResponse;
import com.cpigeon.app.utils.http.HttpUtil;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Zhu TingYu on 2017/11/28.
 */

public class SendMessageModel {
    public static Observable<ApiResponse> sendMessage(int userId, String groupIds, String content) {
        return GXYHttpUtil.<ApiResponse>build()
                .setToJsonType(new TypeToken<ApiResponse>() {
                }.getType())
                .setType(HttpUtil.TYPE_POST)
                .url(R.string.api_send_message)
                .addQueryString("u", String.valueOf(userId))
                .addBody("fzids", groupIds)
                .addBody("dxnr", content)
                .request();
    }
}
