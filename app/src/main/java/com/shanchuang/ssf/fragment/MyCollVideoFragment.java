package com.shanchuang.ssf.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.activity.VideoPlayActivity;
import com.shanchuang.ssf.adapter.CollVideoListAdapter;
import com.shanchuang.ssf.bean.VideoListBean;
import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.rxjava.HttpMethods;
import com.shanchuang.ssf.net.subscribers.ProgressSubscriber;
import com.shanchuang.ssf.net.subscribers.SubscriberOnNextListener;
import com.shanchuang.ssf.utils.SharedHelper;
import com.shanchuang.ssf.utils.SwipeItemLayout;
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
 * @author 金源
 * @time 2019/10/24
 */
public class MyCollVideoFragment extends FragmentLazy implements BaseQuickAdapter.OnItemChildClickListener {
    Unbinder unbinder;
    @BindView(R.id.rec_main)
    RecyclerView recMain;
    @BindView(R.id.none)
    ImageView none;
    @BindView(R.id.srl_all)
    SmartRefreshLayout srlAll;
    private List<VideoListBean.CourseBean> mList;
    private CollVideoListAdapter mAdapter;
    private int page = 1;
    private boolean isShowDialog = true;

    @Override
    protected View initViews(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.fragment_my_coll_video_layout, viewGroup, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        initSrl();
        return view;
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

    private void init() {
        mList = new ArrayList<>();
        LinearLayoutManager linear = new LinearLayoutManager(getActivity());
        recMain.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(getActivity()));
        recMain.setLayoutManager(linear);
        mAdapter = new CollVideoListAdapter(R.layout.item_coll_video_list, mList);
        mAdapter.setOnItemChildClickListener(this);
        recMain.setAdapter(mAdapter);


    }

    @Override
    protected void initData() {
        SubscriberOnNextListener<BaseBean<VideoListBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                if (baseBean.getData().getCourse().isEmpty()) {
                    if (page == 1) {
                        none.setVisibility(View.VISIBLE);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        RxToast.normal("没有更多数据了");
                    }

                } else {
                    none.setVisibility(View.INVISIBLE);
                    mList.addAll(baseBean.getData().getCourse());
                    mAdapter.notifyDataSetChanged();
                }

            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("uid", SharedHelper.readId(mContext));
        map.put("page", page);
        HttpMethods.getInstance().course(new ProgressSubscriber<>(onNextListener, mContext, isShowDialog), map);
    }

    /**
     * 收藏
     *
     */
    private void httpCollSub(int pos) {
        SubscriberOnNextListener<BaseBean> onNextListener = baseBean -> {
            RxToast.normal(baseBean.getMsg());
            srlAll.autoRefresh();
        };
        Map<String, Object> map = new HashMap<>();
        map.put("cid", mList.get(pos).getId());
        map.put("uid", SharedHelper.readId(mContext));
        HttpMethods.getInstance().course_del(new ProgressSubscriber<>(onNextListener, mContext, true), map);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.main:
                Intent intent = new Intent(mContext, VideoPlayActivity.class);
                intent.putExtra("id", mList.get(position).getId());
                startActivity(intent);
                break;
            case R.id.delete:
                httpCollSub(position);
                break;
        }
    }
}
