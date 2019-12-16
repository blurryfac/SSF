package com.shanchuang.ssf.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.adapter.EmployerEvaAadapter;
import com.shanchuang.ssf.adapter.NewsItemAdapter;
import com.shanchuang.ssf.bean.NewsItemBean;
import com.vondear.rxui.fragment.FragmentLazy;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/12/16
 */
public class NewsItemFragment extends FragmentLazy {
    Unbinder unbinder;
    @BindView(R.id.rec_main)
    RecyclerView recMain;
    @BindView(R.id.none)
    ImageView none;
    @BindView(R.id.srl_all)
    SmartRefreshLayout srlAll;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected View initViews(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.fragment_news_iten_layout, viewGroup, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        initSrl();
        return view;
    }

    private int type;

    public static NewsItemFragment getInstance(int type) {
        NewsItemFragment fragment = new NewsItemFragment();
        fragment.type = type;
        return fragment;
    }

    private void initSrl() {

    }
    private NewsItemAdapter mAdapter;
    private List<NewsItemBean> mList;

    private void init() {
        mList=new ArrayList<>();
        for(int i=0;i<10;i++){
            NewsItemBean newsItemBean=new NewsItemBean();
            newsItemBean.setContent("item"+i);
            newsItemBean.setStatus(0);
            newsItemBean.setTime("2011-2-2");
            newsItemBean.setTitle("ahahha");
            mList.add(newsItemBean);
        }
        LinearLayoutManager linear = new LinearLayoutManager(mContext);
        recMain.setLayoutManager(linear);
        mAdapter = new NewsItemAdapter(R.layout.item_news_item, mList);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {

        });
        recMain.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {

    }
}
