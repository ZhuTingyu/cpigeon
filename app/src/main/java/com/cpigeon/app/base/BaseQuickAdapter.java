package com.cpigeon.app.base;


import android.view.View;

import com.cpigeon.app.utils.CommonTool;
import com.cpigeon.app.utils.http.CommonUitls;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/1/11.
 */

public abstract class BaseQuickAdapter<T,K extends BaseViewHolder> extends com.chad.library.adapter.base.BaseQuickAdapter<T,K> {
    public BaseQuickAdapter(int layoutResId, List<T> data) {
        super(layoutResId, data);
    }



    public void setLoadMore(boolean isEnd) {
        if (isEnd) this.loadMoreEnd();
        else
            this.loadMoreComplete();
    }

    @Override
    public void setNewData(List<T> data) {
        super.setNewData(data);
        if(data.isEmpty()){
            if(!getEmptyViewText().isEmpty()){
                CommonTool.setEmptyView(this,getEmptyViewText());
            }
        }
    }

    protected  String getEmptyViewText(){
        return "";
    }
}
