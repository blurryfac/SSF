package com.shanchuang.ssf.adapter;

import com.shanchuang.ssf.net.download.ProgressDownloader;
import com.vondear.rxui.view.RxProgressBar;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2018/8/22
 */
public class DownBean {
    private long breakPoints;
    private long totalBytes;
    private long contentLength;
    private String url;
    private boolean isDown;
    private String name;
    private ProgressDownloader downloader;
    private RxProgressBar rx_pro;

    public boolean isDown() {
        return isDown;
    }

    public void setDown(boolean down) {
        isDown = down;
    }

    public RxProgressBar getRx_pro() {
        return rx_pro;
    }

    public void setRx_pro(RxProgressBar rx_pro) {
        this.rx_pro = rx_pro;
    }

    @Override
    public String toString() {
        return "DownBean{" +
                "breakPoints=" + breakPoints +
                ", totalBytes=" + totalBytes +
                ", contentLength=" + contentLength +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", downloader=" + downloader +
                '}';
    }

    public ProgressDownloader getDownloader() {
        return downloader;
    }

    public void setDownloader(ProgressDownloader downloader) {
        this.downloader = downloader;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getBreakPoints() {
        return breakPoints;
    }

    public void setBreakPoints(long breakPoints) {
        this.breakPoints = breakPoints;
    }

    public long getTotalBytes() {
        return totalBytes;
    }

    public void setTotalBytes(long totalBytes) {
        this.totalBytes = totalBytes;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }
}
