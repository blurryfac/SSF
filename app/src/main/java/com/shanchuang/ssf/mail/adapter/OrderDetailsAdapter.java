package com.shanchuang.ssf.mail.adapter;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.bean.OrderDetailsBean;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/5/14
 */
public class OrderDetailsAdapter extends BaseQuickAdapter<OrderDetailsBean.OrderBean, BaseViewHolder> {

    public OrderDetailsAdapter(int layoutResId, @Nullable List<OrderDetailsBean.OrderBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderDetailsBean.OrderBean item) {
        helper.addOnClickListener(R.id.tv_order_shop);
        helper.setText(R.id.tv_order_shop,item.getMer_title());
        LinearLayoutManager linear = new LinearLayoutManager(mContext);
        ((RecyclerView) helper.getView(R.id.rec_goods)).setLayoutManager(linear);
        OrderItemAdapter orderItemAdapter = new OrderItemAdapter(R.layout.item_order_item, item.getGoods());
        orderItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                getOnItemClickListener().onItemClick(adapter, view, position);
            }
        });
        ((RecyclerView) helper.getView(R.id.rec_goods)).setAdapter(orderItemAdapter);
    }
}
