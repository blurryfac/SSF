package com.shanchuang.ssf.login.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.login.BindSchoolActivity;
import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.entity.LoginBean;
import com.shanchuang.ssf.net.rxjava.HttpMethods;
import com.shanchuang.ssf.net.subscribers.ProgressSubscriber;
import com.shanchuang.ssf.net.subscribers.SubscriberOnNextListener;
import com.vondear.rxtool.view.RxToast;
import com.vondear.rxui.fragment.FragmentLazy;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 描述：添加类的描述
 *
 * @author
 * @time 2019/10/12
 */
public class RegisterFragment extends FragmentLazy {
    @BindView(R.id.et_login_name)
    EditText etLoginName;
    @BindView(R.id.iv_clean_phone)
    ImageView ivCleanPhone;
    @BindView(R.id.et_reg_code)
    EditText etRegCode;
    @BindView(R.id.btn_reg_code)
    TextView btnRegCode;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.clean_password)
    ImageView cleanPassword;
    @BindView(R.id.iv_show_pwd)
    ImageView ivShowPwd;
    @BindView(R.id.et_confirm_pwd)
    EditText etConfirmPwd;
    @BindView(R.id.confirm_clean_password)
    ImageView confirmCleanPassword;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    Unbinder unbinder;
    //验证码计时器
    TimeCount time;
    @BindView(R.id.cb_xieyi)
    CheckBox cbXieyi;
    private boolean isFinish = false;

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            btnRegCode.setClickable(false);
            btnRegCode.setText("(" + millisUntilFinished / 1000 + ") 秒后可发送");
        }

        @Override
        public void onFinish() {
            if (!isFinish) {
                btnRegCode.setText("重新获取");
                btnRegCode.setClickable(true);
            }


        }
    }
    private SlidingTabLayout mStbLogin;
    @Override
    protected View initViews(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.fragment_register_layout, viewGroup, false);
        unbinder = ButterKnife.bind(this, view);
        mStbLogin=getActivity().findViewById(R.id.stl_login);
        time = new TimeCount(60000, 1000);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        isFinish = true;
        time.cancel();
    }

    @Override
    protected void initData() {

    }

    private void sendCode(String mobile) {

        SubscriberOnNextListener<BaseBean> onNextListener = new SubscriberOnNextListener<BaseBean>() {
            @Override
            public void onNext(BaseBean baseBean) {
                if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                    RxToast.normal("验证码发送成功");
                } else {
                    RxToast.normal(baseBean.getMsg());

                }

            }
        };
        HttpMethods.getInstance().sendCode(new ProgressSubscriber<>(onNextListener, mContext, true), mobile, 1,0);
    }

    @OnClick({R.id.btn_reg_code, R.id.iv_show_pwd, R.id.confirm_clean_password, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_reg_code:
                if (etLoginName.getText().toString().isEmpty()) {
                    RxToast.normal("手机号为空");
                } else {
                    time.start();
                    sendCode(etLoginName.getText().toString());
                }
                break;
            case R.id.iv_show_pwd:
                break;
            case R.id.confirm_clean_password:
                break;
            case R.id.tv_register:
                String mobile = etLoginName.getText().toString();
                String code = etRegCode.getText().toString();
                String pwd = etPwd.getText().toString();
                String pwd1 = etConfirmPwd.getText().toString();
                if (mobile.isEmpty()) {
                    RxToast.normal("手机号为空");
                    return;
                }
                if (code.isEmpty()) {
                    RxToast.normal("验证码为空");
                    return;
                }
                if (pwd.isEmpty()) {
                    RxToast.normal("密码为空");
                    return;
                }
                if (pwd1.isEmpty()) {
                    RxToast.normal("验证密码为空");
                    return;
                }
                doRegister(mobile, code, pwd, pwd1);
                break;
        }
    }

    /**
     * 执行注册
     *
     * @param mobile 手机号
     * @param code   验证码
     * @param pwd    密码
     * @param pwd1   验证密码
     */
    private void doRegister(String mobile, String code, String pwd, String pwd1) {
        SubscriberOnNextListener<BaseBean<LoginBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                clearScreen();
                mStbLogin.setCurrentTab(0);
                Intent intent =new Intent(mContext, BindSchoolActivity.class);
                intent.putExtra("type",1);
                intent.putExtra("uid",baseBean.getData().getUserinfo().getId());
                startActivity(intent);

            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("code", code);
        map.put("password", pwd);
        map.put("pwd", pwd1);
        if (cbXieyi.isChecked()) {
            map.put("xieyi", 1);
        }else {
            map.put("xieyi", 0);
        }
        HttpMethods.getInstance().register(new ProgressSubscriber<>(onNextListener, mContext, true), map);

    }

    /**
     * 清除屏幕
     */
    private void clearScreen() {
        time.cancel();
        etLoginName.setText("");
        etRegCode.setText("");
        etPwd.setText("");
        etConfirmPwd.setText("");
    }


}
