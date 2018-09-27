package com.guc.ui.dialogchooseinfo;

import android.os.Parcel;
import android.os.Parcelable;

public class BeanChooseOption implements Parcelable {
    public static final Creator<BeanChooseOption> CREATOR = new Creator<BeanChooseOption>() {
        @Override
        public BeanChooseOption createFromParcel(Parcel in) {
            return new BeanChooseOption(in);
        }

        @Override
        public BeanChooseOption[] newArray(int size) {
            return new BeanChooseOption[size];
        }
    };
    public String key;
    public String value;
    public boolean selected;

    public BeanChooseOption() {

    }

    protected BeanChooseOption(Parcel in) {
        key = in.readString();
        value = in.readString();
        selected = in.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(value);
        dest.writeByte((byte) (selected ? 1 : 0));
    }


}
