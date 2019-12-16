package com.shanchuang.ssf.bean;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/11/4
 */
public class AnswerBean {
    private String name;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AnswerBean(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
