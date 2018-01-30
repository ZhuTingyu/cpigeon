package com.cpigeon.app.message.ui.order.ui.presenter;

import android.app.Activity;

import com.cpigeon.app.commonstandard.model.dao.IBaseDao;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.entity.OrderInfoEntity;
import com.cpigeon.app.entity.UserBalanceEntity;
import com.cpigeon.app.entity.WeiXinPayEntity;
import com.cpigeon.app.message.ui.order.ui.OrderModel;
import com.cpigeon.app.utils.CpigeonData;
import com.cpigeon.app.utils.DialogUtils;
import com.cpigeon.app.utils.IntentBuilder;
import com.cpigeon.app.utils.SendWX;
import com.cpigeon.app.utils.StringValid;
import com.cpigeon.app.utils.ToastUtil;
import com.cpigeon.app.utils.databean.ApiResponse;
import com.cpigeon.app.utils.http.HttpErrorException;

import org.xutils.common.util.MD5;

import io.reactivex.functions.Consumer;

/**
 * Created by Zhu TingYu on 2017/12/8.
 */

public class PayOrderPre extends BasePresenter {

    String password;
    int userId;
    public OrderInfoEntity orderInfoEntity;

    public PayOrderPre(Activity activity) {
        super(activity);
        userId = CpigeonData.getInstance().getUserId(activity);
        orderInfoEntity = getActivity().getIntent().getParcelableExtra(IntentBuilder.KEY_DATA);
    }

    @Override
    protected IBaseDao initDao() {
        return null;
    }

    public void payOrderByBalance(Consumer<ApiResponse> consumer){

        if((!StringValid.isStringValid(password) || password.length() < 6)){
            DialogUtils.createHintDialog(getActivity(),"请输入密码（6-12位）");
            return;
        }

        submitRequestThrowError(OrderModel.payOrderByBalance(userId,orderInfoEntity.id,password),consumer);
    }

    public void getUserBalance(Consumer<UserBalanceEntity> consumer){
        submitRequestThrowError(OrderModel.getUserBalance(userId).map(r -> {
            if(r.isHaveDate()){
                return r.data;
            }else throw new HttpErrorException(r);
        }),consumer);
    }

    public void getWXOrder(Consumer<ApiResponse<WeiXinPayEntity>> consumer){
        submitRequestThrowError(OrderModel.greatWXOrder(userId, orderInfoEntity.id),consumer);
    }

    public Consumer<String> setPassword(){
        return s -> {
            password = s;
        };
    }

}
