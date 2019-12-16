package com.shanchuang.ssf.event;

public class MessageEvent{
    private String tag;
    private String message;

    public MessageEvent(String tag, String message) {
        this.tag = tag;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}