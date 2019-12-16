package com.shanchuang.ssf.mail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.activity.MyOrderActivity;
import com.shanchuang.ssf.base.BaseActivity;
import com.shanchuang.ssf.bean.OrderAllBean;
import com.shanchuang.ssf.bean.OrderItemBean;
import com.shanchuang.ssf.bean.UploadBean;
import com.shanchuang.ssf.mail.adapter.ReleaseEvaAdapter;
import com.shanchuang.ssf.mail.bean.DataBean;
import com.shanchuang.ssf.mail.bean.EvaBean;
import com.shanchuang.ssf.net.entity.BaseBean;
import com.shanchuang.ssf.net.rxjava.HttpMethods;
import com.shanchuang.ssf.net.subscribers.ProgressSubscriber;
import com.shanchuang.ssf.net.subscribers.SubscriberOnNextListener;
import com.shanchuang.ssf.utils.SharedHelper;
import com.vondear.rxtool.view.RxToast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * title:我的评价
 * Created by JY on 2018/6/4.
 */

public class EvaluateActivity extends BaseActivity {
    //图片返回列表
//    List<LocalMedia> selectList = new ArrayList<>();
    @BindView(R.id.rec_eva)
    RecyclerView recEva;
    //    private EvaImageAdapter adapter;
    private String id = "";
    private String oid = "";
    private ReleaseEvaAdapter mAdapter;
    private List<OrderItemBean> mList;
    private EvaBean evaBean = new EvaBean();
    private int type = 0;

    @OnClick({R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_evaluate_layout;
    }

    @Override
    protected void initView() {
        title.setText("评价");
        menu.setText("发布");
        Bundle bundle = getIntent().getExtras();
        oid = bundle.getString("oid");
        type = bundle.getInt("type");
        mList = bundle.getParcelableArrayList("eva");
        if (mList != null) {
            for (int i = 0; i < mList.size(); i++) {
                List<LocalMedia> localMedia = new ArrayList<>();
                mList.get(i).setLocalMedia(localMedia);
            }
        }
        List<DataBean> dataBeanList = new ArrayList<>();
        for (int i = 0; i < mList.size(); i++) {
            DataBean dataBean = new DataBean();
            dataBeanList.add(dataBean);
            evaBean.setData(dataBeanList);
        }
        menu.setTextColor(getResources().getColor(R.color.color_yellow));
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isRelease = false;
                for (int i = 0; i < mList.size(); i++) {
                    if (!isNull(mList.get(i).getContent())) {
                        isRelease = true;
                    }
                }
                if (isRelease) {
                    releaseEva();
                } else {
                    RxToast.normal("请至少选择一个商品评论");
                }

//                if (!etOption.getText().toString().isEmpty()) {
//                    releaseEva();
//                } else {
//                    RxToast.normal("评价内容为空");
//                }
            }
        });
        initRec();
    }

    @Override
    public void initData() {

    }

    private void releaseEva() {
        SubscriberOnNextListener<BaseBean<List<OrderAllBean>>> onNextListener = new SubscriberOnNextListener<BaseBean<List<OrderAllBean>>>() {
            @Override
            public void onNext(BaseBean<List<OrderAllBean>> baseBean) {
                RxToast.normal(baseBean.getMsg());
                if (type != 1) {
                    setResult(0);
                    finish();
                } else {
                    Intent intent = new Intent(EvaluateActivity.this, MyOrderActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }

            }
        };
        Map<String ,Object> map=new HashMap<>();
        map.put("id",oid);
        map.put("uid",SharedHelper.readId(this));
        String content ="";
        for (int i = 0; i < mList.size(); i++) {
            if(i==0){
                content=mList.get(i).getGid()+"|"+mList.get(i).getContent()+"|"+evaBean.getData().get(i).getImgs();
            }else {
                content=content+","+mList.get(i).getGid()+"|"+mList.get(i).getContent()+"|"+evaBean.getData().get(i).getImgs();
            }
        }
        map.put("content",content);
        HttpMethods.getInstance().order_comments(new ProgressSubscriber<>(onNextListener, this, true), map);
    }

    private void initRec() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recEva.setNestedScrollingEnabled(false);
        recEva.setLayoutManager(layoutManager);
        mAdapter = new ReleaseEvaAdapter(R.layout.item_eva, mList, this);
        recEva.setAdapter(mAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mList.get(requestCode).setLocalMedia(PictureSelector.obtainMultipleResult(data));
        mAdapter.notifyDataSetChanged();
        uploadImg(requestCode, PictureSelector.obtainMultipleResult(data));
//        selectList.addAll(PictureSelector.obtainMultipleResult(data));
//        if (!selectList.isEmpty()) {
//            adapter.setList(selectList);
//            adapter.notifyDataSetChanged();

//        }

    }

    private void uploadImg(int pos, List<LocalMedia> selectList) {
        SubscriberOnNextListener<BaseBean<UploadBean>> onNextListener = baseBean -> {
            if (HttpMethods.SUCCESS_CODE == baseBean.getCode()) {
                String path="";
                for(int i=0;i<baseBean.getData().getUrl().size();i++){

                    if(i==0){
                        path=baseBean.getData().getUrl().get(i);
                    }else {
                        path=path+"#"+baseBean.getData().getUrl().get(i);
                    }
                }
                evaBean.getData().get(pos).setImgs(path);
            } else {
                RxToast.normal(baseBean.getMsg());
            }
        };
        Map<String, RequestBody> params = new HashMap<>();
        for (int i = 0; i < selectList.size(); i++) {
            File file = new File(selectList.get(i).getCompressPath());
            RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);
            params.put("file[" + i + "]\"; filename=\"" + file.getName() + "\"", fileBody);
            Log.i("----------params1", file.getAbsolutePath());

        }
        params.put("uid", RequestBody.create(null, SharedHelper.readId(this)));
        HttpMethods.getInstance().upload(new ProgressSubscriber<>(onNextListener, this, true), params);
//        SubscriberOnNextListener<BaseBean<List<String>>> onNextListener = new SubscriberOnNextListener<BaseBean<List<String>>>() {
//            @Override
//            public void onNext(BaseBean<List<String>> baseBean) {
//                if (200 == baseBean.getCode()) {
//                    Log.i("------------", baseBean.toString());
//                    String path = "";
//                    for (int i = 0; i < baseBean.getData().size(); i++) {
//
//
//                        if (i == 0) {
//                            path = baseBean.getData().get(0);
//
//                        } else {
//                            path = path + "@#@" + baseBean.getData().get(i);
//                        }
//                    }
//
//                    evaBean.getData().get(pos).setImgs(path);
//                }
//
//            }
//        };
//        Map<String, RequestBody> params = new HashMap<>();
//        for (int i = 0; i < selectList.size(); i++) {
//            File file = new File(selectList.get(i).getCompressPath());
//            RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);
//            // String s = "{\"type\":\"" + 1 + "\"}";
//            // RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), "");
//            if (i == 0) {
//                params.put("file\"; filename=\"" + file.getName() + "\"", fileBody);
//                //    params.put("type", fileBody);
//            } else {
//                params.put("file" + i + "\"; filename=\"" + file.getName() + "\"", fileBody);
//            }
//
//            Log.i("----------params1", file.getAbsolutePath());
//
//        }
//
//        Log.i("----------params", params.toString());
//        HttpMethods.getInstance().uploadFile(new ProgressSubscriber<>(onNextListener, this, true), params);
    }
}
