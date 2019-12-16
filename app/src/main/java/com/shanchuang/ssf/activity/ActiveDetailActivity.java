package com.shanchuang.ssf.activity;

import android.content.Intent;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shanchuang.ssf.R;
import com.shanchuang.ssf.adapter.ActiveDetailAdapter;
import com.shanchuang.ssf.base.BaseActivity;
import com.shanchuang.ssf.bean.ActiveDetailBean;
import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.rxjava.HttpMethods;
import com.shanchuang.ssf.net.subscribers.ProgressSubscriber;
import com.shanchuang.ssf.net.subscribers.SubscriberOnNextListener;
import com.tencent.smtt.sdk.WebView;
import com.vondear.rxtool.RxTimeTool;
import com.vondear.rxtool.view.RxToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/11/26
 */
public class ActiveDetailActivity extends BaseActivity {
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
    @BindView(R.id.rec_all)
    RecyclerView recAll;

    @Override
    protected int getLayoutId() {
        return R.layout.acvitity_detail_layout;
    }
    private String id;
    @Override
    protected void initView() {
        title.setText("活动详情");
        id=getIntent().getStringExtra("id");
        initRec();
    }
    ActiveDetailAdapter mAdapter;
    private List<ActiveDetailBean.CourseBean> mList;
    private void initRec() {
        mList = new ArrayList<>();

        LinearLayoutManager linear = new LinearLayoutManager(this);
        recAll.setLayoutManager(linear);
        recAll.setNestedScrollingEnabled(false);
        mAdapter = new ActiveDetailAdapter(R.layout.item_video_list, mList);
        recAll.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent(this, VideoPlayActivity.class);
            intent.putExtra("id",mList.get(position).getId());
            startActivity(intent);

        });
    }

    @Override
    protected void initData() {
        SubscriberOnNextListener<BaseBean<ActiveDetailBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                mList.addAll(baseBean.getData().getCourse());
                mAdapter.notifyDataSetChanged();
                ActiveDetailBean.HuoDongBean huoDongBean=baseBean.getData().getHuodong();
                web.loadUrl(baseBean.getData().getUrl());
                tvTitle.setText(huoDongBean.getTitle());
                tvTime.setText(RxTimeTool.timeStamp2Date(huoDongBean.getStime(),"")+" 至 "+RxTimeTool.timeStamp2Date(huoDongBean.getEtime(),""));
            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        HttpMethods.getInstance().huodong_detail(new ProgressSubscriber<>(onNextListener, this, true), map);
    }


}
