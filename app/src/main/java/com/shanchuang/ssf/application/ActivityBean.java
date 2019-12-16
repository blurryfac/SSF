package com.shanchuang.ssf.application;

import android.os.Parcel;
import android.os.Parcelable;

import butterknife.Unbinder;

public class ActivityBean implements Parcelable {
    private Unbinder unbinder;

    protected ActivityBean(Parcel in) {
    }

    public static final Creator<ActivityBean> CREATOR = new Creator<ActivityBean>() {
        @Override
        public ActivityBean createFromParcel(Parcel in) {
            return new ActivityBean(in);
        }

        @Override
        public ActivityBean[] newArray(int size) {
            return new ActivityBean[size];
        }
    };

    public ActivityBean() {

    }

    public void setUnbinder(Unbinder unbinder) {
        this.unbinder = unbinder;
    }

    public Unbinder getUnbinder() {
        return unbinder;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}

