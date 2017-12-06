package com.cpigeon.app.utils;

import android.content.Context;
import android.view.View;

import com.cpigeon.app.MyApp;
import com.cpigeon.app.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Zhu TingYu on 2017/11/20.
 */

public class DialogUtils {

    public static void createDialog(Context context, String title, String content,String left){
        SweetAlertDialog dialogPrompt;
        dialogPrompt = new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE);
        dialogPrompt.setCancelable(false);
        dialogPrompt.setTitleText(title)
                .setContentText(content)
                .setConfirmText(left).show();
    }

    public static void createDialog(Context context, String title, String content,String right,
                                    SweetAlertDialog.OnSweetClickListener rightListener){
        SweetAlertDialog dialogPrompt;
        dialogPrompt = new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE);
        dialogPrompt.setCancelable(false);
        dialogPrompt.setTitleText(title)
                .setConfirmClickListener(rightListener)
                .setContentText(content)
                .setConfirmText(right).show();
    }

    public static void createDialog(Context context, String content,
                                    SweetAlertDialog.OnSweetClickListener rightListener){
        SweetAlertDialog dialogPrompt;
        dialogPrompt = new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE);
        dialogPrompt.setCanceledOnTouchOutside(false);
        dialogPrompt.setCancelable(false);
        dialogPrompt.setTitleText("提示")
                .setConfirmClickListener(rightListener)
                .setContentText(content)
                .setConfirmText("确定").show();
    }

    public static void createDialogWithLeft(Context context, String content,
                                    SweetAlertDialog.OnSweetClickListener rightListener){
        SweetAlertDialog dialogPrompt;
        dialogPrompt = new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE);
        dialogPrompt.setCanceledOnTouchOutside(false);
        dialogPrompt.setCancelable(false);
        dialogPrompt.setTitleText("提示")
                .setCancelText("取消")
                .setCancelClickListener(sweetAlertDialog -> {
                    dialogPrompt.dismissWithAnimation();
                })
                .setConfirmClickListener(rightListener)
                .setContentText(content)
                .setConfirmText("确定").show();
    }

    public static void createDialog(Context context, View view){
        SweetAlertDialog dialogPrompt;
        dialogPrompt = new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE);
        dialogPrompt.setContentView(view);
    }
}
