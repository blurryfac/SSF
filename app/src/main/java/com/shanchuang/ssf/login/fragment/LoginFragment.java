package com.shanchuang.ssf.login.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shanchuang.ssf.MainActivity;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.bean.AuthBean;
import com.shanchuang.ssf.login.BindMobileActivity;
import com.shanchuang.ssf.login.ForgetPassActivity;
import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.entity.LoginBean;
import com.shanchuang.ssf.net.rxjava.HttpMethods;
import com.shanchuang.ssf.net.subscribers.ProgressSubscriber;
import com.shanchuang.ssf.net.subscribers.SubscriberOnNextListener;
import com.shanchuang.ssf.utils.SharedHelper;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.vondear.rxtool.RxActivityTool;
import com.vondear.rxtool.view.RxToast;
import com.vondear.rxui.fragment.FragmentLazy;

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
public class LoginFragment extends FragmentLazy {
    @BindView(R.id.et_login_name)
    EditText etLoginName;
    @BindView(R.id.iv_clean_phone)
    ImageView ivCleanPhone;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.clean_password)
    ImageView cleanPassword;
    @BindView(R.id.iv_show_pwd)
    ImageView ivShowPwd;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_forget_pwd)
    TextView tvForgetPwd;
    @BindView(R.id.iv_qq_login)
    ImageView ivQqLogin;
    @BindView(R.id.iv_wx_login)
    ImageView ivWxLogin;
    Unbinder unbinder;
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    @Override
    protected View initViews(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.fragment_login_layout, viewGroup, false);
        unbinder = ButterKnife.bind(this, view);
        mShareAPI = UMShareAPI.get(mContext);
        initTextWatcher();
        return view;
    }

    private void initTextWatcher() {
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
        etPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && cleanPassword.getVisibility() == View.GONE) {
                    cleanPassword.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    cleanPassword.setVisibility(View.GONE);
                }
                if (s.toString().isEmpty()) {
                    return;
                }
                if (!s.toString().matches("[A-Za-z0-9]+")) {
                    String temp = s.toString();
                    Toast.makeText(mContext, "请输入数字或字母", Toast.LENGTH_SHORT).show();
                    s.delete(temp.length() - 1, temp.length());
                    etPwd.setSelection(s.length());
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.iv_clean_phone, R.id.clean_password, R.id.iv_show_pwd, R.id.tv_login, R.id.tv_forget_pwd, R.id.iv_qq_login, R.id.iv_wx_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_clean_phone:
                etLoginName.setText("");
                break;
            case R.id.clean_password:
                etPwd.setText("");
                break;
            case R.id.iv_show_pwd:
                if (etPwd.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ivShowPwd.setImageResource(R.mipmap.jz_zheng);
                } else {
                    etPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ivShowPwd.setImageResource(R.mipmap.jz_yan);
                }
                if (!TextUtils.isEmpty(etPwd.getText().toString()))
                    etPwd.setSelection(etPwd.getText().toString().length());
                break;
            case R.id.tv_login:
                if (etLoginName.getText().toString().isEmpty()) {
                    RxToast.normal(getString(R.string.login_mobile_empty));
                    return;
                }
                if (etPwd.getText().toString().isEmpty()) {
                    RxToast.normal(getString(R.string.login_pwd_empty));
                    return;
                }
                doLogin(etLoginName.getText().toString(),etPwd.getText().toString());
                break;
            case R.id.tv_forget_pwd:
                RxActivityTool.skipActivity(mContext, ForgetPassActivity.class);
                break;
            case R.id.iv_qq_login:
                login_type=1;
                mShareAPI.getPlatformInfo(mContext, SHARE_MEDIA.QQ, authListener);
                break;
            case R.id.iv_wx_login:
                login_type=2;
                mShareAPI.getPlatformInfo(mContext, SHARE_MEDIA.WEIXIN, authListener);
                break;
        }
    }

    UMShareAPI mShareAPI;
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
            HttpOpenId(data.get("openid"));

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
    private int login_type;

    private void HttpOpenId(String openid) {
        SubscriberOnNextListener<BaseBean<AuthBean>> onNextListener = new SubscriberOnNextListener<BaseBean<AuthBean>>() {
            @Override
            public void onNext(BaseBean<AuthBean> baseBean) {
                if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                    Log.i("------------", baseBean.toString());
                    if(baseBean.getData().getFangshi()==1){
                        Intent intent=new Intent(mContext, BindMobileActivity.class);
                        intent.putExtra("openid",openid);
                        intent.putExtra("type",login_type);
                        startActivity(intent);
                    }else {
                        SharedHelper.saveId(mContext, baseBean.getData().getUserinfo().getId());
//                        SharedHelper.saveAvatar(mContext,  baseBean.getData().getUserinfo().getAvatar());
                        SharedHelper.saveNick(mContext, baseBean.getData().getUserinfo().getNickname());
                        SharedHelper.saveMobile(mContext, baseBean.getData().getUserinfo().getMobile());
                        SharedHelper.saveSchoolId(mContext, baseBean.getData().getUserinfo().getSchool_id());
                        SharedHelper.saveOtherId(mContext, baseBean.getData().getUserinfo().getSchool_id());
                        SharedPreferences sp = mContext.getSharedPreferences("login", Context.MODE_PRIVATE);
                        sp.edit().putBoolean("isFirst", false).apply();
                        Intent intent = new Intent();
                        intent.setClass(mContext, MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }

                } else {
                    RxToast.normal(baseBean.getMsg());

                }

            }
        };
        HttpMethods.getInstance().oauthLogin(new ProgressSubscriber<>(onNextListener, mContext, true), openid,login_type);
    }



    /**
     * 执行登录
     */
    private void doLogin(String mobile,String pwd) {
        SubscriberOnNextListener<BaseBean<LoginBean>> onNextListener = new SubscriberOnNextListener<BaseBean<LoginBean>>() {
            @Override
            public void onNext(BaseBean<LoginBean> baseBean) {
                if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                    Log.i("------------", baseBean.toString());
                    SharedHelper.saveId(mContext, baseBean.getData().getUserinfo().getId());
                    SharedHelper.saveAvatar(mContext,  baseBean.getData().getUserinfo().getAvatar());
                    SharedHelper.saveNick(mContext, baseBean.getData().getUserinfo().getNickname());
                    SharedHelper.saveMobile(mContext, baseBean.getData().getUserinfo().getMobile());
                    SharedHelper.saveSchoolId(mContext, baseBean.getData().getUserinfo().getSchool_id());
                    SharedHelper.saveOtherId(mContext, baseBean.getData().getUserinfo().getSchool_id());
                    SharedPreferences sp = mContext.getSharedPreferences("login", Context.MODE_PRIVATE);
                    sp.edit().putBoolean("isFirst", false).apply();
                    Intent intent = new Intent();
                    intent.setClass(mContext, MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                } else {
                    RxToast.normal(baseBean.getMsg());
                }
            }
        };
        HttpMethods.getInstance().login(new ProgressSubscriber<>(onNextListener, mContext, true), mobile, pwd);
    }
}
