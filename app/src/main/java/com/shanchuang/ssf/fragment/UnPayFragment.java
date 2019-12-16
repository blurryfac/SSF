package com.shanchuang.ssf.fragment;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.bean.OrderAllBean;
import com.shanchuang.ssf.bean.PayBean;
import com.shanchuang.ssf.bean.WxPayBean;
import com.shanchuang.ssf.dialog.DialogUtil;
import com.shanchuang.ssf.mail.OrderDetailsActivity;
import com.shanchuang.ssf.mail.adapter.OrderAdapter;
import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.rxjava.HttpMethods;
import com.shanchuang.ssf.net.subscribers.ProgressSubscriber;
import com.shanchuang.ssf.net.subscribers.SubscriberOnNextListener;
import com.shanchuang.ssf.pay.PayListenerUtils;
import com.shanchuang.ssf.pay.PayResultListener;
import com.shanchuang.ssf.pay.PayUtils;
import com.shanchuang.ssf.utils.DividerItemDecoration;
import com.shanchuang.ssf.utils.SharedHelper;
import com.vondear.rxtool.view.RxToast;
import com.vondear.rxui.fragment.FragmentLazy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/4/16
 */
public class UnPayFragment extends FragmentLazy implements BaseQuickAdapter.OnItemChildClickListener, PayResultListener {
    private static final String TAG = "UnPayFragment";
    @BindView(R.id.rl_coll)
    RecyclerView rlColl;
    @BindView(R.id.img_gone)
    ImageView imgGone;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;
    Unbinder unbinder;
    Dialog dialog;
    private OrderAdapter orderAdapter;
    private List<OrderAllBean.OrderBean> orderBeanList;
    private int page = 1;
    private boolean isShowDialog = true;

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.tv_1:
                cancelOrder(position);
                break;
            case R.id.tv_2:
                showPayDialog(position);
                break;
            case R.id.tv_order_shop:
//                Bundle bundle = new Bundle();
//                bundle.putString("id", orderBeanList.get(position).getId());
//                bundle.putString("title", orderBeanList.get(position).getName());
//                RxActivityTool.skipActivity(getActivity(), ShopActivity.class, bundle);
                break;
        }
    }

    private void cancelOrder(int pos) {
        SubscriberOnNextListener<BaseBean> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                srl.autoRefresh();
            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();

        map.put("uid", SharedHelper.readId(mContext));
        map.put("id", orderBeanList.get(pos).getId());
        HttpMethods.getInstance().order_del(new ProgressSubscriber<>(onNextListener, mContext, isShowDialog), map);
    }

    private void showPayDialog(int pos) {
        dialog = DialogUtil.getInstance().showPayDialog(getActivity());
        DialogUtil.getInstance().setOnPayClickListener(new DialogUtil.OnPayClickListener() {
            @Override
            public void onYuE(View v) {

            }

            @Override
            public void onAliPay(View v) {
                getPay(pos);
            }

            @Override
            public void onWeiXinPay(View v) {
                weixinPay(pos);
            }
        });
        dialog.show();
    }

    private void getPay( int pos) {
        SubscriberOnNextListener<BaseBean<PayBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                PayUtils.getInstance(mContext).alipay(PayUtils.PAY_TYPE_ALI, baseBean.getData().getPay());
            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("id", orderBeanList.get(pos).getId());
        map.put("paytype", 2);
        map.put("uid", SharedHelper.readId(mContext));
        HttpMethods.getInstance().goods_pay(new ProgressSubscriber<>(onNextListener, mContext, true), map);

    }

    private void weixinPay(int pos) {
        SubscriberOnNextListener<BaseBean<WxPayBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                PayUtils.getInstance(mContext).wxpay(PayUtils.PAY_TYPE_WX, baseBean.getData().getPay());
            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("id", orderBeanList.get(pos).getId());
        map.put("paytype", 1);
        map.put("uid", SharedHelper.readId(mContext));
        HttpMethods.getInstance().goods_wx_pay(new ProgressSubscriber<>(onNextListener, mContext, true), map);
    }
BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
        RxToast.normal("支付成功");
      srl.autoRefresh();
    }
};
    @Override
    protected View initViews(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.fragment_oz_layout, viewGroup, false);
        unbinder = ButterKnife.bind(this, view);
        IntentFilter intentFilter =new IntentFilter("PaySuccess");
        getActivity().registerReceiver(broadcastReceiver,intentFilter);
        PayListenerUtils.getInstance(getActivity()).addListener(this);
        init();
        return view;
    }

    private void init() {
        orderBeanList = new ArrayList<>();
        LinearLayoutManager linear = new LinearLayoutManager(getActivity());
        rlColl.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        rlColl.setNestedScrollingEnabled(false);
        rlColl.setLayoutManager(linear);
        orderAdapter = new OrderAdapter(R.layout.item_order, orderBeanList, 1);
        rlColl.setAdapter(orderAdapter);
        orderAdapter.setOnItemChildClickListener(this);
        orderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), OrderDetailsActivity.class);
                intent.putExtra("oid", orderBeanList.get(position).getId());
                intent.putExtra("status", orderBeanList.get(position).getStatus());
                startActivityForResult(intent,1);
            }
        });

        srl.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000);
                page++;
                isShowDialog=false;
                initData();
            }
        });
        srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
                refresh();
            }
        });
    }



    private void refresh() {
        orderBeanList.clear();
        orderAdapter.notifyDataSetChanged();
        page = 1;
        isShowDialog = false;
        initData();
    }

    @Override
    protected void initData() {
        SubscriberOnNextListener<BaseBean<OrderAllBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                if (baseBean.getData().getOrder().isEmpty()) {
                    if(page==1){
                        imgGone.setVisibility(View.VISIBLE);
                        orderAdapter.notifyDataSetChanged();
                    }else {
                        RxToast.normal("没有更多数据了");
                    }

                } else {
                    imgGone.setVisibility(View.INVISIBLE);
                    orderBeanList.addAll(baseBean.getData().getOrder());
                    orderAdapter.notifyDataSetChanged();
                }

            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();

        map.put("uid", SharedHelper.readId(mContext));
        map.put("status", 1);
        map.put("page", page);
        HttpMethods.getInstance().me_order(new ProgressSubscriber<>(onNextListener, mContext, isShowDialog), map);
    }

    @Override
    protected void onVisible() {
        super.onVisible();

        Log.d(TAG, "onVisible: ");
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        getActivity().unregisterReceiver(broadcastReceiver);
        PayListenerUtils.getInstance(getActivity()).removeListener(this);
    }

    @Override
    public void onPaySuccess() {
        RxToast.normal("支付成功");
        refresh();
    }

    @Override
    public void onPayError() {

    }

    @Override
    public void onPayCancel() {
        RxToast.normal("支付取消");
    }
}