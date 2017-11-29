package com.cpigeon.app.message.ui.contacts;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.entity.ContactsGroupEntity;
import com.cpigeon.app.message.adapter.ContactsInfoAdapter;
import com.cpigeon.app.message.ui.contacts.presenter.ContactsListPre;
import com.cpigeon.app.utils.IntentBuilder;
import com.cpigeon.app.utils.RxUtils;
import com.cpigeon.app.utils.customview.SearchEditText;


/**
 * Created by Zhu TingYu on 2017/11/21.
 */

public class ContactsListFragment extends BaseMVPFragment<ContactsListPre> {

    RecyclerView recyclerView;
    SearchEditText searchEditText;

    ContactsInfoAdapter adapter;
    ContactsGroupEntity contactsGroupEntity;


    @Override
    protected ContactsListPre initPresenter() {
        return new ContactsListPre(getActivity());
    }

    @Override
    protected boolean isCanDettach() {
        return false;
    }

    @Override
    public void finishCreateView(Bundle state) {
        contactsGroupEntity = getActivity().getIntent().getParcelableExtra(IntentBuilder.KEY_DATA);
        setTitle(contactsGroupEntity.fzmc);
        initView();
    }

    private void initView() {
        searchEditText = findViewById(R.id.widget_title_bar_search);
        bindUi(RxUtils.textChanges(searchEditText),mPresenter.setSearchString());
        searchEditText.clearFocus();
        hideSoftInput(searchEditText.getWindowToken());
        searchEditText.setOnSearchClickListener((view, keyword) -> {
            mPresenter.page = 1;
            bindData();
        });

        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.requestFocus();
        addItemDecorationLine(recyclerView);
        adapter = new ContactsInfoAdapter();
        /*adapter.setOnLoadMoreListener(() -> {
            mPresenter.page++;
            mPresenter.getContactsInGroup(contactsEntities -> {
                if(contactsEntities.isEmpty()){
                    adapter.setLoadMore(true);
                }else {
                    adapter.addData(contactsEntities);
                    adapter.setLoadMore(false);
                }
            });
        },recyclerView);*/
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            IntentBuilder.Builder()
                    .putExtra(IntentBuilder.KEY_TITLE, contactsGroupEntity.fzmc)
                    .putExtra(IntentBuilder.KEY_DATA, adapter.getData().get(position))
                    .putExtra(IntentBuilder.KEY_TYPE,ContactsInfoFragment.TYPE_LOOK)
                    .startParentActivity(getActivity(), ContactsInfoFragment.class);
        });
        adapter.bindToRecyclerView(recyclerView);


        bindData();
    }


    private void bindData() {
       /* ArrayList<String> data = Lists.newArrayList();
        for (int i = 0; i < 5; i++) {
            data.add("");
        }*/
       showLoading();
       mPresenter.getContactsInGroup(contactsEntities -> {
           adapter.setNewData(contactsEntities);
           hideLoading();
       });

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_recyclerview_seach_layout;
    }
}
