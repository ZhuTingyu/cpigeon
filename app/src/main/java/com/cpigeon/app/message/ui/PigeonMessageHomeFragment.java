package com.cpigeon.app.message.ui;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.message.adapter.PigeonMessageHomeAdapter;
import com.cpigeon.app.message.ui.common.CommonMessageFragment;
import com.cpigeon.app.utils.IntentBuilder;
import com.cpigeon.app.utils.Lists;

import java.util.List;

/**
 * Created by Zhu TingYu on 2017/11/17.
 */

public class PigeonMessageHomeFragment extends BaseMVPFragment{

    RecyclerView recyclerView;

    PigeonMessageHomeAdapter adapter;

    private List<String> titleList;

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected boolean isCanDettach() {
        return false;
    }


    private void initView() {
        toolbar.setTitle("鸽运通");
        recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        adapter = new PigeonMessageHomeAdapter(getActivity(), titleList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            if(0 == position){
                IntentBuilder.Builder().startParentActivity(getActivity(),SendMessageFragment.class);
            }else if(1 == position){

            }else if(2 == position){
                IntentBuilder.Builder().startParentActivity(getActivity(), CommonMessageFragment.class);
            }else if(3 == position){

            }else if(4 == position){

            }else if(5 == position){

            }else if(6 == position){

            }else if(7 == position){

            }
        });

    }

    @Override
    public void finishCreateView(Bundle state) {
        titleList = Lists.newArrayList("发送短信","电话薄","短语库","发送记录"
                ,"修改签名","使用帮助","个人信息","用户协议");
        initView();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_recyclerview_layout;
    }
}
