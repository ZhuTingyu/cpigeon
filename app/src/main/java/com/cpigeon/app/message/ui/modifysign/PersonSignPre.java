package com.cpigeon.app.message.ui.modifysign;

import android.app.Activity;

import com.cpigeon.app.commonstandard.model.dao.IBaseDao;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.utils.CpigeonData;
import com.cpigeon.app.utils.databean.ApiResponse;

import io.reactivex.functions.Consumer;

/**
 * Created by Zhu TingYu on 2017/11/30.
 */

public class PersonSignPre extends BasePresenter {

    int userId;//：用户ID
    public String sign;//签名
    public String idCardP;//身份证正面图片，data
    public String idCardN;//身份证背面图片，data
    public String license;//营业执照图片，data
    public String name;//身份证信息：姓名 
    public String sex;//身份证信息：性别 
    public String familyName;//身份证信息：民族
    public String address;//身份证信息：住址
    public String idCardNumber;//身份证信息：身份证号码
    public String organization;//身份证信息：签发机关
    public String idCardDate;//身份证信息：有效期

    public PersonSignPre(Activity activity) {
        super(activity);
        userId = CpigeonData.getInstance().getUserId(activity);
    }

    @Override
    protected IBaseDao initDao() {
        return null;
    }

    private void modifySign(Consumer<ApiResponse> consumer) {
        submitRequestThrowError(PersonSignModel.modifySign(userId, sign, idCardP, idCardN, license, name
                , sex, familyName, address, idCardNumber, organization, idCardDate).map(r -> {
            return r;
        }), consumer);
    }

    public Consumer<String> setSign(){
        return s -> {
          sign = s;
        };
    }

}
