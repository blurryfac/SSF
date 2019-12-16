package com.shanchuang.ssf.bean;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/11/27
 */
public class MyCourseBean {
        /**
         * order : [{"id":13,"cid":3,"total":0.01,"title":"人教版初中语文七年级上学期同步教学","zhujiang":"赵银花","old_price":26.5,"createtime":1571209084,"view":0,"image":"http://k12.w.shanchuang360.com/uploads/20191016/ce11d0d4961b35c5b931fb05a6136627.jpg"}]
         * next : 0
         */

        private int next;
        private List<OrderBean> order;

        public int getNext() {
            return next;
        }

        public void setNext(int next) {
            this.next = next;
        }

        public List<OrderBean> getOrder() {
            return order;
        }

        public void setOrder(List<OrderBean> order) {
            this.order = order;
        }

        public static class OrderBean {
            /**
             * id : 13
             * cid : 3
             * total : 0.01
             * title : 人教版初中语文七年级上学期同步教学
             * zhujiang : 赵银花
             * old_price : 26.5
             * createtime : 1571209084
             * view : 0
             * image : http://k12.w.shanchuang360.com/uploads/20191016/ce11d0d4961b35c5b931fb05a6136627.jpg
             */

            private String id;
            private String cid;
            private String total;
            private String title;
            private String zhujiang;
            private String old_price;
            private String createtime;
            private String view;
            private String image;

            public String getCid() {
                return cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getZhujiang() {
                return zhujiang;
            }

            public void setZhujiang(String zhujiang) {
                this.zhujiang = zhujiang;
            }

            public String getOld_price() {
                return old_price;
            }

            public void setOld_price(String old_price) {
                this.old_price = old_price;
            }

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }

            public String getView() {
                return view;
            }

            public void setView(String view) {
                this.view = view;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }
}
