package com.shanchuang.ssf.login.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.bean.SchoolDataBean;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author
 * @time 2019/10/14
 */
public class SchoolAdapter extends BaseQuickAdapter<SchoolDataBean.SchoolBean, BaseViewHolder> {
    public SchoolAdapter(int layoutResId, @Nullable List<SchoolDataBean.SchoolBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SchoolDataBean.SchoolBean item) {
        helper.setText(R.id.tv_text,item.getTitle());
    }
}
