package com.shanchuang.ssf.adapter;


import android.view.View;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.bean.AddrBean;

import java.util.List;

/**
 * Created by JY on 2018/5/7.
 */

public class AddrAdapter extends BaseQuickAdapter<AddrBean.AddrMsgBean, BaseViewHolder> {

    public AddrAdapter(int layoutResId, @Nullable List<AddrBean.AddrMsgBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddrBean.AddrMsgBean item) {
        if(item.getIs_default()==1){
            helper.getView(R.id.tv_default).setVisibility(View.VISIBLE);
        }else {
            helper.getView(R.id.tv_default).setVisibility(View.GONE);
        }
        helper.addOnClickListener(R.id.btn_edit);
        helper.addOnClickListener(R.id.main);
        helper.addOnClickListener(R.id.delete);
        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.tv_mobile, item.getMobile());
        helper.setText(R.id.tv_desc, item.getSheng()+item.getShi()+item.getXian()+item.getAddress());


    }
}
