package com.cpigeon.app.modular.matchlive.view.adapter;

import android.graphics.Color;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.cpigeon.app.R;
import com.cpigeon.app.modular.matchlive.model.bean.GeCheJianKongOrgInfo;
import com.cpigeon.app.modular.matchlive.model.bean.GeCheJianKongRace;
import com.cpigeon.app.modular.matchlive.model.bean.MatchInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenshuai on 2017/7/11.
 */

public class GeCheJianKongExpandListAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    public static final int TYPE_ORG = 1;
    public static final int TYPE_RACE = 2;

    private int _redColor = Color.parseColor("#ff3a3a");
    private int _greenColor = Color.parseColor("#2c9c00");
    private int _blueColor = Color.parseColor("#2f96ff");

    private static final int STATE_NOT_MONITOR = 0;
    private static final int STATE_MONITORING = 1;
    private static final int STATE_END_OF_MONITOR = 2;

    public GeCheJianKongExpandListAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_ORG, R.layout.listitem_gechejiankong_title);
        addItemType(TYPE_RACE, R.layout.listitem_gechejiankong_race);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, MultiItemEntity multiItemEntity) {
        switch (baseViewHolder.getItemViewType()) {
            case TYPE_ORG:
                //((OrgItem)multiItemEntity).orgInfo
                baseViewHolder.setText(R.id.tv_item_orgname, ((OrgItem) multiItemEntity).orgInfo.getOrgName());
                boolean isNoRace = ((OrgItem) multiItemEntity).orgInfo.getRaces() == null || ((OrgItem) multiItemEntity).orgInfo.getRaces().size() == 0;
                baseViewHolder.setText(R.id.tv_item_state, isNoRace ? "无监控" : isHaveMonitoring(multiItemEntity) ? "监控中" : "监控结束");
                baseViewHolder.setTextColor(R.id.tv_item_state, isNoRace ? _redColor : isHaveMonitoring(multiItemEntity) ? _greenColor : _blueColor);
                AppCompatImageView downImg = baseViewHolder.getView(R.id.aciv_item_expand);
                downImg.setVisibility(isNoRace ? View.INVISIBLE : View.VISIBLE);
                downImg.setRotation(((OrgItem) multiItemEntity).isExpanded() ? 180 : 0);
                break;
            case TYPE_RACE:
                baseViewHolder.setText(R.id.tv_item_racename, ((RaceItem) multiItemEntity).race.getRaceName());
                baseViewHolder.setText(R.id.tv_item_state, ((RaceItem) multiItemEntity).race.getState());
                baseViewHolder.setTextColor(R.id.tv_item_state, ((RaceItem) multiItemEntity).race.getStateCode() == 0 ? _redColor : ((RaceItem) multiItemEntity).race.getStateCode() == 1 ? _greenColor : _blueColor);
                break;
        }
    }

    public static List<MultiItemEntity> get(List<GeCheJianKongOrgInfo> data) {
        List<MultiItemEntity> result = new ArrayList<>();
        if (data == null)
            return result;
        OrgItem orgItem;
        RaceItem raceItem;
        if (data.size() > 0) {
            for (GeCheJianKongOrgInfo orginfo : data) {
                orgItem = new OrgItem(orginfo);
                if (orginfo.getRaces() != null)
                    for (GeCheJianKongRace race : orginfo.getRaces()) {
                        raceItem = new RaceItem(race);
                        orgItem.addSubItem(raceItem);
                    }
                result.add(orgItem);
            }
        }
        return result;
    }


    public static class OrgItem extends AbstractExpandableItem<RaceItem> implements MultiItemEntity {

        GeCheJianKongOrgInfo orgInfo;

        public OrgItem(GeCheJianKongOrgInfo orgInfo) {
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

        public GeCheJianKongOrgInfo getOrgInfo() {
            return orgInfo;
        }
    }

    public static class RaceItem implements MultiItemEntity {
        public GeCheJianKongRace getRace() {
            return race;
        }

        GeCheJianKongRace race;

        public RaceItem(GeCheJianKongRace race) {
            this.race = race;
        }

        @Override
        public int getItemType() {
            return TYPE_RACE;
        }
    }

    private boolean isHaveMonitoring(MultiItemEntity itemEntity) {
        List<GeCheJianKongRace> data = ((OrgItem) itemEntity).getOrgInfo().getRaces();
        for (GeCheJianKongRace g : data) {
            if (g.getStateCode() == STATE_MONITORING){
                return true;
            }
        }
        return false;
    }

}
