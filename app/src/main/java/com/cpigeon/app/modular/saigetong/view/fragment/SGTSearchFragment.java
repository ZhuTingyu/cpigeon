package com.cpigeon.app.modular.saigetong.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.modular.saigetong.presenter.SGTPresenter;
import com.cpigeon.app.modular.saigetong.view.adapter.SGTSearchAdapter;
import com.cpigeon.app.utils.customview.SearchEditText;

import butterknife.BindView;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2018/1/22.
 */

public class SGTSearchFragment extends BaseMVPFragment<SGTPresenter> implements SearchEditText.OnSearchClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.search_edittext)
    SearchEditText mSearchEditText;

    private SGTSearchAdapter mAdapter;

    @Override
    protected SGTPresenter initPresenter() {
        return new SGTPresenter(getActivity());
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_sgt_search;
    }

    @Override
    protected boolean isCanDettach() {
        return false;
    }

    private String guid;//

    @Override
    public void finishCreateView(Bundle state) {
        setTitle("公棚赛鸽搜索");

        mSearchEditText.setOnSearchClickListener(this);
        guid = getActivity().getIntent().getStringExtra("guid");
//        guid = SGTRpRecordFragment.usetId;

        Log.d(TAG, "搜索页: "+guid);

        mAdapter = new SGTSearchAdapter(null);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 文本框输入监听
     *
     * @param view
     * @param keyword
     */
    @Override
    public void onSearchClick(View view, String keyword) {
        mPresenter.getSGTSearchFootListData(data -> {
            mAdapter.setNewData(data);
        }, String.valueOf(guid), keyword);
    }
}
