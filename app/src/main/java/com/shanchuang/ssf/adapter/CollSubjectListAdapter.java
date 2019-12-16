package com.shanchuang.ssf.adapter;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.bean.SubjectListBean;
import com.shanchuang.ssf.view.ImageViewPlus;
import com.vondear.rxtool.RxTextTool;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/10/24
 */
public class CollSubjectListAdapter extends BaseQuickAdapter<SubjectListBean.XitiBean, BaseViewHolder> {
    public CollSubjectListAdapter(int layoutResId, @Nullable List<SubjectListBean.XitiBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SubjectListBean.XitiBean item) {
        helper.addOnClickListener(R.id.main);
        helper.addOnClickListener(R.id.delete);
        helper.setText(R.id.tv_subject_title,item.getTitle());
        helper.setText(R.id.tv_subject_num,"试卷题目数："+item.getCount());
        RxTextTool.getBuilder("").append("上次得分：").append(item.getDefen())
                .setForegroundColor(mContext.getResources().getColor(R.color.colorRed)).into(helper.getView(R.id.tv_last_score));
        helper.setText(R.id.tv_subject_score,"试卷分数："+item.getFenshu());
        Glide.with(mContext).load(item.getImage()).into((ImageViewPlus)helper.getView(R.id.iv_logo));
    }
}
