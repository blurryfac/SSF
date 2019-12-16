package com.shanchuang.ssf.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.shanchuang.ssf.R;
import com.shanchuang.ssf.base.BaseActivity;
import com.vondear.rxtool.RxActivityTool;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/12/16
 */
public class DepositPaySuccessActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    TextView ivBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_menu)
    TextView toolbarMenu;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_pay_price)
    TextView tvPayPrice;
    @BindView(R.id.tv_pay_status)
    TextView tvPayStatus;
    @BindView(R.id.tv_go_order)
    TextView tvGoOrder;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_depositpay_success_layout;
    }

    @Override
    protected void initView() {
        title.setText("报名成功");
    }

    @Override
    protected void initData() {

    }



    @OnClick(R.id.tv_go_order)
    public void onViewClicked() {
        RxActivityTool.skipActivity(this,RleaseOrderActivity.class);
    }
}
