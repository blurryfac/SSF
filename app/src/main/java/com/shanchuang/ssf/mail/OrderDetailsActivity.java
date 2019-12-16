package com.shanchuang.ssf.mail;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.shanchuang.ssf.base.BaseActivity;
import com.shanchuang.ssf.bean.OrderDetailsBean;
import com.shanchuang.ssf.bean.PayBean;
import com.shanchuang.ssf.bean.WxPayBean;
import com.shanchuang.ssf.dialog.DialogUtil;
import com.shanchuang.ssf.mail.adapter.OrderDetailsAdapter;
import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.rxjava.HttpMethods;
import com.shanchuang.ssf.net.subscribers.ProgressSubscriber;
import com.shanchuang.ssf.net.subscribers.SubscriberOnNextListener;
import com.shanchuang.ssf.pay.PayListenerUtils;
import com.shanchuang.ssf.pay.PayResultListener;
import com.shanchuang.ssf.pay.PayUtils;
import com.shanchuang.ssf.utils.SharedHelper;
import com.vondear.rxtool.RxTimeTool;
import com.vondear.rxtool.view.RxToast;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/3/8
 */
public class OrderDetailsActivity extends BaseActivity implements PayResultListener {
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            RxToast.normal("支付成功");
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
    @BindView(R.id.iv_loc)
    ImageView ivLoc;
    @BindView(R.id.tv_add_empty)
    TextView tvAddEmpty;
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
    @BindView(R.id.rl_coll)
    RecyclerView rlColl;
    @BindView(R.id.tv_shop_all_price)
    TextView tvShopAllPrice;
    @BindView(R.id.tv_all_score)
    TextView tvAllScore;
    @BindView(R.id.tv_all_price)
    TextView tvAllPrice;
    @BindView(R.id.tv_o_num)
    TextView tvONum;
    @BindView(R.id.tv_o_time)
    TextView tvOTime;
    @BindView(R.id.tv_pay_time)
    TextView tvPayTime;
    @BindView(R.id.tv_down_time)
    TextView tvDownTime;
    @BindView(R.id.rec_imgs)
    RecyclerView recImgs;
    @BindView(R.id.btn_1)
    TextView btn1;
    @BindView(R.id.btn_2)
    TextView btn2;
    @BindView(R.id.rl_bottom)
    RelativeLayout rlBottom;
    @BindView(R.id.rl_pay_time)
    RelativeLayout rlPayTime;
    @BindView(R.id.rl_end_time)
    RelativeLayout rlEndTime;

    private int status;
    private String allPrice;//订单金额
    //初始化支付弹窗
    private OrderDetailsAdapter orderAdapter;
    private List<OrderDetailsBean.OrderBean> orderBeanList;
    private String oid = "";
    //    private String all_price;
//    private ImgAdapter imgAdapter;
//    private List<OrderAllBean.PingBean> mEvaList;
//    private ShowEvaAdapter mEvaAdapter;
//    private String wloid;
    private Dialog dialog;

