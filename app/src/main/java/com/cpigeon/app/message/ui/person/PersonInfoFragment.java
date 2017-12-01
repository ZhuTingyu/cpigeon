package com.cpigeon.app.message.ui.person;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.cpigeon.app.R;
import com.cpigeon.app.commonstandard.presenter.BasePresenter;
import com.cpigeon.app.commonstandard.view.activity.BaseActivity;
import com.cpigeon.app.commonstandard.view.fragment.BaseMVPFragment;
import com.cpigeon.app.entity.IdCardNInfoEntity;
import com.cpigeon.app.entity.IdCardPInfoEntity;
import com.cpigeon.app.entity.PersonInfoEntity;
import com.cpigeon.app.message.adapter.PersonImageInfoAdapter;
import com.cpigeon.app.message.ui.idCard.IdCardCameraActivity;
import com.cpigeon.app.message.ui.modifysign.PersonSignPre;
import com.cpigeon.app.utils.FileTool;
import com.cpigeon.app.utils.IntentBuilder;
import com.cpigeon.app.utils.Lists;
import com.cpigeon.app.utils.RxUtils;
import com.cpigeon.app.utils.customview.SaActionSheetDialog;
import com.cpigeon.app.utils.inputfilter.PhotoUtil;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by Zhu TingYu on 2017/11/22.
 */

public class PersonInfoFragment extends BaseMVPFragment {

    protected PersonSignPre signPre;

    public static final int TYPE_LOOK = 0;
    public static final int TYPE_EDIT = 1;

    private int PHOTO_SUCCESS_REQUEST = 2083;

    int type;

    RecyclerView recyclerView;
    PersonImageInfoAdapter adapter;

    AppCompatEditText edName;
    AppCompatEditText edNumber;
    AppCompatEditText edWork;

    PersonInfoEntity entity;

