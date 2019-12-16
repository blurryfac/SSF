package com.shanchuang.ssf.adapter;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.bean.HotBean;
import com.shanchuang.ssf.view.ImageViewPlus;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/12/4
 */
public class HotAdapter extends BaseQuickAdapter<HotBean, BaseViewHolder> {
    public HotAdapter(int layoutResId, @Nullable List<HotBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HotBean item) {
        helper.setText(R.id.tv_hot_item_title,item.getTitle());
        helper.setText(R.id.tv_hot_item_time,item.getTime());
        helper.setText(R.id.tv_hot_item_num,"浏览："+item.getNum());
        Glide.with(mContext).load(item.getNum()).into((ImageViewPlus)helper.getView(R.id.iv_logo));
    }
}
