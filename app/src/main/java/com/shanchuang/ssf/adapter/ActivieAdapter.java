package com.shanchuang.ssf.adapter;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.bean.ActiveListBean;
import com.shanchuang.ssf.view.ImageViewPlus;
import com.vondear.rxtool.RxTimeTool;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/10/14
 */
public class ActivieAdapter extends BaseQuickAdapter<ActiveListBean.HuodongBean, BaseViewHolder> {
    public ActivieAdapter(int layoutResId, @Nullable List<ActiveListBean.HuodongBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ActiveListBean.HuodongBean item) {
        Glide.with(mContext).load(item.getImage()).into((ImageViewPlus)helper.getView(R.id.iv_logo));
        helper.setText(R.id.tv_title,item.getTitle());
        helper.setText(R.id.tv_time,RxTimeTool.timeStamp2Date(item.getStime(),"")+"至"+ RxTimeTool.timeStamp2Date(item.getEtime(),""));
    }
}
