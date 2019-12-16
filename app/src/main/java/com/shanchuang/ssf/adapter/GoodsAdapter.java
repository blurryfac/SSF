package com.shanchuang.ssf.adapter;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.bean.ShopGoodsBean;
import com.shanchuang.ssf.view.ImageViewPlus;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/10/21
 */
public class GoodsAdapter extends BaseQuickAdapter<ShopGoodsBean.GoodsBean, BaseViewHolder> {
    public GoodsAdapter(int layoutResId, @Nullable List<ShopGoodsBean.GoodsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopGoodsBean.GoodsBean item) {
        Glide.with(mContext).load(item.getImage()).into((ImageViewPlus)helper.getView(R.id.iv_logo));
        helper.setText(R.id.tv_mail_name,item.getTitle());
        helper.setText(R.id.tv_mail_price,"¥ "+item.getPrice());
        helper.setText(R.id.tv_mail_score,"销量"+item.getSales());
        helper.setText(R.id.tv_main_desc,item.getJianjie());
    }
}
