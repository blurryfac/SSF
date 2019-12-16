package com.shanchuang.ssf.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.adapter.ExercisesAdapter;
import com.shanchuang.ssf.base.BaseActivity;
import com.shanchuang.ssf.bean.ExercisesBean;
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
 * @time 2019/10/31
 */
public class ExercisesListActivity extends BaseActivity {
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
        return R.layout.activity_exercises_layout;
    }
    private List<ExercisesBean.XitiBean> mList;
    private ExercisesAdapter mAdapter;
    private String cid;
    private String cid2;
    private void initRec() {
        mList = new ArrayList<>();
        LinearLayoutManager linear = new LinearLayoutManager(this);
        recMain.setLayoutManager(linear);
        recMain.setNestedScrollingEnabled(false);
        mAdapter = new ExercisesAdapter(R.layout.item_exercises_list, mList);
        recMain.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent =new Intent(ExercisesListActivity.this,DownLoadMsgActivity.class);
            intent.putExtra("id",mList.get(position).getId());
            startActivity(intent);

        });
    }
    @Override
    protected void initView() {
        Intent intent=getIntent();
        title.setText(intent.getStringExtra("title"));
        cid = intent.getStringExtra("cid");
        cid2 = intent.getStringExtra("cid2");
        initRec();
        initSrl();
    }
    private void initSrl() {
        srlAll.setOnLoadmoreListener(refreshlayout -> {
            refreshlayout.finishLoadmore(1500);
            page++;
            isShowDialog=false;
            initData();
        });
        srlAll.setOnRefreshListener(refreshlayout -> {
            refreshlayout.finishRefresh(1500);
            page=1;
            mList.clear();
            mAdapter.notifyDataSetChanged();
            initData();
        });
    }
    private int page=1;
    private boolean isShowDialog=true;

    @Override
    protected void initData() {
        SubscriberOnNextListener<BaseBean<ExercisesBean>> onNextListener = baseBean -> {
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
        map.put("cid", cid);
        map.put("cid2", cid2);
        if ("0".equals(cid)){
            map.put("keyword", title.getText().toString());
        }
        map.put("uid", SharedHelper.readId(this));
        map.put("school_id", SharedHelper.readOtherId(this));
        map.put("page", page);
        HttpMethods.getInstance().exercises_lists(new ProgressSubscriber<>(onNextListener, this, isShowDialog), map);
    }


}
