package com.shanchuang.ssf.fragment;

import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/10/24
 */
public class MyCollActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    TextView ivBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_menu)
    TextView toolbarMenu;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.stl_main)
    SlidingTabLayout stlMain;
    @BindView(R.id.vp)
    ViewPager vp;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_coll_layout;
    }
   private ArrayList<Fragment> fragments=new ArrayList<>();
    private String [] titles={"视频","习题"};
    @Override
    protected void initView() {
        title.setText("我的收藏");
        fragments.add(new MyCollVideoFragment());
        fragments.add(new MyCollSubjectFragment());

        stlMain.setViewPager(vp,titles,this,fragments);
    }

    @Override
    protected void initData() {

    }


}
