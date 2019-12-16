package com.shanchuang.ssf.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.adapter.GroupAdapter;
import com.shanchuang.ssf.base.BaseActivity;
import com.shanchuang.ssf.bean.GroupBean;
import com.shanchuang.ssf.bean.GroupMsgBean;
import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.rxjava.HttpMethods;
import com.shanchuang.ssf.net.subscribers.ProgressSubscriber;
import com.shanchuang.ssf.net.subscribers.SubscriberOnNextListener;
import com.shanchuang.ssf.utils.SharedHelper;
import com.vondear.rxtool.RxActivityTool;
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
 * @time 2019/10/23
 */
public class MyGroupActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    TextView ivBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_menu)
    TextView toolbarMenu;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_see_video_time)
    TextView tvSeeVideoTime;
    @BindView(R.id.tv_start_subject_num)
    TextView tvStartSubjectNum;
    @BindView(R.id.tv_already_buy_course)
    TextView tvAlreadyBuyCourse;
    @BindView(R.id.rv_group)
    RecyclerView rvGroup;
    @BindView(R.id.tv_add_recoard)
    TextView tvAddRecoard;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_group_layout;
    }

    @Override
    protected void initView() {
        toolbar.setNavigationIcon(R.mipmap.ic_back_white);
        title.setText("我的成长");
        initRec();
        initSrl();
        httpGetTop();
    }
    private int page = 1;
    private boolean isShowDialog = true;
    private void initSrl() {
        srl.setOnLoadmoreListener(refreshlayout -> {
            refreshlayout.finishLoadmore(1500);
            page++;
            isShowDialog=false;
            initData();
        });
        srl.setOnRefreshListener(new OnRefreshListener() {
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

    private List<GroupBean.ChengzhangBean> mList;
    private GroupAdapter mAdapter;

    private void initRec() {
        mList = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            GroupBean groupBean = new GroupBean();
//            if (i == 1 || i == 3 || i == 7) {
//                groupBean.setContent("item" + i);
//                List<LocalMedia> list = new ArrayList<>();
//                for (int j = 0; j < 4; j++) {
//                    LocalMedia localMedia = new LocalMedia();
//                    localMedia.setPath("");
//                    list.add(localMedia);
//                }
//                groupBean.setImgs(list);
//            } else {
//                groupBean.setContent("item" + i);
//            }
//            mList.add(groupBean);
//        }
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvGroup.setLayoutManager(manager);
        mAdapter = new GroupAdapter(R.layout.item_group, mList);
        rvGroup.setAdapter(mAdapter);
        mAdapter.setOnItemImgClickListener(new GroupAdapter.OnItemImgClickListener() {
            @Override
            public void onItemImgClick(BaseQuickAdapter adapter, View view, int position, int f_pos) {
                LocalMedia media = mList.get(f_pos).getImgs().get(position);
                String pictureType = media.getPictureType();
                int mediaType = PictureMimeType.pictureToVideo(pictureType);
                switch (mediaType) {
                    case 1:
                        // 预览图片 可自定长按保存路径
                        //PictureSelector.create(MainActivity.this).externalPicturePreview(position, "/custom_file", selectList);
                        PictureSelector.create(MyGroupActivity.this).themeStyle(R.style.picture_default_style).openExternalPreview(position, mList.get(f_pos).getImgs());
                        break;
                }
            }
        });
    }

    @Override
    protected void initData() {
        SubscriberOnNextListener<BaseBean<GroupBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                if (baseBean.getData().getChengzhang().isEmpty()) {
                    if(page==1){
                        mAdapter.notifyDataSetChanged();
                    }else {
                        RxToast.normal("没有更多数据了");
                    }

                } else {
                    mList.addAll(baseBean.getData().getChengzhang());
                    for (int j = 0; j < mList.size(); j++) {
                        List<LocalMedia> img_list = new ArrayList<>();
                        for (int i = 0; i < mList.get(j).getImages().size(); i++) {
                            LocalMedia localMedia = new LocalMedia();
                            localMedia.setPath(baseBean.getData().getChengzhang().get(j).getImages().get(i));
                            img_list.add(localMedia);
                        }
                        mList.get(j).setImgs(img_list);
                    }
                    mAdapter.notifyDataSetChanged();
                }

            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("uid", SharedHelper.readId(this));
        map.put("page", page);
        HttpMethods.getInstance().chengzhang(new ProgressSubscriber<>(onNextListener, this, isShowDialog), map);


    }

    private void httpGetTop() {
        SubscriberOnNextListener<BaseBean<GroupMsgBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                tvAlreadyBuyCourse.setText(baseBean.getData().getCourse_count() + "个");
                tvSeeVideoTime.setText(baseBean.getData().getVideo_sc() + "分钟");
                tvStartSubjectNum.setText(baseBean.getData().getXiti_count() + "道");

            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("uid", SharedHelper.readId(this));
        HttpMethods.getInstance().chengzhang_data(new ProgressSubscriber<>(onNextListener, this, true), map);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x11 && resultCode == 0x12) {
           srl.autoRefresh();
        }
    }

    @OnClick(R.id.tv_add_recoard)
    public void onViewClicked() {
        RxActivityTool.skipActivityForResult(this, AddStudyActivity.class, 0x11);
    }


}
