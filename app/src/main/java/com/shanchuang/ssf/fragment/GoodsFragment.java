package com.shanchuang.ssf.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.adapter.GoodsAdapter;
import com.shanchuang.ssf.bean.ShopGoodsBean;
import com.shanchuang.ssf.event.EventTag;
import com.shanchuang.ssf.event.MessageEvent;
import com.shanchuang.ssf.mail.ShopDetailActivity;
import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.rxjava.HttpMethods;
import com.shanchuang.ssf.net.subscribers.ProgressSubscriber;
import com.shanchuang.ssf.net.subscribers.SubscriberOnNextListener;
import com.shanchuang.ssf.utils.SharedHelper;
import com.shanchuang.ssf.view.CustomViewPager;
import com.vondear.rxtool.RxActivityTool;
import com.vondear.rxtool.view.RxToast;
import com.vondear.rxui.fragment.FragmentLazy;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
 * @author 金源
 * @time 2019/10/21
 */
public class GoodsFragment extends FragmentLazy {
    @BindView(R.id.iv_none)
    ImageView ivNone;
    @BindView(R.id.rec_all)
    RecyclerView recAll;
    Unbinder unbinder;
    private int fragmentId;
    private CustomViewPager vp;
    /**
     * 我的需求状态 待接单 0，已接单 1，已完成 2
     */
    private String id;
    private String search = "";
    private List<ShopGoodsBean.GoodsBean> mailList;
    private GoodsAdapter mMailAdapter;
    private boolean isShowDialog = true;
    private int page = 1;
    private String keyword = "";
    private boolean isVisable = false;

    public static GoodsFragment getInstance(String id, int fragmentId, CustomViewPager vp) {
        GoodsFragment fragment = new GoodsFragment();
        fragment.id = id;
        fragment.fragmentId = fragmentId;
        fragment.vp = vp;
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {

            EventBus.getDefault().register(this);

        }
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
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        if (EventTag.MAIN.equals(messageEvent.getTag()) && isVisable) {
            refresh();
        } else if (EventTag.LOAD_MORE.equals(messageEvent.getTag()) && isVisable) {
            page++;
            initData();
        } else if (EventTag.SEARCH.equals(messageEvent.getTag()) && isVisable) {
            search = messageEvent.getMessage();
            refresh();
        }
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
        View view = layoutInflater.inflate(R.layout.fragment_goods_layout, viewGroup, false);
        unbinder = ButterKnife.bind(this, view);
        vp.setObjectForPosition(view, fragmentId);
        initView();
        return view;
    }

    protected void initView() {
        mailList = new ArrayList<>();
        GridLayoutManager linear = new GridLayoutManager(mContext, 2);
        recAll.setLayoutManager(linear);
        mMailAdapter = new GoodsAdapter(R.layout.item_mail, mailList);
        recAll.setNestedScrollingEnabled(false);
        recAll.setAdapter(mMailAdapter);

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
                        ivNone.setVisibility(View.VISIBLE);
                        mMailAdapter.notifyDataSetChanged();
                    } else {
                        RxToast.normal("没有更多数据了");
                    }

                } else {
                    ivNone.setVisibility(View.INVISIBLE);
                    mailList.addAll(baseBean.getData().getGoods());
                    mMailAdapter.notifyDataSetChanged();
                }

            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("cid", id);
        map.put("keyword", search);
        map.put("uid", SharedHelper.readId(mContext));
        map.put("page", page);
        HttpMethods.getInstance().goods_lists(new ProgressSubscriber<>(onNextListener, mContext, isShowDialog), map);
    }

    @Override
    protected void onVisible() {
        super.onVisible();
        isVisable = true;
    }

    @Override
    protected void onInvisible() {
        super.onInvisible();
        isVisable = false;
    }
}

