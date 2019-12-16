package com.shanchuang.ssf.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.bean.OrderAllBean;
import com.shanchuang.ssf.mail.EvaluateActivity;
import com.shanchuang.ssf.mail.OrderDetailsActivity;
import com.shanchuang.ssf.mail.adapter.OrderAdapter;
import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.rxjava.HttpMethods;
import com.shanchuang.ssf.net.subscribers.ProgressSubscriber;
import com.shanchuang.ssf.net.subscribers.SubscriberOnNextListener;
import com.shanchuang.ssf.utils.DividerItemDecoration;
import com.shanchuang.ssf.utils.SharedHelper;
import com.vondear.rxtool.view.RxToast;
import com.vondear.rxui.fragment.FragmentLazy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 描述：添加类的描述
 *
 * @author 佚名
 * @time 2018/12/15
 */
public class AlreadyDoneFragment extends FragmentLazy implements BaseQuickAdapter.OnItemChildClickListener {
    private static final String TAG = "AlreadyDoneFragment";
    @BindView(R.id.rl_coll)
    RecyclerView rlColl;
    @BindView(R.id.img_gone)
    ImageView imgGone;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;
    Unbinder unbinder;
    private OrderAdapter orderAdapter;
    private List<OrderAllBean.OrderBean> orderBeanList;
    private int page = 1;
    private boolean isShowDialog = true;

    @Override
    protected View initViews(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.fragment_oz_layout, viewGroup, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        orderBeanList = new ArrayList<>();
        LinearLayoutManager linear = new LinearLayoutManager(getActivity());
        rlColl.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        rlColl.setNestedScrollingEnabled(false);
        rlColl.setLayoutManager(linear);
        orderAdapter = new OrderAdapter(R.layout.item_order, orderBeanList, 4);
        rlColl.setAdapter(orderAdapter);
        orderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), OrderDetailsActivity.class);
                intent.putExtra("oid", orderBeanList.get(position).getId());
                intent.putExtra("status", orderBeanList.get(position).getStatus());
                startActivityForResult(intent,1);
            }
        });

        orderAdapter.setOnItemChildClickListener(this);
        srl.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000);
                page++;
                isShowDialog=false;
                initData();
            }
        });
        srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
                refresh();
            }
        });
    }

    private void refresh() {
        orderBeanList.clear();
        orderAdapter.notifyDataSetChanged();
        page = 1;
        isShowDialog = false;
        initData();
    }

    @Override
    protected void initData() {
        SubscriberOnNextListener<BaseBean<OrderAllBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                if (baseBean.getData().getOrder().isEmpty()) {
                    if(page==1){
                        imgGone.setVisibility(View.VISIBLE);
                        orderAdapter.notifyDataSetChanged();
                    }else {
                        RxToast.normal("没有更多数据了");
                    }

                } else {
                    imgGone.setVisibility(View.INVISIBLE);
                    orderBeanList.addAll(baseBean.getData().getOrder());
                    orderAdapter.notifyDataSetChanged();
                }

            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();

        map.put("uid", SharedHelper.readId(mContext));
        map.put("status", 4);
        map.put("page", page);
        HttpMethods.getInstance().me_order(new ProgressSubscriber<>(onNextListener, mContext, isShowDialog), map);
    }

    @Override
    protected void onVisible() {
        super.onVisible();

        Log.d(TAG, "onVisible: ");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 0) {
            srl.autoRefresh();
        }
    }

    @Override
    public void onResume() {
        super.onResume();


    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.tv_1:
                break;
            case R.id.tv_2:
                if (orderBeanList.get(position).getStatus() == 4) {
                    intent.setClass(getActivity(), EvaluateActivity.class);
                    Bundle bundle_2 = new Bundle();
                    bundle_2.putString("oid", orderBeanList.get(position).getId());
                    bundle_2.putParcelableArrayList("eva", (ArrayList<? extends Parcelable>) orderBeanList.get(position).getGoods());
                    intent.putExtras(bundle_2);
                    startActivityForResult(intent, 0);
                }

                break;
            case R.id.tv_order_shop:
//                Bundle bundle = new Bundle();
//                bundle.putString("id", orderBeanList.get(position).getId());
//                bundle.putString("title", orderBeanList.get(position).getName());
//                RxActivityTool.skipActivity(getActivity(), ShopActivity.class, bundle);
                break;
        }
    }
}
