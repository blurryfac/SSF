package com.shanchuang.ssf.mail.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.bean.ShopCarBean;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/5/7
 */
public class ConfirmItemAdapter extends BaseQuickAdapter<ShopCarBean.OrderBean.GoodsBean, BaseViewHolder> {
    public ConfirmItemAdapter(int layoutResId, @Nullable List<ShopCarBean.OrderBean.GoodsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopCarBean.OrderBean.GoodsBean item) {
        Glide.with(mContext).load(item.getImage()).into((ImageView) helper.getView(R.id.iv_logo));
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_num, "x" + item.getAmount());
        helper.setText(R.id.tv_price, "¥ " + item.getPrice());

    }
}
