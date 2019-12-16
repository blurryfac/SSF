package com.shanchuang.ssf.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.activity.MyInfoActivity;
import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.entity.UserBean;
import com.shanchuang.ssf.net.rxjava.HttpMethods;
import com.shanchuang.ssf.net.subscribers.ProgressSubscriber;
import com.shanchuang.ssf.net.subscribers.SubscriberOnNextListener;
import com.shanchuang.ssf.utils.SharedHelper;
import com.shanchuang.ssf.view.ImageViewPlus;
import com.vondear.rxtool.RxActivityTool;
import com.vondear.rxtool.view.RxToast;
import com.vondear.rxui.fragment.FragmentLazy;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.shanchuang.ssf.Config.Type.INFO_RESULT_CODE;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/1/29
 */
public class PersonCenterFragment extends FragmentLazy {
    private static final int INFO_CODE = 1;
    Unbinder unbinder;
    @BindView(R.id.iv_tou)
    ImageViewPlus ivTou;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_me_info)
    TextView tvMeInfo;
    @BindView(R.id.tv_me_coll)
    TextView tvMeColl;
    @BindView(R.id.tv_me_wallet)
    TextView tvMeWallet;
    @BindView(R.id.tv_me_coupon)
    TextView tvMeCoupon;
    @BindView(R.id.tv_me_shop_verify)
    TextView tvMeShopVerify;
    @BindView(R.id.tv_me_g_verify)
    TextView tvMeGVerify;
    @BindView(R.id.tv_me_share)
    TextView tvMeShare;
    @BindView(R.id.tv_me_lxkf)
    TextView tvMeLxkf;
    @BindView(R.id.tv_me_yjfk)
    TextView tvMeYjfk;
    @BindView(R.id.tv_me_bzj)
    TextView tvMeBzj;
    @BindView(R.id.tv_me_set)
    TextView tvMeSet;


    /**
     * 获取用户信息
     */

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            setUserInfo();
        }
    }

    @Override
    protected View initViews(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.fragment_person_center_layout, viewGroup, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {

    }

    /**
     * 设置用户信息
     */
    private boolean isBind = false;
    private String mobile;

    private void setUserInfo() {
        SubscriberOnNextListener<BaseBean<UserBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                tvName.setText(baseBean.getData().getUser().getNickname());
                Glide.with(mContext).load(baseBean.getData().getUser().getAvatar()).into(ivTou);

                mobile = baseBean.getData().getTel();
            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("uid", SharedHelper.readId(mContext));
        HttpMethods.getInstance().user_index(new ProgressSubscriber<>(onNextListener, mContext, false), map);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INFO_CODE && resultCode == INFO_RESULT_CODE) {
            setUserInfo();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
        setUserInfo();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.tv_me_info, R.id.tv_me_coll, R.id.tv_me_wallet, R.id.tv_me_coupon, R.id.tv_me_shop_verify, R.id.tv_me_g_verify, R.id.tv_me_share, R.id.tv_me_lxkf, R.id.tv_me_yjfk, R.id.tv_me_bzj, R.id.tv_me_set})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_me_info:
                RxActivityTool.skipActivity(mContext, MyInfoActivity.class);
                break;
            case R.id.tv_me_coll:
                RxActivityTool.skipActivity(mContext, MyCollActivity.class);
                break;
            case R.id.tv_me_wallet:
                break;
            case R.id.tv_me_coupon:
                break;
            case R.id.tv_me_shop_verify:
                break;
            case R.id.tv_me_g_verify:
                break;
            case R.id.tv_me_share:
                break;
            case R.id.tv_me_lxkf:
                break;
            case R.id.tv_me_yjfk:
                break;
            case R.id.tv_me_bzj:
                break;
            case R.id.tv_me_set:
                break;
        }
    }
}
