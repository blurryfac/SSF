package com.shanchuang.ssf.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.shanchuang.ssf.R;
import com.vondear.rxui.fragment.FragmentLazy;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 描述：添加类的描述
 *
 * @author
 * @time 2019/10/12
 */
public class MessageFragment extends FragmentLazy {

    Unbinder unbinder;
    @BindView(R.id.stl_main)
    SlidingTabLayout stlMain;
    @BindView(R.id.vp)
    ViewPager vp;
    private String[] titles ={"全部","已读","未读"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    protected View initViews(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.fragment_video_layout, viewGroup, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        mFragments.add( NewsItemFragment.getInstance(1));
        mFragments.add( NewsItemFragment.getInstance(2));
        mFragments.add( NewsItemFragment.getInstance(3));
        stlMain.setViewPager(vp, titles, mContext, mFragments);
       stlMain.showDot(2);
        stlMain.setMsgMargin(2,50,0);

    }

    @Override
    protected void initData() {

    }


}
