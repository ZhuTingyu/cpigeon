/*
package com.cpigeon.app.modular.footsearch.view.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.modular.footsearch.presenter.FootSearchPre;
import com.cpigeon.app.modular.footsearch.view.activity.FootSearchResultActivity;
import com.cpigeon.app.modular.order.model.bean.CpigeonServicesInfo;
import com.cpigeon.app.modular.order.view.activity.OpenServiceActivity;
import com.cpigeon.app.modular.usercenter.model.bean.CpigeonUserServiceInfo;
import com.cpigeon.app.utils.CpigeonData;
import com.cpigeon.app.utils.customview.SearchEditText;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;


*/
/**
 * Created by Administrator on 2017/4/5.
 *//*


public class FootSearchFragment extends BaseMVPFragment<FootSearchPre> implements IFootSearchView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_prompt_title)
    TextView tvPromptTitle;
    @BindView(R.id.tv_prompt_right)
    TextView tvPromptRight;
    @BindView(R.id.tv_openServices)
    TextView tvOpenServices;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.search_edittext)
    SearchEditText searchEdittext;
    private View mView;
    private int userQueryTimes = 0;
    private boolean isDev = false;
    private boolean doubleClickCloseLoadingDialog = false;
    private ArrayList<CpigeonServicesInfo> allServicesInfo;
    private String queryKey;
    private SweetAlertDialog pDialog;
    private Callback.Cancelable mFootSearchCancelable;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            doubleClickCloseLoadingDialog = false;
        }
    };

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_footsearch;
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        initToolbar();
        if (searchEdittext != null) {
            searchEdittext.setOnSearchClickListener(new SearchEditText.OnSearchClickListener() {
                @Override
                public void onSearchClick(View view, String keyword) {
                    footSearchRun(keyword.trim());
                }
            });

        }
        mPresenter.loadUserServiceInfo();
        isPrepared = false;
    }

    @Override
    public void finishCreateView(Bundle state) {
        isPrepared = true;
        lazyLoad();
    }

    private void initToolbar() {
        toolbar.setTitle("足环查询");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(null);
        toolbar.setNavigationOnClickListener(null);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (isDev) {
            super.onResume();
            return;
        }
        mPresenter.loadUserServiceInfo();

    }

    @Override
    public void getFootSearchService(CpigeonUserServiceInfo info) {
        boolean noService = info == null || TextUtils.isEmpty(info.getName());
        if(info != null){
            CpigeonData.getInstance().setUserFootSearchServiceInfo(info);
        }
        if (tvPromptTitle != null)
            tvPromptTitle.setText(noService ? "未购买套餐,最多显示2条结果" :
                    info.getShowNumber() == 0 ? String.format("您当前使用%s,结果条数不限制", info.getPackageName()) :
                            String.format("您当前使用%s套餐，当前套餐最多显示%d条结果", info.getPackageName(), info.getShowNumber()));
        if (!noService && tvPromptRight != null) {
            tvPromptRight.setVisibility(View.VISIBLE);
            tvPromptRight.setText(String.format("套餐剩余查询次数:%s%s", info.getNumbers(), info.getUnitname()));
        }
        userQueryTimes = noService ? -1 : info.getNumbers();
    }

    @Override
    public void queryFoot(Map<String, Object> map) {
        userQueryTimes = (int) map.get("rest");//设置套餐剩余次数

        CpigeonUserServiceInfo userData = CpigeonData.getInstance().getUserFootSearchServiceInfo();
        if (userData != null && (int) map.get("resultCount") > 0) {
            userData.setNumbers(userQueryTimes);
            CpigeonData.getInstance().setUserFootSearchServiceInfo(userData);
        }

        Intent intent = new Intent(getActivity(), FootSearchResultActivity.class);
        Bundle b = new Bundle();
        b.putSerializable(FootSearchResultActivity.BUNDLE_FOOT_QUERY_RESULT_LIST, (ArrayList<FootSearchResultActivity>) map.get("data"));
        b.putString(FootSearchResultActivity.BUNDLE_KEY_SEARCH_KEY, getQueryKey());
        b.putSerializable(FootSearchResultActivity.BUNDLE_KEY_ALL_SERVICES_INFO, allServicesInfo);
        b.putSerializable(FootSearchResultActivity.BUNDLE_KEY_SEARCH_MAX_SHOW_COUNT, (int) map.get("rest") == -1 ? 0 : (int) map.get("maxShowCount"));
        b.putInt(FootSearchResultActivity.BUNDLE_KEY_SEARCH_ALL_COUNT, (int) map.get("rest") == -1 ? 0 : (int) map.get("resultCount"));
        intent.putExtra(FootSearchResultActivity.INTENT_DATA_KEY, b);
        startActivity(intent);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                //更新套餐剩余信息
                CpigeonUserServiceInfo userPackageData = CpigeonData.getInstance().getUserFootSearchServiceInfo();
                if (userPackageData == null) {
                    tvPromptRight.setVisibility(View.GONE);
                } else {
                    tvPromptTitle.setText(userQueryTimes <= 0 ? "未购买套餐,最多显示2条结果" :
                            String.format("您当前使用%s，当前套餐最多显示%d条结果", userPackageData.getPackageName(), userPackageData.getShowNumber()));
                    if (userPackageData.getShowNumber() == 0) {
                        tvPromptTitle.setText(String.format("您当前使用%%s,结果条数不限制%s", userPackageData.getPackageName()));
                    }
                    tvPromptRight.setVisibility(View.VISIBLE);
                    tvPromptRight.setText(String.format("剩余查询次数:%s%s", userPackageData.getNumbers(), userPackageData.getUnitname()));
                }
                pDialog.dismiss();
            }
        });
    }

    @Override
    public String getQueryKey() {
        return queryKey;
    }

    @Override
    public String getQueryService() {
        return "足环查询服务";
    }


    private void footSearchRun(String queryKey) {
        this.queryKey = queryKey;
        if (TextUtils.isEmpty(queryKey)) {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("请您输入足环号码")
                    .setConfirmText("知道了")
                    .show();

        } else if (!isNetworkConnected()) {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("无法连接网络")
                    .setConfirmText("知道了")
                    .show();
        } else {
            pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("搜索中...");
            pDialog.setCancelable(true);
            pDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    if (mFootSearchCancelable != null && !mFootSearchCancelable.isCancelled()) {
                        mFootSearchCancelable.cancel();
                    }
                }
            });
            pDialog.show();
            mFootSearchCancelable = mPresenter.queryFoot();
        }


    }

    @OnClick({R.id.tv_openServices, R.id.tv_search, R.id.search_edittext})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_openServices:
                Intent intent = new Intent(getActivity(), OpenServiceActivity.class);
                intent.putExtra(OpenServiceActivity.INTENT_DATA_KEY_SERVICENAME, "足环查询服务");
                startActivity(intent);
                break;
            case R.id.tv_search:
                footSearchRun(searchEdittext.getText().toString().trim());
                break;
            case R.id.search_edittext:
                break;
        }
    }


    @Override
    protected FootSearchPre initPresenter() {
        return new FootSearchPre(this);
    }

    @Override
    protected boolean isCanDettach() {
        return true;
    }
}
*/
