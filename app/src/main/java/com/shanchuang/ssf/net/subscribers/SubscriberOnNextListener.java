package com.shanchuang.ssf.net.subscribers;

/**
 * Created by Administrator on 2017/9/13.
 */

public interface SubscriberOnNextListener<T> {
    void onNext(T t);
}
