package com.shanchuang.ssf.activity;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.adapter.DownAdapter;
import com.shanchuang.ssf.adapter.DownBean;
import com.shanchuang.ssf.base.BaseActivity;
import com.vondear.rxtool.view.RxToast;
import com.vondear.rxui.view.RxProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class DownloadListActivity extends BaseActivity implements BaseQuickAdapter.OnItemChildClickListener {
    @BindView(R.id.rec_down)
    RecyclerView recDown;
    private DownAdapter downAdapter;
    private List<DownBean> list;



    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.rx_pro:
                if(list.get(position).isDown()){
                    RxToast.normal("打开文件");
                }else {
                    list.get(position).getDownloader().download(0L);
                }

                break;
//            case R.id.btn_pause:
//                list.get(position).getDownloader().pause();
//                Toast.makeText(this, "下载暂停", Toast.LENGTH_SHORT).show();
//                // 存储此时的totalBytes，即断点位置。
//                list.get(position).setBreakPoints(list.get(position).getTotalBytes());
//                break;
//            case R.id.btn_pre:
//                list.get(position).getDownloader().download(list.get(position).getBreakPoints());
//                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_down_list_layout;
    }

    @Override
    protected void initView() {
        list = new ArrayList<>();
        for(int i=0;i<5;i++){
            DownBean downBean =new DownBean();
            downBean.setBreakPoints(0L);
            RxProgressBar rxProgressBar =new RxProgressBar(this);
            downBean.setName(i+".apk");
            downBean.setRx_pro(rxProgressBar);
            list.add(downBean);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recDown.setLayoutManager(linearLayoutManager);
        downAdapter = new DownAdapter(R.layout.item_download_layout, list);
        downAdapter.setOnItemChildClickListener(this);
        recDown.setAdapter(downAdapter);
    }

    @Override
    protected void initData() {

    }
}