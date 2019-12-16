package com.shanchuang.ssf.activity;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.shanchuang.ssf.R;
import com.shanchuang.ssf.base.BaseActivity;
import com.vondear.rxtool.RxActivityTool;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述：保证金支付
 *
 * @author 金源
 * @time 2019/12/7
 */
public class BondPayActivity extends BaseActivity {


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
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.tv_cancel_pay)
    TextView tvCancelPay;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bond_pay_layout;
    }

    @Override
    protected void initView() {
        title.setText("支付保证金");
    }

    @Override
    protected void initData() {

    }




    @OnClick({R.id.tv_submit, R.id.tv_cancel_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_submit:
                RxActivityTool.skipActivity(this,WorkerVerifyStatusActivity.class);
                break;
            case R.id.tv_cancel_pay:
                finish();
                break;
        }
    }
}
