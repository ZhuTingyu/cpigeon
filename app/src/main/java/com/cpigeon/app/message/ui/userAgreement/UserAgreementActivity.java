package com.cpigeon.app.message.ui.userAgreement;

import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.TextView;

import com.cpigeon.app.R;
import com.cpigeon.app.message.ui.BaseWebViewActivity;
import com.cpigeon.app.utils.DialogUtils;
import com.cpigeon.app.utils.IntentBuilder;

/**
 * Created by Zhu TingYu on 2017/11/22.
 */

public class UserAgreementActivity extends BaseWebViewActivity<UserAgreementPre> {

    boolean agreeState;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_agreement_webview_layout;
    }

    @Override
    public UserAgreementPre initPresenter() {
        return new UserAgreementPre(this);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        agreeState = getIntent().getBooleanExtra(IntentBuilder.KEY_BOOLEAN,false);

        AppCompatCheckBox checkBox = findViewById(R.id.checkbox);
        TextView btn = findViewById(R.id.text_btn);

        if(agreeState){
            checkBox.setVisibility(View.GONE);
            btn.setOnClickListener(v -> {
                finish();
            });
        }else {
            btn.setOnClickListener(v -> {
                if(checkBox.isChecked()){
                    mPresenter.setUserAgreement(apiResponse -> {
                        if(apiResponse.status){
                            DialogUtils.createDialog(this,"提示",apiResponse.msg,"确定",sweetAlertDialog -> {
                                finish();
                            });
                        }else {
                            showTips(apiResponse.msg, TipType.DialogError);
                        }
                    });
                }else {
                    showTips("请点击同意", TipType.DialogError);
                }
            });
        }


    }
}
