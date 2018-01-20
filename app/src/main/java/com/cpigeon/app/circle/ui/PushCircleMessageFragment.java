package com.cpigeon.app.circle.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import com.cpigeon.app.MyApp;
import com.cpigeon.app.R;
import com.cpigeon.app.circle.adpter.ChooseImageAdapter;
import com.cpigeon.app.circle.presenter.PushCircleMessagePre;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.entity.ChooseImageEntity;
import com.cpigeon.app.utils.IntentBuilder;
import com.cpigeon.app.utils.Lists;
import com.cpigeon.app.utils.RxUtils;
import com.cpigeon.app.utils.ToastUtil;
import com.cpigeon.app.view.SingleSelectCenterDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.model.LocalMediaLoader;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/1/16.
 */

public class PushCircleMessageFragment extends BaseMVPFragment<PushCircleMessagePre> {

    public static final int CODE_CHOOSE_LOCATION = 0x123;

    RecyclerView recyclerView;
    ChooseImageAdapter adapter;

    TextView content;
    TextView visible;
    TextView location;


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_push_circle_message_layout;
    }

    @Override
    protected PushCircleMessagePre initPresenter() {
        return new PushCircleMessagePre(getActivity());
    }

    @Override
    protected boolean isCanDettach() {
        return false;
    }

    @Override
    public void finishCreateView(Bundle state) {

        setTitle("说说");
        content = findViewById(R.id.content);
        location = findViewById(R.id.location);
        visible = findViewById(R.id.visibility);

        bindUi(RxUtils.textChanges(content),mPresenter.setMessage());
        bindUi(RxUtils.textChanges(location),mPresenter.setLocation());
        bindUi(RxUtils.textChanges(visible),mPresenter.setShowType());


        toolbar.getMenu().clear();
        toolbar.getMenu().add("发表")
                .setOnMenuItemClickListener(item -> {
                    mPresenter.pushMessage(b -> {
                        ToastUtil.showLongToast(MyApp.getInstance().getBaseContext(),"发布成功");
                        finish();
                    });
                    return false;
                }).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        findViewById(R.id.rl_user_location).setOnClickListener(v -> {
            IntentBuilder.Builder().
                    startParentActivity(getActivity(), ChooseLocationFragment.class, CODE_CHOOSE_LOCATION);
        });

        findViewById(R.id.rl_user_msg_visibility).setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            final String[] items = new String[]{getString(R.string.string_circle_message_show_type_public)
                    , getString(R.string.string_circle_message_show_type_friend)
                    , getString(R.string.string_circle_message_show_type_person)};/*设置列表的内容*/
            builder.setItems(items, new DialogInterface.OnClickListener() {/*设置列表的点击事件*/
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    visible.setText(items[which]);
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
                mPresenter.imgs = adapter.getImgs();
                mPresenter.messageType = PushCircleMessagePre.TYPE_PICTURE;
            }else if(requestCode == PictureMimeType.ofVideo()){
                for (LocalMedia localMedia : selectList) {
                    ChooseImageEntity entity = new ChooseImageEntity();
                    entity.url = localMedia.getPath();
                    entities.add(entity);
                }
                adapter.addData(entities);
                mPresenter.messageType = PushCircleMessagePre.TYPE_VIDEO;
                mPresenter.video = adapter.getImgs().get(0);
            }

        }
        if(requestCode == CODE_CHOOSE_LOCATION){
            if(data != null && data.hasExtra(IntentBuilder.KEY_DATA)){
                location.setText(data.getStringExtra(IntentBuilder.KEY_DATA));
            }
        }
    }
}
