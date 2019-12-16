package com.shanchuang.ssf.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.activity.ExercisesDownLoadListActivity;
import com.shanchuang.ssf.activity.ExercisesListActivity;
import com.shanchuang.ssf.adapter.VideoCateAdapter;
import com.shanchuang.ssf.adapter.VideoMainAdapter;
import com.shanchuang.ssf.bean.ExercisesAdvertBean;
import com.shanchuang.ssf.bean.VideoCateBean;
import com.shanchuang.ssf.bean.VideoMainBean;
import com.shanchuang.ssf.manager.FullyGridLayoutManager;
import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.rxjava.HttpMethods;
import com.shanchuang.ssf.net.subscribers.ProgressSubscriber;
import com.shanchuang.ssf.net.subscribers.SubscriberOnNextListener;
import com.shanchuang.ssf.view.ImageViewPlus;
import com.vondear.rxtool.view.RxToast;
import com.vondear.rxui.fragment.FragmentLazy;

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
public class ExercisesFragment extends FragmentLazy {
    @BindView(R.id.rec_cate)
    RecyclerView recCate;
    @BindView(R.id.rec_main)
    RecyclerView recMain;
    @BindView(R.id.et_search)
    EditText etSearch;
    Unbinder unbinder;
    @BindView(R.id.iv_logo)
    ImageViewPlus ivLogo;
    @BindView(R.id.iv_none)
    ImageView ivNone;
    private List<VideoCateBean.CateBean> mCateList;
    private VideoCateAdapter mCateAdapter;
    private int mCatePostion;
    private List<VideoMainBean.CateBean> mMainList;
    private VideoMainAdapter mMainAdapter;
    private boolean isShowDialog = false;

    @Override
    protected View initViews(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.fragment_exercises_layout, viewGroup, false);
        unbinder = ButterKnife.bind(this, view);
        initSearch();
        initCate();
        initMain();
        return view;
    }

    private void initCate() {
        mCateList = new ArrayList<>();

        LinearLayoutManager linear = new LinearLayoutManager(mContext);
        recCate.setLayoutManager(linear);
        recCate.setNestedScrollingEnabled(false);
        mCateAdapter = new VideoCateAdapter(R.layout.item_video_cate, mCateList);
        recCate.setAdapter(mCateAdapter);
        mCateAdapter.setOnItemClickListener((adapter, view, position) -> {
            mCatePostion = position;
            for (int i = 0; i < mCateList.size(); i++) {
                if (i == position) {
                    mCateList.get(i).setCheck(true);
                } else {
                    mCateList.get(i).setCheck(false);
                }

            }
            mCateAdapter.notifyDataSetChanged();
            mMainList.clear();
            mMainAdapter.notifyDataSetChanged();
            isShowDialog = true;
            httpGetSecondCate(mCateList.get(position).getId());
        });
    }

    private void initMain() {
        mMainList = new ArrayList<>();

        FullyGridLayoutManager linear = new FullyGridLayoutManager(mContext, 3);
        recMain.setLayoutManager(linear);
        recMain.setNestedScrollingEnabled(false);
        mMainAdapter = new VideoMainAdapter(R.layout.item_video_main, mMainList);
        recMain.setAdapter(mMainAdapter);
        mMainAdapter.setOnItemClickListener((adapter, view, position) -> {

            Intent intent = new Intent(mContext, ExercisesListActivity.class);
            intent.putExtra("title", mMainList.get(position).getTitle());
            intent.putExtra("cid", mCateList.get(mCatePostion).getId());
            intent.putExtra("cid2", mMainList.get(position).getId());
            startActivity(intent);
        });
    }

    private void httpGetSecondCate(String pid) {
        SubscriberOnNextListener<BaseBean<VideoMainBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                if (baseBean.getData().getCate().isEmpty()) {
                    ivNone.setVisibility(View.VISIBLE);
                } else {
                    ivNone.setVisibility(View.INVISIBLE);
                    mMainList.addAll(baseBean.getData().getCate());
                    mMainAdapter.notifyDataSetChanged();
                }

            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("pid", pid);
        HttpMethods.getInstance().exercises_cate2(new ProgressSubscriber<>(onNextListener, mContext, isShowDialog), map);
    }

    @Override
    protected void initData() {
        httpGetBanner();
        httpGetFirstCate();
    }

    private void httpGetBanner() {
        SubscriberOnNextListener<BaseBean<ExercisesAdvertBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                Glide.with(mContext).load(baseBean.getData().getAdvert().getImage()).into(ivLogo);
            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        HttpMethods.getInstance().exercises_advert(new ProgressSubscriber<>(onNextListener, mContext, true));
    }

    private void httpGetFirstCate() {
        SubscriberOnNextListener<BaseBean<VideoCateBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                mCateList.addAll(baseBean.getData().getCate());
                mCateList.get(0).setCheck(true);
                mCateAdapter.notifyDataSetChanged();
                isShowDialog = false;
                httpGetSecondCate(mCateList.get(0).getId());
            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        HttpMethods.getInstance().exercises_cate(new ProgressSubscriber<>(onNextListener, mContext, false));
    }

    private void initSearch() {
        etSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    try {
                        Intent intent = new Intent(mContext, ExercisesListActivity.class);
                        intent.putExtra("title", etSearch.getText().toString());
                        intent.putExtra("cid", "0");
                        intent.putExtra("cid2", "0");
                        startActivity(intent);
                        ((InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE)).
                                hideSoftInputFromWindow(etSearch.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.et_search, R.id.iv_logo})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
//            case R.id.et_search:
//
//
//                intent.setClass(mContext, SearchActivity.class);
//
//                intent.putExtra("type", 2);
//                startActivity(intent);
//                break;
            case R.id.iv_logo:

                intent.setClass(mContext, ExercisesDownLoadListActivity.class);
                startActivity(intent);
                break;
        }
    }
}

