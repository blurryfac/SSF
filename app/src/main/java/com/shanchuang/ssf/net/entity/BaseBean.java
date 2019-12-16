package com.shanchuang.ssf.net.entity;

import java.io.Serializable;

/**
 * Created by liukun on 16/3/5.
 */
public class BaseBean<T> implements Serializable {
    private int code;
    //用来模仿Data
    private T data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
