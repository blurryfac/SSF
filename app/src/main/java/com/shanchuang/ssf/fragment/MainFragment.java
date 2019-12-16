package com.shanchuang.ssf.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shanchuang.ssf.R;
import com.shanchuang.ssf.activity.HotNewsActivity;
import com.shanchuang.ssf.activity.MessageListActivity;
import com.shanchuang.ssf.activity.OrderRoomActivity;
import com.shanchuang.ssf.activity.OrderRoomEmptyActivity;
import com.shanchuang.ssf.activity.RleaseOrderActivity;
import com.shanchuang.ssf.adapter.HotAdapter;
import com.shanchuang.ssf.bean.AdvertBean;
import com.shanchuang.ssf.bean.HotBean;
import com.shanchuang.ssf.bean.MainBean;
import com.shanchuang.ssf.mail.adapter.ConfirmAdapter;
import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.rxjava.HttpMethods;
import com.shanchuang.ssf.net.subscribers.ProgressSubscriber;
import com.shanchuang.ssf.net.subscribers.SubscriberOnNextListener;
import com.shanchuang.ssf.utils.DividerItemDecoration;
import com.shanchuang.ssf.utils.GlideImageLoader;
import com.vondear.rxtool.RxActivityTool;
import com.vondear.rxtool.view.RxToast;
import com.vondear.rxui.fragment.FragmentLazy;
import com.vondear.rxui.view.RxTextViewVertical;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 描述：添加类的描述
 *
 * @author
 * @time 2019/10/12
 */
public class MainFragment extends FragmentLazy {

    Unbinder unbinder;
    @BindView(R.id.iv_back)
    TextView ivBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_menu)
    TextView toolbarMenu;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_main_message)
    RxTextViewVertical tvMainMessage;
    @BindView(R.id.iv_main_order_room)
    ImageView ivMainOrderRoom;
    @BindView(R.id.iv_main_release_order)
    ImageView ivMainReleaseOrder;
    @BindView(R.id.iv_main_insurance)
    ImageView ivMainInsurance;
    @BindView(R.id.tv_main_hot_more)
    TextView tvMainHotMore;
    @BindView(R.id.rec_main_hot)
    RecyclerView recMainHot;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected View initViews(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.fragment_main_layout, viewGroup, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        initBanner();
        initRec();

        return view;
    }
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
        LinearLayoutManager linear = new LinearLayoutManager(mContext);
        recMainHot.setLayoutManager(linear);
        mHotAdapter = new HotAdapter(R.layout.item_hot, mHotList);
        mHotAdapter.setOnItemClickListener((adapter, view, position) -> {

        });
        recMainHot.setAdapter(mHotAdapter);
    }

    /**
     * 轮播图列表
     */
    private List<String> img_list = new ArrayList<>();
    private List<AdvertBean> lunbolistBeanList = new ArrayList<>();

    private void initBanner() {
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

            }
        }).setImageLoader(new GlideImageLoader());
    }

    private void init() {
        tvMainMessage.setText(14, 0, 0xff6281D4);//设置属性
        tvMainMessage.setTextStillTime(3000);//设置停留时长间隔
        tvMainMessage.setAnimTime(300);//设置进入和退出的时间间隔
        tvMainMessage.setOnItemClickListener(new RxTextViewVertical.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent();
                intent.setClass(mContext, MessageListActivity.class);
                startActivity(intent);
            }
        });
    }

    private String school_id = "0";
    private ArrayList<String> titleList = new ArrayList<String>();

    @Override
    protected void initData() {
        SubscriberOnNextListener<BaseBean<MainBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                if (img_list.isEmpty()) {
                    lunbolistBeanList.addAll(baseBean.getData().getAdvert());
                    for (int i = 0; i < lunbolistBeanList.size(); i++) {
                        img_list.add(lunbolistBeanList.get(i).getImage());
                    }
                    banner.setImages(img_list);
                    banner.start();
                }

            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("school_id", school_id);
        HttpMethods.getInstance().index(new ProgressSubscriber<>(onNextListener, mContext, true), map);
    }

    @Override
    public void onResume() {
        super.onResume();
        tvMainMessage.startAutoScroll();
    }

    @Override
    public void onPause() {
        super.onPause();
        tvMainMessage.stopAutoScroll();
    }


    @OnClick({R.id.tv_main_message, R.id.iv_main_order_room, R.id.iv_main_release_order, R.id.iv_main_insurance, R.id.tv_main_hot_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_main_message:
                break;
            case R.id.iv_main_order_room:
                RxActivityTool.skipActivity(mContext, OrderRoomActivity.class);//接单大厅
                RxActivityTool.skipActivity(mContext, OrderRoomEmptyActivity.class);//工人未认证
                break;
            case R.id.iv_main_release_order:
                RxActivityTool.skipActivity(mContext, RleaseOrderActivity.class);//
                break;
            case R.id.iv_main_insurance:
                break;
            case R.id.tv_main_hot_more:
                RxActivityTool.skipActivity(mContext, HotNewsActivity.class);//
                break;
        }
}
}