package com.cpigeon.app.message.ui;

import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.widget.TextView;

import com.cpigeon.app.R;

/**
 * Created by Zhu TingYu on 2017/11/22.
 */

public class UserAgreementActivity extends BaseWebViewActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_user_agreement_webview_layout;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        AppCompatCheckBox checkBox = findViewById(R.id.checkbox);
        TextView btn = findViewById(R.id.text_btn);

        btn.setOnClickListener(v -> {
            if(checkBox.isChecked()){
                finish();
            }else {
                showTips("请点击同意", TipType.DialogError);
            }
        });
    }
}
