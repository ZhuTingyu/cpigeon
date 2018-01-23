package com.cpigeon.app.modular.saigetong.model.daoimpl;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.model.dao.IBaseDao;
import com.cpigeon.app.modular.saigetong.model.bead.SGTFootSearchEntity;
import com.cpigeon.app.modular.saigetong.model.bead.SGTGzListEntity;
import com.cpigeon.app.modular.saigetong.model.bead.SGTImgEntity;
import com.cpigeon.app.modular.saigetong.model.bead.SGTRpRecordEntity;
import com.cpigeon.app.modular.saigetong.model.bead.SGTUserListEntity;
import com.cpigeon.app.utils.databean.ApiResponse;
import com.cpigeon.app.utils.http.CPAPIHttpUtil;
import com.cpigeon.app.utils.http.HttpUtil;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/1/20.
 */

public class ISGTImpl implements IBaseDao {

    //获取赛鸽通用户列表
    public static Observable<ApiResponse<List<SGTUserListEntity>>> getSGTHomeData(int pi, int ps) {
        return CPAPIHttpUtil.<ApiResponse<List<SGTUserListEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<SGTUserListEntity>>>() {
                }.getType())
                .url(R.string.api_sgt_user_info)
                .setType(HttpUtil.TYPE_POST)
                .addBody("pi", String.valueOf(pi))
                .addBody("ps", String.valueOf(ps))
                .request();
    }

    //获取入棚记录列表
    public static Observable<ApiResponse<SGTRpRecordEntity>> getSGTRpRecoudData(int guid, int pi, int ps) {
        return CPAPIHttpUtil.<ApiResponse<SGTRpRecordEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<SGTRpRecordEntity>>() {
                }.getType())
                .url(R.string.api_sgt_rpRecord)
                .setType(HttpUtil.TYPE_POST)
                .addBody("guid", String.valueOf(guid))
                .addBody("pi", String.valueOf(pi))
                .addBody("ps", String.valueOf(ps))
                .request();
    }


    //获取赛鸽通用户列表
    public static Observable<ApiResponse<List<SGTGzListEntity>>> getSGTGzListData(String guid, String id, int pi, int ps) {
        return CPAPIHttpUtil.<ApiResponse<List<SGTGzListEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<SGTGzListEntity>>>() {
                }.getType())
                .url(R.string.api_sgt_gezhu)
                .setType(HttpUtil.TYPE_POST)
                .addBody("guid", String.valueOf(guid))
                .addBody("id", String.valueOf(id))
                .addBody("pi", String.valueOf(pi))
                .addBody("ps", String.valueOf(ps))
                .request();
    }

    //公棚赛鸽搜索（搜索足环或鸽主姓名）
    public static Observable<ApiResponse<List<SGTFootSearchEntity>>> getSGTSearchFootListData(String guid, String s) {
        return CPAPIHttpUtil.<ApiResponse<List<SGTFootSearchEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<SGTFootSearchEntity>>>() {
                }.getType())
                .url(R.string.api_sgt_search)
                .setType(HttpUtil.TYPE_POST)
                .addBody("guid", String.valueOf(guid))
                .addBody("s", String.valueOf(s))
                .request();
    }

    //获取足环信息（含照片）
    public static Observable<ApiResponse<List<SGTImgEntity>>> getFootInfoData(String f) {
        return CPAPIHttpUtil.<ApiResponse<List<SGTImgEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<SGTImgEntity>>>() {
                }.getType())
                .url(R.string.api_sgt_foot_img_info)
                .setType(HttpUtil.TYPE_POST)
                .addBody("f", String.valueOf(f))
                .request();
    }
}
