package com.shanchuang.ssf.login;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shanchuang.ssf.R;
import com.shanchuang.ssf.base.BaseActivity;
import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.entity.LoginBean;
import com.shanchuang.ssf.net.rxjava.HttpMethods;
import com.shanchuang.ssf.net.subscribers.ProgressSubscriber;
import com.shanchuang.ssf.net.subscribers.SubscriberOnNextListener;
import com.vondear.rxtool.view.RxToast;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述：添加类的描述
 *
 * @author
 * @time 2019/10/12
 */
public class ForgetPassActivity extends BaseActivity {
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_pass_layout;
    }

    @Override
    protected void initView() {
        time = new TimeCount(60000, 1000);
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
    /**
     * 执行注册
     *
     * @param mobile 手机号
     * @param code   验证码
     * @param pwd    密码
     * @param pwd1   验证密码
     */
    private void doModify(String mobile, String code, String pwd, String pwd1) {
        SubscriberOnNextListener<BaseBean<LoginBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                finish();

            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("code", code);
        map.put("password", pwd);
        map.put("pwd", pwd1);

        HttpMethods.getInstance().password(new ProgressSubscriber<>(onNextListener, this, true), map);

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
        HttpMethods.getInstance().sendCode(new ProgressSubscriber<>(onNextListener, this, true), mobile, 2,0);
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
                doModify(mobile, code, pwd, pwd1);
                break;
        }
    }
}
