package com.shanchuang.ssf.bean;

import java.math.BigDecimal;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/11/8
 */
public class AllCoupon {
    private String id;
    private BigDecimal coupon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getCoupon() {
        return coupon;
    }

    public void setCoupon(BigDecimal coupon) {
        this.coupon = coupon;
    }
}
