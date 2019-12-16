package com.shanchuang.ssf.mail.adapter;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.bean.OrderAllBean;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 佚名
 * @time 2018/6/8
 */
public class OrderAdapter extends BaseQuickAdapter<OrderAllBean.OrderBean, BaseViewHolder> {

    private int type;

    public OrderAdapter(int layoutResId, @Nullable List<OrderAllBean.OrderBean> data, int type) {
        super(layoutResId, data);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderAllBean.OrderBean item) {
        helper.addOnClickListener(R.id.tv_1);
        helper.addOnClickListener(R.id.tv_2);
        helper.addOnClickListener(R.id.tv_order_shop);
        if (type == 1) {
            helper.getView(R.id.tv_1).setVisibility(View.VISIBLE);
            helper.getView(R.id.tv_2).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_order_status, "待付款");
            helper.setText(R.id.tv_1, "取消订单");
            helper.setText(R.id.tv_2, "立即付款");
        } else if (type == 2) {
            helper.getView(R.id.tv_1).setVisibility(View.GONE);
            helper.getView(R.id.tv_2).setVisibility(View.GONE);
            helper.setText(R.id.tv_order_status, "等待卖家发货");
        } else if (type == 4) {
            if (item.getStatus() == 5) {
                helper.setText(R.id.tv_2, "已完成");
                helper.setText(R.id.tv_order_status, "已完成");
                helper.getView(R.id.tv_1).setVisibility(View.GONE);
                helper.getView(R.id.tv_2).setVisibility(View.VISIBLE);
            } else if (item.getStatus() == 4) {
                helper.setText(R.id.tv_2, "立即评价");
                helper.setText(R.id.tv_order_status, "待评价");
                helper.getView(R.id.tv_1).setVisibility(View.GONE);
                helper.getView(R.id.tv_2).setVisibility(View.VISIBLE);
            }
        } else if (type == 3) {
            helper.getView(R.id.tv_1).setVisibility(View.GONE);
            helper.getView(R.id.tv_2).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_1, "查看物流");
            helper.setText(R.id.tv_2, "确认收货");
            helper.setText(R.id.tv_order_status, "待收货");
        } else {
            if (item.getStatus() == 2) {
                helper.getView(R.id.tv_1).setVisibility(View.GONE);
                helper.getView(R.id.tv_2).setVisibility(View.GONE);
                helper.setText(R.id.tv_order_status, "等待卖家发货");
            } else if (item.getStatus() == 4) {
                helper.setText(R.id.tv_2, "立即评价");
                helper.getView(R.id.tv_1).setVisibility(View.GONE);
                helper.getView(R.id.tv_2).setVisibility(View.VISIBLE);
                helper.setText(R.id.tv_order_status, "待评价");
            } else if (item.getStatus() == 5) {
                helper.setText(R.id.tv_2, "已完成");
                helper.getView(R.id.tv_1).setVisibility(View.GONE);
                helper.getView(R.id.tv_2).setVisibility(View.VISIBLE);
                helper.setText(R.id.tv_order_status, "已完成");
            } else if (item.getStatus() == 3) {
                helper.setText(R.id.tv_1, "查看物流");
                helper.setText(R.id.tv_2, "确认收货");
                helper.getView(R.id.tv_1).setVisibility(View.GONE);
                helper.getView(R.id.tv_2).setVisibility(View.VISIBLE);
                helper.setText(R.id.tv_order_status, "待收货");

            } else if (item.getStatus() == 1) {
                helper.setText(R.id.tv_1, "取消订单");
                helper.setText(R.id.tv_2, "立即付款");
                helper.getView(R.id.tv_1).setVisibility(View.VISIBLE);
                helper.getView(R.id.tv_2).setVisibility(View.VISIBLE);
                helper.setText(R.id.tv_order_status, "待付款");

            }
        }
        helper.setText(R.id.tv_order_shop, item.getMer_title() + " ＞");
        helper.setText(R.id.tv_num, "共" + item.getCount() + "件");
        helper.setText(R.id.tv_score, "¥ " + item.getPay_total());

        LinearLayoutManager linear = new LinearLayoutManager(mContext);
        ((RecyclerView) helper.getView(R.id.rec_goods)).setLayoutManager(linear);
        OrderItemAdapter orderItemAdapter = new OrderItemAdapter(R.layout.item_order_item, item.getGoods());
        orderItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                getOnItemClickListener().onItemClick(adapter, view, helper.getAdapterPosition());
            }
        });
        ((RecyclerView) helper.getView(R.id.rec_goods)).setAdapter(orderItemAdapter);
    }
}
