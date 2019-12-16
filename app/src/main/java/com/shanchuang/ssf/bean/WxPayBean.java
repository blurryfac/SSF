package com.shanchuang.ssf.bean;

import com.shanchuang.ssf.net.entity.WeiXinBean;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/10/30
 */
public class WxPayBean {
        /**
         * pay : {"appid":"wx7ed3cc4129223295","noncestr":"djKhMqdUrYGWmSPOCpv3EJGhqHDdF5Vr","package":"Sign=WXPay","partnerid":"1551458231","prepayid":"wx30170602386690cffcc09bf31324040300","timestamp":1572426360,"sign":"EC7193D3D361AC0C260548B7743D74BE"}
         */

        private WeiXinBean pay;

    public WeiXinBean getPay() {
        return pay;
    }

    public void setPay(WeiXinBean pay) {
        this.pay = pay;
    }
}
