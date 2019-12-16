package com.shanchuang.ssf.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.base.BaseActivity;
import com.shanchuang.ssf.bean.PayBean;
import com.shanchuang.ssf.bean.VideoPlayBean;
import com.shanchuang.ssf.bean.WxPayBean;
import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.rxjava.HttpMethods;
import com.shanchuang.ssf.net.subscribers.ProgressSubscriber;
import com.shanchuang.ssf.net.subscribers.SubscriberOnNextListener;
import com.shanchuang.ssf.pay.PayListenerUtils;
import com.shanchuang.ssf.pay.PayResultListener;
import com.shanchuang.ssf.pay.PayUtils;
import com.shanchuang.ssf.utils.SharedHelper;
import com.shanchuang.ssf.view.ImageViewPlus;
import com.shanchuang.ssf.view.XRadioGroup;
import com.vondear.rxtool.view.RxToast;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/10/15
 */
public class BuyVideoActivity extends BaseActivity implements XRadioGroup.OnCheckedChangeListener, PayResultListener {
    @BindView(R.id.iv_back)
    TextView ivBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_menu)
    TextView toolbarMenu;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_logo)
    ImageViewPlus ivLogo;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_video_price)
    TextView tvVideoPrice;
    @BindView(R.id.rb_wx)
    RadioButton rbWx;
    @BindView(R.id.rb_ali)
    RadioButton rbAli;
    @BindView(R.id.tv_user_score)
    TextView tvUserScore;
    @BindView(R.id.rb_score)
    RadioButton rbScore;
    @BindView(R.id.ll_score)
    LinearLayout llScore;
    @BindView(R.id.tv_price_text)
    TextView tvPriceText;
    @BindView(R.id.tv_all_price)
    TextView tvAllPrice;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    @BindView(R.id.rg_all)
    XRadioGroup rgAll;
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            RxToast.normal("购买成功");
            BuyVideoActivity.this.setResult(2);
            finish();
        }
    };
    private String mCourseId;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
        PayListenerUtils.getInstance(this).removeListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_buy_video_layout;
    }

    @Override
    protected void initView() {
        IntentFilter intentFilter = new IntentFilter("PaySuccess");
        registerReceiver(broadcastReceiver, intentFilter);
        PayListenerUtils.getInstance(this).addListener(this);
        title.setText("订单确认");
        mCourseId = getIntent().getStringExtra("id");
        rgAll.setOnCheckedChangeListener(this);

    }

    @Override
    protected void initData() {
        SubscriberOnNextListener<BaseBean<VideoPlayBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                tvTitle.setText(baseBean.getData().getCourse().getTitle());
                tvVideoPrice.setText("¥ " + baseBean.getData().getCourse().getPrice());
                tvAllPrice.setText("¥ " + baseBean.getData().getCourse().getPrice());
                Glide.with(BuyVideoActivity.this).load(baseBean.getData().getCourse().getImage()).into(ivLogo);
                tvUserScore.setText("账户可用：" + baseBean.getData().getUser_jifen());

            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("id", mCourseId);
        map.put("uid", SharedHelper.readId(this));
        HttpMethods.getInstance().course_order(new ProgressSubscriber<>(onNextListener, this, true), map);
    }


    @Override
    public void onCheckedChanged(XRadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_ali:

                break;
            case R.id.rb_wx:

                break;
            case R.id.rb_score:

                break;
        }
    }

    @OnClick({R.id.ll_score, R.id.tv_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_score:
                rbScore.setChecked(true);
                break;
            case R.id.tv_pay:
                if (rbAli.isChecked()) {
                    httpAliPay(2);
                } else if (rbWx.isChecked()) {
                    httpWXPay(1);
                } else if (rbScore.isChecked()) {
                    httpAliPay(3);
                }
                break;
        }
    }

    private void httpAliPay(int pay_type) {
        SubscriberOnNextListener<BaseBean<PayBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                if (pay_type == 3) {
                    RxToast.normal("购买成功");
                    BuyVideoActivity.this.setResult(2);
                    finish();
                } else if (pay_type == 2) {
                    PayUtils.getInstance(BuyVideoActivity.this).alipay(PayUtils.PAY_TYPE_ALI, baseBean.getData().getPay());
                }


            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("id", mCourseId);
        map.put("uid", SharedHelper.readId(this));
        map.put("paytype", pay_type);
        HttpMethods.getInstance().ali_pay(new ProgressSubscriber<>(onNextListener, this, true), map);
    }

    private void httpWXPay(int i) {
        SubscriberOnNextListener<BaseBean<WxPayBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {

                PayUtils.getInstance(BuyVideoActivity.this).wxpay(PayUtils.PAY_TYPE_WX, baseBean.getData().getPay());


            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("id", mCourseId);
        map.put("uid", SharedHelper.readId(this));
        map.put("paytype", i);
        HttpMethods.getInstance().wx_pay(new ProgressSubscriber<>(onNextListener, this, true), map);
    }

    @Override
    public void onPaySuccess() {
        RxToast.normal("购买成功");
        BuyVideoActivity.this.setResult(2);
        finish();
    }

    @Override
    public void onPayError() {

    }

    @Override
    public void onPayCancel() {

    }
}
