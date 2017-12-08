package com.cpigeon.app.message.ui.order.ui;

import com.cpigeon.app.R;
import com.cpigeon.app.entity.OrderInfoEntity;
import com.cpigeon.app.message.GXYHttpUtil;
import com.cpigeon.app.utils.databean.ApiResponse;
import com.cpigeon.app.utils.http.HttpUtil;
import com.google.gson.reflect.TypeToken;

import io.reactivex.Observable;

/**
 * Created by Zhu TingYu on 2017/12/8.
 */

public class OrderModel {
    public static Observable<ApiResponse<OrderInfoEntity>> greatGXTOrder(int userId, int sid) {
        return GXYHttpUtil.<ApiResponse<OrderInfoEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<OrderInfoEntity>>() {
                }.getType())
                .setType(HttpUtil.TYPE_POST)
                .url(R.string.api_creat_order)
                .addQueryString("u", String.valueOf(userId))
                .addBody("sid", String.valueOf(sid))
                .request();
    }
}
