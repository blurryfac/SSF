package com.shanchuang.ssf.activity;

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
import com.shanchuang.ssf.adapter.CouponListAdapter;
import com.shanchuang.ssf.base.BaseActivity;
import com.shanchuang.ssf.bean.CouponListBean;
import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.rxjava.HttpMethods;
import com.shanchuang.ssf.net.subscribers.ProgressSubscriber;
import com.shanchuang.ssf.net.subscribers.SubscriberOnNextListener;
import com.shanchuang.ssf.utils.SharedHelper;
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
 * @time 2019/10/24
 */
public class MyCouponActivity extends BaseActivity {
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_coupon_layout;
    }

    @Override
    protected void initView() {
        title.setText("我的优惠券");
        initRec();
        initSrl();
    }
    private int page = 1;
    private boolean isShowDialog = true;
    private void initSrl() {
        srlAll.setEnableLoadmore(false);
//        srlAll.setOnLoadmoreListener(refreshlayout -> {
//            refreshlayout.finishLoadmore(1500);
//            page++;
//            isShowDialog=false;
//            initData();
//        });
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

    private List<CouponListBean.YouhuiquanBean> mList;
    private CouponListAdapter mAdapter;
    private void initRec() {

        mList = new ArrayList<>();

        LinearLayoutManager linear = new LinearLayoutManager(this);
        recMain.setLayoutManager(linear);
        recMain.setNestedScrollingEnabled(false);
        mAdapter = new CouponListAdapter(R.layout.item_coupon_list, mList);
        recMain.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {


        });
    }
    @Override
    protected void initData() {
        SubscriberOnNextListener<BaseBean<CouponListBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                if (baseBean.getData().getYouhuiquan().isEmpty()) {
                    none.setVisibility(View.VISIBLE);

                } else {
                    none.setVisibility(View.GONE);
                    for(int i=0;i<baseBean.getData().getYouhuiquan().size();i++){
                        if(baseBean.getData().getYouhuiquan().get(i).getStatus()!=1){
                            mList.add(baseBean.getData().getYouhuiquan().get(i));

                        }

                    }
                    mAdapter.notifyDataSetChanged();

                }

            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("uid", SharedHelper.readId(this));
        HttpMethods.getInstance().youhuiquan(new ProgressSubscriber<>(onNextListener, this, isShowDialog), map);
    }


}
