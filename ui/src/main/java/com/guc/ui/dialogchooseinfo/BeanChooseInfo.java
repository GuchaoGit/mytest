package com.guc.ui.dialogchooseinfo;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class BeanChooseInfo implements Parcelable {
    public static final Creator<BeanChooseInfo> CREATOR = new Creator<BeanChooseInfo>() {
        @Override
        public BeanChooseInfo createFromParcel(Parcel source) {
            return new BeanChooseInfo(source);
        }

        @Override
        public BeanChooseInfo[] newArray(int size) {
            return new BeanChooseInfo[size];
        }
    };
    public String title;
    public List<BeanChooseOption> beanChooseOptionList;

    public BeanChooseInfo() {
    }

    protected BeanChooseInfo(Parcel in) {
        this.title = in.readString();
        this.beanChooseOptionList = new ArrayList<BeanChooseOption>();
        in.readList(this.beanChooseOptionList, BeanChooseOption.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeList(this.beanChooseOptionList);
    }
}
