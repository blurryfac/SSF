package com.shanchuang.ssf.adapter;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shanchuang.ssf.R;
import com.shanchuang.ssf.net.download.ProgressDownloader;
import com.shanchuang.ssf.net.download.ProgressResponseBody;

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
 * @time 2018/8/22
 */
public class DownAdapter extends BaseQuickAdapter<DownBean, BaseViewHolder> {
    public DownAdapter(int layoutResId, @Nullable List<DownBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DownBean item) {
        helper.addOnClickListener(R.id.rx_pro);
//        helper.addOnClickListener(R.id.btn_pause);
//        helper.addOnClickListener(R.id.btn_pre);
        downloadButtons(helper, item);

    }

    public void downloadButtons(BaseViewHolder helper, DownBean item) {

        // 新下载前清空断点信息
        item.setBreakPoints(0L);
        String url = "";
        url = "http://imtt.dd.qq.com/16891/FC92B1B4471DE5AAD0D009DF9BF1AD01.apk";

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
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), item.getName());
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
//                if (((RxProgressBar) helper.getView(R.id.rx_pro)).getProgress() != pro) {
//                    ((RxProgressBar) helper.getView(R.id.rx_pro)).setProgress(pro);
//                }
                helper.setText(R.id.rx_pro,pro+"%");
                if (done) {
                    // 切换到主线程
                    Observable
                            .empty()
                            .observeOn(AndroidSchedulers.mainThread())
                            .doOnCompleted(new Action0() {
                                @Override
                                public void call() {
                                    item.setDown(true);
                                    helper.setText(R.id.rx_pro,"打开");
                                    Toast.makeText(mContext, "下载完成", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .subscribe();
                }
            }
        });

        item.setDownloader(downloader);
    }
}
