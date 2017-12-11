package com.cpigeon.app.modular.matchlive.presenter;

import android.app.Activity;

import com.cpigeon.app.commonstandard.model.dao.IBaseDao;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.modular.matchlive.model.bean.MatchInfo;
import com.cpigeon.app.modular.matchlive.model.bean.MatchReportXH;
import com.cpigeon.app.modular.matchlive.model.bean.MatchEntity;
import com.cpigeon.app.modular.matchlive.model.daoimpl.MatchModel;
import com.cpigeon.app.modular.matchlive.view.fragment.BaseSearchResultFragment;
import com.cpigeon.app.utils.CpigeonData;
import com.cpigeon.app.utils.IntentBuilder;
import com.cpigeon.app.utils.Lists;
import com.cpigeon.app.utils.http.HttpErrorException;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by Zhu TingYu on 2017/12/11.
 */

public class SearchMatchPre extends BasePresenter {

    int userId;
    MatchInfo matchInfo;
    String key;

    public SearchMatchPre(Activity activity) {
        super(activity);
        userId = CpigeonData.getInstance().getUserId(getActivity());
        matchInfo = (MatchInfo) activity.getIntent().getSerializableExtra(IntentBuilder.KEY_DATA);
        key = activity.getIntent().getStringExtra(BaseSearchResultFragment.KEY_WORD);
    }

    @Override
    protected IBaseDao initDao() {
        return null;
    }

    public void getReport(Consumer<List<MatchReportXH>> consumer){
        submitRequestThrowError(MatchModel.greatReport(userId, matchInfo.getLx(), matchInfo.getSsid(),key).map(r -> {
            if(r.isOk()){
                if(r.status){
                    return r.data;
                }else return Lists.newArrayList();
            }else throw new HttpErrorException(r);
        }),consumer);
    }

    public void getJGMessage(Consumer<List<MatchEntity>> consumer){
        submitRequestThrowError(MatchModel.getJGMessage(userId, matchInfo.getLx(), matchInfo.getSsid(),key).map(r -> {
            if(r.isOk()){
                if(r.status){
                    return r.data;
                }else return Lists.newArrayList();
            }else throw new HttpErrorException(r);
        }),consumer);
    }

}
