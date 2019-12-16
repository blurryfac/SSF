package com.shanchuang.ssf.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shanchuang.ssf.MainActivity;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.base.BaseActivity;
import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.entity.LoginBean;
import com.shanchuang.ssf.net.rxjava.HttpMethods;
import com.shanchuang.ssf.net.subscribers.ProgressSubscriber;
import com.shanchuang.ssf.net.subscribers.SubscriberOnNextListener;
import com.shanchuang.ssf.utils.SharedHelper;
import com.shanchuang.ssf.utils.TimeCount;
import com.vondear.rxtool.view.RxToast;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/11/28
 */
public class BindMobileActivity extends BaseActivity {
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
    @BindView(R.id.tv_confirm_modify)
    TextView tvConfirmModify;
    @BindView(R.id.cb_xieyi)
    CheckBox cbXieyi;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_qq_layout;
    }

    private String openid;
    private int type;

    @Override
    protected void initView() {
        time = new TimeCount(60000, 1000);
        openid = getIntent().getStringExtra("openid");
        type=getIntent().getIntExtra("type",0);
    }

    @Override
    protected void initData() {

    }


    //验证码计时器
    TimeCount time;
    private boolean isFinish = false;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isFinish = true;
        time.cancel();
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
        HttpMethods.getInstance().sendCode(new ProgressSubscriber<>(onNextListener, this, true), mobile, 3,type);
    }

    @OnClick({R.id.btn_reg_code, R.id.iv_show_pwd, R.id.confirm_clean_password, R.id.tv_confirm_modify})
    public void onViewClicked(View view) {
        String mobile = etLoginName.getText().toString();
        String code = etRegCode.getText().toString();
        String pwd = etPwd.getText().toString();
        String pwd1 = etConfirmPwd.getText().toString();
        switch (view.getId()) {
            case R.id.btn_reg_code:
                if (isNull(etLoginName)) {
                    RxToast.normal("手机号为空");
                } else {
                    time.start();
                    sendCode(mobile);
                }
                break;
            case R.id.iv_show_pwd:

                break;
            case R.id.confirm_clean_password:
                break;
            case R.id.tv_confirm_modify:
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
                dologin(mobile, code, pwd, pwd1);
                break;
        }
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


    }

    private void dologin(String mobile, String code, String pwd, String pwd1) {
        SubscriberOnNextListener<BaseBean<LoginBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                SharedHelper.saveId(this, baseBean.getData().getUserinfo().getId());
                SharedHelper.saveAvatar(this,  baseBean.getData().getUserinfo().getAvatar());
                SharedHelper.saveNick(this, baseBean.getData().getUserinfo().getNickname());
                SharedHelper.saveMobile(this, baseBean.getData().getUserinfo().getMobile());
                SharedHelper.saveSchoolId(this, baseBean.getData().getUserinfo().getSchool_id());
                SharedHelper.saveOtherId(this, baseBean.getData().getUserinfo().getSchool_id());
                SharedPreferences sp = this.getSharedPreferences("login", Context.MODE_PRIVATE);
                sp.edit().putBoolean("isFirst", false).apply();
                if(SharedHelper.readSchoolId(this).equals("0")){
                    Intent intent = new Intent(BindMobileActivity.this, BindSchoolActivity.class);
                    intent.putExtra("type", 1);
                    intent.putExtra("uid", baseBean.getData().getUserinfo().getId());
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent();
                    intent.setClass(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }


            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("code", code);
        map.put("openid", openid);
        map.put("type", type);
        map.put("password", pwd);
        map.put("pwd", pwd1);
        if (cbXieyi.isChecked()) {
            map.put("xieyi", 1);
        } else {
            map.put("xieyi", 0);
        }
        HttpMethods.getInstance().sq_register(new ProgressSubscriber<>(onNextListener, this, true), map);
    }
}

   
