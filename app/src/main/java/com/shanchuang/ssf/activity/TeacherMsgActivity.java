package com.shanchuang.ssf.activity;

import android.content.Intent;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.base.BaseActivity;
import com.shanchuang.ssf.bean.TeamMsgBean;
import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.rxjava.HttpMethods;
import com.shanchuang.ssf.net.subscribers.ProgressSubscriber;
import com.shanchuang.ssf.net.subscribers.SubscriberOnNextListener;
import com.shanchuang.ssf.view.ImageViewPlus;
import com.tencent.smtt.sdk.WebView;
import com.vondear.rxtool.view.RxToast;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * 描述：教师简介
 *
 * @author
 * @time 2019/10/14
 */
public class TeacherMsgActivity extends BaseActivity {
    @BindView(R.id.tv_teacher_name)
    TextView tvTeacherName;
    @BindView(R.id.tv_teacher_age)
    TextView tvTeacherAge;
    @BindView(R.id.tv_teacher_education)
    TextView tvTeacherEducation;
    @BindView(R.id.tv_teacher_years)
    TextView tvTeacherYears;
    @BindView(R.id.iv_logo)
    ImageViewPlus ivLogo;
    @BindView(R.id.web_teacher)
    WebView webTeacher;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_teacher_msg_layout;
    }
private String id;
    @Override
    protected void initView() {
        title.setText("教师简介");
        Intent intent=getIntent();
        id=intent.getStringExtra("id");
    }

    @Override
    protected void initData() {
        SubscriberOnNextListener<BaseBean<TeamMsgBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                TeamMsgBean.TeamBean teamBean=baseBean.getData().getTeam();
                tvTeacherAge.setText(teamBean.getAge()+"岁");
                tvTeacherEducation.setText("学历："+teamBean.getXueli());
                tvTeacherName.setText(teamBean.getName());
                tvTeacherYears.setText("任教："+teamBean.getRenjiao());
                webTeacher.loadUrl(baseBean.getData().getUrl());
                Glide.with(this).load(teamBean.getImage()).into(ivLogo);
            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        HttpMethods.getInstance().team_detail(new ProgressSubscriber<>(onNextListener, this, true), map);
    }
}
