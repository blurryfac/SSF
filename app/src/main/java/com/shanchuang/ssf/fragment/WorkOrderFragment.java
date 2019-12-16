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
import com.shanchuang.ssf.activity.WorkOrderDetailActivity;
import com.shanchuang.ssf.adapter.HotAdapter;
import com.shanchuang.ssf.adapter.WorderOrderAdapter;
import com.shanchuang.ssf.bean.WorkOrderBean;
import com.vondear.rxtool.RxActivityTool;
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
 * @time 2019/12/7
 */
public class WorkOrderFragment extends FragmentLazy {
    @BindView(R.id.rec_main)
    RecyclerView recMain;
    @BindView(R.id.none)
    ImageView none;
    @BindView(R.id.srl_all)
    SmartRefreshLayout srlAll;
    private int type;

    public static WorkOrderFragment getInstance(int type) {
        WorkOrderFragment fragment = new WorkOrderFragment();
        fragment.type = type;
        return fragment;
    }

    Unbinder unbinder;

    @Override
    protected View initViews(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.fragment_work_order_layout, viewGroup, false);
        unbinder = ButterKnife.bind(this, view);
        initRv();
        return view;
    }
    private WorderOrderAdapter mAdapter;
    private List<WorkOrderBean> mList;
    private void initRv() {
        mList=new ArrayList<>();
        for(int i=0;i<10;i++){
            WorkOrderBean workOrderBean=new WorkOrderBean();
            workOrderBean.setAddress("郑州市");
            workOrderBean.setDeposit("456");
            workOrderBean.setDistance("20km");
            workOrderBean.setEstimated_amount("20");
            workOrderBean.setLimit("2019-08-17");
            workOrderBean.setNum("20");
            workOrderBean.setRelease_time("2010-20-10");
            workOrderBean.setTitle("item"+i);
            workOrderBean.setType("瓦工");
            mList.add(workOrderBean);
        }
        LinearLayoutManager linear = new LinearLayoutManager(mContext);
        recMain.setLayoutManager(linear);
        mAdapter = new WorderOrderAdapter(R.layout.item_work_order, mList);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            RxActivityTool.skipActivity(mContext, WorkOrderDetailActivity.class);
        });
        recMain.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {

    }
}
