package com.shanchuang.ssf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.shanchuang.ssf.R;
import com.shanchuang.ssf.base.BaseActivity;
import com.shanchuang.ssf.bean.AddrEditBean;
import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.rxjava.HttpMethods;
import com.shanchuang.ssf.net.subscribers.ProgressSubscriber;
import com.shanchuang.ssf.net.subscribers.SubscriberOnNextListener;
import com.shanchuang.ssf.utils.SharedHelper;
import com.shanchuang.ssf.view.PickerView;
import com.vondear.rxtool.view.RxToast;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JY on 2018/5/7.
 */

public class EditAddrActivity extends BaseActivity {

    private static final int MSG_LOAD_DATA = 0x0001;
    @BindView(R.id.et_addr)
    EditText etAddr;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.rl_city)
    LinearLayout rlCity;
    @BindView(R.id.tv_city)
    TextView tvCity;
    PickerView pickerView;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.iv_back)
    TextView ivBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_menu)
    TextView toolbarMenu;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.switch_default)
    Switch switchDefault;
    private String type;
    private String id = "";

    @OnClick({R.id.iv_back, R.id.btn_save, R.id.tv_city})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_city:
                String s = pickerView.showPickerView(this, tvCity);
                break;
            case R.id.btn_save:

                if (etName.getText().toString().isEmpty()) {
                } else if (etMobile.getText().toString().isEmpty()) {
                } else if (etAddr.getText().toString().isEmpty()) {
                } else if ("请选择地区".equals(tvCity.getText().toString())) {
                } else {
                    saveMessage();
                }
                break;
        }
    }

    private void saveMessage() {
        SubscriberOnNextListener<BaseBean> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                RxToast.normal(baseBean.getMsg());
               setResult(2);
               finish();

            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("uid", SharedHelper.readId(this));
        if("1".equals(type)){
            map.put("id", id);
        }
        map.put("name", getString(etName));
        map.put("mobile", getString(etMobile));
        map.put("address", getString(etAddr));
        String [] addr=getString(tvCity).split("-");
        map.put("sheng", addr[0]);
        map.put("shi", addr[1]);
        map.put("xian", addr[2]);
        if(switchDefault.isChecked()){
            map.put("is_default",1);
        }else {
            map.put("is_default",0);
        }
        HttpMethods.getInstance().add_addr(new ProgressSubscriber<>(onNextListener, this, true), map);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_addr_layout;
    }

    @Override
    protected void initView() {
        title.setText("收货地址");
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        if ("1".equals(type)) {
//            etAddr.setText(intent.getStringExtra("addrinfo"));
//            tvCity.setText(intent.getStringExtra("addr"));
//            etName.setText(intent.getStringExtra("name"));
//            etMobile.setText(intent.getStringExtra("mobile"));
            id = intent.getStringExtra("id");
            getInfo();
//            if (intent.getIntExtra("tc", 0) == 1) {
//                checkDefault.setChecked(true);
//            }
        }
        pickerView = new PickerView(this);
        pickerView.mHandler.sendEmptyMessage(MSG_LOAD_DATA);
    }

    @Override
    public void initData() {

    }

    private void getInfo() {
        SubscriberOnNextListener<BaseBean<AddrEditBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                RxToast.normal(baseBean.getMsg());
                etAddr.setText(baseBean.getData().getAddr().getAddress());
                tvCity.setText(baseBean.getData().getAddr().getSheng() + "-" + baseBean.getData().getAddr().getShi() + "-" + baseBean.getData().getAddr().getXian());
                etName.setText(baseBean.getData().getAddr().getName());
                etMobile.setText(baseBean.getData().getAddr().getMobile());
                if (baseBean.getData().getAddr().getIs_default() == 1) {
                    switchDefault.setChecked(true);
                } else {
                    switchDefault.setChecked(false);
                }

            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("uid", SharedHelper.readId(this));
        map.put("id", id);
        HttpMethods.getInstance().edit_addr(new ProgressSubscriber<>(onNextListener, this, true), map);
    }
}
