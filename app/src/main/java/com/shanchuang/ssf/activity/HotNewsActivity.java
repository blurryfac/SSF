package com.shanchuang.ssf.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.adapter.HotAdapter;
import com.shanchuang.ssf.base.BaseActivity;
import com.shanchuang.ssf.bean.HotBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/12/16
 */
public class HotNewsActivity extends BaseActivity {
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
    private List<HotBean> mHotList;
    private HotAdapter mHotAdapter;
    private void initRec() {
        mHotList=new ArrayList<>();
        for(int i=0;i<10;i++){
            HotBean hotBean=new HotBean();
            hotBean.setNum(i+"");
            hotBean.setTime("2017-12-9");
            hotBean.setTitle("item"+i);
            mHotList.add(hotBean);
        }
        LinearLayoutManager linear = new LinearLayoutManager(this);
        recMain.setLayoutManager(linear);
        mHotAdapter = new HotAdapter(R.layout.item_hot, mHotList);
        mHotAdapter.setOnItemClickListener((adapter, view, position) -> {

        });
        recMain.setAdapter(mHotAdapter);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_hot_news_layout;
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
                mHotList.clear();
                isShowDialog=false;
                mHotAdapter.notifyDataSetChanged();
                initData();
            }
        });
    }
    private int page=1;
    private boolean isShowDialog=true;
    @Override
    protected void initView() {
        title.setText("热门资讯");
        initRec();
        initSrl();
    }



    @Override
    protected void initData() {

    }


}
