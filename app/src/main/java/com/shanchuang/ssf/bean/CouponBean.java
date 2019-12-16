package com.shanchuang.ssf.bean;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/10/21
 */
public class CouponBean {

        private List<YouhuiquanBean> youhuiquan;

        public List<YouhuiquanBean> getYouhuiquan() {
            return youhuiquan;
        }

        public void setYouhuiquan(List<YouhuiquanBean> youhuiquan) {
            this.youhuiquan = youhuiquan;
        }

        public static class YouhuiquanBean {
            /**
             * id : 4
             * total : 199
             * money : 99
             * etime : 1572510600
             * is_lq : 0
             */

            private String id;
            private String total;
            private String money;
            private String etime;
            private int is_lq;

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

            public int getIs_lq() {
                return is_lq;
            }

            public void setIs_lq(int is_lq) {
                this.is_lq = is_lq;
            }
        }
}
