package com.shanchuang.ssf.adapter;

import android.os.Environment;
import android.util.Log;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.bean.ExercisesListBean;
import com.shanchuang.ssf.net.download.ProgressDownloader;
import com.shanchuang.ssf.net.download.ProgressResponseBody;
import com.shanchuang.ssf.view.ImageViewPlus;
import com.vondear.rxtool.RxTimeTool;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/10/15
 */
public class ExercisesListAdapter extends BaseQuickAdapter<ExercisesListBean.XitiBean, BaseViewHolder> {
    public ExercisesListAdapter(int layoutResId, @Nullable List<ExercisesListBean.XitiBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ExercisesListBean.XitiBean item) {
        helper.addOnClickListener(R.id.tv_download);
        downloadButtons(helper, item);
        helper.setText(R.id.tv_name,item.getTitle());
        helper.setText(R.id.tv_scroe,"试卷分数："+item.getFenshu());
        helper.setText(R.id.tv_num,"试卷题目数："+item.getCount());
        helper.setText(R.id.tv_time, "上传时间："+RxTimeTool.timeStamp2Date(item.getCreatetime(),""));
        Glide.with(mContext).load(item.getImage()).into((ImageViewPlus)helper.getView(R.id.iv_logo));

    }
    public void downloadButtons(BaseViewHolder helper, ExercisesListBean.XitiBean item) {

        // 新下载前清空断点信息
        item.setBreakPoints(0L);
        String url = item.getFujian();
        String suffixes = "avi|mpeg|3gp|mp3|mp4|wav|jpeg|gif|jpg|png|apk|exe|pdf|rar|zip|docx|doc";
        Pattern pat = Pattern.compile("[\\w]+[\\.](" + suffixes + ")");//正则判断
        Matcher mc = pat.matcher(url);//条件匹配
        StringBuffer name = new StringBuffer();
        while (mc.find()) {
            String substring = mc.group();//截取文件名后缀名
            Log.e("substring:", substring);
            name.append(substring);
        }
        // 下载的位置
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), name.toString());
        ProgressDownloader downloader = new ProgressDownloader(url, file, new ProgressResponseBody.ProgressListener() {
            @Override
            public void onPreExecute(long contentLength) {
                if (item.getContentLength() == 0L) {
                    item.setContentLength(contentLength);
                }
            }

            @Override
            public void update(long totalBytes, boolean done) {
                item.setTotalBytes(totalBytes + item.getBreakPoints());
                float a = (float) (totalBytes + item.getBreakPoints()) / 1024;
                float b = (float) (item.getContentLength() / 1024);
                int pro = (int) ((a / b) * 100);
                Log.i(item.getName() + "===============", pro + "");
                helper.setText(R.id.tv_download,pro+"%");
                if (done) {
                    // 切换到主线程
                    Observable
                            .empty()
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnCompleted(new Action0() {
                                @Override
                                public void call() {
                                    item.setDown(true);
//                                    helper.setText(R.id.tv_download,"打开");
//                                    Toast.makeText(mContext, "下载完成", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .subscribe();
                }
            }
        });

        item.setDownloader(downloader);
    }
}
