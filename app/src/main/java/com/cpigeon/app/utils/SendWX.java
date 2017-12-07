package com.cpigeon.app.utils;

/**
 * Created by Zhu TingYu on 2017/12/7.
 */


import com.cpigeon.app.commonstandard.view.activity.BaseActivity;
import com.cpigeon.app.wxapi.WXPayEntryActivity;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Base64;

import java.io.UnsupportedEncodingException;

public class SendWX {
    public String APP_ID = "";
    public static String STATE = "EShopApp";
    private Context context;
    private IWXAPI api;

    public SendWX(BaseActivity context) {
        this.context = context;
        this.APP_ID = getAppId();
    }
    public SendWX(Context context) {
        this.context = context;
        this.APP_ID = getAppId();
    }

    public static String getAppId() {
        try {
            return new String(Base64.decode(WXPayEntryActivity.APP_ID, Base64.DEFAULT), "utf-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
    public static String getAppSecret()
    {
        try {
            return new String(Base64.decode(WXPayEntryActivity.APP_ID, Base64.DEFAULT), "utf-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public Boolean isBinding() {
        api = WXAPIFactory.createWXAPI(context, APP_ID);
        api.registerApp(APP_ID);
        return api.isWXAppInstalled();
    }


    public boolean isInstalled() {
        api = WXAPIFactory.createWXAPI(context, APP_ID);
        return api.isWXAppInstalled();
    }



    public void payWeiXin(PayReq payReq) {
        if (context == null) {
            return;
        }
        if (payReq == null) {
            if(context instanceof  BaseActivity){
                BaseActivity baseActivity=(BaseActivity)context;
            }
            return;
        }
        api = WXAPIFactory.createWXAPI(context, APP_ID);
        if (!api.isWXAppInstalled()) {
            if(context instanceof  BaseActivity){
                BaseActivity baseActivity=(BaseActivity)context;
            }
            createDialog();
            return;
        }
        boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
        if (!isPaySupported) {
            if(context instanceof  BaseActivity){
                BaseActivity baseActivity=(BaseActivity)context;
            }
            createDialogNotPay();
            return;
        }
        api.sendReq(payReq);
    }



    public void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("当前没有下载微信，请下载安装");
        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    public void createDialogNotPay() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("当前版本不支持微信功能，请下载安装");
        builder.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }
}
