package com.cpigeon.app.commonstandard.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.view.activity.IView;
import com.cpigeon.app.utils.EncryptionTool;
import com.cpigeon.app.utils.SharedPreferencesTool;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 2017/4/6.
 */

public abstract class BaseFragment extends Fragment implements IView {
    private View parentView;

    private FragmentActivity activity;

    // 标志位 标志已经初始化完成。
    protected boolean isPrepared;

    //标志位 fragment是否可见
    protected boolean isVisible;

    private Unbinder bind;
    /**
     * 加载中--对话框
     */
    protected SweetAlertDialog mLoadingSweetAlertDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = inflater.inflate(getLayoutResource(), container, false);
        activity = getSupportActivity();
        return parentView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.activity = null;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (FragmentActivity) activity;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind = ButterKnife.bind(this, view);
        finishCreateView(savedInstanceState);
    }

    public abstract void finishCreateView(Bundle state);

    public Context getApplicationContext() {

        return this.activity == null
                ? (getActivity() == null ? null :
                getActivity().getApplicationContext())
                : this.activity.getApplicationContext();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }


    protected void onVisible() {

        lazyLoad();
    }

    protected void lazyLoad() {}


    protected void onInvisible() {}


    protected void loadData() {}


    //获取布局文件
    @LayoutRes
    protected abstract int getLayoutResource();

    public boolean isNetworkConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        return mNetworkInfo != null && mNetworkInfo.isAvailable();
    }

    @Override
    public boolean showTips(String tip, TipType tipType) {
        SweetAlertDialog dialogPrompt;
        switch (tipType) {
            case Dialog:
                dialogPrompt = new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE);
                dialogPrompt.setCancelable(false);
                dialogPrompt.setTitleText(getString(R.string.prompt))
                        .setContentText(tip)
                        .setConfirmText(getString(R.string.confirm)).show();
                return true;
            case DialogSuccess:
                dialogPrompt = new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE);
                dialogPrompt.setCancelable(false);
                dialogPrompt.setTitleText("成功")
                        .setContentText(tip)
                        .setConfirmText(getString(R.string.confirm)).show();
                return true;
            case DialogError:
                dialogPrompt = new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE);
                dialogPrompt.setCancelable(false);
                dialogPrompt.setTitleText("失败")
                        .setContentText(tip)
                        .setConfirmText(getString(R.string.confirm)).show();
                return true;
            case View:
            case ViewSuccess:
            case ViewError:
                return false;
            case LoadingShow:
                if (mLoadingSweetAlertDialog != null && mLoadingSweetAlertDialog.isShowing())
                    mLoadingSweetAlertDialog.dismiss();
                mLoadingSweetAlertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
                mLoadingSweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                mLoadingSweetAlertDialog.setTitleText(tip);
                mLoadingSweetAlertDialog.setCancelable(true);
                mLoadingSweetAlertDialog.show();
                return true;
            case LoadingHide:
                if (mLoadingSweetAlertDialog != null && mLoadingSweetAlertDialog.isShowing())
                    mLoadingSweetAlertDialog.dismiss();
                return true;
            case ToastLong:
                Toast.makeText(getActivity(), tip, Toast.LENGTH_LONG).show();
                return true;
            case ToastShort:
                Toast.makeText(getActivity(), tip, Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean showTips(String tip, TipType tipType, int tag) {
        return tag == 0 && showTips(tip, tipType);
    }

    @Override
    public boolean checkLogin() {
        try {
            boolean res = (boolean) SharedPreferencesTool.Get(getActivity(), "logined", false, SharedPreferencesTool.SP_FILE_LOGIN);
            res &= SharedPreferencesTool.Get(getActivity(), "userid", 0, SharedPreferencesTool.SP_FILE_LOGIN).equals(
                    Integer.valueOf(EncryptionTool.decryptAES(SharedPreferencesTool.Get(getActivity(), "token", "", SharedPreferencesTool.SP_FILE_LOGIN).toString()).split("\\|")[0]));
            return res;
        } catch (Exception e) {
            return false;
        }
    }

    public Map<String, Object> getLoginUserInfo() {
        Map<String, Object> map = new HashMap<>();
        map.put("username", getString(R.string.user_name));
        map.put("touxiang", "");
        map.put("touxiangurl", "");
        map.put("nicheng", "");
        map.put("userid", 0);
        map.put("phone", "");
        return SharedPreferencesTool.Get(getActivity(), map, SharedPreferencesTool.SP_FILE_LOGIN);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();

    }

    public FragmentActivity getSupportActivity() {
        return super.getActivity();
    }

}
