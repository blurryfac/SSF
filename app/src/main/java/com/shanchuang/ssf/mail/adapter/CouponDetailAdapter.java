package com.shanchuang.ssf.mail.adapter;

import android.widget.Button;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.bean.CouponBean;
import com.vondear.rxtool.RxTimeTool;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/7/16
 */
public class CouponDetailAdapter extends BaseQuickAdapter<CouponBean.YouhuiquanBean, BaseViewHolder> {
    public CouponDetailAdapter(int layoutResId, @Nullable List<CouponBean.YouhuiquanBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponBean.YouhuiquanBean item) {
        helper.addOnClickListener(R.id.tv_get_coupon);
        if(item.getIs_lq()==0){
            ((Button)helper.getView(R.id.tv_get_coupon)).setTextColor(mContext.getResources().getColor(R.color.white));
            helper.getView(R.id.tv_get_coupon).setBackgroundResource(R.drawable.btn_orange_shape);
            helper.setText(R.id.tv_get_coupon,"立即领取");
        }else{
            ((Button)helper.getView(R.id.tv_get_coupon)).setTextColor(mContext.getResources().getColor(R.color.white));
            helper.getView(R.id.tv_get_coupon).setBackgroundResource(R.drawable.btn_gray_20_shape);
            helper.setText(R.id.tv_get_coupon,"已领取");
            helper.getView(R.id.tv_get_coupon).setEnabled(false);
        }
        helper.setText(R.id.tv_coupon_price, item.getMoney());
        helper.setText(R.id.tv_coupon_name, "满" + item.getTotal() + "可用");

        helper.setText(R.id.tv_coupon_time, RxTimeTool.timeStamp2Date(item.getEtime(), ""));
    }
}
