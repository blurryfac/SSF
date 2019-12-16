package com.shanchuang.ssf.net.entity;

/**
 * Created by JY on 2017/11/19.
 */

public class RegBean {
    private String uid;
    private String mobile;
    private String unid;
    private String uname;

    public String getUnid() {
        return unid;
    }

    public void setUnid(String unid) {
        this.unid = unid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    @Override
    public String toString() {
        return "RegBean{" +
                "uid='" + uid + '\'' +
                ", mobile='" + mobile + '\'' +
                ", unid='" + unid + '\'' +
                ", uname='" + uname + '\'' +
                '}';
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
