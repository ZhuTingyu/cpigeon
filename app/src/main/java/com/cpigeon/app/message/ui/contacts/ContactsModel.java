package com.cpigeon.app.message.ui.contacts;

import com.cpigeon.app.R;
import com.cpigeon.app.entity.CommonEntity;
import com.cpigeon.app.entity.ContactsGroupEntity;
import com.cpigeon.app.message.GXYHttpUtil;
import com.cpigeon.app.utils.databean.ApiResponse;
import com.cpigeon.app.utils.http.HttpUtil;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Zhu TingYu on 2017/11/24.
 */

public class ContactsModel {

    public static Observable<ApiResponse<List<ContactsGroupEntity>>> getContactsGroups(int userId) {
        return GXYHttpUtil.<ApiResponse<List<ContactsGroupEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<ContactsGroupEntity>>>() {
                }.getType())
                .url(R.string.api_contact_group)
                .addParameter("u", userId)
                .request();
    }

    public static Observable<ApiResponse> ContactsGroupsAdd(int userId, String groupName) {
        return GXYHttpUtil.<ApiResponse>build()
                .setToJsonType(new TypeToken<ApiResponse>() {
                }.getType())
                .setType(HttpUtil.TYPE_POST)
                .url(R.string.api_contact_group_add)
                .addQueryString("u", String.valueOf(userId))
                .addBody("fzmc", groupName)
                .request();
    }

}
