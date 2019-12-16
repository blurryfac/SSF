package com.shanchuang.ssf.adapter;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.bean.ExercisesBean;
import com.shanchuang.ssf.view.ImageViewPlus;
import com.vondear.rxtool.RxTextTool;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/10/31
 */
public class ExercisesAdapter extends BaseQuickAdapter<ExercisesBean.XitiBean, BaseViewHolder> {
    public ExercisesAdapter(int layoutResId, @Nullable List<ExercisesBean.XitiBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ExercisesBean.XitiBean item) {
        helper.setText(R.id.tv_exercises_title,item.getTitle());
        helper.setText(R.id.tv_exercises_num,"试卷题目数："+item.getCount());
        RxTextTool.getBuilder("").append("上次得分：").append(item.getDefen())
                .setForegroundColor(mContext.getResources().getColor(R.color.colorRed)).into(helper.getView(R.id.tv_last_score));
        helper.setText(R.id.tv_exercises_score,"试卷分数："+item.getFenshu());
        Glide.with(mContext).load(item.getImage()).into((ImageViewPlus)helper.getView(R.id.iv_logo));
    }
}
