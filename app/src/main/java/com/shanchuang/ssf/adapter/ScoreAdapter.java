package com.shanchuang.ssf.adapter;

import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.bean.ScoreBean;
import com.vondear.rxtool.RxTimeTool;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/10/23
 */
public class ScoreAdapter extends BaseQuickAdapter<ScoreBean.LogBean, BaseViewHolder> {
    public ScoreAdapter(int layoutResId, @Nullable List<ScoreBean.LogBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ScoreBean.LogBean item) {
        if(item.getScore()<0){
            ((TextView)helper.getView(R.id.tv_score_num)).setTextColor(mContext.getResources().getColor(R.color.colorRed));
            helper.setText(R.id.tv_score_num,"-"+item.getScore());
        }else {
            ((TextView)helper.getView(R.id.tv_score_num)).setTextColor(mContext.getResources().getColor(R.color.colorGreen));
            helper.setText(R.id.tv_score_num,"+"+item.getScore());
        }
        helper.setText(R.id.tv_score_title,item.getMemo());
        helper.setText(R.id.tv_score_time, RxTimeTool.timeStamp2Date(item.getCreatetime(),""));

    }
}
