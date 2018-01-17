package com.cpigeon.app.circle.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.cpigeon.app.R;
import com.cpigeon.app.circle.adpter.ChooseImageAdapter;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.entity.ChooseImageEntity;
import com.cpigeon.app.utils.IntentBuilder;
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

    public static final int CODE_CHOOSE_LOCATION = 0x123;

    RecyclerView recyclerView;
    ChooseImageAdapter adapter;
    TextView tvLocation;
    TextView tvUserVisibility;


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

        tvLocation = findViewById(R.id.tv_user_location);
        tvUserVisibility = findViewById(R.id.tv_user_visibility);

        findViewById(R.id.rl_user_location).setOnClickListener(v -> {
            IntentBuilder.Builder().
                    startParentActivity(getActivity(), ChooseLocationFragment.class, CODE_CHOOSE_LOCATION);
        });

        findViewById(R.id.rl_user_msg_visibility).setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            final String[] items = new String[]{"所有人可见", "好友可见", "自己可见"};/*设置列表的内容*/
            builder.setItems(items, new DialogInterface.OnClickListener() {/*设置列表的点击事件*/
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    tvUserVisibility.setText(items[which]);
                }
            });
            builder.setCancelable(true);
            builder.show();
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
                adapter.addData(entities);
            }else if(requestCode == PictureMimeType.ofVideo()){
                for (LocalMedia localMedia : selectList) {
                    ChooseImageEntity entity = new ChooseImageEntity();
                    entity.url = localMedia.getCompressPath();
                    entities.add(entity);
                }
                adapter.addData(entities);
            }

        }
        if(requestCode == CODE_CHOOSE_LOCATION){
            if(data.hasExtra(IntentBuilder.KEY_DATA)){
                tvLocation.setText(data.getStringExtra(IntentBuilder.KEY_DATA));
            }
        }
    }
}
