package com.shanchuang.ssf.activity;

import android.os.Bundle;
import android.widget.Switch;
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
 * @time 2019/12/13
 */
public class DepositPayGZActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    TextView ivBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_menu)
    TextView toolbarMenu;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_bond)
    TextView tvBond;
    @BindView(R.id.tv_sy_price)
    TextView tvSyPrice;
    @BindView(R.id.switch_dk)
    Switch switchDk;
    @BindView(R.id.tv_pay_price)
    TextView tvPayPrice;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_deposit_pay_gz_layout;
    }

    @Override
    protected void initView() {
        title.setText("支付押金");
    }

    @Override
    protected void initData() {

    }



    @OnClick(R.id.tv_submit)
    public void onViewClicked() {
        RxActivityTool.skipActivity(this,DepositPaySuccessActivity.class);
    }
}
