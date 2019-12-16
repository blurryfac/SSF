package com.shanchuang.ssf.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.shanchuang.ssf.R;
import com.shanchuang.ssf.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/12/5
 */
public class WorkerVerifyActivity extends BaseActivity {
    @BindView(R.id.et_true_name)
    EditText etTrueName;
    @BindView(R.id.rb_man)
    RadioButton rbMan;
    @BindView(R.id.rb_woman)
    RadioButton rbWoman;
    @BindView(R.id.rg_sex)
    RadioGroup rgSex;
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.tv_now_address)
    TextView tvNowAddress;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_id_num)
    EditText etIdNum;
    @BindView(R.id.et_emergency_contact)
    EditText etEmergencyContact;
    @BindView(R.id.et_emergency_contact_mobile)
    EditText etEmergencyContactMobile;
    @BindView(R.id.rv_type_of_work)
    RecyclerView rvTypeOfWork;
    @BindView(R.id.tv_gl)
    TextView tvGl;
    @BindView(R.id.et_hj)
    EditText etHj;
    @BindView(R.id.iv_zheng)
    ImageView ivZheng;
    @BindView(R.id.iv_fan)
    ImageView ivFan;
    @BindView(R.id.iv_zj)
    ImageView ivZj;
    @BindView(R.id.et_option)
    EditText etOption;
    @BindView(R.id.rv_imgs)
    RecyclerView rvImgs;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_worker_verify_layout;
    }

    @Override
    protected void initView() {
        title.setText("工人认证");
    }

    @Override
    protected void initData() {

    }



    @OnClick({R.id.tv_now_address, R.id.tv_gl, R.id.iv_zheng, R.id.iv_fan, R.id.iv_zj, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_now_address:
                break;
            case R.id.tv_gl:
                break;
            case R.id.iv_zheng:
                break;
            case R.id.iv_fan:
                break;
            case R.id.iv_zj:
                break;
            case R.id.tv_submit:
                break;
        }
    }
}
