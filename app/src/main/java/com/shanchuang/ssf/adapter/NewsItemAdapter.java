package com.shanchuang.ssf.adapter;

import android.view.View;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.bean.NewsItemBean;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/12/16
 */
public class NewsItemAdapter extends BaseQuickAdapter<NewsItemBean, BaseViewHolder> {
    public NewsItemAdapter(int layoutResId, @Nullable List<NewsItemBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsItemBean item) {
        helper.setText(R.id.tv_msg_title,item.getTitle());
        helper.setText(R.id.tv_msg_content,item.getContent());
        helper.setText(R.id.tv_msg_time,item.getTime());
        if(item.getStatus()==1){
            helper.getView(R.id.iv_red).setVisibility(View.VISIBLE);
            helper.getView(R.id.tv_msg_status).setVisibility(View.VISIBLE);
        }        else {
            helper.getView(R.id.iv_red).setVisibility(View.INVISIBLE);
            helper.getView(R.id.tv_msg_status).setVisibility(View.INVISIBLE);
        }
    }
}
