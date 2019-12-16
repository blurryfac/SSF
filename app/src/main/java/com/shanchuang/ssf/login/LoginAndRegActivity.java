package com.shanchuang.ssf.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.base.BaseActivity;
import com.shanchuang.ssf.login.fragment.LoginFragment;
import com.shanchuang.ssf.login.fragment.RegisterFragment;
import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 描述：添加类的描述
 *
 * @author
 * @time 2019/10/12
 */
public class LoginAndRegActivity extends BaseActivity {
    @BindView(R.id.stl_login)
    SlidingTabLayout stlLogin;
    @BindView(R.id.vp)
    ViewPager vp;
    private String[] titles ={"登录","注册"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_and_reg_layout;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {

        mFragments.add(new LoginFragment());
        mFragments.add(new RegisterFragment());
        stlLogin.setViewPager(vp, titles, this, mFragments);
    }

    @Override
    protected void initData() {

    }
}
