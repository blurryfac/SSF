package com.shanchuang.ssf.activity;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.shanchuang.ssf.R;
import com.shanchuang.ssf.base.BaseActivity;
import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.rxjava.HttpMethods;
import com.shanchuang.ssf.net.subscribers.ProgressSubscriber;
import com.shanchuang.ssf.net.subscribers.SubscriberOnNextListener;
import com.shanchuang.ssf.utils.SharedHelper;
import com.vondear.rxtool.view.RxToast;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/7/18
 */
public class ModityMobileActivity extends BaseActivity {
    private static final String TAG = "ModityMobileActivity";
    @BindView(R.id.et_reg_mobile)
    EditText etRegMobile;
    @BindView(R.id.et_reg_code)
    EditText etRegCode;
    @BindView(R.id.btn_reg_code)
    TextView btnRegCode;
    @BindView(R.id.btn_reg)
    Button btnReg;
    //验证码计时器
    TimeCount time;
    private boolean isFinish = false;

    @OnClick({R.id.btn_reg_code, R.id.btn_reg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_reg_code:
                if (isNull(etRegMobile)) {
                    RxToast.normal("手机号为空");
                } else {
                    time.start();
                    sendCode(getString(etRegMobile));
                }
                break;
            case R.id.btn_reg:
                submit();
                break;
        }
    }

    /**
     * 发送验证码
     *
     * @param mobile
     */
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
        HttpMethods.getInstance().user_Code(new ProgressSubscriber<>(onNextListener, this, true), mobile, SharedHelper.readId(this));
    }

    private void submit() {
        SubscriberOnNextListener<BaseBean> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {

                RxToast.normal(baseBean.getMsg());
                setResult(3);
                finish();
            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("uid", SharedHelper.readId(this));
        map.put("code", etRegCode.getText().toString());
        map.put("mobile", etRegMobile.getText().toString());
        HttpMethods.getInstance().user_mobile(new ProgressSubscriber<>(onNextListener, this, true), map);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isFinish = true;
        time.cancel();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_mobile_layout;
    }

    @Override
    protected void initView() {
        title.setText("更换手机号");
        etRegMobile.setText(getIntent().getExtras().getString("value"));
        time = new TimeCount(60000, 1000);
    }

    @Override
    protected void initData() {

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
