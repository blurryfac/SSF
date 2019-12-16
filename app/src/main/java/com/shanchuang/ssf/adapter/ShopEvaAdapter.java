package com.shanchuang.ssf.adapter;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import com.shanchuang.ssf.R;
import com.shanchuang.ssf.bean.ShopEvaBean;
import com.shanchuang.ssf.manager.FullyGridLayoutManager;
import com.shanchuang.ssf.view.ImageViewPlus;
import com.vondear.rxtool.RxTimeTool;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 佚名
 * @time 2018/12/19
 */
public class ShopEvaAdapter extends BaseQuickAdapter<ShopEvaBean.PinglunBean,BaseViewHolder> {
    public ShopEvaAdapter(int layoutResId, @Nullable List<ShopEvaBean.PinglunBean> data) {
        super(layoutResId, data);
    }
    /**
     * Interface definition for a callback to be invoked when an itemchild in this
     * view has been clicked
     */
    public interface OnItemImgClickListener {
        /**
         * callback method to be invoked when an item in this view has been
         * click and held
         *
         * @param view     The view whihin the ItemView that was clicked
         * @param position The position of the view int the adapter
         */
        void onItemImgClick(BaseQuickAdapter adapter, View view, int position,int f_pos);
    }
    private OnItemImgClickListener mOnItemImgClickListener;
    public void setOnItemImgClickListener(@Nullable OnItemImgClickListener listener) {
        mOnItemImgClickListener = listener;
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopEvaBean.PinglunBean item) {
        helper.setText(R.id.tv_time, RxTimeTool.timeStamp2Date(item.getCreatetime(),""));
        helper.setText(R.id.tv_name,item.getTitle());
        helper.setText(R.id.tv_eva_details,item.getContent());
        Glide.with(mContext).load(item.getImage()).into((ImageViewPlus)helper.getView(R.id.iv_logo));

        FullyGridLayoutManager manager = new FullyGridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false);
        ((RecyclerView)helper.getView(R.id.rec_img)).setLayoutManager(manager);

        ImgAdapter  imgAdapter = new ImgAdapter(R.layout.item_back_img, item.getLocalMedia());
        ((RecyclerView)helper.getView(R.id.rec_img)).setAdapter(imgAdapter);
        imgAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                mOnItemImgClickListener.onItemImgClick(adapter,view,position,helper.getAdapterPosition());
            }
        });
    }
}
