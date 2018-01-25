package com.cpigeon.app.utils;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.cpigeon.app.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;


/**
 * Created by Administrator on 2017/1/7.
 * 分享Fragment
 */

public class ShareDialogFragment extends DialogFragment {


    private ImageButton imgbtn_wx, imgbtn_pyq, imgbtn_qq, imgbtn_qqz;//分享按钮
    private Button btn_cancel;//取消

    private Bitmap mBitmap;

    public UMShareListener umShareListener;

    private String shareUrl;

    private int shareType = -1;// -1  默认， 1 链接   2 图片  3 本地图片


    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imgbtn_wx:
                    //微信分享
                    startShareApp(SHARE_MEDIA.WEIXIN);
                    break;
                case R.id.imgbtn_pyq:
                    //微信朋友圈
                    startShareApp(SHARE_MEDIA.WEIXIN_CIRCLE);
                    break;
                case R.id.imgbtn_qq:
                    //QQ
                    startShareApp(SHARE_MEDIA.QQ);
                    break;

                case R.id.imgbtn_qqz:
                    //QQ空间
                    startShareApp(SHARE_MEDIA.QZONE);
                    break;
                case R.id.btn_cancel:
                    dismiss();
                    break;
            }
        }
    };

    private void startShareApp(SHARE_MEDIA platform) {
        if (shareType == 1) {
            //分享链接
            UMWeb web = new UMWeb(shareUrl);
            web.setTitle("中鸽网");//标题
            web.setDescription("中鸽网分享");//描述

            new ShareAction(getActivity())
                    .setPlatform(platform)//传入平台
                    .withMedia(web)//分享内容
                    .setCallback(umShareListener)//回调监听器
                    .share();
        } else if (shareType == 2) {
            //分享图片
            UMImage image = new UMImage(getActivity(), shareUrl);//网络图片

            new ShareAction(getActivity())
                    .setPlatform(platform)//传入平台
                    .withMedia(image)//分享内容
                    .setCallback(umShareListener)//回调监听器
                    .share();
        } else if (shareType == 3) {
            //分享图片
            UMImage image = new UMImage(getActivity(), mBitmap);//bitmap图片

            new ShareAction(getActivity())
                    .setPlatform(platform)//传入平台
                    .withMedia(image)//分享内容
                    .setCallback(umShareListener)//回调监听器
                    .share();
        }
    }

    private String TAG = "ShareDialogFragment";

    public void setShareListener(UMShareListener umShareListener) {
        this.umShareListener = umShareListener;
    }

    //分享类型 // -1  默认， 1 链接   2。图片
    public void setShareType(int shareType) {
        this.shareType = shareType;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.dialog_layout_share);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        // 设置宽度为屏宽, 靠近屏幕底部。
        final Window window = dialog.getWindow();
        assert window != null;
        window.setWindowAnimations(R.style.AnimBottomDialog);
        final WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
//        lp.height = getActivity().getWindowManager().getDefaultDisplay().getHeight() * 2 / 5;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        initView(dialog);

        return dialog;
    }

    private void initView(Dialog dialog) {
        imgbtn_wx = (ImageButton) dialog.findViewById(R.id.imgbtn_wx);
        imgbtn_pyq = (ImageButton) dialog.findViewById(R.id.imgbtn_pyq);
        imgbtn_qq = (ImageButton) dialog.findViewById(R.id.imgbtn_qq);
        imgbtn_qqz = (ImageButton) dialog.findViewById(R.id.imgbtn_qqz);
        btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);

        imgbtn_wx.setOnClickListener(clickListener);
        imgbtn_pyq.setOnClickListener(clickListener);
        imgbtn_qq.setOnClickListener(clickListener);
        imgbtn_qqz.setOnClickListener(clickListener);
        btn_cancel.setOnClickListener(clickListener);

    }

    public void setShareContent(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public void setShareContent(Bitmap shareBitmap) {
        this.mBitmap = shareBitmap;
    }


    public interface OnShareListener {
        void onShare(Dialog dialog);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
