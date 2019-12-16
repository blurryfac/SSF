package com.shanchuang.ssf.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.adapter.SubAnswerOptionAdapter;
import com.shanchuang.ssf.adapter.SubChoiceAdapter;
import com.shanchuang.ssf.base.BaseActivity;
import com.shanchuang.ssf.bean.SubJectBean;
import com.shanchuang.ssf.bean.SubJectNumBean;
import com.shanchuang.ssf.bean.SubOptionBean;
import com.shanchuang.ssf.dialog.DialogUtil;
import com.shanchuang.ssf.manager.FullyGridLayoutManager;
import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.rxjava.HttpMethods;
import com.shanchuang.ssf.net.subscribers.ProgressSubscriber;
import com.shanchuang.ssf.net.subscribers.SubscriberOnNextListener;
import com.shanchuang.ssf.utils.SharedHelper;
import com.shanchuang.ssf.view.ImageViewPlus;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.vondear.rxtool.RxDataTool;

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
 * @time 2019/10/17
 */
public class AnswerAnalysisActivity extends BaseActivity {
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
    @BindView(R.id.tv_right_answer)
    TextView tvRightAnswer;
    @BindView(R.id.ll_main)
    LinearLayout llMain;
    @BindView(R.id.tv_subject_next)
    TextView tvSubjectNext;
    @BindView(R.id.scrollview)
    NestedScrollView mScrollView;
    @BindView(R.id.container)
    RelativeLayout mContainer;
    /**
     * 题目选择弹窗
     */
    private Dialog mSubChoiceDialog;
    private ImageView mIvSubChoiceClose;//关闭弹窗
    private TextView mTvSubChoiceNum;//当前选中题目
    private RecyclerView mRecSubChoice;//题目选择列表
    private SubChoiceAdapter mSubChoiceAdapter;
    private List<SubJectNumBean.TihaoBean> mSubChoiceList;//题目列表数据
    private String id;
    /**
     * 选择题题目列表
     */
    private SubAnswerOptionAdapter mSubOptionAdapter;
    private List<SubOptionBean> mSubOptionList;
    private SubJectBean.ShitiBean mSubject = new SubJectBean.ShitiBean();
    /**
     * 拉取所有题号
     */
    private int mSubjectNum = 0;
    private List<SubJectNumBean.TihaoBean> mTiHaoList = new ArrayList<>();
    private String option[] = {"A", "B", "C", "D"};

    @Override
    protected int getLayoutId() {
        return R.layout.avtivity_answer_analysis_layout;
    }

    @Override
    protected void initView() {
        title.setText("题目解析");
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        initSubjectRec();
        initSubjectDialog();
    }

