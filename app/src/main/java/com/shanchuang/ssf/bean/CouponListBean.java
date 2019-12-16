package com.shanchuang.ssf.bean;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/10/24
 */
public class CouponListBean {
        /**
         * youhuiquan : [{"id":9,"total":10,"money":2,"mid":1,"etime":1575099660,"status":1,"fabuzhe":"中心学校"},{"id":8,"total":200,"money":20,"mid":1,"etime":1574926860,"status":0,"fabuzhe":"中心学校"}]
         * next : 0
         */

        private int next;
        private List<YouhuiquanBean> youhuiquan;

        public int getNext() {
            return next;
        }

        public void setNext(int next) {
            this.next = next;
        }

        public List<YouhuiquanBean> getYouhuiquan() {
            return youhuiquan;
        }

        public void setYouhuiquan(List<YouhuiquanBean> youhuiquan) {
            this.youhuiquan = youhuiquan;
        }

        public static class YouhuiquanBean {
            /**
             * id : 9
             * total : 10
             * money : 2
             * mid : 1
             * etime : 1575099660
             * status : 1
             * fabuzhe : 中心学校
             */

            private String id;
            private String total;
            private String money;
            private String mid;
            private String etime;
            private int status;
            private String fabuzhe;

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

            public String getMid() {
                return mid;
            }

            public void setMid(String mid) {
                this.mid = mid;
            }

            public String getEtime() {
                return etime;
            }

            public void setEtime(String etime) {
                this.etime = etime;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getFabuzhe() {
                return fabuzhe;
            }

            public void setFabuzhe(String fabuzhe) {
                this.fabuzhe = fabuzhe;
            }
        }
}
