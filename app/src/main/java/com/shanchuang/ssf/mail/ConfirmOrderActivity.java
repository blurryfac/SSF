package com.shanchuang.ssf.mail;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.activity.AddrActivity;
import com.shanchuang.ssf.activity.MyOrderActivity;
import com.shanchuang.ssf.adapter.CouponUseAdapter;
import com.shanchuang.ssf.base.BaseActivity;
import com.shanchuang.ssf.bean.AddressBean;
import com.shanchuang.ssf.bean.AllCoupon;
import com.shanchuang.ssf.bean.PayBean;
import com.shanchuang.ssf.bean.ShopCarBean;
import com.shanchuang.ssf.bean.WxPayBean;
import com.shanchuang.ssf.dialog.DialogUtil;
import com.shanchuang.ssf.mail.adapter.ConfirmAdapter;
import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.rxjava.HttpMethods;
import com.shanchuang.ssf.net.subscribers.ProgressSubscriber;
import com.shanchuang.ssf.net.subscribers.SubscriberOnNextListener;
import com.shanchuang.ssf.pay.PayListenerUtils;
import com.shanchuang.ssf.pay.PayResultListener;
import com.shanchuang.ssf.pay.PayUtils;
import com.shanchuang.ssf.utils.DividerItemDecoration;
import com.shanchuang.ssf.utils.SharedHelper;
import com.vondear.rxtool.RxActivityTool;
import com.vondear.rxtool.RxDataTool;
import com.vondear.rxtool.view.RxToast;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by JY on 2018/6/7.
 */

public class ConfirmOrderActivity extends BaseActivity implements PayResultListener, BaseQuickAdapter.OnItemChildClickListener {


    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            Log.i("====home====", "收到home");

