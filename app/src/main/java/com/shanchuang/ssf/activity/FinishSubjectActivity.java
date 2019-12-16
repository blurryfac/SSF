package com.shanchuang.ssf.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.base.BaseActivity;
import com.shanchuang.ssf.bean.FinishSubjectBean;
import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.rxjava.HttpMethods;
import com.shanchuang.ssf.net.subscribers.ProgressSubscriber;
import com.shanchuang.ssf.net.subscribers.SubscriberOnNextListener;
import com.shanchuang.ssf.utils.SharedHelper;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/11/4
 */
public class FinishSubjectActivity extends BaseActivity {
    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.tv_result_status)
    TextView tvResultStatus;
    @BindView(R.id.tv_result_get_score)
    TextView tvResultGetScore;
    @BindView(R.id.iv_result_status)
    ImageView ivResultStatus;
    @BindView(R.id.tv_result_text)
    TextView tvResultText;
    @BindView(R.id.tv_check_answer)
    TextView tvCheckAnswer;
    @BindView(R.id.tv_back_main)
    TextView tvBackMain;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_finish_subject_layout;
    }

    private String answer;
    private String id;

    @Override
    protected void initView() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        answer = intent.getStringExtra("answer");
    }

    @Override
    protected void initData() {
        @SuppressLint("SetTextI18n")
        SubscriberOnNextListener<BaseBean<FinishSubjectBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                tvResult.setText(baseBean.getData().getDefen());
                if(baseBean.getData().getIs_jg()==1){
                    tvResultStatus.setText("成绩合格");
                    Glide.with(FinishSubjectActivity.this).load(R.mipmap.ic_finish_success).into(ivResultStatus);
                }else {
                    tvResultStatus.setText("成绩未合格");
                    Glide.with(FinishSubjectActivity.this).load(R.mipmap.ic_finish_error).into(ivResultStatus);
                }
                tvResultGetScore.setText("获得积分："+baseBean.getData().getJifen()+"积分");
                tvResultText.setText("共"+baseBean.getData().getCount()+"题  正确"+baseBean.getData().getYes_count()+"题，错误"+baseBean.getData().getNo_count()+"题");

            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("daan", answer);
        map.put("uid", SharedHelper.readId(this));
        HttpMethods.getInstance().jiaojuan(new ProgressSubscriber<>(onNextListener, this, true), map);
    }



    @OnClick({R.id.iv_return, R.id.tv_check_answer, R.id.tv_back_main})
    public void onViewClicked(View view) {
        Intent intent=new Intent();
        switch (view.getId()) {
            case R.id.iv_return:
                finish();
                break;
            case R.id.tv_check_answer:
                intent.setClass(this,AnswerAnalysisActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_back_main:
                intent.setClass(this, ExercisesListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
        }
    }
}
