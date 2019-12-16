package com.shanchuang.ssf.adapter;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.bean.VideoListBean;
import com.shanchuang.ssf.view.ImageViewPlus;
import com.vondear.rxtool.RxTextTool;
import com.vondear.rxtool.RxTimeTool;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/10/14
 */
public class VideoListAdapter extends BaseQuickAdapter<VideoListBean.CourseBean, BaseViewHolder> {
    public VideoListAdapter(int layoutResId, @Nullable List<VideoListBean.CourseBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoListBean.CourseBean item) {
        Glide.with(mContext).load(item.getImage()).into((ImageViewPlus)helper.getView(R.id.iv_logo));
        helper.setText(R.id.tv_video_title,item.getTitle());
        helper.setText(R.id.tv_video_teacher,item.getZhujiang());
        helper.setText(R.id.tv_video_time, RxTimeTool.timeStamp2Date(item.getCreatetime(),null));
        helper.setText(R.id.tv_video_num,item.getView());
        helper.setText(R.id.tv_now_price,"¥ "+item.getPrice());
        RxTextTool.getBuilder("").append("¥ "+item.getOld_price()).setStrikethrough().into(helper.getView((R.id.tv_old_price)));
    }
}
