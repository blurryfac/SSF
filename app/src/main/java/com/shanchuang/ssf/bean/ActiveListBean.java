package com.shanchuang.ssf.bean;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/10/14
 */
public class ActiveListBean {

        /**
         * huodong : [{"id":2,"title":"神策2019数据驱动大会：矩·变","stime":1571704200,"etime":1571824800,"image":"http://k12.w.shanchuang360.com/uploads/20191008/5f3668401720f69621dd90ede7acbeee.jpg"},{"id":1,"title":"2019少儿校外教育高峰论坛","stime":1571878800,"etime":1571999400,"image":"http://k12.w.shanchuang360.com/uploads/20191008/e5c2cf6b653ba9bbb5c0d1f8d42476e0.jpg"}]
         * next : 0
         */

        private int next;
        private List<HuodongBean> huodong;

        public int getNext() {
            return next;
        }

        public void setNext(int next) {
            this.next = next;
        }

        public List<HuodongBean> getHuodong() {
            return huodong;
        }

        public void setHuodong(List<HuodongBean> huodong) {
            this.huodong = huodong;
        }

        public static class HuodongBean {
            /**
             * id : 2
             * title : 神策2019数据驱动大会：矩·变
             * stime : 1571704200
             * etime : 1571824800
             * image : http://k12.w.shanchuang360.com/uploads/20191008/5f3668401720f69621dd90ede7acbeee.jpg
             */

            private String id;
            private String title;
            private String stime;
            private String etime;
            private String image;

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

            public String getStime() {
                return stime;
            }

            public void setStime(String stime) {
                this.stime = stime;
            }

            public String getEtime() {
                return etime;
            }

            public void setEtime(String etime) {
                this.etime = etime;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }
        }
}
