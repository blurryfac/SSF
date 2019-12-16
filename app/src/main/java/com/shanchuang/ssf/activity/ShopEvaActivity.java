package com.shanchuang.ssf.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.shanchuang.ssf.adapter.GoodsEvaAdapter;
import com.shanchuang.ssf.adapter.ShopEvaAdapter;
import com.shanchuang.ssf.base.BaseActivity;
import com.shanchuang.ssf.bean.GoodsEvaBean;
import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.rxjava.HttpMethods;
import com.shanchuang.ssf.net.subscribers.ProgressSubscriber;
import com.shanchuang.ssf.net.subscribers.SubscriberOnNextListener;
import com.shanchuang.ssf.utils.DividerItemDecoration;
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
 * @time 2019/11/25
 */
public class ShopEvaActivity extends BaseActivity {
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
        return R.layout.activity_my_eva_layout;
    }
    private String id;
    @Override
    protected void initView() {
        title.setText("商品评价");
        Intent intent=getIntent();
        id=intent.getStringExtra("id");
        initRec();
    }
    private List<GoodsEvaBean.CommentsBean> shopEvaBeanList;
    private GoodsEvaAdapter mAdapter;
    private void initRec() {
        shopEvaBeanList = new ArrayList<>();

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recMain.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        recMain.setLayoutManager(manager);
        recMain.setNestedScrollingEnabled(false);
        mAdapter = new GoodsEvaAdapter(R.layout.item_shop_eva, shopEvaBeanList);
        recMain.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        srlAll.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(1500);
                shopEvaBeanList.clear();
                mAdapter.notifyDataSetChanged();
                isShowDialog=false;
                page=1;
                initData();
            }
        });
        srlAll.setOnLoadmoreListener(refreshlayout -> {
            refreshlayout.finishLoadmore(1500);
            page++;
            isShowDialog=false;
            initData();
        });
        mAdapter.setOnItemImgClickListener(new ShopEvaAdapter.OnItemImgClickListener() {
            @Override
            public void onItemImgClick(BaseQuickAdapter adapter, View view, int position, int f_pos) {
                LocalMedia media = shopEvaBeanList.get(f_pos).getLocalMedia().get(position);
                String pictureType = media.getPictureType();
                int mediaType = PictureMimeType.pictureToVideo(pictureType);
                switch (mediaType) {
                    case 1:
                        // 预览图片 可自定长按保存路径
                        //PictureSelector.create(MainActivity.this).externalPicturePreview(position, "/custom_file", selectList);
                        PictureSelector.create(ShopEvaActivity.this).themeStyle(R.style.picture_default_style).openExternalPreview(position, shopEvaBeanList.get(f_pos).getLocalMedia());
                        break;
                }
            }
        });
    }

    private int page=1;
    private boolean isShowDialog=true;
    @Override
    protected void initData() {
        SubscriberOnNextListener<BaseBean<GoodsEvaBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                if (baseBean.getData().getComments().isEmpty()) {
                    if(page==1){
                        none.setVisibility(View.VISIBLE);
                        mAdapter.notifyDataSetChanged();
                    }else {
                        RxToast.normal("没有更多数据了");
                    }

                } else {
                    none.setVisibility(View.INVISIBLE);
                    shopEvaBeanList.addAll(baseBean.getData().getComments());
                    for (int j = 0; j < shopEvaBeanList.size(); j++) {
                        List<LocalMedia> img_list = new ArrayList<>();
                        for (int i = 0; i < shopEvaBeanList.get(j).getImages().size(); i++) {
                            LocalMedia localMedia = new LocalMedia();
                            localMedia.setPath(baseBean.getData().getComments().get(j).getImages().get(i));
                            img_list.add(localMedia);
                        }
                        shopEvaBeanList.get(j).setLocalMedia(img_list);
                    }
                    mAdapter.notifyDataSetChanged();
                }

            } else {
                RxToast.normal(baseBean.getMsg());
            }

        };
        Map<String, Object> map = new HashMap<>();
        map.put("gid", id);
        map.put("page", page);
        HttpMethods.getInstance().comments(new ProgressSubscriber<>(onNextListener, this, isShowDialog), map);
    }
}
