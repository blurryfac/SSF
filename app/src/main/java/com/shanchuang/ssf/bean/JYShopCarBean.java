package com.shanchuang.ssf.bean;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/11/8
 */
public class JYShopCarBean {
        private List<CartBean> cart;

        public List<CartBean> getCart() {
            return cart;
        }

        public void setCart(List<CartBean> cart) {
            this.cart = cart;
        }

        public static class CartBean {
            /**
             * mer : {"id":1,"title":"中心学校","status":1}
             * goods : [{"id":2,"title":"中性笔GP-1008学生用水笔签字0.5笔芯蓝黑批发黑色红笔水性碳素圆珠笔","image":"http://test.k12.com/uploads/20191017/bbea5e835e462f7952b9d0370d439f3f.jpg","price":22.5,"amount":2,"cart_id":3}]
             */
            private boolean  isCheck;
            private MerBean mer;

            private List<JYShopItemBean> goods;

            public boolean isCheck() {
                return isCheck;
            }

            public void setCheck(boolean check) {
                isCheck = check;
            }

            public MerBean getMer() {
                return mer;
            }

            public void setMer(MerBean mer) {
                this.mer = mer;
            }

            public List<JYShopItemBean> getGoods() {
                return goods;
            }

            public void setGoods(List<JYShopItemBean> goods) {
                this.goods = goods;
            }

            public static class MerBean {
                /**
                 * id : 1
                 * title : 中心学校
                 * status : 1
                 */

                private String id;
                private String title;
                private int status;

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

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }
            }


        }
}
