package com.shanchuang.ssf.adapter;


import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.luck.picture.lib.entity.LocalMedia;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.view.ImageViewPlus;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/5/15
 */
public class ImgAdapter extends BaseQuickAdapter<LocalMedia, BaseViewHolder> {

    public ImgAdapter(int layoutResId, @Nullable List<LocalMedia> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LocalMedia item) {
        Glide.with(mContext).load(item.getPath()).into((ImageViewPlus) helper.getView(R.id.iv_logo));
    }
}
