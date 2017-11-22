package com.cpigeon.app.message.ui.person;

import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.message.adapter.PersonImageInfoAdapter;
import com.cpigeon.app.utils.IntentBuilder;
import com.cpigeon.app.utils.Lists;

/**
 * Created by Zhu TingYu on 2017/11/22.
 */

public class PersonInfoFragment extends BaseMVPFragment {

    public static final int TYPE_LOOK = 0;
    public static final int TYPE_EDIT = 1;

    int type;

    RecyclerView recyclerView;
    PersonImageInfoAdapter adapter;

    AppCompatEditText edName;
    AppCompatEditText edNumber;
    AppCompatEditText edWork;


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
        type = getActivity().getIntent().getIntExtra(IntentBuilder.KEY_TYPE, 0);
        setTitle("个人信息");
        initView();
    }

    private void initView() {

        findViewById(R.id.ll1).setVisibility(View.GONE);
        TextView btn = findViewById(R.id.text_btn);
        btn.setVisibility(View.VISIBLE);

        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        adapter = new PersonImageInfoAdapter(getContext());
        adapter.bindToRecyclerView(recyclerView);
        adapter.addHeaderView(initHeadView());
        adapter.setNewData(Lists.newArrayList("","",""));

        if(type == TYPE_LOOK){

            btn.setText("编辑");
            btn.setOnClickListener(v -> {
                IntentBuilder.Builder()
                        .putExtra(IntentBuilder.KEY_TYPE, PersonInfoFragment.TYPE_EDIT)
                        .startParentActivity(getActivity(), PersonInfoFragment.class);
            });

            adapter.setOnItemClickListener((adapter1, view, position) -> {

                if(0 == position){  //身份证正面

                }else if(1 == position){    //身份中反面

                }else if(2 == position){     //运营执照

                }
            });

        }else {
            btn.setText("确定");
            btn.setOnClickListener(v -> {

            });
        }
    }

    private View initHeadView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_person_info_head_layout, recyclerView, false);
        edName = findViewById(view,R.id.name);
        edNumber = findViewById(view,R.id.phone_numbers);
        edWork = findViewById(view,R.id.work);

        if(type == TYPE_LOOK){
            edName.setFocusable(false);
            edNumber.setFocusable(false);
            edWork.setFocusable(false);

            edName.setText("12312");
            edNumber.setText("123213");
            edWork.setText("13123");
        }

        return view;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_recyclerview_with_button_layout;
    }
}
