package com.cpigeon.app.message.ui.common;

import com.cpigeon.app.R;
import com.cpigeon.app.entity.CommonEntity;
import com.cpigeon.app.message.GXYHttpUtil;
import com.cpigeon.app.utils.databean.ApiResponse;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Zhu TingYu on 2017/11/23.
 */

public class CommonModel {

    public static Observable<ApiResponse<List<CommonEntity>>> getCommons(int userId){
        return GXYHttpUtil.<ApiResponse<List<CommonEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<CommonEntity>>>(){}.getType())
                .url(R.string.api_common_massage_list)
                .addParameter("u",userId)
                .request();
    }
}
