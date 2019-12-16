package com.shanchuang.ssf.bean;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/12/13
 */
public class ReleaseCateBean {
    private int status;
    private int drawable;

    private String title;

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
