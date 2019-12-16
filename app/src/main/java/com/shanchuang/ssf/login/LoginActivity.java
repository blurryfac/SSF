package com.shanchuang.ssf.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shanchuang.ssf.R;
import com.shanchuang.ssf.base.BaseActivity;
import com.shanchuang.ssf.base.TcWebActivity;
import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.entity.LoginBean;
import com.shanchuang.ssf.net.rxjava.HttpMethods;
import com.shanchuang.ssf.net.subscribers.ProgressSubscriber;
import com.shanchuang.ssf.net.subscribers.SubscriberOnNextListener;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.vondear.rxtool.RxTextTool;
import com.vondear.rxtool.view.RxToast;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.umeng.commonsdk.stateless.UMSLEnvelopeBuild.mContext;


/**
 * Created by JY on 2017/11/15.
 */

public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    @BindView(R.id.et_login_name)
    EditText etLoginName;
    @BindView(R.id.iv_clean_phone)
    ImageView ivCleanPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.cb_xieyi)
    CheckBox cbXieyi;
    @BindView(R.id.tv_xieyi)
    TextView tvXieyi;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_login_weixin)
    TextView tvLoginWeixin;



    class TimeCount extends CountDownTimer {

        TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            tvCode.setClickable(false);
            tvCode.setText("(" + millisUntilFinished / 1000 + ") 秒后可发送");
        }

        @Override
        public void onFinish() {
            if (!isFinish) {
                tvCode.setText("重新获取");
                tvCode.setClickable(true);
            }


        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isFinish = true;
        time.cancel();

    }

    /**
     * ***************************************** 登录 **************************************
     */
    private String mOpen_id = "";


    private boolean isFinish = false;
    private TimeCount time;

    @OnClick({R.id.btn_login, R.id.tv_xieyi, R.id.iv_clean_phone, R.id.tv_code, R.id.tv_login_weixin})
    public void onViewClicked(View view) {
        String mobile = etLoginName.getText().toString();
        String code = etCode.getText().toString();
        switch (view.getId()) {
            case R.id.tv_code:
                if (mobile.isEmpty()) {
                    RxToast.normal("手机号为空");
                } else {
                    time.start();
                    sendCode(mobile);
                }
                break;
            case R.id.tv_xieyi:
                Intent intent = new Intent(this, TcWebActivity.class);
                intent.putExtra("url", HttpMethods.BASE_URL + "index.php/api/index/aboutinfo?id=9");
                intent.putExtra("title", "用户协议");
                startActivity(intent);
                break;
            case R.id.btn_login:
                if (mobile.isEmpty()) {
                    RxToast.normal(getString(R.string.login_mobile_empty));
                } else if (code.isEmpty()) {
                    RxToast.normal(getString(R.string.login_code_empty));
                } else if (!cbXieyi.isChecked()) {
                    RxToast.normal("请阅读并勾选用户协议");
                } else {
                    login(mobile, code);
                }

                break;
            case R.id.iv_clean_phone:
                etLoginName.setText("");
                etLoginName.setHint("请输入您的手机号");
                break;
            case R.id.tv_login_weixin:
                mShareAPI.getPlatformInfo(this, SHARE_MEDIA.WEIXIN, authListener);
                break;


        }
    }

    private void HttpOpenId(String openid, String name, String iconurl, String sex) {
        SubscriberOnNextListener<BaseBean<LoginBean>> onNextListener = new SubscriberOnNextListener<BaseBean<LoginBean>>() {
            @Override
            public void onNext(BaseBean<LoginBean> baseBean) {
                if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                    Log.i("------------", baseBean.toString());
//                    SharedHelper.saveId(LoginActivity.this, baseBean.getData().ge());
//                    SharedHelper.saveAvatar(LoginActivity.this, HttpMethods.BASE_IMG_URL + baseBean.getData().getUserinfo().getAvatar());
//                    SharedHelper.saveNick(LoginActivity.this, baseBean.getData().getUserinfo().getNickname());
//                    SharedHelper.saveMobile(LoginActivity.this, baseBean.getData().getUserinfo().getMobile());
//                    SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
//                    sp.edit().putBoolean("isFirst", false).apply();
//                    Intent intent = new Intent();
//                    intent.setClass(LoginActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();

                } else if (2 == baseBean.getCode()) {
//                    Intent intent = new Intent(LoginActivity.this, BindMobileActivity.class);
//                    intent.putExtra("nickname", name);
//                    intent.putExtra("openid", openid);
//                    intent.putExtra("avatar", iconurl);
//                    intent.putExtra("sex", sex);
//
//                    startActivity(intent);

                }
                {
                    RxToast.normal(baseBean.getMsg());

                }

            }
        };
        HttpMethods.getInstance().oauthLogin(new ProgressSubscriber<>(onNextListener, this, true), openid, 1);
    }

    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(mContext, "授权成功", Toast.LENGTH_LONG).show();
            Log.i("data", data.toString());
            HttpOpenId(data.get("unionid"), data.get("name"), data.get("iconurl"), data.get("gender"));

//            qqLogin();
        }


        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(mContext, "授权失败：" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(mContext, "授权取消", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareAPI.onActivityResult(requestCode, resultCode, data);

    }

    private void sendCode(String mobile) {

        SubscriberOnNextListener<BaseBean> onNextListener = new SubscriberOnNextListener<BaseBean>() {
            @Override
            public void onNext(BaseBean baseBean) {
                if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                    Log.d(TAG, "onNext: " + baseBean.toString());
                    RxToast.normal("验证码发送成功");

                } else {
                    RxToast.normal(baseBean.getMsg());

                }

            }
        };
//        HttpMethods.getInstance().sendCode(new ProgressSubscriber<>(onNextListener, this, true), mobile, 1);
    }

    private void login(final String mobile, final String code) {
        SubscriberOnNextListener<BaseBean<LoginBean>> onNextListener = new SubscriberOnNextListener<BaseBean<LoginBean>>() {
            @Override
            public void onNext(BaseBean<LoginBean> baseBean) {
                if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
//                    Log.i("------------", baseBean.toString());
//                    SharedHelper.saveId(LoginActivity.this, baseBean.getData().getToken());
//                    SharedHelper.saveAvatar(LoginActivity.this, HttpMethods.BASE_IMG_URL + baseBean.getData().getUserinfo().getAvatar());
//                    SharedHelper.saveNick(LoginActivity.this, baseBean.getData().getUserinfo().getNickname());
//                    SharedHelper.saveMobile(LoginActivity.this, baseBean.getData().getUserinfo().getMobile());
//                    SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
//                    sp.edit().putBoolean("isFirst", false).apply();
//                    Intent intent = new Intent();
//                    intent.setClass(LoginActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
                } else {
                    RxToast.normal(baseBean.getMsg());
                }
            }
        };
        HttpMethods.getInstance().login(new ProgressSubscriber<>(onNextListener, this, true), mobile, code);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_layout;
    }

    UMShareAPI mShareAPI;

    @Override
    public void initView() {
        mShareAPI = UMShareAPI.get(this);
        time = new TimeCount(60000, 1000);
        RxTextTool.getBuilder("同意 ").append("《免责声明》").setForegroundColor(getResources().getColor(R.color.blue_baby)).into(tvXieyi);
        etLoginName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && ivCleanPhone.getVisibility() == View.GONE) {
                    ivCleanPhone.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    ivCleanPhone.setVisibility(View.GONE);
                }
            }
        });


    }

    @Override
    public void initData() {

    }


}
