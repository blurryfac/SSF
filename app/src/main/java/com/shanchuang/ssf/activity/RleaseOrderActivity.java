package com.shanchuang.ssf.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.adapter.CateAdapter;
import com.shanchuang.ssf.adapter.ImgAdapter;
import com.shanchuang.ssf.base.BaseActivity;
import com.shanchuang.ssf.bean.ReleaseCateBean;
import com.shanchuang.ssf.manager.FullyGridLayoutManager;
import com.shanchuang.ssf.manager.GridSpacingItemDecoration;
import com.shanchuang.ssf.view.XRadioGroup;
import com.vondear.rxtool.RxActivityTool;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/12/11
 */
public class RleaseOrderActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    TextView ivBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_menu)
    TextView toolbarMenu;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rec_cate)
    RecyclerView recCate;
    @BindView(R.id.tv_more)
    TextView tvMore;
    @BindView(R.id.rb_dian)
    RadioButton rbDian;
    @BindView(R.id.tv_dian)
    TextView tvDian;
    @BindView(R.id.rb_bao)
    RadioButton rbBao;
    @BindView(R.id.tv_bao)
    TextView tvBao;
    @BindView(R.id.xg_all)
    XRadioGroup xgAll;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_address_detail)
    TextView tvAddressDetail;
    @BindView(R.id.et_ys_price)
    EditText etYsPrice;
    @BindView(R.id.tv_work_time)
    TextView tvWorkTime;
    @BindView(R.id.et_work_day_num)
    EditText etWorkDayNum;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.et_option)
    EditText etOption;
    @BindView(R.id.rec_img)
    RecyclerView recImg;
    @BindView(R.id.et_beizhu)
    EditText etBeizhu;
    @BindView(R.id.cb_xy)
    CheckBox cbXy;
    @BindView(R.id.tv_xy)
    TextView tvXy;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_release_order_layout;
    }

    @Override
    protected void initView() {
        title.setText("我是雇主");
        initCate();
        initRec();
    }

    private void initRec() {

    }
    private CateAdapter mCateAdapter;
    private List<ReleaseCateBean> mCateList;
    private List<ReleaseCateBean> mAllCateList=new ArrayList<>();
    private void initCate() {
        mCateList=new ArrayList<>();
        for(int i=0;i<10;i++){

            ReleaseCateBean releaseCateBean =new ReleaseCateBean();
            releaseCateBean.setDrawable(R.mipmap.ic_tab_one_selctor);
            if(i==0){
                releaseCateBean.setStatus(1);
            }else {
                releaseCateBean.setStatus(0);
            }
            releaseCateBean.setTitle("item"+i);
            mAllCateList.add(releaseCateBean);
        }
        mCateList.addAll(mAllCateList.subList(0,6));
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);

        recCate.setLayoutManager(manager);
        recCate.addItemDecoration(new GridSpacingItemDecoration(3,10,false));
        mCateAdapter = new CateAdapter(R.layout.item_release_cate, mCateList);
        recCate.setAdapter(mCateAdapter);
        mCateAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for(int i=0;i<mCateList.size();i++){
                    if(i==position){
                        mCateList.get(i).setStatus(1);
                    }else {
                        mCateList.get(i).setStatus(0);
                    }

                }
                mCateAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void initData() {

    }
private boolean isShow=false;


    @OnClick({R.id.tv_more, R.id.tv_address, R.id.tv_address_detail, R.id.tv_work_time, R.id.tv_xy, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_more:
                if(!isShow){
                    tvMore.setText("收起");
                }else {
                    tvMore.setText("更多");
                }
                mCateList.clear();
                mCateList.addAll(mAllCateList);
                mCateAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_address:
                break;
            case R.id.tv_address_detail:
                break;
            case R.id.tv_work_time:
                break;
            case R.id.tv_xy:
                break;
            case R.id.tv_submit:
                RxActivityTool.skipActivity(this,DepositPayGZActivity.class);
                break;
        }
    }
}
