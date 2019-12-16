package com.shanchuang.ssf.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.bean.VideoMainBean;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/10/14
 */
public class VideoMainAdapter extends BaseQuickAdapter<VideoMainBean.CateBean, BaseViewHolder> {
    public VideoMainAdapter(int layoutResId, @Nullable List<VideoMainBean.CateBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoMainBean.CateBean item) {
        helper.setText(R.id.tv_title,item.getTitle());
    }
}
