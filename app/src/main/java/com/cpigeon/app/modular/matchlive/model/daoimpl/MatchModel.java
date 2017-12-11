package com.cpigeon.app.modular.matchlive.model.daoimpl;

import com.cpigeon.app.R;
import com.cpigeon.app.message.GXYHttpUtil;
import com.cpigeon.app.modular.matchlive.model.bean.MatchPigeonsGP;
import com.cpigeon.app.modular.matchlive.model.bean.MatchPigeonsXH;
import com.cpigeon.app.modular.matchlive.model.bean.MatchReportGP;
import com.cpigeon.app.modular.matchlive.model.bean.MatchReportXH;
import com.cpigeon.app.modular.matchlive.model.bean.MatchJGEntity;
import com.cpigeon.app.utils.databean.ApiResponse;
import com.cpigeon.app.utils.http.HttpUtil;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Zhu TingYu on 2017/12/11.
 */

public class MatchModel {
    public static Observable<ApiResponse<List<MatchReportXH>>> greatReportXH(int userId, String matchType, String ssid, String sKey/*,int pager, int pageSize*/) {
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

    public static Observable<ApiResponse<List<MatchReportGP>>> greatReportGP(int userId, String matchType, String ssid, String sKey/*,int pager, int pageSize*/) {
        return GXYHttpUtil.<ApiResponse<List<MatchReportGP>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<MatchReportGP>>>() {
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


    public static Observable<ApiResponse<List<MatchPigeonsXH>>> getJGMessageXH(int userId, String matchType, String ssid, String sKey/*,int pager, int pageSize*/) {
        return GXYHttpUtil.<ApiResponse<List<MatchPigeonsXH>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<MatchPigeonsXH>>>() {
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

    public static Observable<ApiResponse<List<MatchPigeonsGP>>> getJGMessageGP(int userId, String matchType, String ssid, String sKey/*,int pager, int pageSize*/) {
        return GXYHttpUtil.<ApiResponse<List<MatchPigeonsGP>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<MatchPigeonsGP>>>() {
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
