package com.shanchuang.ssf.mail;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shanchuang.ssf.R;
import com.shanchuang.ssf.activity.ShopEvaActivity;
import com.shanchuang.ssf.activity.ShopMsgActivity;
import com.shanchuang.ssf.base.BaseActivity;
import com.shanchuang.ssf.bean.CouponBean;
import com.shanchuang.ssf.bean.ShopDetailsBean;
import com.shanchuang.ssf.dialog.DialogUtil;
import com.shanchuang.ssf.mail.adapter.CouponDetailAdapter;
import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.rxjava.HttpMethods;
import com.shanchuang.ssf.net.subscribers.ProgressSubscriber;
import com.shanchuang.ssf.net.subscribers.SubscriberOnNextListener;
import com.shanchuang.ssf.utils.GlideImageLoader;
import com.shanchuang.ssf.utils.SharedHelper;
import com.shanchuang.ssf.view.adderView;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.vondear.rxtool.RxActivityTool;
import com.vondear.rxtool.view.RxToast;
import com.vondear.rxui.view.dialog.RxDialogSure;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述：商品详情
 *
 * @author 佚名
 * @time 2018/12/19
 */
public class ShopDetailActivity extends BaseActivity {


    @BindView(R.id.img_shop_details)
    Banner imgShopDetails;
    @BindView(R.id.tv_shop_detail_price)
    TextView tvShopDetailPrice;
    @BindView(R.id.tv_pay_num)
    TextView tvPayNum;
    @BindView(R.id.ll_detail_normal)
    LinearLayout llDetailNormal;
    @BindView(R.id.tv_shop_detail_title)
    TextView tvShopDetailTitle;
    @BindView(R.id.tv_shop_detail_desc)
    TextView tvShopDetailDesc;
    @BindView(R.id.tv_shop_text)
    TextView tvShopText;
    @BindView(R.id.tv_shop)
    TextView tvShop;
    @BindView(R.id.tv_coupon_text)
    TextView tvCouponText;
    @BindView(R.id.tv_coupon)
    TextView tvCoupon;
    @BindView(R.id.ll_coupon)
    RelativeLayout llCoupon;
    @BindView(R.id.tv_eva)
    TextView tvEva;
    @BindView(R.id.web_shop_details)
    WebView webShopDetails;
    @BindView(R.id.cb_shop_coll)
    TextView cbShopColl;
    @BindView(R.id.tv_add_car)
    TextView tvAddCar;
    @BindView(R.id.tv_shop_pay)
    TextView tvShopPay;
    @BindView(R.id.rl_bottom)
    RelativeLayout rlBottom;
    @BindView(R.id.cb_coll)
    CheckBox cbColl;
    @BindView(R.id.iv_detail_back)
    ImageView ivDetailBack;
    @BindView(R.id.iv_detail_share)
    ImageView ivDetailShare;
    @BindView(R.id.tv_eva_num)
    TextView tvEvaNum;
    private String id;
    private Dialog mShareDialog;
    /**
     * 加入购物车成功
     */
    private RxDialogSure mJoinCarSuccessDialog;//选择商品数量
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x10:
                    mJoinCarSuccessDialog.dismiss();
                    break;
            }
        }
    };
    /**
     * 初始化选择数量弹窗
     */
    private Dialog mChoiceNumDialog;//选择商品数量
    private int mChoiceType = 0;//1：加入购物车，2：直接购买
    private int mChoiceValue;
    /**
     * 优惠券弹窗
     */
    private Dialog mCouponDialog;//选择优惠券
    private CouponDetailAdapter mChoiceCouponAdapter;
    private List<CouponBean.YouhuiquanBean> mCouponList = new ArrayList<>();
    /**
     * 轮播图列表
     */
    private List<String> img_list = new ArrayList<>();
    private String mid;//店铺id
    private boolean isFirst = true;

    @OnClick({R.id.tv_add_car, R.id.tv_shop_pay, R.id.tv_coupon, R.id.tv_shop, R.id.tv_eva, R.id.iv_detail_back, R.id.iv_detail_share})
    public void onViewClicked(View view) {
//        if (!SharedHelper.readId(this).isEmpty()) {
        switch (view.getId()) {
            case R.id.tv_shop:
                Bundle bundle = new Bundle();
                bundle.putString("id", mid);
                RxActivityTool.skipActivity(this, ShopMsgActivity.class, bundle);
                break;
            case R.id.tv_eva:
                Intent intent =new Intent(this, ShopEvaActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
                break;
            case R.id.iv_detail_back:
                finish();
                break;
            case R.id.iv_detail_share:
                mShareDialog.show();
                break;
            case R.id.tv_coupon:
                mCouponDialog.show();
//                    mCouponList.clear();
//                    initCouponData();
                break;
            case R.id.tv_add_car:
                addCar();
//                mChoiceType = 1;
//                mChoiceNumDialog.show();
//                    addCar();
                break;

            case R.id.tv_shop_pay:
                mChoiceType = 2;
                mChoiceNumDialog.show();
//                    alreadyPay();
                break;
        }
//        } else {
//            MainActivity.showLoginDialog(this);
//        }
    }

    /**
     * 加入购物车
     */
    private void addCar() {
        SubscriberOnNextListener<BaseBean> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                RxToast.normal(baseBean.getMsg());
            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("uid", SharedHelper.readId(this));
        HttpMethods.getInstance().cart(new ProgressSubscriber<>(onNextListener, this, true), map);
    }

    private void initCouponData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webShopDetails != null) {
            webShopDetails.destroy();
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_detail_layout;
    }

    @Override
    protected void initView() {

        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");
        mChoiceNumDialog = initChoiceNumDialog();
        cbColl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isFirst) {
                    httpCollSub(isChecked);
                }

            }
        });
        initBanner();
        initCouponDialog();
        initJoinCarDialog();
        initShareDialog();
    }

    private Dialog initChoiceNumDialog() {
        Dialog shareDialog = new Dialog(this, R.style.Theme_Light_Dialog);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_choice_param, null);
        adderView adderView = dialogView.findViewById(R.id.add_view);
        TextView mDialogTvCancel = dialogView.findViewById(R.id.tv_cancel);
        TextView mDialogTvConfirm = dialogView.findViewById(R.id.tv_confirm);
        ImageView mDialogTvFinish = dialogView.findViewById(R.id.tv_finish);
        DialogUtil.getInstance().setBottomDialog(this, shareDialog, dialogView);
        mDialogTvFinish.setOnClickListener(v -> mChoiceNumDialog.dismiss());
        mDialogTvCancel.setOnClickListener(v -> mChoiceNumDialog.dismiss());
        mDialogTvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChoiceNumDialog.dismiss();
                if (mChoiceType == 1) {
                    mJoinCarSuccessDialog.show();
                    mHandler.sendEmptyMessageDelayed(0x10, 1000);


                } else if (mChoiceType == 2) {
                    Intent intent = new Intent(ShopDetailActivity.this, ConfirmOrderActivity.class);
                    intent.putExtra("pay_type", 1);
                    intent.putExtra("id", id);
                    intent.putExtra("amount", adderView.getValue() + "");
                    startActivity(intent);
                }
            }
        });
        adderView.setOnValueChangeListene(new adderView.OnValueChangeListener() {
            @Override
            public void onValueChange(int value, View btn_reduce) {
                mChoiceValue = value;
            }
        });

        return shareDialog;
    }

    /**
     * 收藏
     *
     * @param isChecked
     */
    private void httpCollSub(boolean isChecked) {
        SubscriberOnNextListener<BaseBean> onNextListener = baseBean -> RxToast.normal(baseBean.getMsg());
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("uid", SharedHelper.readId(this));
        if (isChecked) {
            cbColl.setText("已收藏");
        } else {
            cbColl.setText("收藏");

        }
        HttpMethods.getInstance().goods_coll(new ProgressSubscriber<>(onNextListener, this, false), map);
    }

    private void initBanner() {
        imgShopDetails.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

            }
        }).setImageLoader(new GlideImageLoader());
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
        mChoiceCouponAdapter = new CouponDetailAdapter(R.layout.item_detail_coupon, mCouponList);
        mCouponBackTv.setOnClickListener(v -> mCouponDialog.dismiss());
        mChoiceCouponAdapter.setOnItemChildClickListener((adapter, view1, position) -> {
            switch (view1.getId()) {
                case R.id.tv_get_coupon:
                    getCoupon(position);
                    break;
            }
        });

        mCouponRv.setAdapter(mChoiceCouponAdapter);
    }

    private void initJoinCarDialog() {
        mJoinCarSuccessDialog = new RxDialogSure(this);
        mJoinCarSuccessDialog.getSureView().setVisibility(View.GONE);
//        mJoinCarSuccessDialog.getmTvLine().setVisibility(View.GONE);
        mJoinCarSuccessDialog.getTitleView().setVisibility(View.GONE);
        mJoinCarSuccessDialog.setLogo(R.mipmap.ic_join_success);
        mJoinCarSuccessDialog.setContent("已成功加入购物车");
    }

    private void initShareDialog() {
        mShareDialog = DialogUtil.getInstance().showShareDialog(this);
        DialogUtil.getInstance().setOnShareClickListener(new DialogUtil.OnShareClickListener() {
            @Override
            public void qqOnClick(View v) {
                mShareDialog.dismiss();
                DialogUtil.ShareWeb(R.mipmap.logo, SHARE_MEDIA.QQ, ShopDetailActivity.this,
                        HttpMethods.SHARE_TITLE, HttpMethods.SHARE_DESC, HttpMethods.BASE_URL + "k12/article/fenxiang");
            }

            @Override
            public void circleOnClick(View v) {

            }

            @Override
            public void qZoneOnClick(View v) {

            }

            @Override
            public void weixinOnClick(View v) {
                mShareDialog.dismiss();
                DialogUtil.ShareWeb(R.mipmap.logo, SHARE_MEDIA.WEIXIN, ShopDetailActivity.this,
                        HttpMethods.SHARE_TITLE, HttpMethods.SHARE_DESC, HttpMethods.BASE_URL + "k12/article/fenxiang");
            }
        });
    }

    /**
     * 获取优惠券
     *
     * @param pos
     */
    private void getCoupon(int pos) {
        SubscriberOnNextListener<BaseBean> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                mCouponList.get(pos).setIs_lq(1);
                mChoiceCouponAdapter.notifyDataSetChanged();
                RxToast.normal(baseBean.getMsg());
            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("yid", mCouponList.get(pos).getId());
        map.put("uid", SharedHelper.readId(this));
        HttpMethods.getInstance().goods_lingqu(new ProgressSubscriber<>(onNextListener, this, true), map);
    }

    @Override
    protected void initData() {
        httpGetData();

    }

    private void httpGetData() {
        SubscriberOnNextListener<BaseBean<ShopDetailsBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                ShopDetailsBean shopDetailsBean = baseBean.getData();
                mid = shopDetailsBean.getGoods().getMid();
                webShopDetails.loadUrl(baseBean.getData().getUrl());
                tvShopDetailPrice.setText("¥ " + shopDetailsBean.getGoods().getPrice());
                tvShopDetailTitle.setText(shopDetailsBean.getGoods().getTitle());
                tvShopDetailDesc.setText(shopDetailsBean.getGoods().getJianjie());
                tvPayNum.setText(shopDetailsBean.getGoods().getSales() + "人付款");
                tvEvaNum.setText("商品评论（" + shopDetailsBean.getComments_count() + "）");
                img_list.addAll(shopDetailsBean.getImages());
                imgShopDetails.setImages(img_list);
                imgShopDetails.start();
                if (baseBean.getData().getIs_coll() == 0) {

                    cbColl.setChecked(false);
                    cbColl.setText("收藏");
                    isFirst = false;
                } else {

                    cbColl.setChecked(true);
                    cbColl.setText("已收藏");
                    isFirst = false;
                }
                httpGetCouPon();
            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("uid", SharedHelper.readId(this));
        HttpMethods.getInstance().goods_detail(new ProgressSubscriber<>(onNextListener, this, true), map);
    }

    private void httpGetCouPon() {
        SubscriberOnNextListener<BaseBean<CouponBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                mCouponList.addAll(baseBean.getData().getYouhuiquan());
                mChoiceCouponAdapter.notifyDataSetChanged();
            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("mid", mid);
        map.put("uid", SharedHelper.readId(this));
        HttpMethods.getInstance().goods_youhuiquan(new ProgressSubscriber<>(onNextListener, this, true), map);
    }

    /**
     * 加载html标签
     *
     * @param bodyHTML
     * @return
     */
    private String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width: 100%; width:auto; height:auto!important;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }


}