    private void initWeb(WebView webAnswerAnalysis) {
        webAnswerAnalysis.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView webView, String s) {
                webAnswerAnalysis.loadUrl("javascript:App.resize(document.body.getBoundingClientRect().height)");
                super.onPageFinished(webView, s);

            }

        });
        webAnswerAnalysis.addJavascriptInterface(this, "App");
    }
    @JavascriptInterface
    public void resize(final float height) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //Toast.makeText(getActivity(), height + "", Toast.LENGTH_LONG).show();
                //此处的 layoutParmas 需要根据父控件类型进行区分，这里为了简单就不这么做了
                child.setLayoutParams(new LinearLayout.LayoutParams(getResources().getDisplayMetrics().widthPixels, (int) (height * getResources().getDisplayMetrics().density)));
            }
        });
    }
    private void initSubjectRec() {
        mSubOptionList = new ArrayList<>();

        LinearLayoutManager fullyGridLayoutManager = new LinearLayoutManager(this);
        recSubjectOption.setLayoutManager(fullyGridLayoutManager);
        recSubjectOption.setNestedScrollingEnabled(false);
        mSubOptionAdapter = new SubAnswerOptionAdapter(R.layout.item_answer_sub_option, mSubOptionList);
        recSubjectOption.setAdapter(mSubOptionAdapter);
        mSubOptionAdapter.setOnItemClickListener((adapter, view, position) -> {
        });
    }

    /**
     * 初始化弹框
     */
    private void initSubjectDialog() {
        mSubChoiceList = new ArrayList<>();
        mSubChoiceDialog = new Dialog(this, R.style.Theme_Light_Dialog_1);
        View v = LayoutInflater.from(this).inflate(R.layout.dialog_subject_choice, null);
        findViewById(v);
        DialogUtil.getInstance().setBottomDialog(this, mSubChoiceDialog, v);
        FullyGridLayoutManager fullyGridLayoutManager = new FullyGridLayoutManager(this, 6);
        mRecSubChoice.setLayoutManager(fullyGridLayoutManager);
        mSubChoiceAdapter = new SubChoiceAdapter(R.layout.item_sub_choice, mSubChoiceList);
        mRecSubChoice.setAdapter(mSubChoiceAdapter);
        mSubChoiceAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mSubChoiceDialog.dismiss();
                mSubChoiceList.get(position).setCheck(true);
                mSubChoiceAdapter.notifyDataSetChanged();
                int num = position + 1;
                mSubjectNum = position;
                mTvSubChoiceNum.setText(num + "/" + mTiHaoList.size());
                clearDataAndGetData();
            }
        });
        mIvSubChoiceClose.setOnClickListener(v1 -> mSubChoiceDialog.dismiss());
    }


    /**
     * 获取弹窗ViewID
     *
     * @param view
     */
    private void findViewById(View view) {
        mIvSubChoiceClose = view.findViewById(R.id.iv_subject_choice_close);
        mRecSubChoice = view.findViewById(R.id.rec_subject_choice);
        mTvSubChoiceNum = view.findViewById(R.id.tv_subject_choice_num);
    }

    /**
     * 隐藏界面内容获取数据
     */
    private void clearDataAndGetData() {
        mContainer.setVisibility(View.INVISIBLE);//隐藏主布局
        ivExpState.setVisibility(View.INVISIBLE);
        tvSubjectNext.setVisibility(View.INVISIBLE);
        tvSubjectTitle.setVisibility(View.GONE);
        ivSubjectTitle.setVisibility(View.GONE);
        mSubOptionList.clear();
        mSubOptionAdapter.notifyDataSetChanged();
        httpGetSubject(mTiHaoList.get(mSubjectNum).getSt_no());//获取题目
    }
    /**
     * 按钮点击事件，向容器中添加TextView
     */
    WebView child;

    /**
     * 动态添加webview
     * @param url
     */
    private void addView(String url) {
         child = new WebView(this);
        child.setBackgroundResource(R.drawable.btn_gray_shape);
        child.loadUrl(url);
        initWeb(child);
        llMain.addView(child);
    }

    /**
     * 动态移除webview
     */
    private void removeView() {
        int m=llMain.getChildCount();
        child.removeAllViews();
        child.destroy();
        Log.i("childcount",String.valueOf(m));//调试输出
        llMain.removeView(llMain.getChildAt(m-1));
    }
    private boolean isFirst=true;
    private void httpGetSubject(String st_no) {
        SubscriberOnNextListener<BaseBean<SubJectBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                mContainer.setVisibility(View.VISIBLE);
                ivExpState.setVisibility(View.VISIBLE);
                tvSubjectNext.setVisibility(View.VISIBLE);
                mScrollView.scrollTo(0, 0);
                mSubject = baseBean.getData().getShiti();
                tvRightAnswer.setText("正确答案：" + mSubject.getZq_daan());
                if(isFirst){
                    addView(baseBean.getData().getUrl());
                    isFirst=false;
                }else {
                    removeView();
                    addView(baseBean.getData().getUrl());
                }

                tvSubjectNum.setText((mSubjectNum + 1) + "/" + baseBean.getData().getXiti().getCount());
                tvSubjectScore.setText(baseBean.getData().getXiti().getFen() + " 分");
                if (RxDataTool.isEmpty(mSubject.getTitle_img())) {
                    tvSubjectTitle.setText(mSubject.getTitle());
                    ivSubjectTitle.setVisibility(View.GONE);
                    tvSubjectTitle.setVisibility(View.VISIBLE);
                } else {
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
                    for (int i = 0; i < option.length; i++) {
                        if (option[i].equals(mSubject.getZq_daan())) {
                            mSubOptionList.get(i).setStatus(2);
                        }
                    }
                    for (int i = 0; i < option.length; i++) {
                        if (option[i].equals(baseBean.getData().getMy_daan())) {
                            if (baseBean.getData().getMy_daan().equals(mSubject.getZq_daan())) {
                                mSubOptionList.get(i).setStatus(2);
                            } else {
                                mSubOptionList.get(i).setStatus(1);
                            }
                        }
                    }
                } else {
                    ivExpState.setImageResource(R.mipmap.ic_exp_ge);
                    addJudgeList("正确");
                    addJudgeList("错误");
                    for (int i = 0; i < 2; i++) {
                        if (option[i].equals(mSubject.getZq_daan())) {
                            mSubOptionList.get(i).setStatus(2);
                        }
                    }
                    for (int i = 0; i < 2; i++) {
                        if (option[i].equals(baseBean.getData().getMy_daan())) {
                            if (baseBean.getData().getMy_daan().equals(mSubject.getZq_daan())) {
                                mSubOptionList.get(i).setStatus(2);
                            } else {
                                mSubOptionList.get(i).setStatus(1);
                            }
                        }
                    }
                }
                mSubOptionAdapter.notifyDataSetChanged();
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("st_no", st_no);
        map.put("uid", SharedHelper.readId(this));
        HttpMethods.getInstance().jiexi(new ProgressSubscriber<>(onNextListener, this, true), map);
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
//        subOptionBean.setStatus(status);//0没有选择的答案，1 自己选择的答案，2正确答案
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

    @Override
    protected void initData() {
        httpGetSubjectNum();
    }

    /**
     * 获取题号以及弹框数据
     */
    private void httpGetSubjectNum() {
        SubscriberOnNextListener<BaseBean<SubJectNumBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                mTiHaoList.addAll(baseBean.getData().getTihao());
                mSubChoiceList.addAll(baseBean.getData().getTihao());
                mSubChoiceAdapter.notifyDataSetChanged();
                mTvSubChoiceNum.setText("1/" + mTiHaoList.size());
                httpGetSubject(mTiHaoList.get(mSubjectNum).getSt_no());
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("uid", SharedHelper.readId(this));
        HttpMethods.getInstance().tihao(new ProgressSubscriber<>(onNextListener, this, true), map);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.option_normal_1:
                mSubChoiceDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_subject, menu);
        return true;
    }

    @OnClick(R.id.tv_subject_next)
    public void onViewClicked() {
        mSubjectNum++;//下一题自增
        if (mSubjectNum < mTiHaoList.size()) {
            clearDataAndGetData();
        } else {
            mSubjectNum--;
            tvSubjectNext.setText("已经是最后一题了");
        }
    }


}
