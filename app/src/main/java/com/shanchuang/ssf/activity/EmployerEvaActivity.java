package com.shanchuang.ssf.activity;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.adapter.EmployerEvaAadapter;
import com.shanchuang.ssf.adapter.HotAdapter;
import com.shanchuang.ssf.base.BaseActivity;
import com.shanchuang.ssf.bean.EmployerEvaBean;
import com.shanchuang.ssf.view.XRadioGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/12/10
 */
public class EmployerEvaActivity extends BaseActivity implements XRadioGroup.OnCheckedChangeListener{
    @BindView(R.id.iv_back)
    TextView ivBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_menu)
    TextView toolbarMenu;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rb_1)
    RadioButton rb1;
    @BindView(R.id.rb_2)
    RadioButton rb2;
    @BindView(R.id.rb_3)
    RadioButton rb3;
    @BindView(R.id.rg_all)
    XRadioGroup rgAll;
    @BindView(R.id.tv_eva_num)
    TextView tvEvaNum;
    @BindView(R.id.rec_main)
    RecyclerView recMain;
    @BindView(R.id.none)
    TextView none;
    @BindView(R.id.srl_all)
    SmartRefreshLayout srlAll;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_employer_eva_layout;
    }

    @Override
    protected void initView() {
        title.setText("雇主评价");
        initRec();
    }
    private EmployerEvaAadapter mAdapter;
    private List<EmployerEvaBean> mList;
    private void initRec() {
        mList=new ArrayList<>();
        for(int i=0;i<10;i++){
            EmployerEvaBean employerEvaBean=new EmployerEvaBean();
            employerEvaBean.setName("张三");
            employerEvaBean.setContent("张三1111111111111111111");
            employerEvaBean.setTime("2019-11-11");
            mList.add(employerEvaBean);
        }
        LinearLayoutManager linear = new LinearLayoutManager(this);
        recMain.setLayoutManager(linear);
        mAdapter = new EmployerEvaAadapter(R.layout.item_employer_eva, mList);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {

        });
        recMain.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {

    }


    @Override
    public void onCheckedChanged(XRadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.rb_1:
                break;
            case R.id.rb_2:
                break;
            case R.id.rb_3:
                break;
        }
    }
}
