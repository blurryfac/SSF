package com.shanchuang.ssf.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.bean.WorkOrderBean;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/12/10
 */
public class WorderOrderAdapter extends BaseQuickAdapter<WorkOrderBean, BaseViewHolder> {
    public WorderOrderAdapter(int layoutResId, @Nullable List<WorkOrderBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkOrderBean item) {
        helper.setText(R.id.tv_work_address,item.getAddress());
        helper.setText(R.id.tv_work_distance,item.getDistance());
        helper.setText(R.id.tv_work_estimated_amount,"预算金额：¥ "+item.getEstimated_amount());
        helper.setText(R.id.tv_work_limit,"预计工期："+item.getLimit());
        helper.setText(R.id.tv_work_release_time,"发布时间："+item.getRelease_time());
        helper.setText(R.id.tv_work_see_num,item.getNum());
        helper.setText(R.id.tv_work_start_time,"开工时间："+item.getStart_time());
        helper.setText(R.id.tv_work_title,item.getTitle());
        helper.setText(R.id.tv_worker_type,item.getType());
        helper.setText(R.id.tv_deposit,"¥ "+item.getDeposit());
    }
}
