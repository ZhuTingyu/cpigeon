package com.cpigeon.app.message.ui.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.utils.IntentBuilder;

/**
 * Created by Zhu TingYu on 2017/11/21.
 */

public class ContactsInfoFragment extends BaseMVPFragment {

    public static final int CODE_SELECTE = 0x123;

    public static final int TYPE_LOOK = 0;
    public static final int TYPE_EDIT = 1;

    EditText editText1;
    EditText editText2;
    EditText editText3;
    TextView editText4;

    TextView textView4;

    AppCompatImageView icon;

    RelativeLayout relativeLayout;

    TextView btn;

    int type;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragent_cantacts_info_layout;
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected boolean isCanDettach() {
        return false;
    }

    @Override
    public void finishCreateView(Bundle state) {
        type = getActivity().getIntent().getIntExtra(IntentBuilder.KEY_TYPE,0);
        initView();
    }

    private void initView() {
        editText1 = findViewById(R.id.ed1);
        editText2 = findViewById(R.id.ed2);
        editText3 = findViewById(R.id.ed3);
        editText4 = findViewById(R.id.ed4);

        textView4 = findViewById(R.id.text4);

        icon = findViewById(R.id.ic_right);

        relativeLayout = findViewById(R.id.rl1);
        btn = findViewById(R.id.text_btn);


        if(TYPE_EDIT == type){
            setTitle("添加联系人");
            editText4.setOnClickListener(v -> {
                IntentBuilder.Builder()
                        .putExtra(IntentBuilder.KEY_TYPE, SelectContactsFragment.TYPE_CONTACTS_ADD)
                        .startParentActivity(getActivity(), SelectContactsFragment.class, CODE_SELECTE);
            });

            btn.setOnClickListener(v -> {

            });

        }else {

            setTitle("联系人详情");
            editText1.setFocusable(false);
            editText2.setFocusable(false);
            editText3.setFocusable(false);
            icon.setVisibility(View.GONE);

            editText1.setText("123123");
            editText2.setText("123123");
            editText3.setText("123123");
            editText4.setText("123123");

            textView4.setText("分组");

            relativeLayout.setVisibility(View.GONE);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(CODE_SELECTE == requestCode){
            if(data != null && data.hasExtra(IntentBuilder.KEY_DATA)){
                editText4.setText(data.getStringExtra(IntentBuilder.KEY_DATA));
            }
        }
    }
}
