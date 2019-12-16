package com.shanchuang.ssf.adapter;

import android.widget.Button;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.bean.ShopCarBean;
import com.vondear.rxtool.RxTimeTool;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/11/8
 */
public class CouponUseAdapter extends BaseQuickAdapter<ShopCarBean.OrderBean.YouhuiquanBean, BaseViewHolder> {
    public CouponUseAdapter(int layoutResId, @Nullable List<ShopCarBean.OrderBean.YouhuiquanBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopCarBean.OrderBean.YouhuiquanBean item) {
        helper.addOnClickListener(R.id.tv_get_coupon);
        ((Button)helper.getView(R.id.tv_get_coupon)).setTextColor(mContext.getResources().getColor(R.color.white));
        helper.getView(R.id.tv_get_coupon).setBackgroundResource(R.drawable.btn_orange_shape);
        helper.setText(R.id.tv_get_coupon, "选择");
        helper.setText(R.id.tv_coupon_price, item.getMoney());
        helper.setText(R.id.tv_coupon_name, "满" + item.getTotal() + "可用");
        helper.setText(R.id.tv_coupon_time, RxTimeTool.timeStamp2Date(item.getEtime(), ""));
    }
}