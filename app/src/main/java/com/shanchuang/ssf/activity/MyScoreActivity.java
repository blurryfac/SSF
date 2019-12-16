package com.shanchuang.ssf.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.adapter.ScoreAdapter;
import com.shanchuang.ssf.base.BaseActivity;
import com.shanchuang.ssf.bean.DefaultBean;
import com.shanchuang.ssf.bean.ScoreBean;
import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.rxjava.HttpMethods;
import com.shanchuang.ssf.net.subscribers.ProgressSubscriber;
import com.shanchuang.ssf.net.subscribers.SubscriberOnNextListener;
import com.shanchuang.ssf.utils.SharedHelper;
import com.vondear.rxtool.view.RxToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/10/22
 */
public class MyScoreActivity extends BaseActivity {
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.rec_main)
    RecyclerView recMain;
    @BindView(R.id.none)
    ImageView none;
    @BindView(R.id.srl_all)
    SmartRefreshLayout srlAll;
    @BindView(R.id.iv_return)
    ImageView ivReturn;
    private List<ScoreBean.LogBean> mList;
    private ScoreAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_score_layout;
    }

    @Override
    protected void initView() {
        mList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recMain.setLayoutManager(linearLayoutManager);
        mAdapter = new ScoreAdapter(R.layout.item_score, mList);
        recMain.setAdapter(mAdapter);
        initSrl();
    }

    private int page = 1;
    private boolean isShowDialog = true;

    private void initSrl() {
        srlAll.setOnLoadmoreListener(refreshlayout -> {
            refreshlayout.finishLoadmore(1500);
            page++;
            isShowDialog = false;
            initData();
        });
        srlAll.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(1500);
                page = 1;
                mList.clear();
                mAdapter.notifyDataSetChanged();
                initData();
            }
        });
    }

    @Override
    protected void initData() {
        httpGetScore();
        httpGetDetails();

    }

    private void httpGetDetails() {
        SubscriberOnNextListener<BaseBean<ScoreBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                if (baseBean.getData().getLog().isEmpty()) {
                    if (page == 1) {
                        none.setVisibility(View.VISIBLE);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        RxToast.normal("没有更多数据了");
                    }

                } else {
                    none.setVisibility(View.INVISIBLE);
                    mList.addAll(baseBean.getData().getLog());
                    mAdapter.notifyDataSetChanged();
                }

            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("uid", SharedHelper.readId(this));
        map.put("page", page);
        HttpMethods.getInstance().score_log(new ProgressSubscriber<>(onNextListener, this, isShowDialog), map);
    }

    private void httpGetScore() {
        SubscriberOnNextListener<BaseBean<DefaultBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                tvScore.setText(baseBean.getData().getScore());
            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("uid", SharedHelper.readId(this));
        HttpMethods.getInstance().jifen(new ProgressSubscriber<>(onNextListener, this, true), map);
    }




    @OnClick(R.id.iv_return)
    public void onViewClicked() {
        finish();
    }
}
