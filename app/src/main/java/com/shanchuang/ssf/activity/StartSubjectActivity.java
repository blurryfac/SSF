package com.shanchuang.ssf.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.adapter.SubOptionAdapter;
import com.shanchuang.ssf.base.BaseActivity;
import com.shanchuang.ssf.bean.AnswerBean;
import com.shanchuang.ssf.bean.SubJectBean;
import com.shanchuang.ssf.bean.SubJectNumBean;
import com.shanchuang.ssf.bean.SubOptionBean;
import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.rxjava.HttpMethods;
import com.shanchuang.ssf.net.subscribers.ProgressSubscriber;
import com.shanchuang.ssf.net.subscribers.SubscriberOnNextListener;
import com.shanchuang.ssf.utils.SharedHelper;
import com.shanchuang.ssf.view.ImageViewPlus;
import com.vondear.rxtool.RxDataTool;
import com.vondear.rxtool.view.RxToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/10/31
 */
public class StartSubjectActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    TextView ivBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_menu)
    TextView toolbarMenu;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_subject_num)
    TextView tvSubjectNum;
    @BindView(R.id.tv_subject_score)
    TextView tvSubjectScore;
    @BindView(R.id.iv_exp_state)
    ImageView ivExpState;
    @BindView(R.id.iv_subject_title)
    ImageViewPlus ivSubjectTitle;
    @BindView(R.id.tv_subject_title)
    TextView tvSubjectTitle;
    @BindView(R.id.rec_subject_option)
    RecyclerView recSubjectOption;
    @BindView(R.id.ll_main)
    LinearLayout llMain;
    @BindView(R.id.tv_subject_next)
    TextView tvSubjectNext;
    private SubJectBean.ShitiBean mSubject = new SubJectBean.ShitiBean();
    private String id;
    /**
     * 选择题题目列表
     */
    private SubOptionAdapter mSubOptionAdapter;
    private List<SubOptionBean> mSubOptionList;
    private boolean isCheckBoxClick = true;//判断CheckBox点击还是条目点击
    /**
     * 拉取所有题号
     */
    private int mSubjectNum = 0;
    private List<SubJectNumBean.TihaoBean> mTiHaoList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.avtivity_start_subject_layout;
    }

    @Override
    protected void initView() {
        title.setText("题库");
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        initSubjectRec();
    }

    private void initSubjectRec() {
        mSubOptionList = new ArrayList<>();
        LinearLayoutManager fullyGridLayoutManager = new LinearLayoutManager(this);
        recSubjectOption.setLayoutManager(fullyGridLayoutManager);
        recSubjectOption.setNestedScrollingEnabled(false);
        mSubOptionAdapter = new SubOptionAdapter(R.layout.item_sub_option, mSubOptionList);
        recSubjectOption.setAdapter(mSubOptionAdapter);
        ((SimpleItemAnimator)recSubjectOption.getItemAnimator()).setSupportsChangeAnimations(false);
        mSubOptionAdapter.setOnItemClickListener((adapter, view, position) -> {
//            isCheckBoxClick = false;
//            for (int i = 0; i < mSubOptionList.size(); i++) {
//                if (i == position) {
//                    mSubOptionList.get(i).setCheck(true);
//                } else {
//                    if (mSubOptionList.get(i).isCheck()) {
//                        mSubOptionList.get(i).setCheck(false);
//                    }
//                }
//            }
//            mSubOptionAdapter.notifyDataSetChanged();
        });
        mSubOptionAdapter.setOnCheckClickListener((buttonView, isChecked, postion) -> {
                if (isChecked) {//判断CheckBox是否选中
                    mSubOptionAdapter.setChecbox(isChecked,postion);
                    for (int i = 0; i < mSubOptionList.size(); i++) {
                        if (i == postion) {
                            mSubOptionAdapter.setChecbox(true,i);
                        } else {
                            if (mSubOptionList.get(i).isCheck()) {
                                mSubOptionAdapter.setChecbox(false,i);
                            }
                        }
                    }
            }


        });
    }

    @Override
    protected void initData() {
        httpGetSubjectNum();

    }

    private void httpGetSubjectNum() {
        SubscriberOnNextListener<BaseBean<SubJectNumBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                mTiHaoList.addAll(baseBean.getData().getTihao());
                httpGetSubject(mTiHaoList.get(mSubjectNum).getSt_no());
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("uid", SharedHelper.readId(this));
        HttpMethods.getInstance().tihao(new ProgressSubscriber<>(onNextListener, this, true), map);

    }
    /**
     * 初始化问答题
     */
    private void initImgTitle() {
        //设置问答题题目图片宽高比

        Glide.with(this).asBitmap().load(mSubject.getTitle_img()).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                ivSubjectTitle.setRatio((float) width / height);
                ivSubjectTitle.setImageBitmap(bitmap);
            }
        });

    }
    private void httpGetSubject(String st_no) {
        SubscriberOnNextListener<BaseBean<SubJectBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                ivExpState.setVisibility(View.VISIBLE);
                tvSubjectNext.setVisibility(View.VISIBLE);
                mSubject = baseBean.getData().getShiti();
                tvSubjectNum.setText((mSubjectNum+1)+"/"+baseBean.getData().getXiti().getCount());
                if(mSubjectNum+1==mTiHaoList.size()){
                    tvSubjectNext.setText("立刻交卷");
                }
                tvSubjectScore.setText(baseBean.getData().getXiti().getFen()+" 分");
                if(RxDataTool.isEmpty(mSubject.getTitle_img())){
                    tvSubjectTitle.setText(mSubject.getTitle());
                    ivSubjectTitle.setVisibility(View.GONE);
                    tvSubjectTitle.setVisibility(View.VISIBLE);
                }else {
                    tvSubjectTitle.setVisibility(View.GONE);
                    ivSubjectTitle.setVisibility(View.VISIBLE);
                    initImgTitle();
                }
                if (mSubject.getType() == 1) {
                    ivExpState.setImageResource(R.mipmap.ic_exp_single);
                    addSubjectList(mSubject.getA_img(), mSubject.getDaan_a());
                    addSubjectList(mSubject.getB_img(), mSubject.getDaan_b());
                    addSubjectList(mSubject.getC_img(), mSubject.getDaan_c());
                    addSubjectList(mSubject.getD_img(), mSubject.getDaan_d());
                } else {
                    ivExpState.setImageResource(R.mipmap.ic_exp_ge);
                    addJudgeList("正确");
                    addJudgeList("错误");
                }
                mSubOptionAdapter.notifyDataSetChanged();
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("st_no", st_no);
        map.put("uid", SharedHelper.readId(this));
        HttpMethods.getInstance().zuoti(new ProgressSubscriber<>(onNextListener, this, true), map);
    }

    /**
     * 添加单选题目列表
     *
     * @param img
     * @param text
     */
    private void addSubjectList(String img, String text) {
        SubOptionBean subOptionBean = new SubOptionBean();
        if (RxDataTool.isEmpty(img)) {
            subOptionBean.setName(text);
            subOptionBean.setType(1);//文字
        } else {
            subOptionBean.setName(img);
            subOptionBean.setType(2);//图片
        }
        mSubOptionList.add(subOptionBean);
    }

    /**
     * 添加判断题列表
     *
     * @param text
     */
    private void addJudgeList(String text) {
        SubOptionBean subOptionBean = new SubOptionBean();
        subOptionBean.setName(text);
        subOptionBean.setType(1);
        mSubOptionList.add(subOptionBean);
    }

    @OnClick(R.id.tv_subject_next)
    public void onViewClicked() {
        if(getAnswer()){
            mSubjectNum++;//下一题自增
            if(mSubjectNum<mTiHaoList.size()){

                ivExpState.setVisibility(View.INVISIBLE);
                tvSubjectNext.setVisibility(View.INVISIBLE);
                tvSubjectTitle.setVisibility(View.GONE);
                ivSubjectTitle.setVisibility(View.GONE);
                mSubOptionList.clear();
                mSubOptionAdapter.notifyDataSetChanged();
                httpGetSubject(mTiHaoList.get(mSubjectNum).getSt_no());//获取题目
            }else {
                String answer=submitAnswer();
                Intent in =new Intent(this, FinishSubjectActivity.class);
                in.putExtra("answer",answer);
                in.putExtra("id",id);
                startActivity(in);
                finish();
            }
        }else {
            RxToast.normal("请选择答案！");
        }




    }

    /**
     * 拼接答案
     * @return
     */
    private String submitAnswer() {
        StringBuilder s= new StringBuilder();
        for(int i=0;i<mAnswerList.size();i++){
            if(i==0){
                s = new StringBuilder(mAnswerList.get(i).getId() + "," + mAnswerList.get(i).getName());
            }else {
                s.append("|").append(mAnswerList.get(i).getId()).append(",").append(mAnswerList.get(i).getName());
            }
        }

        return s.toString();

    }

    private List<AnswerBean> mAnswerList=new ArrayList<>();

    /**
     * 每次做题的答案进行记录
     */
    private boolean getAnswer() {
        boolean isCheck=false;
        for(int i=0;i<mSubOptionList.size();i++){
            if(mSubOptionList.get(i).isCheck()){
                isCheck=true;
                if(i==0){
                    mAnswerList.add(new AnswerBean("A",mSubject.getId()));
                }else if(i==1){
                    mAnswerList.add(new AnswerBean("B",mSubject.getId()));
                }else if(i==2){
                    mAnswerList.add(new AnswerBean("C",mSubject.getId()));
                }else if(i==3){
                    mAnswerList.add(new AnswerBean("D",mSubject.getId()));
                }

            }
        }
        return isCheck;

    }


}

