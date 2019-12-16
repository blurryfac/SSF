package com.shanchuang.ssf.bean;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/11/26
 */
public class ActiveDetailBean {
    private String url;
    private List<CourseBean> course;
    private HuoDongBean huodong;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<CourseBean> getCourse() {
        return course;
    }

    public void setCourse(List<CourseBean> course) {
        this.course = course;
    }

    public HuoDongBean getHuodong() {
        return huodong;
    }

    public void setHuodong(HuoDongBean huodong) {
        this.huodong = huodong;
    }

    public static class HuoDongBean {
        /**
         * id : 2
         * title : 初一数学下册 平行线与相交线（知识点快速复习）
         * image : http://test.k12.com/uploads/20191016/98cfeb6b87880a036c2f89e04e0f2210.jpg
         * zhujiang : 筱筱老师
         * createtime : 1571208930
         * price : 19.6
         * old_price : 26.9
         * view : 0
         */

        private String id;
        private String title;
        private String etime;
        private String stime;

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

        public String getEtime() {
            return etime;
        }

        public void setEtime(String etime) {
            this.etime = etime;
        }

        public String getStime() {
            return stime;
        }

        public void setStime(String stime) {
            this.stime = stime;
        }
    }
    public static class CourseBean {
        /**
         * id : 2
         * title : 初一数学下册 平行线与相交线（知识点快速复习）
         * image : http://test.k12.com/uploads/20191016/98cfeb6b87880a036c2f89e04e0f2210.jpg
         * zhujiang : 筱筱老师
         * createtime : 1571208930
         * price : 19.6
         * old_price : 26.9
         * view : 0
         */

        private String id;
        private String title;
        private String image;
        private String zhujiang;
        private String createtime;
        private String price;
        private String old_price;
        private String view;

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

        public String getZhujiang() {
            return zhujiang;
        }

        public void setZhujiang(String zhujiang) {
            this.zhujiang = zhujiang;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getOld_price() {
            return old_price;
        }

        public void setOld_price(String old_price) {
            this.old_price = old_price;
        }

        public String getView() {
            return view;
        }

        public void setView(String view) {
            this.view = view;
        }
    }
}
