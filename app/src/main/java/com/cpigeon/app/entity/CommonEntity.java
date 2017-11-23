package com.cpigeon.app.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Zhu TingYu on 2017/11/23.
 */

public class CommonEntity extends MultiSelectEntity implements Parcelable {
    public int id;
    public String dxnr; //短语内容

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.dxnr);
    }

    public CommonEntity() {
    }

    protected CommonEntity(Parcel in) {
        this.id = in.readInt();
        this.dxnr = in.readString();
    }

    public static final Parcelable.Creator<CommonEntity> CREATOR = new Parcelable.Creator<CommonEntity>() {
        @Override
        public CommonEntity createFromParcel(Parcel source) {
            return new CommonEntity(source);
        }

        @Override
        public CommonEntity[] newArray(int size) {
            return new CommonEntity[size];
        }
    };
}
