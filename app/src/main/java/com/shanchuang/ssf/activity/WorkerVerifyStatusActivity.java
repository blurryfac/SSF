package com.shanchuang.ssf.activity;

import android.os.Bundle;
import android.widget.ImageView;
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
 * @time 2019/12/7
 */
public class WorkerVerifyStatusActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    TextView ivBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_menu)
    TextView toolbarMenu;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_status)
    ImageView ivStatus;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_modify)
    TextView tvModify;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_worker_verify_status_layout;
    }

    @Override
    protected void initView() {
        title.setText("工人认证");
    }

    private int mVerifyStatus = 0;

    @Override
    protected void initData() {
        if (mVerifyStatus == 1) {//审核中
            tvStatus.setText(R.string.tv_submit_verify_success);
            setImg(ivStatus,R.mipmap.ic_verify_post);
        }else  if (mVerifyStatus == 2) {//审核通过
            tvStatus.setText(R.string.tv_verify_success);
            setImg(ivStatus,R.mipmap.ic_verify_success);
        }else  if (mVerifyStatus == 3) {//审核失败
            tvStatus.setText("审核失败");
            setImg(ivStatus,R.mipmap.ic_verify_error);
        }
    }



    @OnClick(R.id.tv_modify)
    public void onViewClicked() {
        RxActivityTool.skipActivity(this,ModifyWorkerMsgActivity.class);
    }
}
