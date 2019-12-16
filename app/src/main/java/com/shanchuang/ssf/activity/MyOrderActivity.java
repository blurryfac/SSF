package com.shanchuang.ssf.activity;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.base.BaseActivity;
import com.shanchuang.ssf.fragment.AllOrderFragment;
import com.shanchuang.ssf.fragment.AlreadyDoneFragment;
import com.shanchuang.ssf.fragment.UnPayFragment;
import com.shanchuang.ssf.fragment.UnSendOrderFragment;
import com.shanchuang.ssf.fragment.unReceiveOrderFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 描述：我的订单
 *
 * @author 佚名
 * @time 2018/6/8
 */
public class MyOrderActivity extends BaseActivity {


    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.stl_main)
    SlidingTabLayout stlMain;
    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    private int order_type;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_order_layout;
    }

    @Override
    protected void initView() {
        title.setText("全部订单");
        order_type = getIntent().getExtras().getInt("order");
        fragmentList.add(new AllOrderFragment());
        fragmentList.add(new UnPayFragment());
        fragmentList.add(new UnSendOrderFragment());
        fragmentList.add(new unReceiveOrderFragment());
        fragmentList.add(new AlreadyDoneFragment());
        stlMain.setViewPager(vp, new String[]{"全部", "待付款", "待发货", "待收货", "待评价"}, this, fragmentList);
        stlMain.setCurrentTab(order_type);

    }

    @Override
    public void initData() {

    }


}
