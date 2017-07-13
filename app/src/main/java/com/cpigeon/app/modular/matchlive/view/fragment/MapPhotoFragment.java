package com.cpigeon.app.modular.matchlive.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.view.fragment.BaseFragment;
import com.cpigeon.app.modular.matchlive.model.bean.GeCheJianKongRace;
import com.cpigeon.app.modular.matchlive.model.bean.RaceImageOrVideo;
import com.cpigeon.app.modular.matchlive.view.activity.MapLiveActivity;
import com.cpigeon.app.modular.matchlive.view.adapter.TimeLinePhotoAdapter;
import com.cpigeon.app.utils.CallAPI;
import com.cpigeon.app.utils.customview.CustomEmptyView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 *
 * Created by Administrator on 2017/7/13
 * .
 */

public class MapPhotoFragment extends BaseFragment {

    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @BindView(R.id.empty_layout)
    CustomEmptyView mCustomEmptyView;
    private boolean mIsRefreshing = false;
    private GeCheJianKongRace geCheJianKongRace;
    private List<LocalMedia> list = new ArrayList<>();
    private TimeLinePhotoAdapter mAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (geCheJianKongRace == null)
            this.geCheJianKongRace = ((MapLiveActivity) getActivity()).getGeCheJianKongRace();
    }

    @Override
    public void finishCreateView(Bundle state) {
        isPrepared = true;
        lazyLoad();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_matchlive_sub;
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }

        initRefreshLayout();
        initRecyclerView();
        isPrepared = false;
    }



    protected void initRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.post(() -> {

            mSwipeRefreshLayout.setRefreshing(true);
            mIsRefreshing = true;
            loadData();
        });

        mSwipeRefreshLayout.setOnRefreshListener(() -> {

            mIsRefreshing = true;

            loadData();
        });
    }

    @Override
    protected void loadData() {
        if (list.size() > 0)
        {
            list.clear();
        }
        CallAPI.getGYTRaceImgOrVideo(getActivity(), geCheJianKongRace.getId(), "image", new CallAPI.Callback<List<RaceImageOrVideo>>() {
            @Override
            public void onSuccess(List<RaceImageOrVideo> data) {
                Logger.e("图片的size：" + data.size());
                if (data.size() > 0) {
                    for (RaceImageOrVideo raceImage : data) {
                        LocalMedia localMedia = new LocalMedia();
                        localMedia.setPath(raceImage.getUrl());
                        list.add(localMedia);
                    }
                    mAdapter.setNewData(data);


                } else {
                    initEmptyView("数据为空");
                }
                finishTask();
            }

            @Override
            public void onError(int errorType, Object data) {

            }
        });
    }

    public void finishTask() {
        mSwipeRefreshLayout.setRefreshing(false);
        mIsRefreshing = false;
        hideEmptyView();
        mAdapter.notifyDataSetChanged();
    }

    public void initRecyclerView() {
        mAdapter = new TimeLinePhotoAdapter(null);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (list.size() > 0) {
                PictureSelector.create(MapPhotoFragment.this).externalPicturePreview(position, list);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);


    }

    public void initEmptyView(String tips) {

        mSwipeRefreshLayout.setRefreshing(false);
        mCustomEmptyView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mCustomEmptyView.setEmptyImage(R.mipmap.ic_about_logo);
        mCustomEmptyView.setEmptyText(tips);
    }


    public void hideEmptyView() {
        mCustomEmptyView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }
}
