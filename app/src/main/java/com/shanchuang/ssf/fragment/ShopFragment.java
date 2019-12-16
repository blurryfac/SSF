package com.shanchuang.ssf.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shanchuang.ssf.MainActivity;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.activity.SearchActivity;
import com.shanchuang.ssf.bean.AdvertBean;
import com.shanchuang.ssf.bean.AdvertMainBean;
import com.shanchuang.ssf.bean.VideoCateBean;
import com.shanchuang.ssf.event.EventTag;
import com.shanchuang.ssf.event.MessageEvent;
import com.shanchuang.ssf.mail.JYShoppingActivity;
import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.rxjava.HttpMethods;
import com.shanchuang.ssf.net.subscribers.ProgressSubscriber;
import com.shanchuang.ssf.net.subscribers.SubscriberOnNextListener;
import com.shanchuang.ssf.utils.GlideImageLoader;
import com.shanchuang.ssf.utils.SharedHelper;
import com.shanchuang.ssf.view.CustomViewPager;
import com.vondear.rxtool.RxActivityTool;
import com.vondear.rxtool.view.RxToast;
import com.vondear.rxui.fragment.FragmentLazy;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

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
public class ShopFragment extends FragmentLazy {
    @BindView(R.id.et_search)
    TextView etSearch;
    @BindView(R.id.iv_cart)
    ImageView ivCart;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.stl_main)
    SlidingTabLayout stlMain;
    @BindView(R.id.vp)
    CustomViewPager vp;
    Unbinder unbinder;
    @BindView(R.id.iv_back)
    TextView ivBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_menu)
    TextView toolbarMenu;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nsv)
    NestedScrollView nsv;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;

    @Override
    protected View initViews(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.fragment_shop_layout, viewGroup, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        initBanner();
        initSrl();
        return view;
    }

    private void initSrl() {
        srl.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000);
                MessageEvent messageEvent = new MessageEvent(EventTag.LOAD_MORE, String.valueOf(mTitlePosition + 1));
                EventBus.getDefault().post(messageEvent);
            }
        });
        srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
                MessageEvent messageEvent = new MessageEvent(EventTag.MAIN, String.valueOf(mTitlePosition + 1));
                EventBus.getDefault().post(messageEvent);
            }
        });
        stlMain.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mTitlePosition = position;
//                MessageEvent messageEvent = new MessageEvent(EventTag.MAIN, String.valueOf(mTitlePosition + 1));
//                EventBus.getDefault().post(messageEvent);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    private int mTitlePosition = 0;

    private void init() {
        toolbarTitle.setText("商城");
//        etSearch.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View view, int i, KeyEvent keyEvent) {
//                if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
//
//                    try {
//
//
////                        EventBus.getDefault().post(new MessageEvent(EventTag.SEARCH, etSearch.getText().toString()));
//                        ((InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE)).
//                                hideSoftInputFromWindow(etSearch.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//                return false;
//            }
//        });
        vp.addOnPageChangeListener(new CustomViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                vp.resetHeight(position);
//                ((NestedScrollView) getActivity().findViewById(R.id.nsv)).scrollTo(0, 0);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vp.resetHeight(0);
    }

    private void initBanner() {
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

            }
        }).setImageLoader(new GlideImageLoader());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private ArrayList<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void initData() {
        httpGetBanner();
        httpGetCate();
    }

    private void httpGetCate() {
        SubscriberOnNextListener<BaseBean<VideoCateBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                String arr[] = new String[baseBean.getData().getCate().size()];
                for (int i = 0; i < baseBean.getData().getCate().size(); i++) {
                    arr[i] = baseBean.getData().getCate().get(i).getTitle();
                    fragmentList.add(GoodsFragment.getInstance(baseBean.getData().getCate().get(i).getId(), i, vp));
                }
                stlMain.setViewPager(vp, arr, mContext, fragmentList);
            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        HttpMethods.getInstance().goods_cate(new ProgressSubscriber<>(onNextListener, mContext, false));
    }

    /**
     * 轮播图列表
     */
    private List<String> img_list = new ArrayList<>();
    private List<AdvertBean> lunbolistBeanList = new ArrayList<>();

    private void httpGetBanner() {
        SubscriberOnNextListener<BaseBean<AdvertMainBean>> onNextListener = baseBean -> {
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
        HttpMethods.getInstance().goods_advert(new ProgressSubscriber<>(onNextListener, mContext, true));
    }


    @OnClick({R.id.et_search, R.id.iv_cart})
    public void onViewClicked(View view) {
        if(!SharedHelper.readId(mContext).isEmpty()) {
            switch (view.getId()) {
                case R.id.et_search:
                    Intent intent = new Intent(mContext, SearchActivity.class);
                    intent.putExtra("title", etSearch.getText().toString());
                    intent.putExtra("type", 2);
                    startActivity(intent);
                    break;
                case R.id.iv_cart:
                    RxActivityTool.skipActivity(mContext, JYShoppingActivity.class);
                    break;
            }
        }else {
            MainActivity.showLoginDialog(mContext);
        }

    }
}
