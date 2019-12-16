package com.shanchuang.ssf.adapter;

import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.bean.SubJectNumBean;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/10/17
 */
public class SubChoiceAdapter extends BaseQuickAdapter<SubJectNumBean.TihaoBean, BaseViewHolder> {
    public SubChoiceAdapter(int layoutResId, @Nullable List<SubJectNumBean.TihaoBean> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, SubJectNumBean.TihaoBean item) {
       TextView mTvItem= helper.getView(R.id.tv_item_num);
        if(item.getIs_zq()==-1){
            if(item.isCheck()){
                mTvItem.setBackgroundResource(R.drawable.btn_blue_line_shape);
            }else {
                mTvItem.setBackgroundResource(R.drawable.btn_white_black_line_shape);
            }
            mTvItem.setTextColor(mContext.getResources().getColor(R.color.black));
        }else if(item.getIs_zq()==0){
            if(item.isCheck()){
                mTvItem.setBackgroundResource(R.drawable.btn_pink_line_shape);
            }else {
                mTvItem.setBackgroundResource(R.drawable.btn_pink_shape);
            }

            mTvItem.setTextColor(mContext.getResources().getColor(R.color.color_red747));
        }else if(item.getIs_zq()==1){
            if(item.isCheck()){
                mTvItem.setBackgroundResource(R.drawable.btn_green_line_shape);
            }else {
                mTvItem.setBackgroundResource(R.drawable.btn_green_shape);
            }

            mTvItem.setTextColor(mContext.getResources().getColor(R.color.color_green957));
        }
        helper.setText(R.id.tv_item_num,String.valueOf(helper.getAdapterPosition()+1));
    }
}
