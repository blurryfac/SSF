package com.shanchuang.ssf.bean;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/11/8
 */
public class ShopCarBean {
        /**
         * address : null
         * total_money : 258
         * order : [{"xiaoji":258,"mer":{"id":2,"title":"郑州九中"},"goods":[{"id":7,"title":"文房四宝礼盒写练毛笔字的字帖工具套装初学者小学生","image":"http://test.k12.com/uploads/20191018/2019_10_18_16_14_05_43156.jpg","price":129,"amount":2}],"youhuiquan":[{"id":7,"total":50,"money":5,"etime":1571992200}]}]
         */

        private AddressBean address;
        private String total_money;
        private List<OrderBean> order;

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

    public String getTotal_money() {
            return total_money;
        }

        public void setTotal_money(String total_money) {
            this.total_money = total_money;
        }

        public List<OrderBean> getOrder() {
            return order;
        }

        public void setOrder(List<OrderBean> order) {
            this.order = order;
        }

        public static class OrderBean {
            /**
             * xiaoji : 258
             * mer : {"id":2,"title":"郑州九中"}
             * goods : [{"id":7,"title":"文房四宝礼盒写练毛笔字的字帖工具套装初学者小学生","image":"http://test.k12.com/uploads/20191018/2019_10_18_16_14_05_43156.jpg","price":129,"amount":2}]
             * youhuiquan : [{"id":7,"total":50,"money":5,"etime":1571992200}]
             */
            private String choicePrice;
            private String xiaoji;
            private MerBean mer;
            private List<GoodsBean> goods;
            private List<YouhuiquanBean> youhuiquan;

            public String getChoicePrice() {
                return choicePrice;
            }

            public void setChoicePrice(String choicePrice) {
                this.choicePrice = choicePrice;
            }

            public String getXiaoji() {
                return xiaoji;
            }

            public void setXiaoji(String xiaoji) {
                this.xiaoji = xiaoji;
            }

            public MerBean getMer() {
                return mer;
            }

            public void setMer(MerBean mer) {
                this.mer = mer;
            }

            public List<GoodsBean> getGoods() {
                return goods;
            }

            public void setGoods(List<GoodsBean> goods) {
                this.goods = goods;
            }

            public List<YouhuiquanBean> getYouhuiquan() {
                return youhuiquan;
            }

            public void setYouhuiquan(List<YouhuiquanBean> youhuiquan) {
                this.youhuiquan = youhuiquan;
            }

            public static class MerBean {
                /**
                 * id : 2
                 * title : 郑州九中
                 */

                private int id;
                private String title;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }
            }

            public static class GoodsBean {
                /**
                 * id : 7
                 * title : 文房四宝礼盒写练毛笔字的字帖工具套装初学者小学生
                 * image : http://test.k12.com/uploads/20191018/2019_10_18_16_14_05_43156.jpg
                 * price : 129
                 * amount : 2
                 */

                private int id;
                private String title;
                private String image;
                private String price;
                private int amount;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
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

                public int getAmount() {
                    return amount;
                }

                public void setAmount(int amount) {
                    this.amount = amount;
                }
            }

            public static class YouhuiquanBean {
                /**
                 * id : 7
                 * total : 50
                 * money : 5
                 * etime : 1571992200
                 */

                private String id;
                private String total;
                private String money;
                private String etime;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getTotal() {
                    return total;
                }

                public void setTotal(String total) {
                    this.total = total;
                }

                public String getMoney() {
                    return money;
                }

                public void setMoney(String money) {
                    this.money = money;
                }

                public String getEtime() {
                    return etime;
                }

                public void setEtime(String etime) {
                    this.etime = etime;
                }
            }
        }
}