            RxToast.normal("支付成功");
            Bundle bundle = new Bundle();
            bundle.putInt("order", 2);
            RxActivityTool.skipActivity(ConfirmOrderActivity.this, MyOrderActivity.class, bundle);
            finish();
        }
    };
    @BindView(R.id.iv_back)
    TextView ivBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_menu)
    TextView toolbarMenu;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_add_empty)
    TextView tvAddEmpty;
    @BindView(R.id.iv_loc)
    ImageView ivLoc;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_mobile)
    TextView tvMobile;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_add)
    LinearLayout llAdd;
    @BindView(R.id.rl_choice_address)
    RelativeLayout rlChoiceAddress;
    @BindView(R.id.rec_all)
    RecyclerView recAll;
    @BindView(R.id.tv_all_shop_price)
    TextView tvAllShopPrice;
    @BindView(R.id.tv_price_all)
    TextView tvPriceAll;
    @BindView(R.id.tv_confirm_order)
    TextView tvConfirmOrder;
    @BindView(R.id.ll_del)
    RelativeLayout llDel;

    private ConfirmAdapter confirmAdapter;
    private List<ShopCarBean.OrderBean> confirmBeanList;
    private Dialog dialog;
    private List<AllCoupon> mAllCouponList = new ArrayList<>();
    private String cart_id = "";//购物车id
    private String g_id = "";//商品id
    private String amount = "";//商品数量
    private int type;
    /**
     * 优惠券弹窗
     */
    private CouponUseAdapter mCouponUseAdapter;
    private Dialog mCouponDialog;//选择优惠券
    private List<ShopCarBean.OrderBean.YouhuiquanBean> mCouponList = new ArrayList<>();
    private BigDecimal mBDAllPrice;//商品总价
    private int mShopPosition;//店铺坐标

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            llAdd.setVisibility(View.VISIBLE);
            tvAddEmpty.setVisibility(View.INVISIBLE);
            aid=data.getStringExtra("aid");
            tvName.setText(data.getStringExtra("name"));
            tvMobile.setText(data.getStringExtra("mobile"));
            tvAddress.setText(data.getStringExtra("desc"));
        }
    }

    @OnClick({R.id.iv_back, R.id.rl_choice_address, R.id.tv_confirm_order})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.rl_choice_address:
                intent.setClass(this, AddrActivity.class);
                intent.putExtra("type", "1");
                startActivityForResult(intent, 1);
                break;
            case R.id.tv_confirm_order:
                showPayDialog();
                break;
        }
    }

    private String getYiD() {
        String yid = "";
        for (int i = 0; i < mAllCouponList.size(); i++) {

            if (!mAllCouponList.get(i).getCoupon().toString().equals("0.00")) {
                if (yid.isEmpty()) {
                    yid = confirmBeanList.get(i).getMer().getId() + "," + mAllCouponList.get(i).getId();
                } else {
                    yid = yid + "|" + confirmBeanList.get(i).getMer().getId() + "," + mAllCouponList.get(i).getId();
                }
            }

        }
        return yid;
    }

    private void showPayDialog() {
        dialog = DialogUtil.getInstance().showPayDialog(this);
        DialogUtil.getInstance().setOnPayClickListener(new DialogUtil.OnPayClickListener() {
            @Override
            public void onYuE(View v) {

            }

            @Override
            public void onAliPay(View v) {
getPay();
            }

            @Override
            public void onWeiXinPay(View v) {
weixinPay();
            }
        });
        dialog.show();
    }

    private void getPay() {
        SubscriberOnNextListener<BaseBean<PayBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                PayUtils.getInstance(ConfirmOrderActivity.this).alipay(PayUtils.PAY_TYPE_ALI, baseBean.getData().getPay());
            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("cart_id", cart_id);
        map.put("gid", g_id);
        map.put("amount", amount);
        map.put("aid", aid);
        map.put("yid", getYiD());
        map.put("paytype", 2);
        map.put("uid", SharedHelper.readId(this));
        HttpMethods.getInstance().ali_buy(new ProgressSubscriber<>(onNextListener, this, true), map);

    }

    private void weixinPay() {
        SubscriberOnNextListener<BaseBean<WxPayBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                PayUtils.getInstance(ConfirmOrderActivity.this).wxpay(PayUtils.PAY_TYPE_WX, baseBean.getData().getPay());
            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("cart_id", cart_id);
        map.put("gid", g_id);
        map.put("amount", amount);
        map.put("aid", aid);
        map.put("yid", getYiD());
        map.put("paytype", 1);
        map.put("uid", SharedHelper.readId(this));
        HttpMethods.getInstance().wx_buy(new ProgressSubscriber<>(onNextListener, this, true), map);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
        PayListenerUtils.getInstance(this).removeListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_confirm_order_layout;
    }

    @Override
    protected void initView() {
        IntentFilter filter = new IntentFilter("PaySuccess");
        registerReceiver(broadcastReceiver, filter);
        PayListenerUtils.getInstance(this).addListener(this);
        title.setText("订单填写");
        Intent intent = getIntent();
        type = intent.getIntExtra("pay_type", 0);
        if (type == 2) {
            cart_id = intent.getStringExtra("id");
            g_id = "0";
            amount = "0";
        } else {
            cart_id = "0";
            g_id = intent.getStringExtra("id");
            amount = intent.getStringExtra("amount");
        }

        initRec();
        initCouponDialog();
    }

    private void initRec() {
        confirmBeanList = new ArrayList<>();
        LinearLayoutManager linear = new LinearLayoutManager(this);
        recAll.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        recAll.setLayoutManager(linear);
        confirmAdapter = new ConfirmAdapter(R.layout.item_confirm, confirmBeanList);
        confirmAdapter.setOnItemChildClickListener(this);
        confirmAdapter.setOnItemClickListener((adapter, view, position) -> {

        });
        recAll.setAdapter(confirmAdapter);
    }

    private void initCouponDialog() {
        mCouponList = new ArrayList<>();
        mCouponDialog = new Dialog(this, R.style.Theme_Light_Dialog_1);
        View view = LayoutInflater.from(this).inflate(R.layout.detail_coupon_dialog, null);
        RecyclerView mCouponRv = view.findViewById(R.id.rec_coupon);
        ImageView mCouponBackTv = view.findViewById(R.id.tv_coupon_finish);
        DialogUtil.getInstance().setBottomDialog(this, mCouponDialog, view);
        LinearLayoutManager linear = new LinearLayoutManager(this);
        mCouponRv.setLayoutManager(linear);
        mCouponUseAdapter = new CouponUseAdapter(R.layout.item_detail_coupon, mCouponList);
        mCouponBackTv.setOnClickListener(v -> mCouponDialog.dismiss());
        mCouponUseAdapter.setOnItemChildClickListener((adapter, view1, position) -> {
            switch (view1.getId()) {
                case R.id.tv_get_coupon:
                    if(!RxDataTool.isEmpty(confirmBeanList.get(mShopPosition).getChoicePrice())){
                        BigDecimal price = new BigDecimal(confirmBeanList.get(mShopPosition).getChoicePrice());
                        reduceCouPonPrice(price);
                    }
                    confirmBeanList.get(mShopPosition).setChoicePrice(mCouponList.get(position).getMoney());
                    confirmAdapter.notifyDataSetChanged();
                    mCouponDialog.dismiss();
                    AllCoupon submitCouPon = new AllCoupon();
                    submitCouPon.setId(mCouponList.get(position).getId());
                    BigDecimal price = new BigDecimal(mCouponList.get(position).getMoney());
                    submitCouPon.setCoupon(price);
                    CalculatedPrice(price);
                    mAllCouponList.set(mShopPosition, submitCouPon);
                    break;
            }
        });
        mCouponRv.setAdapter(mCouponUseAdapter);
    }

    private void reduceCouPonPrice(BigDecimal choicePrice) {
        mBDAllPrice = mBDAllPrice.add(choicePrice).setScale(2, BigDecimal.ROUND_HALF_UP);
//        tvPriceAll.setText("¥ " + mBDAllPrice.toString());
//        tvAllShopPrice.setText("¥ " + mBDAllPrice.toString());
    }

    /**
     * 计算使用优惠券之后的价格
     *
     * @param price
     */
    private void CalculatedPrice(BigDecimal price) {
        mBDAllPrice = mBDAllPrice.subtract(price).setScale(2, BigDecimal.ROUND_HALF_UP);
        tvPriceAll.setText("¥ " + mBDAllPrice.toString());
        tvAllShopPrice.setText("¥ " + mBDAllPrice.toString());
    }
    private String aid="";
    @Override
    public void initData() {
        SubscriberOnNextListener<BaseBean<ShopCarBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                confirmBeanList.addAll(baseBean.getData().getOrder());
                confirmAdapter.notifyDataSetChanged();
                for (int i = 0; i < confirmBeanList.size(); i++) {
                    AllCoupon submitCouPon = new AllCoupon();
                    submitCouPon.setId("");
                    BigDecimal bigDecimal = new BigDecimal("0.00");
                    submitCouPon.setCoupon(bigDecimal);
                    mAllCouponList.add(submitCouPon);
                }
                if (RxDataTool.isEmpty(baseBean.getData().getAddress())) {
                    tvAddEmpty.setVisibility(View.VISIBLE);
                    llAdd.setVisibility(View.INVISIBLE);
                    tvAddEmpty.setText("选择添加地址");
                } else {
                    llAdd.setVisibility(View.VISIBLE);
                    tvAddEmpty.setVisibility(View.INVISIBLE);
                    AddressBean addressBean = baseBean.getData().getAddress();
                    aid=addressBean.getId();
                    tvAddress.setText(addressBean.getSheng() + addressBean.getShi() + addressBean.getXian() + addressBean.getAddress());
                    tvMobile.setText(addressBean.getMobile());
                    tvName.setText(addressBean.getName());
                }
                mBDAllPrice = new BigDecimal(baseBean.getData().getTotal_money());
                tvPriceAll.setText("¥ " + baseBean.getData().getTotal_money());
                tvAllShopPrice.setText("¥ " + baseBean.getData().getTotal_money());
            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();

        map.put("cart_id", cart_id);
        map.put("gid", g_id);
        map.put("amount", amount);
        map.put("aid", 0);
        map.put("uid", SharedHelper.readId(this));
        HttpMethods.getInstance().order(new ProgressSubscriber<>(onNextListener, this, true), map);
    }

    @Override
    public void onPaySuccess() {
        RxToast.normal("支付成功");
        Bundle bundle = new Bundle();
        bundle.putInt("order", 2);
        RxActivityTool.skipActivity(this, MyOrderActivity.class, bundle);
        finish();
    }

    @Override
    public void onPayError() {

    }

    @Override
    public void onPayCancel() {
//        Bundle bundle = new Bundle();
//        bundle.putInt("order", 1);
//        RxActivityTool.skipActivity(this, MyOrderActivity.class, bundle);
//        finish();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.tv_coupon:
                mShopPosition = position;
                mCouponList.clear();
                mCouponList.addAll(confirmBeanList.get(position).getYouhuiquan());
                mCouponUseAdapter.notifyDataSetChanged();
                mCouponDialog.show();
                break;
        }
    }


}
