package com.shanchuang.ssf.login;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.base.BaseActivity;
import com.shanchuang.ssf.bean.SchoolDataBean;
import com.shanchuang.ssf.login.adapter.SchoolAdapter;
import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.rxjava.HttpMethods;
import com.shanchuang.ssf.net.subscribers.ProgressSubscriber;
import com.shanchuang.ssf.net.subscribers.SubscriberOnNextListener;
import com.shanchuang.ssf.utils.SharedHelper;
import com.vondear.rxtool.view.RxToast;
import com.vondear.rxui.view.dialog.RxDialogSureCancel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述：添加类的描述
 *
 * @author
 * @time 2019/10/14
 */
public class BindSchoolActivity extends BaseActivity {
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.rec_school)
    RecyclerView recSchool;
    @BindView(R.id.tv_jump)
    TextView tvJump;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.iv_back)
    TextView ivBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_menu)
    TextView toolbarMenu;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.srl_main)
    SmartRefreshLayout srlMain;
    @BindView(R.id.none)
    ImageView none;
    private int type;//判断是登录得时候绑定还是首页选择 2：选择 别的都为绑定
    private String uid = "";//用户id
    private RxDialogSureCancel rxDialogSureCancel;
    private int mPosition;
    /**
     * 初始化列表
     */
    private List<SchoolDataBean.SchoolBean> mList;
    private SchoolAdapter mAdapter;
    private int page = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_school_layout;
    }

    @Override
    protected void initView() {

        type = getIntent().getIntExtra("type", 0);
        if (type == 2) {
            tvJump.setVisibility(View.INVISIBLE);
            tvTip.setVisibility(View.GONE);
            title.setText("选择学校");
        } else {
            uid = getIntent().getStringExtra("uid");
            title.setText("绑定学校");
        }
        initRec();
        initSearch();
        initBindDialog();
        initSrl();
    }

    @Override
    protected void initData() {
        SubscriberOnNextListener<BaseBean<SchoolDataBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                if (!baseBean.getData().getSchool().isEmpty()) {
                    none.setVisibility(View.INVISIBLE);
                    mList.addAll(baseBean.getData().getSchool());
                    mAdapter.notifyDataSetChanged();
                } else {
                    if (page == 1) {
                        none.setVisibility(View.VISIBLE);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        RxToast.normal("没有更多数据了");
                    }
                }
            } else {
                RxToast.normal(baseBean.getMsg());
            }
        };
        HttpMethods.getInstance().school(new ProgressSubscriber<>(onNextListener, this, false), getString(etSearch), page);
    }

    private void initSrl() {
        srlMain.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(1500);
                refreshData();
            }
        });
        srlMain.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(1500);
                page++;
                initData();
            }
        });
    }

    private void initBindDialog() {
        rxDialogSureCancel = new RxDialogSureCancel(this);
        rxDialogSureCancel.getLogoView().setVisibility(View.GONE);
        rxDialogSureCancel.getTitleView().setTextColor(getResources().getColor(R.color.black));
        rxDialogSureCancel.getTitleView().setTextSize(18);
        rxDialogSureCancel.getContentView().setTextColor(getResources().getColor(R.color.color_8));
        rxDialogSureCancel.getContentView().setTextSize(14);
        rxDialogSureCancel.setTitle("确认选择");
        rxDialogSureCancel.setContent("确认选择学校后不可更改");
        rxDialogSureCancel.setCancelListener(v -> {
            rxDialogSureCancel.dismiss();
        });
        rxDialogSureCancel.setSureListener(v -> {
            rxDialogSureCancel.dismiss();
            httpBindSchool(mList.get(mPosition).getId());
        });
    }

    private void httpBindSchool(String school_id) {
        SubscriberOnNextListener<BaseBean<SchoolDataBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                RxToast.normal("绑定学校成功");
                SharedHelper.saveSchoolId(BindSchoolActivity.this,school_id);
                finish();
            } else {
                RxToast.normal(baseBean.getMsg());
            }
        };
        HttpMethods.getInstance().bd_school(new ProgressSubscriber<>(onNextListener, this, false), school_id, uid);
    }

    private void initSearch() {
        etSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {

                    try {
                        refreshData();
                        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                                hideSoftInputFromWindow(etSearch.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }


        });
    }

    /**
     * 刷新数据
     */
    private void refreshData() {
        page = 1;
        mList.clear();
        mAdapter.notifyDataSetChanged();
        initData();
    }

    private void initRec() {
        mList = new ArrayList<>();

        LinearLayoutManager linear = new LinearLayoutManager(this);
        recSchool.setLayoutManager(linear);
        recSchool.setNestedScrollingEnabled(false);
        mAdapter = new SchoolAdapter(R.layout.item_school, mList);
        recSchool.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (type == 2) {
                Intent intent = new Intent();
                intent.putExtra("name", mList.get(position).getTitle());
                intent.putExtra("school_id", mList.get(position).getId());
                setResult(0x11, intent);
                finish();
            } else {
                mPosition = position;
                rxDialogSureCancel.show();
            }

        });
    }

    @OnClick(R.id.tv_jump)
    public void onViewClicked() {
        finish();
    }


}
