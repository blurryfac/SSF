package com.shanchuang.ssf.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.bean.MessageListBean;
import com.vondear.rxtool.RxTimeTool;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/11/20
 */
public class MessageListAdapter extends BaseQuickAdapter<MessageListBean.NewsBean, BaseViewHolder> {
    public MessageListAdapter(int layoutResId, @Nullable List<MessageListBean.NewsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageListBean.NewsBean item) {
        helper.setText(R.id.tv_title,"系统消息");
        helper.setText(R.id.tv_desc,item.getTitle());
        helper.setText(R.id.tv_time, RxTimeTool.timeStamp2Date(item.getCreatetime(),""));
    }
}
