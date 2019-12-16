package com.shanchuang.ssf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.adapter.GoodsAdapter;
import com.shanchuang.ssf.base.BaseActivity;
import com.shanchuang.ssf.bean.ShopGoodsBean;
import com.shanchuang.ssf.mail.ShopDetailActivity;
import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.rxjava.HttpMethods;
import com.shanchuang.ssf.net.subscribers.ProgressSubscriber;
import com.shanchuang.ssf.net.subscribers.SubscriberOnNextListener;
import com.shanchuang.ssf.utils.SharedHelper;
import com.vondear.rxtool.RxActivityTool;
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
public class GoodsSearchActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    TextView ivBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_menu)
    TextView toolbarMenu;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rec_main)
    RecyclerView recMain;
    @BindView(R.id.none)
    ImageView none;
    @BindView(R.id.srl_all)
    SmartRefreshLayout srlAll;
    private List<ShopGoodsBean.GoodsBean> mailList;
    private GoodsAdapter mMailAdapter;
    private boolean isShowDialog = true;
    private int page = 1;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_goods_search_layout;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        title.setText(intent.getStringExtra("title"));
        initRec();
        initSrl();
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
                mailList.clear();
                isShowDialog=false;
                mMailAdapter.notifyDataSetChanged();
                initData();
            }
        });
    }
    private void initRec() {
        mailList = new ArrayList<>();
        GridLayoutManager linear = new GridLayoutManager(this, 2);
        recMain.setLayoutManager(linear);
        mMailAdapter = new GoodsAdapter(R.layout.item_mail, mailList);
        recMain.setNestedScrollingEnabled(false);
        recMain.setAdapter(mMailAdapter);

        mMailAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("id", mailList.get(position).getId());
                RxActivityTool.skipActivity(GoodsSearchActivity.this, ShopDetailActivity.class, bundle);
            }
        });
    }

    @Override
    protected void initData() {
        SubscriberOnNextListener<BaseBean<ShopGoodsBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                if (baseBean.getData().getGoods().isEmpty()) {
                    if (page == 1) {
                        none.setVisibility(View.VISIBLE);
                        mMailAdapter.notifyDataSetChanged();
                    } else {
                        RxToast.normal("没有更多数据了");
                    }

                } else {
                    none.setVisibility(View.INVISIBLE);
                    mailList.addAll(baseBean.getData().getGoods());
                    mMailAdapter.notifyDataSetChanged();
                }

            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();
//        map.put("cid", id);
        map.put("keyword", title.getText().toString());
        map.put("uid", SharedHelper.readId(this));
        map.put("page", page);
        HttpMethods.getInstance().goods_lists(new ProgressSubscriber<>(onNextListener, this, isShowDialog), map);
    }

}
