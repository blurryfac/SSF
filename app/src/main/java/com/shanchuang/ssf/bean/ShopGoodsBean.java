package com.shanchuang.ssf.bean;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/10/21
 */
public class ShopGoodsBean {

        /**
         * goods : [{"id":8,"title":"手捞坊文房四宝毛笔套装初学者入门儿童小学生成人软笔书法套装","image":"http://k12.w.shanchuang360.com/uploads/20191018/2019_10_18_16_16_40_87508.jpg","price":58,"sales":0,"jianjie":"默认发空白印章 需要刻字请联系客服备注！"},{"id":7,"title":"文房四宝礼盒写练毛笔字的字帖工具套装初学者小学生","image":"http://k12.w.shanchuang360.com/uploads/20191018/2019_10_18_16_14_05_43156.jpg","price":129,"sales":0,"jianjie":""},{"id":6,"title":"uniker初中小学生拉杆书包男孩大轮子儿童大容量旅行包女行李箱包","image":"http://k12.w.shanchuang360.com/uploads/20191017/b8b87e4576294934b177baa9b107fe05.jpg","price":399,"sales":0,"jianjie":"专利大轮子设计 可爬楼防侧翻 赠49元防雨罩"},{"id":5,"title":"人教版小学生练字帖一年级二三年级上册下册语文生字同步楷书儿童练字本","image":"http://k12.w.shanchuang360.com/uploads/20191017/f8ee71f85f6f59d5b84aa0c5edb258f0.jpg","price":12.9,"sales":0,"jianjie":"赠送10支褪色笔芯 1个笔壳 2个握笔器"},{"id":4,"title":"三年级儿童凹槽练字帖英文字母课本同步练字本","image":"http://k12.w.shanchuang360.com/uploads/20191017/4d63444a0bc1f7c5d6baca94bade3d1a.jpg","price":19.9,"sales":0,"jianjie":"加深凹槽 完整笔画 人教版同步 老师推荐 "},{"id":3,"title":"100支桶装晨光铅笔小学生用套装揪正握姿2比2b幼儿园hb签笔无毒 ","image":"http://k12.w.shanchuang360.com/uploads/20191017/335096273cc6f83c01f85431dee7a181.png","price":89,"sales":0,"jianjie":"送转笔机橡皮"},{"id":2,"title":"中性笔GP-1008学生用水笔签字0.5笔芯蓝黑批发黑色红笔水性碳素圆珠笔","image":"http://k12.w.shanchuang360.com/uploads/20191017/bbea5e835e462f7952b9d0370d439f3f.jpg","price":22.5,"sales":0,"jianjie":"大容量笔芯三倍书写时长舒适握感久写不累"},{"id":1,"title":"2019高端独角兽学生礼物男女儿童文具套装","image":"http://k12.w.shanchuang360.com/uploads/20191017/b84882826f3018d569133c9abc89f37e.jpg","price":129,"sales":0,"jianjie":"时尚创意 流行元素 学生文具礼物好选择 "}]
         * next : 0
         */

        private int next;
        private List<GoodsBean> goods;

        public int getNext() {
            return next;
        }

        public void setNext(int next) {
            this.next = next;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class GoodsBean {
            /**
             * id : 8
             * title : 手捞坊文房四宝毛笔套装初学者入门儿童小学生成人软笔书法套装
             * image : http://k12.w.shanchuang360.com/uploads/20191018/2019_10_18_16_16_40_87508.jpg
             * price : 58
             * sales : 0
             * jianjie : 默认发空白印章 需要刻字请联系客服备注！
             */

            private String id;
            private String title;
            private String image;
            private String price;
            private String sales;
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

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
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

            public String getJianjie() {
                return jianjie;
            }

            public void setJianjie(String jianjie) {
                this.jianjie = jianjie;
            }
        }
}
