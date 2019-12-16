package com.shanchuang.ssf.activity;

import android.os.Bundle;
import android.widget.EditText;
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
 * @time 2019/12/10
 */
public class SignInActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    TextView ivBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_menu)
    TextView toolbarMenu;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_price)
    EditText etPrice;
    @BindView(R.id.tv_sign_in)
    TextView tvSignIn;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sign_in_layout;
    }

    @Override
    protected void initView() {
        title.setText("立即报名");
    }

    @Override
    protected void initData() {

    }



    @OnClick(R.id.tv_sign_in)
    public void onViewClicked() {
        RxActivityTool.skipActivity(this,DepositPayActivity.class);
    }
}
