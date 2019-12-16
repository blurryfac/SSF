package com.shanchuang.ssf.activity;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.base.BaseActivity;
import com.shanchuang.ssf.bean.ExerCisesMsgBean;
import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.rxjava.HttpMethods;
import com.shanchuang.ssf.net.subscribers.ProgressSubscriber;
import com.shanchuang.ssf.net.subscribers.SubscriberOnNextListener;
import com.shanchuang.ssf.utils.SharedHelper;
import com.shanchuang.ssf.view.ImageViewPlus;
import com.vondear.rxtool.view.RxToast;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/10/17
 */
public class DownLoadMsgActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    TextView ivBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_menu)
    TextView toolbarMenu;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_logo)
    ImageViewPlus ivLogo;
    @BindView(R.id.tv_exp_title)
    TextView tvExpTitle;
    @BindView(R.id.tv_exp_score)
    TextView tvExpScore;
    @BindView(R.id.tv_exp_num)
    TextView tvExpNum;
    @BindView(R.id.tv_pass_score)
    TextView tvPassScore;
    @BindView(R.id.tv_score_text)
    TextView tvScoreText;
    @BindView(R.id.tv_last_score)
    TextView tvLastScore;
    @BindView(R.id.tv_answer_analysis)
    TextView tvAnswerAnalysis;
    @BindView(R.id.tv_coll)
    CheckBox tvColl;
    @BindView(R.id.tv_start_exp)
    TextView tvStartExp;
    private String mExercisesId; //课程ID
    @Override
    protected int getLayoutId() {
        return R.layout.activity_download_msg_layout;
    }

    @Override
    protected void initView() {
        title.setText("试卷详情");
        mExercisesId = getIntent().getStringExtra("id");
        tvColl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                httpCollSub(isChecked);
            }




        });
    }

    /**
     * 收藏
     * @param isChecked
     */
    private void httpCollSub(boolean isChecked) {
        SubscriberOnNextListener<BaseBean> onNextListener = baseBean -> RxToast.normal(baseBean.getMsg());
        Map<String, Object> map = new HashMap<>();
        map.put("xid", mExercisesId);
        map.put("uid", SharedHelper.readId(this));
        if(isChecked){
            tvColl.setText("已收藏");
            HttpMethods.getInstance().coll_add(new ProgressSubscriber<>(onNextListener, this, false), map);
        }else {
            tvColl.setText("收藏");
            HttpMethods.getInstance().coll_del(new ProgressSubscriber<>(onNextListener, this, false), map);
        }
    }


    ExerCisesMsgBean.XitiBean mXiTi=new ExerCisesMsgBean.XitiBean();
    @Override
    protected void initData() {
        SubscriberOnNextListener<BaseBean<ExerCisesMsgBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                mXiTi=baseBean.getData().getXiti();
                Glide.with(DownLoadMsgActivity.this).load(mXiTi.getImage()).into(ivLogo);
                if (baseBean.getData().getIs_coll() == 0) {
                    tvColl.setChecked(false);
                    tvColl.setText("收藏");
                } else {
                    tvColl.setChecked(true);
                    tvColl.setText("已收藏");
                }
                tvExpTitle.setText(mXiTi.getTitle());
                tvExpScore.setText("试卷分数："+mXiTi.getFenshu());

                tvExpNum.setText("试卷题目数："+mXiTi.getCount());
                tvPassScore.setText("试卷及格分数："+mXiTi.getJg_fenshu());
                tvLastScore.setText(mXiTi.getDefen());
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("id", mExercisesId);
        map.put("uid", SharedHelper.readId(this));
        HttpMethods.getInstance().xiti_detail(new ProgressSubscriber<>(onNextListener, this, true), map);
    }



    @OnClick({R.id.tv_answer_analysis, R.id.tv_coll, R.id.tv_start_exp})
    public void onViewClicked(View view) {
        Intent intent =new Intent();
        switch (view.getId()) {
            case R.id.tv_answer_analysis:
                intent.setClass(this,AnswerAnalysisActivity.class);
                intent.putExtra("id",mExercisesId);
                startActivity(intent);
                break;
            case R.id.tv_coll:

                break;
            case R.id.tv_start_exp:
                intent.setClass(this,StartSubjectActivity.class);
                intent.putExtra("id",mExercisesId);
                startActivity(intent);
                break;
        }
    }
}