    List<String> imgs;


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
        signPre = new PersonSignPre(getActivity());
        type = getActivity().getIntent().getIntExtra(IntentBuilder.KEY_TYPE, 0);
        imgs = Lists.newArrayList("idCard_P", "idCard_N", "license");
        setTitle("个人信息");
        hideSoftInput();
        getPersonInfo();
        initView();
    }

    private void getPersonInfo() {
        showLoading();
        signPre.getPersonInfo(personInfoEntity -> {
            if(personInfoEntity != null){
                entity = personInfoEntity;
                FileTool.byte2File(entity.sfzzm,imgs.get(0));
                FileTool.byte2File(entity.sfzbm,imgs.get(1));
                FileTool.byte2File(entity.yyzz,imgs.get(2));
                hideLoading();
                adapter.setNewData(imgs);

            }
        });
    }

    private void initView() {

        findViewById(R.id.ll1).setVisibility(View.GONE);
        TextView btn = findViewById(R.id.text_btn);
        btn.setVisibility(View.VISIBLE);

        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        adapter = new PersonImageInfoAdapter(getContext());
        adapter.bindToRecyclerView(recyclerView);
        adapter.addHeaderView(initHeadView());
        adapter.setNewData(Lists.newArrayList("","",""));
        if(type == TYPE_LOOK){

            btn.setText("编辑");
            btn.setOnClickListener(v -> {
                IntentBuilder.Builder()
                        .putExtra(IntentBuilder.KEY_TYPE, PersonInfoFragment.TYPE_EDIT)
                        .startParentActivity(getActivity(), PersonInfoFragment.class);
            });

            adapter.setOnItemClickListener((adapter1, view, position) -> {

                if (position == 0) {//身份证正面
                    IntentBuilder.Builder(getActivity(), IdCardCameraActivity.class)
                            .putExtra(IntentBuilder.KEY_TYPE, IdCardCameraActivity.TYPE_P)
                            .startActivity(getActivity(), IdCardCameraActivity.CODE_ID_CARD_P);
                } else if (position == 1) {//身份中反面
                    IntentBuilder.Builder(getActivity(), IdCardCameraActivity.class)
                            .putExtra(IntentBuilder.KEY_TYPE, IdCardCameraActivity.TYPE_N)
                            .startActivity(getActivity(), IdCardCameraActivity.CODE_ID_CARD_N);
                } else if (position == 2) {//营业执照
                    new SaActionSheetDialog(getContext())
                            .builder()
                            .addSheetItem("相册选取", OnSheetItemClickListener)
                            .addSheetItem("拍一张", OnSheetItemClickListener)
                            .show();
                }
            });

        }else {
            btn.setText("确定");
            btn.setOnClickListener(v -> {
                signPre.modifyPersonInfo(r -> {
                    if(r.status){
                        showTips(r.msg, TipType.Dialog);
                    }else {
                        error(r.msg);
                    }
                });
            });
        }
    }
    private SaActionSheetDialog.OnSheetItemClickListener OnSheetItemClickListener = new SaActionSheetDialog.OnSheetItemClickListener() {
        @Override
        public void onClick(int which) {
            Logger.e(which + "");
            switch (which) {
                case 2:
                    goCamera();//相机
                    break;
                case 1:
                    goGallry();//相册
                    break;
            }
        }
    };

    private void goGallry() {
        BaseActivity baseActivity = (BaseActivity) getContext();
        MultiImageSelector.create()
                .showCamera(true)
                .single()
                .start(baseActivity, PHOTO_SUCCESS_REQUEST);
    }

    private void goCamera() {
        PhotoUtil.photo(this, uri -> {
            signPre.license = PhotoUtil.getPath(getActivity(), uri);
        });
    }


    private View initHeadView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_person_info_head_layout, recyclerView, false);
        edName = findViewById(view,R.id.name);
        edNumber = findViewById(view,R.id.phone_numbers);
        edWork = findViewById(view,R.id.work);

        if(type == TYPE_LOOK){
            edName.setFocusable(false);
            edNumber.setFocusable(false);
            edWork.setFocusable(false);

            edName.setText("12312");
            edNumber.setText("123213");
            edWork.setText("13123");
        }else {
            bindUi(RxUtils.textChanges(edName), signPre.setPersonName());
            bindUi(RxUtils.textChanges(edNumber), signPre.setPersonPhoneNumber());
            bindUi(RxUtils.textChanges(edWork), signPre.setPersonWork());



        }

        return view;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_recyclerview_with_button_layout;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && data.hasExtra(IntentBuilder.KEY_DATA)) {
            if (IdCardCameraActivity.CODE_ID_CARD_P == requestCode) {
                IdCardPInfoEntity idCardInfoEntity = data.getParcelableExtra(IntentBuilder.KEY_DATA);
                signPre.name = idCardInfoEntity.name;
                signPre.sex = idCardInfoEntity.sex;
                signPre.address = idCardInfoEntity.address;
                signPre.familyName = idCardInfoEntity.nation;
                signPre.idCardNumber = idCardInfoEntity.id;
                AppCompatImageView imageView = (AppCompatImageView) adapter.getViewByPosition(recyclerView, 1, R.id.icon);
                imageView.setImageBitmap(BitmapFactory.decodeFile(idCardInfoEntity.frontimage));
                signPre.idCardP = idCardInfoEntity.frontimage;
            } else if (IdCardCameraActivity.CODE_ID_CARD_N == requestCode) {
                IdCardNInfoEntity idCardNInfoEntity = data.getParcelableExtra(IntentBuilder.KEY_DATA);
                signPre.organization = idCardNInfoEntity.authority;
                signPre.idCardDate = idCardNInfoEntity.valid_date;
                AppCompatImageView imageView = (AppCompatImageView) adapter.getViewByPosition(recyclerView, 2, R.id.icon);
                imageView.setImageBitmap(BitmapFactory.decodeFile(idCardNInfoEntity.backimage));
                signPre.idCardN = idCardNInfoEntity.backimage;
            }

        }

        if(requestCode == PHOTO_SUCCESS_REQUEST){
            if(data != null && data.hasExtra(MultiImageSelectorActivity.EXTRA_RESULT)){
                // Get the result list of select image paths
                List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                // do your logic ....
                signPre.license = path.get(0);
                AppCompatImageView imageView = (AppCompatImageView) adapter.getViewByPosition(recyclerView, 3, R.id.icon);
                imageView.setImageBitmap(BitmapFactory.decodeFile(path.get(0)));
            }
        }

        if(requestCode == PhotoUtil.CAMERA_SUCCESS && resultCode == -1){
            AppCompatImageView imageView = (AppCompatImageView) adapter.getViewByPosition(recyclerView, 3, R.id.icon);
            imageView.setImageBitmap(BitmapFactory.decodeFile(signPre.license));
        }
    }

}
