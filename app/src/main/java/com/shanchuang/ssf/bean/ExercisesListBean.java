package com.shanchuang.ssf.bean;

import com.shanchuang.ssf.net.download.ProgressDownloader;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/10/15
 */
public class ExercisesListBean {
    private int next;
    private List<XitiBean> xiti;

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public List<XitiBean> getXiti() {
        return xiti;
    }

    public void setXiti(List<XitiBean> xiti) {
        this.xiti = xiti;
    }

    public static class CateBean {
        /**
         * cate1 : 初一
         * cate2 : 初一
         */

        private String cate1;
        private String cate2;

        public String getCate1() {
            return cate1;
        }

        public void setCate1(String cate1) {
            this.cate1 = cate1;
        }

        public String getCate2() {
            return cate2;
        }

        public void setCate2(String cate2) {
            this.cate2 = cate2;
        }
    }

    public static class XitiBean {
        /**
         * id : 2
         * title : 初一数学下册 平行线与相交线（知识点快速复习）
         * image : http://test.k12.com/uploads/20191016/98cfeb6b87880a036c2f89e04e0f2210.jpg
         * fenshu : 120
         * count : 50
         * defen : 65
         */
        private long breakPoints;
        private long totalBytes;
        private long contentLength;
        private String url;
        private boolean isDown;
        private String name;
        private ProgressDownloader downloader;
        private String id;
        private String title;
        private String fujian;
        private String image;
        private String fenshu;
        private String count;
        private String defen;
        private String createtime;

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getFujian() {
            return fujian;
        }

        public void setFujian(String fujian) {
            this.fujian = fujian;
        }

        public boolean isDown() {
            return isDown;
        }

        public void setDown(boolean down) {
            isDown = down;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getFenshu() {
            return fenshu;
        }

        public void setFenshu(String fenshu) {
            this.fenshu = fenshu;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getDefen() {
            return defen;
        }

        public void setDefen(String defen) {
            this.defen = defen;
        }
    }

}
