//package com.shanchuang.k12edu.mail.adapter;
//
//import android.support.annotation.Nullable;
//
//import com.bumptech.glide.Glide;
//import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.chad.library.adapter.base.BaseViewHolder;
//import com.shanchuang.k12edu.R;
//import com.shanchuang.k12edu.view.ImageViewPlus;
//
//import java.util.List;
//
///**
// * 描述：添加类的描述
// *
// * @author 金源
// * @time 2018/12/19
// */
//public class ShopEvaAdapter extends BaseQuickAdapter<PingLunBean, BaseViewHolder> {
//    public ShopEvaAdapter(int layoutResId, @Nullable List<PingLunBean> data) {
//        super(layoutResId, data);
//    }
//
//    @Override
//    protected void convert(BaseViewHolder helper, PingLunBean item) {
//        helper.setText(R.id.tv_name,item.getName());
//        helper.setText(R.id.tv_eva_details,item.getContent());
//        Glide.with(mContext).load(item.getLogo()).into((ImageViewPlus)helper.getView(R.id.iv_logo));
//    }
//}
