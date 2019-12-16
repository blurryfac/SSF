package com.shanchuang.ssf.mail.adapter;


import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.bean.OrderItemBean;
import com.shanchuang.ssf.view.ImageViewPlus;
import com.vondear.rxtool.RxTextTool;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/4/18
 */
public class OrderItemAdapter extends BaseQuickAdapter<OrderItemBean, BaseViewHolder> {
    public OrderItemAdapter(int layoutResId, @Nullable List<OrderItemBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderItemBean item) {
        Glide.with(mContext).load(item.getImage()).into((ImageViewPlus) helper.getView(R.id.iv_logo));
        helper.setText(R.id.tv_order_title, item.getTitle());
        helper.setText(R.id.tv_yunfei, "x " + item.getAmount());
        helper.setText(R.id.tv_order_info, RxTextTool.getBuilder("")
                .append("¥ " + item.getPrice()).setForegroundColor(mContext.getResources().getColor(R.color.colorRed))
               .create());

    }
}
