package com.cpigeon.app.even;

import com.cpigeon.app.entity.PersonInfoEntity;

/**
 * Created by Zhu TingYu on 2017/12/1.
 */

public class PersonInfoEvent {
    public int type;
    public PersonInfoEvent(int type){
        this.type = type;
    }
}
