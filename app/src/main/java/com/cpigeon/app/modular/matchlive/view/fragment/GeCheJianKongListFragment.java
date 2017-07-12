package com.cpigeon.app.modular.matchlive.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.view.fragment.BasePageTurnFragment;
import com.cpigeon.app.modular.matchlive.presenter.GeCheJianKongPersenter;
import com.cpigeon.app.modular.matchlive.view.adapter.GeCheJianKongExpandListAdapter;
import com.cpigeon.app.modular.matchlive.view.fragment.viewdao.IGeCheJianKongListView;
import com.cpigeon.app.utils.customview.SearchEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by chenshuai on 2017/7/11.
 */

public class GeCheJianKongListFragment extends BasePageTurnFragment<GeCheJianKongPersenter, GeCheJianKongExpandListAdapter, MultiItemEntity> implements IGeCheJianKongListView {
    public static final String KEY_TYPE = "show_type";
    public static final String TYPE_XIEHUI = "2";
    public static final String TYPE_GONGPENG = "1";
    @BindView(R.id.search_edittext)
    SearchEditText searchEdittext;
    Unbinder unbinder;
    private String _showType = TYPE_XIEHUI;

    String _searchKey = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        String type = getArguments().getString(KEY_TYPE);
        if (!TextUtils.isEmpty(type) &&
                (type.equals(TYPE_XIEHUI) ||
                        type.equals(TYPE_GONGPENG))) {
            _showType = type;
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchEdittext.setOnSearchClickListener(new SearchEditText.OnSearchClickListener() {
            @Override
            public void onSearchClick(View view, String keyword) {
                search(keyword);
            }
        });
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        if (searchEdittext != null) {
            searchEdittext.setText(this._searchKey);
            searchEdittext.setSelection(this._searchKey.length());
        }
    }

    public void search(String keyword) {
        this._searchKey = keyword;
        onRefresh();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.layout_com_swiperefreshlayout_recyclerview_has_searchedittext;
    }

    @Override
    public String getOrgType() {
        return _showType;
    }

    @Override
    public String getSearchKey() {
        return _searchKey;
    }

    @Override
    protected GeCheJianKongPersenter initPresenter() {
        return new GeCheJianKongPersenter(this);
    }

    @Override
    protected boolean isCanDettach() {
        return false;
    }

    @Override
    protected int getDefaultPageSize() {
        return 10;
    }

    @Override
    protected String getEmptyDataTips() {
        return "没有鸽车监控信息";
    }

    @Override
    public GeCheJianKongExpandListAdapter getNewAdapterWithNoData() {
        GeCheJianKongExpandListAdapter adapter = new GeCheJianKongExpandListAdapter(null);
        adapter.setOnItemClickListener(onItemClickListener);
        //lastExpandItemPosition = -1;
        return adapter;
    }

    @Override
    protected void loadDataByPresenter() {
        mPresenter.loadNext();
    }

    //private int lastExpandItemPosition = -1;//最后一个索引
    BaseQuickAdapter.OnItemClickListener onItemClickListener = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            Object item = ((GeCheJianKongExpandListAdapter) adapter).getData().get(position);
            if (item instanceof GeCheJianKongExpandListAdapter.OrgItem) {
                if (((GeCheJianKongExpandListAdapter.OrgItem) item).isExpanded()) {
                    adapter.collapse(position);
                } else {
                    adapter.expand(position);
                }
//                if (((GeCheJianKongExpandListAdapter.OrgItem) item).isExpanded()) {
//                    if (lastExpandItemPosition == position) {
//                        lastExpandItemPosition = -1;
//                    }
//                    adapter.collapse(position);
//                } else {
//                    if (lastExpandItemPosition >= 0) {
//                        adapter.collapse(lastExpandItemPosition);
//                        if (lastExpandItemPosition > position) {//展开上面的项
//                            adapter.expand(position);
//                            lastExpandItemPosition = position;
//                        } else if (lastExpandItemPosition < position) {//展开下面的项
//                            adapter.expand(position - 1);
//                            lastExpandItemPosition = position - 1;
//                        }
//                    } else {
//                        lastExpandItemPosition = position;
//                        adapter.expand(lastExpandItemPosition);
//                    }
//                }
            } else if (item instanceof GeCheJianKongExpandListAdapter.RaceItem) {
                showTips(((GeCheJianKongExpandListAdapter.RaceItem) item).getRace().getRaceName(), TipType.ToastShort);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
