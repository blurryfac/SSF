package com.shanchuang.ssf.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shanchuang.ssf.R;

import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/20.
 */

public class adderView extends LinearLayout implements View.OnClickListener {


    private int value = 1;
    private int minValue = 1;
    private int maxValue = 10;
    private final TextView tvCount;

    private TextView add;
    private TextView reduce;
    public adderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(context, R.layout.number_adder, this);
        TextView btn_reduce = (TextView) view.findViewById(R.id.btn_reduce);
        tvCount = (TextView) view.findViewById(R.id.tv_count);
        TextView btn_add = (TextView) view.findViewById(R.id.btn_add);
        btn_reduce.setOnClickListener(this);
        btn_add.setOnClickListener(this);
        this.add=btn_add;
        this.reduce=btn_reduce;
        //设置默认值
        int value = getValue();
        setValue(value);
    }
public void setEnable()
{
    this.add.setEnabled(false);
    this.reduce.setEnabled(false);
}
    @OnClick({R.id.btn_reduce, R.id.btn_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_reduce://减
                reduce();
                break;
            case R.id.btn_add://加
                add();
                break;
        }
    }

    /**
     * 如果当前值大于最小值   减
     */
    private void reduce() {
        if (value > minValue) {
            value--;
        }
        setValue(value);
//        if(value==1){
//            reduce.setBackgroundResource(R.drawable.btn_gray_5_shape);
//        }else {
//            reduce.setBackgroundResource(R.drawable.btn_red_5_shape);
//
//        }
//        reduce.setPadding(RxImageTool.dp2px(3),0,RxImageTool.dp2px(3),0);
        if (onValueChangeListene != null) {

            onValueChangeListene.onValueChange(value,reduce);
        }
    }

    /**
     * 如果当前值小于最小值  加
     */
    private void add() {
        if (value < maxValue) {
            value++;
        }
        setValue(value);
//        if(value==1){
//            reduce.setBackgroundResource(R.drawable.btn_gray_5_shape);
//        }else {
//            reduce.setBackgroundResource(R.drawable.btn_red_5_shape);
//
//        }
//        reduce.setPadding(RxImageTool.dp2px(3),0,RxImageTool.dp2px(3),0);
        if (onValueChangeListene != null) {

            onValueChangeListene.onValueChange(value,reduce);
        }
    }

    //获取具体值
    public int getValue() {
        String countStr = tvCount.getText().toString().trim();
        if (countStr != null) {
            value = Integer.valueOf(countStr);
        }
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        tvCount.setText(value + "");
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reduce://减
                reduce();
                break;
            case R.id.btn_add://加
                add();
                break;
        }
    }


    //监听回调
    public interface OnValueChangeListener {
        public void onValueChange(int value, View btn_reduce);
    }

    private OnValueChangeListener onValueChangeListene;

    public void setOnValueChangeListene(OnValueChangeListener onValueChangeListene) {
        this.onValueChangeListene = onValueChangeListene;
    }
}