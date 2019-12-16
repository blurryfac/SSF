package com.shanchuang.ssf.net.entity;

/**
 * Created by JY on 2017/12/30.
 */

public class WeiXinBean {


    @Override
    public String toString() {
        return "WeiXinBean{" +
                "appid='" + appid + '\'' +
                ", noncestr='" + noncestr + '\'' +
                ", partnerid='" + partnerid + '\'' +
                ", prepayid='" + prepayid + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }

    /**
     * appid : wx578c1f344905af1b
     * noncestr : oQIpL0kWIPypy6Q2QXv94TRED4G6mGXk
     * package : Sign=WXPay
     * partnerid : 1495150992
     * prepayid : wx201712300959215724d5a60e0470071355
     * timestamp : 1514599161
     * sign : 335A8ED7ECF0F8024ABCF115124D1D8C
     */

    private String appid;
    private String noncestr;
    private String partnerid;
    private String prepayid;
    private String timestamp;
    private String sign;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }


    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
