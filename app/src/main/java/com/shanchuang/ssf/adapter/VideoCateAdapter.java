package com.shanchuang.ssf.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.bean.VideoCateBean;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/10/14
 */
public class VideoCateAdapter extends BaseQuickAdapter<VideoCateBean.CateBean, BaseViewHolder> {
    public VideoCateAdapter(int layoutResId, @Nullable List<VideoCateBean.CateBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoCateBean.CateBean item) {
        if(item.isCheck()){
            helper.getView(R.id.tv_choice).setVisibility(View.VISIBLE);
            ((TextView)helper.getView(R.id.tv_title)).setTextColor(mContext.getResources().getColor(R.color.colorBtn));
            ((TextView)helper.getView(R.id.tv_title)).setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }else {
            helper.getView(R.id.tv_choice).setVisibility(View.GONE);
            ((TextView)helper.getView(R.id.tv_title)).setBackgroundColor(mContext.getResources().getColor(R.color.f5));
            ((TextView)helper.getView(R.id.tv_title)).setTextColor(mContext.getResources().getColor(R.color.black));
        }
        helper.setText(R.id.tv_title,item.getTitle());
    }
}
