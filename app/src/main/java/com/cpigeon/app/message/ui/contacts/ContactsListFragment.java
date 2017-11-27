package com.cpigeon.app.message.ui.contacts;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.entity.MultiSelectEntity;
import com.cpigeon.app.message.adapter.ContactsInfoAdapter;
import com.cpigeon.app.utils.IntentBuilder;
import com.cpigeon.app.utils.Lists;
import com.cpigeon.app.utils.customview.SearchEditText;

import java.util.ArrayList;

/**
 * Created by Zhu TingYu on 2017/11/21.
 */

public class ContactsListFragment extends BaseMVPFragment {

    RecyclerView recyclerView;
    SearchEditText searchEditText;

    ContactsInfoAdapter adapter;

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
        setTitle(getActivity().getIntent().getStringExtra(IntentBuilder.KEY_DATA));
        initView();
    }

    private void initView() {
        searchEditText = findViewById(R.id.widget_title_bar_search);
        searchEditText.clearFocus();
        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        addItemDecorationLine(recyclerView);
        adapter = new ContactsInfoAdapter();
        adapter.bindToRecyclerView(recyclerView);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            IntentBuilder.Builder()
                    .putExtra(IntentBuilder.KEY_TYPE,ContactsInfoFragment.TYPE_LOOK)
                    .startParentActivity(getActivity(), ContactsInfoFragment.class);
        });

        bindData();
    }

    private void bindData() {
        ArrayList<String> data = Lists.newArrayList();
        for (int i = 0; i < 5; i++) {
            data.add("");
        }
        adapter.setNewData(data);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_recyclerview_seach_layout;
    }
}
