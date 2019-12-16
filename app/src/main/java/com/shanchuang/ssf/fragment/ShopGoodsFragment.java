package com.shanchuang.ssf.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.adapter.GoodsAdapter;
import com.shanchuang.ssf.bean.ShopGoodsBean;
import com.shanchuang.ssf.mail.ShopDetailActivity;
import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.rxjava.HttpMethods;
import com.shanchuang.ssf.net.subscribers.ProgressSubscriber;
import com.shanchuang.ssf.net.subscribers.SubscriberOnNextListener;
import com.shanchuang.ssf.utils.SharedHelper;
import com.vondear.rxtool.RxActivityTool;
import com.vondear.rxtool.view.RxToast;
import com.vondear.rxui.fragment.FragmentLazy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.shanchuang.ssf.activity.ShopMsgActivity.mid;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/11/8
 */
public class ShopGoodsFragment extends FragmentLazy {
    Unbinder unbinder;
    @BindView(R.id.rec_main)
    RecyclerView recMain;
    @BindView(R.id.none)
    ImageView none;
    @BindView(R.id.srl_all)
    SmartRefreshLayout srlAll;
    /**
     * 我的需求状态 待接单 0，已接单 1，已完成 2
     */
    private String id;
    private List<ShopGoodsBean.GoodsBean> mailList;
    private GoodsAdapter mMailAdapter;
    private boolean isShowDialog = true;
    private int page = 1;

    public static ShopGoodsFragment getInstance(String id) {
        ShopGoodsFragment fragment = new ShopGoodsFragment();
        fragment.id = id;
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }



    private void refresh() {
        mailList.clear();
        page = 1;
        isShowDialog = false;
        mMailAdapter.notifyDataSetChanged();
        initData();
    }

    @Override
    protected View initViews(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.fragment_shop_goods_layout, viewGroup, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initSrl();
        return view;
    }

    private void initSrl() {
        srlAll.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(1500);
                refresh();
            }
        });
        srlAll.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(1500);
                page++;
                isShowDialog=false;
                initData();
            }
        });
    }

    protected void initView() {
        mailList = new ArrayList<>();
        GridLayoutManager linear = new GridLayoutManager(mContext, 2);
        recMain.setLayoutManager(linear);
        mMailAdapter = new GoodsAdapter(R.layout.item_mail, mailList);
        recMain.setNestedScrollingEnabled(false);
        recMain.setAdapter(mMailAdapter);

        mMailAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("id", mailList.get(position).getId());
                RxActivityTool.skipActivity(mContext, ShopDetailActivity.class, bundle);
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
        map.put("mid", mid);
        map.put("cid", id);
        map.put("uid", SharedHelper.readId(mContext));
        map.put("page", page);
        HttpMethods.getInstance().goods_lists(new ProgressSubscriber<>(onNextListener, mContext, isShowDialog), map);
    }
}


