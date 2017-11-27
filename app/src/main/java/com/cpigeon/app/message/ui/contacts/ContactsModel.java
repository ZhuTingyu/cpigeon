package com.cpigeon.app.message.ui.contacts;

import com.cpigeon.app.R;
import com.cpigeon.app.entity.CommonEntity;
import com.cpigeon.app.entity.ContactsEntity;
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

    public static Observable<ApiResponse> ContactsAdd(int userId, String groupId,
                                                      String phoneNumber, String name, String remarks) {
        return GXYHttpUtil.<ApiResponse>build()
                .setToJsonType(new TypeToken<ApiResponse>() {
                }.getType())
                .setType(HttpUtil.TYPE_POST)
                .url(R.string.api_contact_add)
                .addQueryString("u", String.valueOf(userId))
                .addBody("fzid", groupId)
                .addBody("sjhm", phoneNumber)
                .addBody("xingming", name)
                .addBody("beizhu", remarks)
                .request();
    }

    public static Observable<ApiResponse<List<ContactsEntity>>> ContactsInGroup(int userId, int groupId, int page, String search) {
        return GXYHttpUtil.<ApiResponse<List<ContactsEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<ContactsEntity>>>() {
                }.getType())
                .setType(HttpUtil.TYPE_POST)
                .url(R.string.api_get_contacts_in_group)
                .addQueryString("u", String.valueOf(userId))
                .addBody("fzid", String.valueOf(groupId))
                .addBody("p", String.valueOf(page))
                .addBody("s", search)
                .request();
    }


}
