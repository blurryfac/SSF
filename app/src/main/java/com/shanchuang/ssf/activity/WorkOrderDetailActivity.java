package com.shanchuang.ssf.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.shanchuang.ssf.R;
import com.shanchuang.ssf.base.BaseActivity;
import com.shanchuang.ssf.view.ImageViewPlus;
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
public class WorkOrderDetailActivity extends BaseActivity {
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
    @BindView(R.id.tv_shop_name)
    TextView tvShopName;
    @BindView(R.id.iv_tips)
    TextView ivTips;
    @BindView(R.id.tv_msg)
    TextView tvMsg;
    @BindView(R.id.tv_check_eva)
    TextView tvCheckEva;
    @BindView(R.id.tv_work_title)
    TextView tvWorkTitle;
    @BindView(R.id.tv_work_estimated_amount)
    TextView tvWorkEstimatedAmount;
    @BindView(R.id.tv_work_address)
    TextView tvWorkAddress;
    @BindView(R.id.tv_work_distance)
    TextView tvWorkDistance;
    @BindView(R.id.tv_work_start_time)
    TextView tvWorkStartTime;
    @BindView(R.id.tv_work_limit)
    TextView tvWorkLimit;
    @BindView(R.id.tv_work_release_time)
    TextView tvWorkReleaseTime;
    @BindView(R.id.tv_worker_type)
    TextView tvWorkerType;
    @BindView(R.id.iv_order_status)
    ImageView ivOrderStatus;
    @BindView(R.id.tv_work_see_num)
    TextView tvWorkSeeNum;
    @BindView(R.id.tv_work_desc)
    TextView tvWorkDesc;
    @BindView(R.id.tv_deposit)
    TextView tvDeposit;
    @BindView(R.id.rv_imgs)
    RecyclerView rvImgs;
    @BindView(R.id.tv_beizhu)
    TextView tvBeizhu;
    @BindView(R.id.cb_xieyi)
    CheckBox cbXieyi;
    @BindView(R.id.tv_xieyi)
    TextView tvXieyi;
    @BindView(R.id.tv_sign_in)
    TextView tvSignIn;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_work_order_detail_layout;
    }

    @Override
    protected void initView() {
        title.setText("订单详情");
    }

    @Override
    protected void initData() {

    }




    @OnClick({R.id.tv_check_eva, R.id.tv_xieyi, R.id.tv_sign_in})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_check_eva:
                RxActivityTool.skipActivity(this,EmployerEvaActivity.class);
                break;
            case R.id.tv_xieyi:
                break;
            case R.id.tv_sign_in:
                RxActivityTool.skipActivity(this,SignInActivity.class);
                break;
        }
    }
}
