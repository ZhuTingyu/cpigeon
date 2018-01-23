package com.cpigeon.app.modular.saigetong.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.modular.saigetong.presenter.SGTPresenter;
import com.cpigeon.app.modular.saigetong.view.adapter.SGTGZAdapter;
import com.cpigeon.app.modular.saigetong.view.ceshi.AAA;
import com.cpigeon.app.utils.ToastUtil;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/1/20.
 */

public class SGTGzFragment extends BaseMVPFragment<SGTPresenter> {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private SGTGZAdapter mAdapter;//适配器

    @Override
    protected SGTPresenter initPresenter() {
        return new SGTPresenter(getActivity());
    }

    @Override
    protected boolean isCanDettach() {
        return false;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_sgt_gz;
    }

    @Override
    public void finishCreateView(Bundle state) {

        setTitle("公棚赛鸽");
        toolbar.getMenu().clear();
        toolbar.getMenu().add("").setIcon(R.mipmap.sgt_sousuo).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                ToastUtil.showLongToast(getContext(), "搜索");
                return false;
            }
        }).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        mAdapter = new SGTGZAdapter(null);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);

        AAA.start1();

//        mPresenter.getSGTGzListData(data -> {
//                    mAdapter.addData(SGTGZAdapter.get(data));
//                }, getActivity().getIntent().getStringExtra(IntentBuilder.KEY_DATA)
//                , getActivity().getIntent().getStringExtra(IntentBuilder.KEY_TYPE));
    }
}
