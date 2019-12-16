package com.shanchuang.ssf.bean;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/11/13
 */
public class OrderDetailsBean {
    /**
     * order : [{"id":31,"mid":1,"status":"1","order_id":"1573202484786123","total":129,"pay_total":127,"mer_title":"中心学校","goods":[{"gid":1,"amount":1,"price":129,"title":"2019高端独角兽学生礼物男女儿童文具套装","image":"http://k12.w.shanchuang360.com/uploads/20191017/b84882826f3018d569133c9abc89f37e.jpg"}],"count":1}]
     * next : 0
     */
    private OrderBean order;



    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public static class OrderBean {
        /**
         * id : 31
         * mid : 1
         * status : 1
         * order_id : 1573202484786123
         * total : 129
         * pay_total : 127
         * mer_title : 中心学校
         * goods : [{"gid":1,"amount":1,"price":129,"title":"2019高端独角兽学生礼物男女儿童文具套装","image":"http://k12.w.shanchuang360.com/uploads/20191017/b84882826f3018d569133c9abc89f37e.jpg"}]
         * count : 1
         */

        private String id;
        private String mid;
        private String name;
        private String mobile;
        private String address;
        private String yhq_total;
        private String createtime;
        private String paytime;
        private String endtime;
        private int status;
        private String order_id;
        private String total;
        private String pay_total;
        private String mer_title;
        private int count;
        private List<OrderItemBean> goods;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getYhq_total() {
            return yhq_total;
        }

        public void setYhq_total(String yhq_total) {
            this.yhq_total = yhq_total;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getPaytime() {
            return paytime;
        }

        public void setPaytime(String paytime) {
            this.paytime = paytime;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMid() {
            return mid;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getPay_total() {
            return pay_total;
        }

        public void setPay_total(String pay_total) {
            this.pay_total = pay_total;
        }

        public String getMer_title() {
            return mer_title;
        }

        public void setMer_title(String mer_title) {
            this.mer_title = mer_title;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<OrderItemBean> getGoods() {
            return goods;
        }

        public void setGoods(List<OrderItemBean> goods) {
            this.goods = goods;
        }
    }
}
