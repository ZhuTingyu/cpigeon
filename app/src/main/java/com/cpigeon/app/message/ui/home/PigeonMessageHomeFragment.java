package com.cpigeon.app.message.ui.home;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.message.adapter.PigeonMessageHomeAdapter;
import com.cpigeon.app.message.ui.BaseWebViewActivity;
import com.cpigeon.app.message.ui.UserAgreementActivity;
import com.cpigeon.app.message.ui.common.CommonMessageFragment;
import com.cpigeon.app.message.ui.contacts.TelephoneBookFragment;
import com.cpigeon.app.message.ui.history.MessageHistoryFragment;
import com.cpigeon.app.message.ui.modifysign.ModifySignFragment;
import com.cpigeon.app.message.ui.person.PersonInfoFragment;
import com.cpigeon.app.message.ui.sendmessage.SendMessageFragment;
import com.cpigeon.app.utils.CPigeonApiUrl;
import com.cpigeon.app.utils.CpigeonData;
import com.cpigeon.app.utils.IntentBuilder;
import com.cpigeon.app.utils.Lists;

import java.util.List;

/**
 * Created by Zhu TingYu on 2017/11/17.
 */

public class PigeonMessageHomeFragment extends BaseMVPFragment<PigeonHomePre>{

    RecyclerView recyclerView;

    PigeonMessageHomeAdapter adapter;

    private List<String> titleList;

    @Override
    public PigeonHomePre initPresenter() {
        return new PigeonHomePre(this);
    }

    @Override
    protected boolean isCanDettach() {
        return false;
    }


    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        adapter = new PigeonMessageHomeAdapter(getActivity(), titleList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            if(0 == position){
                IntentBuilder.Builder().startParentActivity(getActivity(),SendMessageFragment.class);
            }else if(1 == position){
                IntentBuilder.Builder().startParentActivity(getActivity(), TelephoneBookFragment.class);
            }else if(2 == position){
                IntentBuilder.Builder().startParentActivity(getActivity(), CommonMessageFragment.class);
            }else if(3 == position){
                IntentBuilder.Builder().startParentActivity(getActivity(), MessageHistoryFragment.class);
            }else if(4 == position){
                IntentBuilder.Builder().startParentActivity(getActivity(), ModifySignFragment.class);
            }else if(5 == position){
                //使用帮助
                IntentBuilder.Builder(getSupportActivity(),BaseWebViewActivity.class)
                        .putExtra(IntentBuilder.KEY_TITLE, "使用帮助")
                        .putExtra(IntentBuilder.KEY_DATA, CPigeonApiUrl.getInstance().getServer() + getString(R.string.api_user_help))
                        .startActivity();
            }else if(6 == position){
                IntentBuilder.Builder()
                        .putExtra(IntentBuilder.KEY_TYPE, PersonInfoFragment.TYPE_LOOK)
                        .startParentActivity(getActivity(), PersonInfoFragment.class);
            }else if(7 == position){
                //用户协议
                IntentBuilder.Builder(getSupportActivity(), UserAgreementActivity.class)
                        .putExtra(IntentBuilder.KEY_TITLE, "用户协议")
                        .putExtra(IntentBuilder.KEY_DATA, CPigeonApiUrl.getInstance().getServer()
                                + getString(R.string.api_user_agreement)).startActivity();
            }
        });

    }

    @Override
    public void finishCreateView(Bundle state) {
        titleList = Lists.newArrayList("发送短信","电话薄","短语库","发送记录"
                ,"修改签名","使用帮助","个人信息","用户协议");


        toolbar.setTitle("鸽运通");

        mPresenter.userId = CpigeonData.getInstance().getUserId(getContext());

        mPresenter.getUserInfo(r -> {
            if(r.status){
                initView();
                if(r.data.syts < 1000){
                    showTips(getString(R.string.message_pigeon_message_count_not_enough),TipType.Dialog);
                }
            }else {
                showTips(getString(R.string.message_not_open_pigeon_message),TipType.Dialog);
            }
        });

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_recyclerview_layout;
    }
}
