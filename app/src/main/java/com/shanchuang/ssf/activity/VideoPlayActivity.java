package com.shanchuang.ssf.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shanchuang.ssf.MainActivity;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.base.BaseActivity;
import com.shanchuang.ssf.bean.VideoPlayBean;
import com.shanchuang.ssf.event.EventTag;
import com.shanchuang.ssf.event.MessageEvent;
import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.rxjava.HttpMethods;
import com.shanchuang.ssf.net.subscribers.ProgressSubscriber;
import com.shanchuang.ssf.net.subscribers.SubscriberOnNextListener;
import com.shanchuang.ssf.utils.SharedHelper;
import com.shanchuang.ssf.view.JZMediaExo;
import com.shanchuang.ssf.view.MyJzvdStd;
import com.tencent.smtt.sdk.WebView;
import com.vondear.rxtool.RxDataTool;
import com.vondear.rxtool.RxTextTool;
import com.vondear.rxtool.RxTimeTool;
import com.vondear.rxtool.view.RxToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/10/14
 */
public class VideoPlayActivity extends BaseActivity {
    @BindView(R.id.jz_video)
    MyJzvdStd jzVideo;
    @BindView(R.id.tv_buy_tip)
    TextView tvBuyTip;
    @BindView(R.id.tv_buy_video)
    TextView tvBuyVideo;
    @BindView(R.id.iv_back)
    TextView ivBack;
    @BindView(R.id.toolbar_menu)
    TextView toolbarMenu;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.fl_video)
    FrameLayout flVideo;
    @BindView(R.id.tv_video_title)
    TextView tvVideoTitle;
    @BindView(R.id.tv_now_price)
    TextView tvNowPrice;
    @BindView(R.id.tv_old_price)
    TextView tvOldPrice;
    @BindView(R.id.tv_video_see_num)
    TextView tvVideoSeeNum;
    @BindView(R.id.tv_teacher_time)
    TextView tvTeacherTime;
    @BindView(R.id.tv_post_time)
    TextView tvPostTime;
    @BindView(R.id.web_main)
    WebView webMain;
    @BindView(R.id.tv_coll)
    CheckBox tvColl;
    @BindView(R.id.ll_score)
    LinearLayout llScore;
    @BindView(R.id.ll_pay)
    LinearLayout llPay;
    @BindView(R.id.tv_video_score)
    TextView tvVideoScore;
    @BindView(R.id.tv_video_price)
    TextView tvVideoPrice;
    @BindView(R.id.tv_already_pay)
    TextView tvAlreadyPay;
    private String mCourseId; //课程ID


    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_play_layout;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getTop(MessageEvent messageEvent) {
        if (EventTag.MAIN.equals(messageEvent.getTag())) {
            resetVideo();

        }
    }

    /**
     * 试看结束
     */
    private void resetVideo() {
        jzVideo.textureViewContainer.setEnabled(false);
        jzVideo.progressBar.setEnabled(false);
        jzVideo.fullscreenButton.setEnabled(false);
        jzVideo.startButton.setVisibility(View.INVISIBLE);
        tvBuyVideo.setVisibility(View.VISIBLE);
        tvBuyTip.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(!isClick){
            Jzvd.releaseAllVideos();
        }

    }
    @SuppressLint("HandlerLeak")
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x10:
                    loadCover(jzVideo.thumbImageView,mCourse.getVideo(),VideoPlayActivity.this);
                    jzVideo.setUp(mCourse.getVideo(), "",JzvdStd.SCREEN_NORMAL, JZMediaExo.class);
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private   void loadCover(ImageView imageView, String url, Context context) {

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(context)
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .frame(1000000)
                                .centerCrop()
                )
                .load(url)
                .into(imageView);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);

        title.setText("课程详情");
        mCourseId = getIntent().getStringExtra("id");
        tvColl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isFirst){
                    if(!SharedHelper.readId(VideoPlayActivity.this).isEmpty()) {
                        httpCollSub(isChecked);
                    }else {
                        MainActivity.showLoginDialog(VideoPlayActivity.this);
                    }
                }

            }
        });
    }
    private boolean isFirst=true;
    /**
     * 收藏
     * @param isChecked
     */
    private void httpCollSub(boolean isChecked) {
        SubscriberOnNextListener<BaseBean> onNextListener = baseBean -> RxToast.normal(baseBean.getMsg());
        Map<String, Object> map = new HashMap<>();
        map.put("cid", mCourseId);
        map.put("uid", SharedHelper.readId(this));
        if(isChecked){
            tvColl.setText("已收藏");
            HttpMethods.getInstance().course_add(new ProgressSubscriber<>(onNextListener, this, false), map);
        }else {
            tvColl.setText("收藏");
            HttpMethods.getInstance().course_del(new ProgressSubscriber<>(onNextListener, this, false), map);
        }
    }
    private VideoPlayBean.CourseBean mCourse=new VideoPlayBean.CourseBean();
    @Override
    protected void initData() {
        SubscriberOnNextListener<BaseBean<VideoPlayBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {

                if (baseBean.getData().getIs_coll() == 0) {
                    tvColl.setChecked(false);
                    tvColl.setText("收藏");
                } else {
                    tvColl.setChecked(true);
                    tvColl.setText("已收藏");
                }
                isFirst=false;
                mCourse=baseBean.getData().getCourse();
                if (baseBean.getData().getIs_buy() == 1||mCourse.getPrice().equals("0")) {
                    if(mCourse.getPrice().equals("0")){
                        tvAlreadyPay.setText("免费");
                    }
                    llPay.setVisibility(View.GONE);
                    llScore.setVisibility(View.GONE);
                    tvAlreadyPay.setVisibility(View.VISIBLE);
                    jzVideo.setPay(true);
                }else {
                    llPay.setVisibility(View.VISIBLE);
                    llScore.setVisibility(View.VISIBLE);
                    tvAlreadyPay.setVisibility(View.GONE);
                    jzVideo.setPay(false);
                }


                if("0".equals(mCourse.getJifen())){
                    llScore.setVisibility(View.GONE);
                }else {
                    tvVideoScore.setText(mCourse.getJifen());
                }

                tvVideoPrice.setText("¥ "+mCourse.getPrice());
                tvVideoTitle.setText(mCourse.getTitle());
                tvNowPrice.setText("¥ "+mCourse.getPrice());
                tvVideoSeeNum.setText(mCourse.getView());
                tvPostTime.setText(RxTimeTool.timeStamp2Date(mCourse.getCreatetime(),null));
                tvTeacherTime.setText("主讲教师："+mCourse.getZhujiang());
                webMain.loadUrl(baseBean.getData().getUrl());
                RxTextTool.getBuilder("").append("¥ "+mCourse.getOld_price()).setStrikethrough().into(tvOldPrice);
                if(!RxDataTool.isEmpty(mCourse.getVideo())){
                    mHandler.sendEmptyMessage(0x10);

                }else {
                    RxToast.normal("暂无视频播放地址");
                }

            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("id", mCourseId);
        map.put("uid", SharedHelper.readId(this));
        map.put("school_id", SharedHelper.readOtherId(this));
        HttpMethods.getInstance().course_detail(new ProgressSubscriber<>(onNextListener, this, true), map);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == 2) {
            isClick=false;
            llPay.setVisibility(View.GONE);
            llScore.setVisibility(View.GONE);
            tvAlreadyPay.setVisibility(View.VISIBLE);
            jzVideo.setPay(true);
            jzVideo.textureViewContainer.setEnabled(true);
            jzVideo.progressBar.setEnabled(true);
            jzVideo.fullscreenButton.setEnabled(true);
            jzVideo.startButton.setVisibility(View.VISIBLE);
            tvBuyVideo.setVisibility(View.INVISIBLE);
            tvBuyTip.setVisibility(View.INVISIBLE);
        }
    }

    private boolean isClick;



    @OnClick({ R.id.ll_score, R.id.ll_pay,R.id.tv_buy_video})
    public void onViewClicked(View view) {
        if(!SharedHelper.readId(this).isEmpty()){
            Intent intent = new Intent();
            switch (view.getId()) {
                case R.id.tv_buy_video:
                    isClick=true;
                    intent.setClass(this, BuyVideoActivity.class);
                    intent.putExtra("id",mCourseId);
                    startActivityForResult(intent,2);
                    break;
                case R.id.ll_score:
                case R.id.ll_pay:
                    intent.setClass(this, BuyVideoActivity.class);
                    intent.putExtra("id",mCourseId);
                    startActivityForResult(intent,2);
                    break;
            }
        }else {
            MainActivity.showLoginDialog(this);
        }

    }


}
