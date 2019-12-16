package com.shanchuang.ssf.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;

import com.shanchuang.ssf.utils.Scroller;


/**
 * 描述：添加类的描述
 *
 * @author
 * @time 2018/11/13
 */
public class ScrollTextView extends androidx.appcompat.widget.AppCompatTextView {

    private Scroller mScroller;
    private OnScrollListener onScrollListener;
    private int time = 100000;
    private Context mContext;
    private int shutCode = 0;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    public ScrollTextView(Context context) {
        super(context);
        mContext = context;
        mScroller = new Scroller(context);
    }

    public ScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mScroller = new Scroller(context);

    }

    public ScrollTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        mScroller = new Scroller(context);

    }


    public int getShutCode() {
        return shutCode;
    }

    public void setShutCode(int shutCode) {
        this.shutCode = shutCode;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    //调用此方法滚动到目标位置  duration滚动时间
    public void smoothScrollToSlow(int fx, int fy, int duration) {
        int dx = fx - getScrollX();//mScroller.getFinalX();  普通view使用这种方法
        int dy = fy - getScrollY();  //mScroller.getFinalY();
        smoothScrollBySlow(dx, dy, duration);
    }

    //调用此方法设置滚动的相对偏移
    public void smoothScrollBySlow(int dx, int dy, int duration) {

        //设置mScroller的滚动偏移量
        mScroller.startScroll(getScrollX(), getScrollY(), dx, dy, duration);//scrollView使用的方法（因为可以触摸拖动）
//        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy, duration);  //普通view使用的方法
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    public void forceFinished() {
        mScroller.forceFinished(true);
    }

    @Override
    public void computeScroll() {

        //先判断mScroller滚动是否完成
        if (mScroller.computeScrollOffset()) {

            //这里调用View的scrollTo()完成实际的滚动
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            //必须调用该方法，否则不一定能看到滚动效果
            postInvalidate();
        } else {
            if (mScroller.getTimePassed() != 0) {
                Log.e("111", "111");
                Intent intent = new Intent("scrollSuccess");
                intent.putExtra("code", shutCode);
                mContext.sendBroadcast(intent);
                mScroller.setTimePassed(0);
            }

//            shutCode++;


        }
        super.computeScroll();
    }

    @Override
    protected void onScrollChanged(int horiz, int vert, int oldHoriz, int oldVert) {
        super.onScrollChanged(horiz, vert, oldHoriz, oldVert);
        if (onScrollListener != null) {
            onScrollListener.onScrollChanged(horiz, vert, oldHoriz, oldVert);
        }
    }

    /**
     * @param onScrollListener
     */
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    public interface OnScrollListener {
        public void onScrollChanged(int x, int y, int oldX, int oldY);

        public void onScrollStopped();

        public void onScrolling();
    }
}
