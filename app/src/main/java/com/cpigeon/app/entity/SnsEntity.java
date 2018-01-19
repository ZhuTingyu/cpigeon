package com.cpigeon.app.entity;

/**
 * Created by Zhu TingYu on 2018/1/12.
 */

public class SnsEntity {

    /**
     * 当前用户是否对该信息进行点赞
     */

    public int zan;

    public boolean isThumb(){
        return zan != 0; //0为取消点赞
    }

    public void setThumb(){
        zan = 1;
    }
    public void setCancelThumb(){
        zan = 0;
    }

}
