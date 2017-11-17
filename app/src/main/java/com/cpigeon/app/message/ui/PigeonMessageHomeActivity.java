package com.cpigeon.app.message.ui;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.activity.BaseActivity;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.message.adapter.PigeonMessageHomeAdapter;
import com.cpigeon.app.utils.Lists;

import java.util.List;

/**
 * Created by Zhu TingYu on 2017/11/17.
 */

public class PigeonMessageHomeActivity extends BaseActivity{

    RecyclerView recyclerView;

    PigeonMessageHomeAdapter adapter;

    private List<String> titleList;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_pigeon_message_home_layout;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        titleList = Lists.newArrayList("发送短信","电话薄","短语库","发送记录"
                ,"修改签名","使用帮助","个人信息","用户协议");
        initView();
    }



    private void initView() {
        setTitle("鸽运通");
        recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        adapter = new PigeonMessageHomeAdapter(getActivity(), titleList);
        recyclerView.setAdapter(adapter);

    }

}
