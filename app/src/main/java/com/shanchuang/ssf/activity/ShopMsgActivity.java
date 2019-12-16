package com.shanchuang.ssf.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.flyco.tablayout.SlidingTabLayout;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.base.BaseActivity;
import com.shanchuang.ssf.bean.ShopMsgBean;
import com.shanchuang.ssf.bean.VideoCateBean;
import com.shanchuang.ssf.fragment.ShopGoodsFragment;
import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.rxjava.HttpMethods;
import com.shanchuang.ssf.net.subscribers.ProgressSubscriber;
import com.shanchuang.ssf.net.subscribers.SubscriberOnNextListener;
import com.shanchuang.ssf.view.ImageViewPlus;
import com.vondear.rxtool.view.RxToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/11/8
 */
public class ShopMsgActivity extends BaseActivity {
    @BindView(R.id.ic_return)
    ImageView icReturn;
    @BindView(R.id.iv_logo)
    ImageViewPlus ivLogo;
    @BindView(R.id.tv_shop_name)
    TextView tvShopName;
    @BindView(R.id.tv_shop_phone)
    TextView tvShopPhone;
    @BindView(R.id.stl_main)
    SlidingTabLayout stlMain;
    @BindView(R.id.vp)
    ViewPager vp;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_msg_layout;
    }
    public static String mid="";
    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    @Override
    protected void initView() {
        Bundle bundle=getIntent().getExtras();
        mid=bundle.getString("id");
    }
    private void httpGetCate() {
        SubscriberOnNextListener<BaseBean<VideoCateBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                String arr[] = new String[baseBean.getData().getCate().size()];
                for (int i = 0; i < baseBean.getData().getCate().size(); i++) {
                    arr[i] = baseBean.getData().getCate().get(i).getTitle();
                    fragmentList.add(ShopGoodsFragment.getInstance(baseBean.getData().getCate().get(i).getId()));
                }
                stlMain.setViewPager(vp, arr, this, fragmentList);
            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        HttpMethods.getInstance().goods_cate(new ProgressSubscriber<>(onNextListener, this, false));
    }
    @Override
    protected void initData() {
        httpGetCate();
        httpGetMsg();
    }

    private void httpGetMsg() {
        SubscriberOnNextListener<BaseBean<ShopMsgBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                tvShopName.setText(baseBean.getData().getStore().getTitle());
                tvShopPhone.setText("联系电话："+baseBean.getData().getStore().getMobile());
                Glide.with(this).load(baseBean.getData().getStore().getImage()).into(ivLogo);

            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("mid", mid);
        HttpMethods.getInstance().goods_store(new ProgressSubscriber<>(onNextListener, this, true), map);
    }


    @OnClick(R.id.ic_return)
    public void onViewClicked() {
        finish();
    }
}
