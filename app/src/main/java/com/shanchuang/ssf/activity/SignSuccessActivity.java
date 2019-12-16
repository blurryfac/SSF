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
 * 描述：报名成功
 *
 * @author 金源
 * @time 2019/12/10
 */
public class SignSuccessActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    TextView ivBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_menu)
    TextView toolbarMenu;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_go_order)
    TextView tvGoOrder;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sign_success_layout;
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
        RxActivityTool.skipActivity(this,OrderRoomActivity.class);
    }
}
