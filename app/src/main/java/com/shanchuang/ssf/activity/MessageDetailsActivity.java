package com.shanchuang.ssf.activity;

import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.shanchuang.ssf.R;
import com.shanchuang.ssf.base.BaseActivity;
import com.shanchuang.ssf.bean.MessageDetailsBean;
import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.rxjava.HttpMethods;
import com.shanchuang.ssf.net.subscribers.ProgressSubscriber;
import com.shanchuang.ssf.net.subscribers.SubscriberOnNextListener;
import com.tencent.smtt.sdk.WebView;
import com.vondear.rxtool.RxTimeTool;
import com.vondear.rxtool.view.RxToast;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/11/20
 */
public class MessageDetailsActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    TextView ivBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_menu)
    TextView toolbarMenu;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.web)
    WebView web;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_detail_layout;
    }
    private String id;
    @Override
    protected void initView() {
        title.setText("消息详情");
        id=getIntent().getStringExtra("id");
    }

    @Override
    protected void initData() {
        SubscriberOnNextListener<BaseBean<MessageDetailsBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
              tvTitle.setText(baseBean.getData().getNews().getTitle());
                tvTime.setText(RxTimeTool.timeStamp2Date(baseBean.getData().getNews().getCreatetime(),""));
                web.loadUrl(baseBean.getData().getUrl());
            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        HttpMethods.getInstance().news_detail(new ProgressSubscriber<>(onNextListener, this, true), map);
    }


}
