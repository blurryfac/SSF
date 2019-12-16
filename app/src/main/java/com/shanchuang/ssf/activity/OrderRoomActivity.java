package com.shanchuang.ssf.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.base.BaseActivity;
import com.shanchuang.ssf.citypicker.CityPickerActivity;
import com.shanchuang.ssf.fragment.WorkOrderFragment;
import com.shanchuang.ssf.login.fragment.LoginFragment;
import com.shanchuang.ssf.login.fragment.RegisterFragment;
import com.vondear.rxtool.RxActivityTool;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/12/5
 */
public class OrderRoomActivity extends BaseActivity {
    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.stl_main)
    SlidingTabLayout stlMain;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.ll_city_choice)
    RelativeLayout llCityChoice;
    @BindView(R.id.vp)
    ViewPager vp;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_room_layout;
    }
    private String[] titles ={"包工工单","点工工单"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    @Override
    protected void initView() {
        mFragments.add( WorkOrderFragment.getInstance(1));
        mFragments.add( WorkOrderFragment.getInstance(2));
        stlMain.setViewPager(vp, titles, this, mFragments);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick({R.id.iv_return, R.id.ll_city_choice})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                finish();
                break;
            case R.id.ll_city_choice:
                RxActivityTool.skipActivityForResult(this, CityPickerActivity.class,2);
                break;
        }
    }
}
