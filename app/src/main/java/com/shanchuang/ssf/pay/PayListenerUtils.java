package com.shanchuang.ssf.pay;

import android.content.Context;

import java.util.ArrayList;

public class PayListenerUtils {

    private static PayListenerUtils instance;
    private Context mContext;

    private final static ArrayList<PayResultListener> resultList = new ArrayList<>();




    private PayListenerUtils(Context context) {
        this.mContext = context;
        //TODO

    }

    public synchronized static PayListenerUtils getInstance(Context context) {
        if (instance == null) {
            instance = new PayListenerUtils(context);
        }
        return instance;
    }

    public void addListener(PayResultListener listener) {
        if (!resultList.contains(listener)) {
            resultList.add(listener);
        }
    }

    public void removeListener(PayResultListener listener) {
        if (resultList.contains(listener)) {
            resultList.remove(listener);
        }
    }

    public void addSuccess() {
        for (PayResultListener listener : resultList) {
            listener.onPaySuccess();
        }
    }

    public void addCancel() {
        for (PayResultListener listener : resultList) {
            listener.onPayCancel();
        }
    }

    public void addError() {
        for (PayResultListener listener : resultList) {
            listener.onPayError();
        }
    }
}
