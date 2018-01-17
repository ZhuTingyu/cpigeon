package com.cpigeon.app.circle.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cpigeon.app.R;
import com.cpigeon.app.circle.adpter.ChooseImageAdapter;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.entity.ChooseImageEntity;
import com.cpigeon.app.utils.Lists;
import com.cpigeon.app.view.SingleSelectCenterDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.model.LocalMediaLoader;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/1/16.
 */

public class PushCircleMessageFragment extends BaseMVPFragment {

    RecyclerView recyclerView;
    ChooseImageAdapter adapter;



    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_push_circle_message_layout;
    }

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

        setTitle("说说");

        findViewById(R.id.rl_user_location).setOnClickListener(v -> {

        });

        findViewById(R.id.rl_user_msg_visibility).setOnClickListener(v -> {

        });

        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        adapter = new ChooseImageAdapter(getActivity());
        adapter.setType(ChooseImageAdapter.TYPE_PICTURE);
        adapter.setNewData(Lists.newArrayList());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == getActivity().RESULT_OK){
            List<ChooseImageEntity> entities = Lists.newArrayList();
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            if(requestCode == PictureMimeType.ofImage()){
                for (LocalMedia localMedia : selectList) {
                    ChooseImageEntity entity = new ChooseImageEntity();
                    entity.url = localMedia.getCompressPath();
                    entities.add(entity);
                }
            }else if(resultCode == PictureMimeType.ofVideo()){
                for (LocalMedia localMedia : selectList) {
                    ChooseImageEntity entity = new ChooseImageEntity();
                    entity.url = localMedia.getCompressPath();
                    entities.add(entity);
                }
            }
            adapter.addData(entities);
        }
    }
}
