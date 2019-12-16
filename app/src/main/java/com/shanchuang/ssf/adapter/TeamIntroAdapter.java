package com.shanchuang.ssf.adapter;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.bean.TeamIntroBean;
import com.shanchuang.ssf.view.ImageViewPlus;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author
 * @time 2019/10/14
 */
public class TeamIntroAdapter extends BaseQuickAdapter<TeamIntroBean.TeamBean, BaseViewHolder> {

    public TeamIntroAdapter(int layoutResId, @Nullable List<TeamIntroBean.TeamBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TeamIntroBean.TeamBean item) {
        helper.setText(R.id.tv_team_item_name,item.getName());
        helper.setText(R.id.tv_team_item_level,item.getZhiwu());
        Glide.with(mContext).load(item.getImage()).into((ImageViewPlus)helper.getView(R.id.iv_logo));
    }
}