    public static BigDecimal subtract(BigDecimal v1, BigDecimal v2) {
        return v1.subtract(v2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
        PayListenerUtils.getInstance(this).removeListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_details_layout;
    }

    @Override
    protected void initView() {
        title.setText("订单详情");
        IntentFilter intentFilter = new IntentFilter("PaySuccess");
        registerReceiver(broadcastReceiver, intentFilter);
        PayListenerUtils.getInstance(this).addListener(this);
        Intent intent = getIntent();
        oid = intent.getStringExtra("oid");
        status = intent.getIntExtra("status", 0);
        switch (status) {
            case 1:
                btn1.setText("取消订单");
                btn2.setText("立即支付");
                btn1.setVisibility(View.VISIBLE);
                btn2.setVisibility(View.VISIBLE);
                rlEndTime.setVisibility(View.INVISIBLE);
                rlPayTime.setVisibility(View.INVISIBLE);
                break;
            case 2:
                btn1.setVisibility(View.GONE);
                btn2.setVisibility(View.GONE);
                rlEndTime.setVisibility(View.INVISIBLE);
                break;
            case 3:
                btn2.setText("确认收货");
                btn1.setVisibility(View.GONE);
                btn2.setVisibility(View.VISIBLE);
                rlEndTime.setVisibility(View.INVISIBLE);
                break;
            case 4:
                btn2.setText("立即评价");
                btn1.setVisibility(View.GONE);
                btn2.setVisibility(View.VISIBLE);
                rlEndTime.setVisibility(View.INVISIBLE);
                break;
            case 5:
                recImgs.setVisibility(View.VISIBLE);
                btn1.setVisibility(View.GONE);
                btn2.setVisibility(View.GONE);
//                initRecImg();
                break;
        }
        initRec();
    }

    @Override
    public void initData() {
        SubscriberOnNextListener<BaseBean<OrderDetailsBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                orderBeanList.add(baseBean.getData().getOrder());
                orderAdapter.notifyDataSetChanged();
                OrderDetailsBean.OrderBean order = orderBeanList.get(0);
                tvAddress.setText(order.getAddress());
                tvName.setText(order.getName());
                tvMobile.setText(order.getMobile());
                tvShopAllPrice.setText("¥ " + order.getTotal());
                tvAllPrice.setText("¥ " + order.getPay_total());
                tvAllScore.setText("¥ " + order.getYhq_total());
                tvONum.setText(order.getOrder_id());
                tvOTime.setText(RxTimeTool.timeStamp2Date(order.getCreatetime(), ""));
                if (status != 1) {
                    tvPayTime.setText(RxTimeTool.timeStamp2Date(order.getPaytime(), ""));
                }
                if (status==5) {
//                    tvPayTime.setText(RxTimeTool.timeStamp2Date(order.getPaytime(),""));
                    tvDownTime.setText(RxTimeTool.timeStamp2Date(order.getEndtime(), ""));
                }
            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("id", oid);
        map.put("uid", SharedHelper.readId(this));
        HttpMethods.getInstance().order_detail(new ProgressSubscriber<>(onNextListener, this, true), map);


    }

    private void initRec() {
        orderBeanList = new ArrayList<>();
        LinearLayoutManager linear = new LinearLayoutManager(this);
        rlColl.setNestedScrollingEnabled(false);
        rlColl.setLayoutManager(linear);
        orderAdapter = new OrderDetailsAdapter(R.layout.item_order_detail, orderBeanList);
        rlColl.setAdapter(orderAdapter);
        orderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }

    @Override
    public void onPaySuccess() {
        RxToast.normal("支付成功");
        finish();

    }

    @Override
    public void onPayError() {

    }

    @Override
    public void onPayCancel() {
        RxToast.normal("支付取消");
    }

    @OnClick({R.id.btn_1, R.id.btn_2})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {

            case R.id.btn_1:
                if (status == 1) {
                    cancelOrder();
                }
                break;
            case R.id.btn_2:
                if (status == 1) {
                    showPayDialog();
                } else if (status == 3) {
                    Confirm();
                } else if (status == 4) {
                    intent.setClass(this, EvaluateActivity.class);
                    Bundle bundle_2 = new Bundle();
                    bundle_2.putString("oid", oid);
                    bundle_2.putInt("type", 1);
                    bundle_2.putParcelableArrayList("eva", (ArrayList<? extends Parcelable>) orderBeanList.get(0).getGoods());
                    intent.putExtras(bundle_2);
                    startActivity(intent);
                }
                break;
        }
    }

    private void cancelOrder() {
        SubscriberOnNextListener<BaseBean> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                setResult(0);
                finish();
            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();

        map.put("uid", SharedHelper.readId(this));
        map.put("id", oid);
        HttpMethods.getInstance().order_del(new ProgressSubscriber<>(onNextListener, this, true), map);
    }

    private void showPayDialog() {
        dialog = DialogUtil.getInstance().showPayDialog(this);
        DialogUtil.getInstance().setOnPayClickListener(new DialogUtil.OnPayClickListener() {
            @Override
            public void onYuE(View v) {

            }

            @Override
            public void onAliPay(View v) {

            }

            @Override
            public void onWeiXinPay(View v) {

            }
        });
        dialog.show();
    }

    private void Confirm() {
        SubscriberOnNextListener<BaseBean> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                setResult(0);
                finish();
            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();

        map.put("uid", SharedHelper.readId(this));
        map.put("id", oid);
        HttpMethods.getInstance().order_shou(new ProgressSubscriber<>(onNextListener, this, true), map);
    }

    private void getPay() {
        SubscriberOnNextListener<BaseBean<PayBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                PayUtils.getInstance(OrderDetailsActivity.this).alipay(PayUtils.PAY_TYPE_ALI, baseBean.getData().getPay());
            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("id", oid);
        map.put("paytype", 2);
        map.put("uid", SharedHelper.readId(this));
        HttpMethods.getInstance().goods_pay(new ProgressSubscriber<>(onNextListener, this, true), map);

    }

    private void weixinPay() {
        SubscriberOnNextListener<BaseBean<WxPayBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                PayUtils.getInstance(OrderDetailsActivity.this).wxpay(PayUtils.PAY_TYPE_WX, baseBean.getData().getPay());
            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("id", oid);
        map.put("paytype", 1);
        map.put("uid", SharedHelper.readId(this));
        HttpMethods.getInstance().goods_wx_pay(new ProgressSubscriber<>(onNextListener, this, true), map);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
