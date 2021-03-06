package com.cpigeon.app.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Zhu TingYu on 2017/11/27.
 */

public class ContactsEntity extends MultiSelectEntity implements Parcelable {
    public int id;//整数，传递给修改接口
    public int fzid;//整数，分组ID
    public String xingming;    //姓名
    public String sjhm;    //手机号码
    public String beizhu;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.fzid);
        dest.writeString(this.xingming);
        dest.writeString(this.sjhm);
        dest.writeString(this.beizhu);
    }

    public ContactsEntity() {
    }

    protected ContactsEntity(Parcel in) {
        this.id = in.readInt();
        this.fzid = in.readInt();
        this.xingming = in.readString();
        this.sjhm = in.readString();
        this.beizhu = in.readString();
    }

    public static final Parcelable.Creator<ContactsEntity> CREATOR = new Parcelable.Creator<ContactsEntity>() {
        @Override
        public ContactsEntity createFromParcel(Parcel source) {
            return new ContactsEntity(source);
        }

        @Override
        public ContactsEntity[] newArray(int size) {
            return new ContactsEntity[size];
        }
    };
}
