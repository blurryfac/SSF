package com.shanchuang.ssf.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.adapter.AddrAdapter;
import com.shanchuang.ssf.base.BaseActivity;
import com.shanchuang.ssf.bean.AddrBean;
import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.rxjava.HttpMethods;
import com.shanchuang.ssf.net.subscribers.ProgressSubscriber;
import com.shanchuang.ssf.net.subscribers.SubscriberOnNextListener;
import com.shanchuang.ssf.utils.SharedHelper;
import com.shanchuang.ssf.utils.SwipeItemLayout;
import com.vondear.rxtool.view.RxToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by JY on 2018/5/4.
 */

public class AddrActivity extends BaseActivity implements BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.rec_all)
    RecyclerView recAll;
    @BindView(R.id.img_gone)
    TextView imgGone;
    @BindView(R.id.srl_all)
    SmartRefreshLayout srlAll;
    private AddrAdapter addrAdapter;
    private List<AddrBean.AddrMsgBean> addrBeanList;
    private String type;
    private boolean isShowDialog = true;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_addr_layout;
    }

    @Override
    protected void initView() {
        title.setText("收货地址");
        addrBeanList = new ArrayList<>();
        Intent intent = getIntent();
        if (intent.getStringExtra("type") != null) {
            type = "1";
        }

        LinearLayoutManager linear = new LinearLayoutManager(this);
        recAll.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
        recAll.setLayoutManager(linear);
        addrAdapter = new AddrAdapter(R.layout.item_addr, addrBeanList);
        addrAdapter.setOnItemChildClickListener(this);
        recAll.setAdapter(addrAdapter);

        srlAll.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
                addrBeanList.clear();
                addrAdapter.notifyDataSetChanged();
                initData();
            }
        });
    }


    @Override
    public void initData() {
        SubscriberOnNextListener<BaseBean<AddrBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                if (baseBean.getData().getAddr().isEmpty()) {
                    imgGone.setVisibility(View.VISIBLE);

                } else {
                    imgGone.setVisibility(View.GONE);
                    addrBeanList.addAll(baseBean.getData().getAddr());
                    addrAdapter.notifyDataSetChanged();
                }

            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("uid", SharedHelper.readId(this));
        HttpMethods.getInstance().address(new ProgressSubscriber<>(onNextListener, this, isShowDialog), map);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            addrBeanList.clear();
            addrAdapter.notifyDataSetChanged();
            initData();
        }
    }

    @OnClick({R.id.iv_back, R.id.tv_add_addr})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_add_addr:
                Intent intent = new Intent();
                intent.setClass(AddrActivity.this, EditAddrActivity.class);
                intent.putExtra("type", "");
                startActivityForResult(intent, 1);
                break;
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.btn_edit:
                Intent intent = new Intent();
                intent.setClass(AddrActivity.this, EditAddrActivity.class);
                intent.putExtra("id", addrBeanList.get(position).getId());
                intent.putExtra("type", "1");
                startActivityForResult(intent, 1);
                break;
            case R.id.delete:
                deleteData(position);

                break;
            case R.id.main:
                if ("1".equals(type)) {
                    setDefault(position);
                    Intent intent1 = new Intent();
                    intent1.putExtra("id", addrBeanList.get(position).getId());
                    intent1.putExtra("name", addrBeanList.get(position).getName());
                    intent1.putExtra("mobile", addrBeanList.get(position).getMobile());
                    intent1.putExtra("desc", addrBeanList.get(position).getSheng()
                            + addrBeanList.get(position).getShi()
                            + addrBeanList.get(position).getXian()
                            + addrBeanList.get(position).getAddress());
                    setResult(1, intent1);
                    finish();
                }

                break;
        }
    }

    private void deleteData(int position) {
        SubscriberOnNextListener<BaseBean<AddrBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                RxToast.normal(baseBean.getMsg());
                addrBeanList.clear();
                addrAdapter.notifyDataSetChanged();
                initData();

            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("uid", SharedHelper.readId(this));
        map.put("id", addrBeanList.get(position).getId());
        HttpMethods.getInstance().addr_del(new ProgressSubscriber<>(onNextListener, this, isShowDialog), map);
    }

    private void setDefault(int position) {
//        SubscriberOnNextListener<BaseBean<List<AddrBean>>> onNextListener = new SubscriberOnNextListener<BaseBean<List<AddrBean>>>() {
//            @Override
//            public void onNext(BaseBean<List<AddrBean>> baseBean) {
//                if (200 == baseBean.getStatus() && !baseBean.getData().isEmpty()) {
//                }
//
//            }
//        };
//        // String s = "{\"uid\":\"" + uid + "\",\"page\":\"" + page + "\"}";
//        String s = "";
//        s = "{\"uid\":\"" + uid + "\",\"type\":\"" + 3 + "\",\"id\":\"" + addrBeanList.get(position).getId() + "\"}";
//        Log.i("============code", s);
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), s);
//        HttpMethods.getInstance().addAddr(new ProgressSubscriber<>(onNextListener, this, false), requestBody);
    }
}
