package com.shanchuang.ssf.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.viewpager.widget.ViewPager;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class CustomViewPager extends ViewPager {

        private int current;
        private int height = 0;
        /**
         * 保存position与对于的View
         */
        private HashMap<Integer, View> mChildrenViews = new LinkedHashMap<Integer, View>();

        private boolean scrollble = true;

        public CustomViewPager(Context context)
        {
            super(context);
        }

        public CustomViewPager(Context context, AttributeSet attrs)
        {
            super(context, attrs);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
        {
            if (mChildrenViews.get(current)!=null) {
                View child = mChildrenViews.get(current);
                child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                height = child.getMeasuredHeight();
            }

            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

        public void resetHeight(int current) {
            this.current = current;
//            if (mChildrenViews.size() > current) {

                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
                if (layoutParams == null) {
                    layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
                } else {
                    layoutParams.height = height;
                }
                setLayoutParams(layoutParams);
//            }
        }
        /**
         * 保存position与对于的View
         */
        public void setObjectForPosition(View view, int position)
        {
            mChildrenViews.put(position, view);
        }


        @Override
        public boolean onTouchEvent(MotionEvent ev) {
            if (!scrollble) {
                return true;
            }
            return super.onTouchEvent(ev);
        }


        public boolean isScrollble() {
            return scrollble;
        }

        public void setScrollble(boolean scrollble) {
            this.scrollble = scrollble;
        }
}
