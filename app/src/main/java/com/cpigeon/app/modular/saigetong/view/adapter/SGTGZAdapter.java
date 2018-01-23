package com.cpigeon.app.modular.saigetong.view.adapter;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.cpigeon.app.R;
import com.cpigeon.app.modular.saigetong.model.bead.SGTGzListEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/22.
 */

public class SGTGZAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_ORG = 1;
    public static final int TYPE_RACE = 2;
    private String TAG = "xiaohl";

    public SGTGZAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_ORG, R.layout.item_list_title);
//        addItemType(TYPE_CONTENT, R.layout.item_list_content);
        addItemType(TYPE_RACE, R.layout.item_list_con_content);
    }
    private OnItemClickListener  listener = new OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        }
    };

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {

        switch (helper.getItemViewType()) {
            case TYPE_ORG:
                SGTGzListEntity mSGTGzListEntity = ((OrgItem) item).orgInfo;
                CheckBox checkBox =  helper.getView(R.id.btn_cb);
                if (((OrgItem) item).orgInfo.getTag() == 1) {
                    checkBox.setChecked(false);
                } else {
                    checkBox.setChecked(true);
                }

                Log.d(TAG, "convert1: " + mSGTGzListEntity.getXingming() + "     " + String.valueOf(mSGTGzListEntity.getCount()));

                helper.setText(R.id.it_title1, mSGTGzListEntity.getXingming());
                helper.setText(R.id.it_title2, String.valueOf(mSGTGzListEntity.getCount()+ "羽"));
                if (mSGTGzListEntity.getData() != null && mSGTGzListEntity.getData().size() > 0) {
                    helper.setText(R.id.it_title3, String.valueOf(mSGTGzListEntity.getData().size()));
                }

                break;
            case TYPE_RACE:
                SGTGzListEntity.DataBean mContentData = ((RaceItem) item).race;
                Log.d(TAG, "convert2: " + mContentData.getFoot() + "    " + mContentData.getCskh());
                helper.setText(R.id.item_content1, mContentData.getFoot());
                helper.setText(R.id.item_content2, mContentData.getCskh());

                break;
        }
    }

    public static List<MultiItemEntity> get(List<SGTGzListEntity> data) {
        List<MultiItemEntity> result = new ArrayList<>();
        if (data == null) {
            return result;
        }
        OrgItem orgItem;
        RaceItem raceItem;
        if (data.size() > 0) {
            for (SGTGzListEntity orginfo : data) {
                orgItem = new OrgItem(orginfo);
                if (orginfo.getData() != null)
                    for (SGTGzListEntity.DataBean race : orginfo.getData()) {
                        raceItem = new RaceItem(race);
                        orgItem.addSubItem(raceItem);
                    }

                result.add(orgItem);
            }
        }

        return result;
    }


    public static class OrgItem extends AbstractExpandableItem<RaceItem> implements MultiItemEntity {

        SGTGzListEntity orgInfo;

        public OrgItem(SGTGzListEntity orgInfo) {
            this.orgInfo = orgInfo;
        }

        @Override
        public int getItemType() {
            return TYPE_ORG;
        }

        @Override
        public int getLevel() {
            return 0;
        }

        public SGTGzListEntity getOrgInfo() {
            return orgInfo;
        }
    }

    public static class RaceItem extends AbstractExpandableItem<SGTGzListEntity> implements MultiItemEntity {

        public SGTGzListEntity.DataBean getRace() {
            return race;
        }

        SGTGzListEntity.DataBean race;

        public RaceItem(SGTGzListEntity.DataBean race) {
            this.race = race;
        }

        @Override
        public int getItemType() {
            return TYPE_RACE;
        }

        @Override
        public int getLevel() {
            return 1;
        }
    }
}
