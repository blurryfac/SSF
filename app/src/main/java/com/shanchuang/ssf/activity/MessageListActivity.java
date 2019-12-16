package com.shanchuang.ssf.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.adapter.MessageListAdapter;
import com.shanchuang.ssf.base.BaseActivity;
import com.shanchuang.ssf.bean.MessageListBean;
import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.rxjava.HttpMethods;
import com.shanchuang.ssf.net.subscribers.ProgressSubscriber;
import com.shanchuang.ssf.net.subscribers.SubscriberOnNextListener;
import com.shanchuang.ssf.utils.DividerItemDecoration;
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
 * @time 2019/11/20
 */
public class MessageListActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    TextView ivBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_menu)
    TextView toolbarMenu;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rec_active)
    RecyclerView recActive;
    @BindView(R.id.none)
    ImageView none;
    @BindView(R.id.srl_all)
    SmartRefreshLayout srlAll;
    private List<MessageListBean.NewsBean> mList;
    private MessageListAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_list_layout;
    }

    @Override
    protected void initView() {
        title.setText("我的消息");
        initRec();
        initSrl();
    }

    private void initRec() {
        mList = new ArrayList<>();

        LinearLayoutManager linear = new LinearLayoutManager(this);
        recActive.setLayoutManager(linear);
        recActive.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        recActive.setNestedScrollingEnabled(false);
        mAdapter = new MessageListAdapter(R.layout.item_message_list, mList);
        recActive.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent(this, MessageDetailsActivity.class);
            intent.putExtra("id",mList.get(position).getId());
            startActivity(intent);

        });
    }

    private void initSrl() {
        srlAll.setOnLoadmoreListener(refreshlayout -> {
            refreshlayout.finishLoadmore(1500);
            page++;
            isShowDialog=false;
            initData();
        });
        srlAll.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(1500);
                page=1;
                mList.clear();
                mAdapter.notifyDataSetChanged();
                initData();
            }
        });
    }
    private int page=1;
    private boolean isShowDialog=true;
    @Override
    protected void initData() {
        SubscriberOnNextListener<BaseBean<MessageListBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                if (baseBean.getData().getNews().isEmpty()) {
                    if(page==1){
                        none.setVisibility(View.VISIBLE);
                        mAdapter.notifyDataSetChanged();
                    }else {
                        RxToast.normal("没有更多数据了");
                    }

                } else {
                    none.setVisibility(View.INVISIBLE);
                    mList.addAll(baseBean.getData().getNews());
                    mAdapter.notifyDataSetChanged();
                }

            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        HttpMethods.getInstance().news_lists(new ProgressSubscriber<>(onNextListener, this, isShowDialog), map);
    }
}
