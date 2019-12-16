package com.shanchuang.ssf.bean;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/11/7
 */
public class ShopDetailsBean {
        /**
         * goods : {"id":1,"title":"2019高端独角兽学生礼物男女儿童文具套装","price":129,"sales":0,"mid":1,"jianjie":"时尚创意 流行元素 学生文具礼物好选择 "}
         * images : ["http://k12.w.shanchuang360.com/uploads/20191017/ebb077db26b998a9bd344d628b26347d.jpg","http://k12.w.shanchuang360.com/uploads/20191017/45fe9878bbb7bcfe33c72e35b9de0c6d.jpg","http://k12.w.shanchuang360.com/uploads/20191017/b84882826f3018d569133c9abc89f37e.jpg"]
         * comments_count : 1
         * is_coll : 0
         * url : http://k12.w.shanchuang360.com/k12/article/goods/id/1.html
         */

        private GoodsBean goods;
        private int comments_count;
        private int is_coll;
        private String url;
        private List<String> images;

        public GoodsBean getGoods() {
            return goods;
        }

        public void setGoods(GoodsBean goods) {
            this.goods = goods;
        }

        public int getComments_count() {
            return comments_count;
        }

        public void setComments_count(int comments_count) {
            this.comments_count = comments_count;
        }

        public int getIs_coll() {
            return is_coll;
        }

        public void setIs_coll(int is_coll) {
            this.is_coll = is_coll;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public static class GoodsBean {
            /**
             * id : 1
             * title : 2019高端独角兽学生礼物男女儿童文具套装
             * price : 129
             * sales : 0
             * mid : 1
             * jianjie : 时尚创意 流行元素 学生文具礼物好选择
             */

            private String id;
            private String title;
            private String price;
            private String sales;
            private String mid;
            private String jianjie;

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

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getSales() {
                return sales;
            }

            public void setSales(String sales) {
                this.sales = sales;
            }

            public String getMid() {
                return mid;
            }

            public void setMid(String mid) {
                this.mid = mid;
            }

            public String getJianjie() {
                return jianjie;
            }

            public void setJianjie(String jianjie) {
                this.jianjie = jianjie;
            }
        }
}
