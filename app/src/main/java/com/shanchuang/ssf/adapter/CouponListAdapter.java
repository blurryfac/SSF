package com.shanchuang.ssf.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.bean.CouponListBean;
import com.vondear.rxtool.RxTimeTool;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/10/24
 */
public class CouponListAdapter extends BaseQuickAdapter<CouponListBean.YouhuiquanBean, BaseViewHolder> {
    public CouponListAdapter(int layoutResId, @Nullable List<CouponListBean.YouhuiquanBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponListBean.YouhuiquanBean item) {
        helper.setText(R.id.tv_coupon_price, item.getMoney());
        helper.setText(R.id.tv_coupon_name, "满" + item.getTotal() + "可用");
        helper.setText(R.id.tv_coupon_time, RxTimeTool.timeStamp2Date(item.getEtime(), ""));
        helper.setText(R.id.tv_coupon_shop, item.getFabuzhe());
    }
}
