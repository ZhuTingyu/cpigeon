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
import com.cpigeon.app.event.PersonInfoEvent;
import com.cpigeon.app.message.adapter.PersonImageInfoAdapter;
import com.cpigeon.app.message.ui.idCard.IdCardCameraActivity;
import com.cpigeon.app.message.ui.modifysign.PersonSignPre;
import com.cpigeon.app.utils.DialogUtils;
import com.cpigeon.app.utils.FileTool;
import com.cpigeon.app.utils.IntentBuilder;
import com.cpigeon.app.utils.Lists;
import com.cpigeon.app.utils.RxUtils;
import com.cpigeon.app.utils.StringValid;
import com.cpigeon.app.utils.ToastUtil;
import com.cpigeon.app.utils.customview.SaActionSheetDialog;
import com.cpigeon.app.utils.http.GsonUtil;
import com.cpigeon.app.utils.http.LogUtil;
import com.cpigeon.app.utils.inputfilter.PhotoUtil;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    public static final int TYPE_UPLOAD_INFO = 2;

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
        EventBus.getDefault().register(this);
        signPre = new PersonSignPre(getActivity());
        type = getActivity().getIntent().getIntExtra(IntentBuilder.KEY_TYPE, 0);
        imgs = Lists.newArrayList("idCard_P", "idCard_N", "license");
        hideSoftInput();
        if(type == TYPE_LOOK){
            setTitle("个人信息");
            getPersonInfo();
        }else if(type == TYPE_EDIT){
            setTitle("个人信息");
            entity = getActivity().getIntent().getParcelableExtra(IntentBuilder.KEY_DATA);
        }else if(type == TYPE_UPLOAD_INFO){
            setTitle("提交个人信息");
        }
        initView();
    }


    private void getPersonInfo() {
        showLoading();
        signPre.getPersonInfo(personInfoEntity -> {
            hideLoading();
            if(personInfoEntity != null){
                entity = personInfoEntity;
                FileTool.byte2File(entity.sfzzm,imgs.get(0));
                FileTool.byte2File(entity.sfzbm,imgs.get(1));
                FileTool.byte2File(entity.yyzz,imgs.get(2));
                bindData();
            }
        });
    }

    private void bindData(){
        adapter.setNewData(imgs);
        edName.setText(StringValid.isStringValid(entity.xingming) ? entity.xingming : "无");
        edNumber.setText(StringValid.isStringValid(entity.sjhm) ? entity.sjhm : "无");
        edWork.setText(StringValid.isStringValid(entity.dwmc) ? entity.dwmc : "无");
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

        if(type == TYPE_LOOK){
            adapter.setNewData(Lists.newArrayList("","",""));
            btn.setText("编辑");
            btn.setOnClickListener(v -> {
                IntentBuilder.Builder()
                        .putExtra(IntentBuilder.KEY_TYPE, PersonInfoFragment.TYPE_EDIT)
                        .putExtra(IntentBuilder.KEY_DATA, entity)
                        .startParentActivity(getActivity(), PersonInfoFragment.class);
            });


        }else if(type == TYPE_EDIT){
            bindData(entity);

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

            btn.setText("确定");
            btn.setOnClickListener(v -> {
                showTips("正在修改", TipType.LoadingShow);
                signPre.modifyPersonInfo(r -> {
                    showTips("", TipType.LoadingHide);
                    if(r.status){
                        PersonInfoEvent personInfoEvent = new PersonInfoEvent(TYPE_LOOK);
                        PersonInfoEntity personInfoEntity = new PersonInfoEntity();
                        personInfoEntity.xingming = signPre.personName;
                        personInfoEntity.sjhm = signPre.personPhoneNumber;
                        personInfoEntity.dwmc = signPre.personWork;
                        personInfoEvent.entity = personInfoEntity;
                        EventBus.getDefault().post(personInfoEvent);
                        ToastUtil.showLongToast(getContext(),"修改成功");
                        finish();
                    }else {
                        error(r.msg);
                    }
                });
            });
        }else if(type == TYPE_UPLOAD_INFO){
            //TODO 开通提交鸽信通个人信息
            adapter.setNewData(Lists.newArrayList("","",""));
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
            btn.setText("提交");
            btn.setOnClickListener(v -> {
                showLoading("正在提交");
                signPre.uploadPersonInfo(r -> {
                    hideLoading();
                    LogUtil.print(r.toJsonString());
                    if(r.status){
                        DialogUtils.createDialog(getContext(), r.msg, sweetAlertDialog -> {
                            sweetAlertDialog.dismiss();
                            finish();
                        });
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

                edName.setText(idCardInfoEntity.name);
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

    private void bindData(PersonInfoEntity entity){
        edName.setText(entity.xingming != null ? entity.xingming : "");
        edNumber.setText(entity.sjhm != null ? entity.sjhm : "");
        edWork.setText(entity.dwmc != null ? entity.dwmc : "");
        adapter.setNewData(imgs);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PersonInfoEvent event){
        if(event.type == TYPE_LOOK){
            PersonInfoEntity personInfoEntity = event.entity;
            bindData(personInfoEntity);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
