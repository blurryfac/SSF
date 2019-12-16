package com.shanchuang.ssf.adapter;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.bean.ReleaseCateBean;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/12/13
 */
public class CateAdapter extends BaseQuickAdapter<ReleaseCateBean, BaseViewHolder> {
    public CateAdapter(int layoutResId, @Nullable List<ReleaseCateBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReleaseCateBean item) {
        if (item.getStatus() == 1) {
            helper.getView(R.id.iv_snajiao).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.iv_snajiao).setVisibility(View.GONE);
        }
        helper.setText(R.id.tv_cate_item,item.getTitle());
        Drawable drawable = mContext.getResources().getDrawable(item.getDrawable());
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        ((TextView)helper.getView(R.id.tv_cate_item)).setCompoundDrawables(null, null, drawable, null);
    }
}
