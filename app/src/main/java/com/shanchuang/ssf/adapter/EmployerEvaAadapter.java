package com.shanchuang.ssf.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.bean.EmployerEvaBean;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/12/11
 */
public class EmployerEvaAadapter extends BaseQuickAdapter<EmployerEvaBean, BaseViewHolder> {
    public EmployerEvaAadapter(int layoutResId, @Nullable List<EmployerEvaBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EmployerEvaBean item) {
        helper.setText(R.id.tv_name,item.getName());
        helper.setText(R.id.tv_time,item.getTime());
        helper.setText(R.id.tv_content,item.getContent());
    }
}
