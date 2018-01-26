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
import com.cpigeon.app.utils.Lists;
import com.cpigeon.app.viewholder.PigeonCarMonitorViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenshuai on 2017/7/11.
 */

public class GeCheJianKongExpandListAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    public static final int TYPE_ORG = 1;
    public static final int TYPE_RACE = 2;
    List<Integer> icons;


    public GeCheJianKongExpandListAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_ORG, R.layout.listitem_gechejiankong_title);
        addItemType(TYPE_RACE, R.layout.listitem_gechejiankong_race);
        icons = Lists.newArrayList(R.mipmap.ic_vertical_blue, R.mipmap.ic_vertical_yellow
                , R.mipmap.ic_vertical_withe);
    }

    @Override
    protected void convert(BaseViewHolder holder, MultiItemEntity multiItemEntity) {
        switch (holder.getItemViewType()) {
            case TYPE_ORG:
                GeCheJianKongOrgInfo data = ((OrgItem) multiItemEntity).orgInfo;
                /*holder.bindData(orgInfo);
                holder.setItemColor(orgInfo.isRace());*/
                holder.setText(R.id.match_count, String.valueOf(data.getRaces().size()));
                holder.setText(R.id.monitoring_count,String.valueOf(data.getMonitoringCount()));
                holder.setText(R.id.end_count,String.valueOf(data.getEndMonitorCount()));
                holder.setText(R.id.not_start_count, String.valueOf(data.getNotMonitorCount()));
                holder.setText(R.id.title, data.getOrgName());

                if (data.getMonitoringCount() != 0) {
                    holder.setText(R.id.state,"监控中");
                    holder.setBackgroundColor(R.id.line1, R.color.white);
                    holder.setBackgroundColor(R.id.line1, R.color.color_blue_57bbdfa);
                } else {
                    holder.setText(R.id.state,"监控结束");
                    holder.setBackgroundColor(R.id.line1, R.color.white);
                    holder.setBackgroundColor(R.id.line1, R.color.color_yellow_f49562);
                }

                if (holder.getAdapterPosition() % 2 == 0) {
                    holder.setImageResource(R.id.icon_image, icons.get(0));
                } else holder.setImageResource(R.id.icon_image, icons.get(1));

                /*if(data.isRace()){
                    holder.setTextColor(R.id.state, R.color.white);
                    holder.setTextColor(R.id.title,R.color.white);
                    holder.setTextColor(R.id.text1,R.color.white);
                    holder.setTextColor(R.id.text2,R.color.white);
                    holder.setTextColor(R.id.text3,R.color.white);
                    holder.setTextColor(R.id.text4,R.color.white);
                    holder.setTextColor(R.id.match_count,R.color.white);
                    holder.setTextColor(R.id.match_count,R.color.white);
                    holder.setTextColor(R.id.match_count,R.color.white);
                    holder.setTextColor(R.id.not_start_count,R.color.white);
                    holder.setImageResource(R.id.icon_image, icons.get(2));
                    if(holder.getAdapterPosition() % 2 == 0){
                        holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.color_blue_57bbdfa));
                    }else holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.color_yellow_f49562));
                }else {
                    holder.setTextColor(R.id.state,R.color.black);
                    holder.setTextColor(R.id.title,R.color.gray_m);
                    holder.setTextColor(R.id.text1,R.color.gray_m);
                    holder.setTextColor(R.id.text1,R.color.gray_m);
                    holder.setTextColor(R.id.text1,R.color.gray_m);
                    holder.setTextColor(R.id.text1,(R.color.gray_m));
                    holder.setTextColor(R.id.match_count,(R.color.black));
                    holder.setTextColor(R.id.match_count,R.color.black);
                    holder.setTextColor(R.id.match_count,R.color.black);
                    holder.setTextColor(R.id.not_start_count,R.color.black);
                }*/


                break;
            case TYPE_RACE:
                holder.setText(R.id.tv_item_racename, ((RaceItem) multiItemEntity).race.getRaceName());
                holder.setText(R.id.tv_item_state, ((RaceItem) multiItemEntity).race.getState());
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

    private void setItemColor(BaseViewHolder holder){

    }

}
