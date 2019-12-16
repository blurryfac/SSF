package com.shanchuang.ssf.view;

import android.content.Context;

import com.tencent.smtt.sdk.WebView;

public class TestWebView extends WebView {
    public TestWebView(Context context) {
        super(context);
    }

    public interface OnOverScrollListener {
        void onOverScrolled(TestWebView v, boolean onBottom);
    }
    private OnOverScrollListener mOnOverScrollListener;
    public void setOnOverScrollListener(OnOverScrollListener listener) {
        this.mOnOverScrollListener = listener;
    }
    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if (mOnOverScrollListener != null) {
            // clampedY=true的前提下，scrollY=0时表示滑动到顶部，scrollY!=0时表示到底部
            mOnOverScrollListener.onOverScrolled(this, scrollY != 0 && clampedY);
        }
    }
}
