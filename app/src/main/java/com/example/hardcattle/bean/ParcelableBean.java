package com.example.hardcattle.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

/**
 * Created by guc on 2018/4/18.
 * 描述：序列化bean测试
 */
public class ParcelableBean implements Parcelable {
    private boolean isRight;
    private String name;
    private int age;
    private long createTime;

    @Override
    public int describeContents() {
        return 0;
    }

    public ParcelableBean(){
    }
    public static final Creator<ParcelableBean> CREATOR = new Creator<ParcelableBean>() {
        @Override
        public ParcelableBean createFromParcel(Parcel source) {
            ParcelableBean bean = new ParcelableBean();
            bean.setRight(source.readByte()!=0);
            bean.setName(source.readString());
            bean.setAge(source.readInt());
            bean.setCreateTime(source.readLong());
            return bean;
        }

        @Override
        public ParcelableBean[] newArray(int size) {
            return new ParcelableBean[size];
        }
    };
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isRight?1:0));
        dest.writeString(name);
        dest.writeInt(age);
        dest.writeLong(createTime);
    }

    public boolean isRight() {
        return isRight;
    }

    public void setRight(boolean right) {
        isRight = right;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return  gson.toJson(this);
    }
}
