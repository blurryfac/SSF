package com.shanchuang.ssf.activity;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.adapter.ExercisesListAdapter;
import com.shanchuang.ssf.base.BaseActivity;
import com.shanchuang.ssf.bean.ExercisesListBean;
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
 * @time 2019/10/15
 */
public class ExercisesDownLoadListActivity extends BaseActivity implements BaseQuickAdapter.OnItemChildClickListener{
    @BindView(R.id.iv_back)
    TextView ivBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_menu)
    TextView toolbarMenu;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.rec_main)
    RecyclerView recMain;
    @BindView(R.id.none)
    ImageView none;
    @BindView(R.id.srl_all)
    SmartRefreshLayout srlAll;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_exercises_download_list_layout;
    }

    @Override
    protected void initView() {
        title.setText("试卷下载");

        initRec();
        initSrl();
        etSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    try {
                        page = 1;
                        mList.clear();
                        mAdapter.notifyDataSetChanged();
                        initData();
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

    private void initSrl() {
        srlAll.setOnLoadmoreListener(refreshlayout -> {
            refreshlayout.finishLoadmore(1500);
            page++;
            isShowDialog=false;
            initData();
        });
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
    private int page=1;
    private boolean isShowDialog=true;

    private List<ExercisesListBean.XitiBean> mList;
    private ExercisesListAdapter mAdapter;
    private void initRec() {
        mList = new ArrayList<>();

        LinearLayoutManager linear = new LinearLayoutManager(this);
        recMain.setLayoutManager(linear);
        recMain.setNestedScrollingEnabled(false);
        mAdapter = new ExercisesListAdapter(R.layout.item_exercises_down_list, mList);
        recMain.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(this);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
//            Intent intent =new Intent(this,DownLoadMsgActivity.class);
//            startActivity(intent);

        });
    }
    @Override
    protected void initData() {
        SubscriberOnNextListener<BaseBean<ExercisesListBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                if (baseBean.getData().getXiti().isEmpty()) {
                    if(page==1){
                        none.setVisibility(View.VISIBLE);
                        mAdapter.notifyDataSetChanged();
                    }else {
                        RxToast.normal("没有更多数据了");
                    }

                } else {
                    none.setVisibility(View.INVISIBLE);
                    mList.addAll(baseBean.getData().getXiti());
                    mAdapter.notifyDataSetChanged();
                }

            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();
            map.put("keyword", etSearch.getText().toString());
        map.put("uid", SharedHelper.readId(this));
        map.put("school_id", SharedHelper.readOtherId(this));
        map.put("page", page);
        HttpMethods.getInstance().shijuan(new ProgressSubscriber<>(onNextListener, this, isShowDialog), map);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.tv_download:
                if(mList.get(position).isDown()){
//                    RxToast.normal("打开文件");
                }else {
                    mList.get(position).getDownloader().download(0L);
                }

                break;

        }
    }
}
