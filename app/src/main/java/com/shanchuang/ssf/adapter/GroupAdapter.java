package com.shanchuang.ssf.adapter;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.bean.GroupBean;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/10/23
 */
public class GroupAdapter extends BaseQuickAdapter<GroupBean.ChengzhangBean, BaseViewHolder> {
    public GroupAdapter(int layoutResId, @Nullable List<GroupBean.ChengzhangBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GroupBean.ChengzhangBean item) {
        if (helper.getAdapterPosition() == 0) {
            helper.getView(R.id.tv_top_line).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.tv_top_line).setVisibility(View.GONE);
        }
        helper.setText(R.id.tv_content,item.getContentX());
        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        ((RecyclerView) helper.getView(R.id.rec_imgs)).setLayoutManager(manager);
//        ((RecyclerView) helper.getView(R.id.rec_imgs)).addItemDecoration(new GridSpacingItemDecoration(3,20,false));
        ImgAdapter imgAdapter = new ImgAdapter(R.layout.item_back_img, item.getImgs());
        ((RecyclerView) helper.getView(R.id.rec_imgs)).setAdapter(imgAdapter);
        imgAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                mOnItemImgClickListener.onItemImgClick(adapter, view, position, helper.getAdapterPosition());
            }
        });

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
        void onItemImgClick(BaseQuickAdapter adapter, View view, int position, int f_pos);
    }
    private OnItemImgClickListener mOnItemImgClickListener;
    public void setOnItemImgClickListener(@Nullable OnItemImgClickListener listener) {
        mOnItemImgClickListener = listener;
    }
}
