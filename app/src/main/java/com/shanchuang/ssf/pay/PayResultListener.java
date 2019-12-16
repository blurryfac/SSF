package com.shanchuang.ssf.pay;

/**
 * Author: syhuang
 * Date:  2018/2/2
 * 支付返回结果
 */

public interface PayResultListener {
    public void onPaySuccess();

    public void onPayError();

    public void onPayCancel();
}

