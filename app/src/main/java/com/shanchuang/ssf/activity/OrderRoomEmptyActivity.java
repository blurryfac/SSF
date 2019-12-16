package com.shanchuang.ssf.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.shanchuang.ssf.R;
import com.shanchuang.ssf.base.BaseActivity;
import com.vondear.rxtool.RxActivityTool;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 描述：接单大厅空数据
 *
 * @author 金源
 * @time 2019/12/5
 */
public class OrderRoomEmptyActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    TextView ivBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_menu)
    TextView toolbarMenu;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_verify_status)
    TextView tvVerifyStatus;
    @BindView(R.id.btn_verify)
    Button btnVerify;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_room_empty_layout;
    }

    @Override
    protected void initView() {
        title.setText("接单大厅");
    }

    @Override
    protected void initData() {

    }




    @OnClick(R.id.btn_verify)
    public void onViewClicked() {
        RxActivityTool.skipActivity(this,WorkerVerifyActivity.class);
    }
}
