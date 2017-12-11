package com.cpigeon.app.modular.matchlive.model.daoimpl;

import com.cpigeon.app.R;
import com.cpigeon.app.message.GXYHttpUtil;
import com.cpigeon.app.modular.matchlive.model.bean.MatchReportXH;
import com.cpigeon.app.modular.matchlive.model.bean.MatchEntity;
import com.cpigeon.app.utils.databean.ApiResponse;
import com.cpigeon.app.utils.http.HttpUtil;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Zhu TingYu on 2017/12/11.
 */

public class MatchModel {
    public static Observable<ApiResponse<List<MatchReportXH>>> greatReport(int userId, String matchType, String ssid, String sKey/*,int pager, int pageSize*/) {
        return GXYHttpUtil.<ApiResponse<List<MatchReportXH>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<MatchReportXH>>>() {
                }.getType())
                .setType(HttpUtil.TYPE_POST)
                .url(R.string.api_get_match_race_report)
                .addQueryString("u", String.valueOf(userId))
                .addBody("t", String.valueOf("gp".equals(matchType) ? 1 : 2))
                .addBody("bi", ssid)
                .addBody("s", sKey)
                .addBody("hcz", String.valueOf(true))
                .addBody("pi", String.valueOf(1))
                .addBody("ps", String.valueOf(100))
                .request();
    }
    public static Observable<ApiResponse<List<MatchEntity>>> getJGMessage(int userId, String matchType, String ssid, String sKey/*,int pager, int pageSize*/) {
        return GXYHttpUtil.<ApiResponse<List<MatchEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<MatchEntity>>>() {
                }.getType())
                .setType(HttpUtil.TYPE_POST)
                .url(R.string.api_get_JG_message)
                .addQueryString("u", String.valueOf(userId))
                .addBody("t", String.valueOf("gp".equals(matchType) ? 1 : 2))
                .addBody("bi", ssid)
                .addBody("s", sKey)
                .addBody("hcz", String.valueOf(1))
                .addBody("pi", String.valueOf(1))
                .addBody("ps", String.valueOf(100))
                .request();
    }

}
