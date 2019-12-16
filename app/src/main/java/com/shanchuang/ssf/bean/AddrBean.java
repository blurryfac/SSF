package com.shanchuang.ssf.bean;

import java.util.List;

/**
 * Created by JY on 2018/5/7.
 */

public class AddrBean {



        private List<AddrMsgBean> addr;

        public List<AddrMsgBean> getAddr() {
            return addr;
        }

        public void setAddr(List<AddrMsgBean> addr) {
            this.addr = addr;
        }

        public static class AddrMsgBean {
            /**
             * id : 1
             * name : 张
             * mobile : 15139091146
             * sheng : 河南省
             * shi : null
             * xian : null
             * address : null
             * is_default : 0
             */

            private String id;
            private String name;
            private String mobile;
            private String sheng;
            private String shi;
            private String xian;
            private String address;
            private int is_default;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

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

            public String getSheng() {
                return sheng;
            }

            public void setSheng(String sheng) {
                this.sheng = sheng;
            }

            public String getShi() {
                return shi;
            }

            public void setShi(String shi) {
                this.shi = shi;
            }

            public String getXian() {
                return xian;
            }

            public void setXian(String xian) {
                this.xian = xian;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getIs_default() {
                return is_default;
            }

            public void setIs_default(int is_default) {
                this.is_default = is_default;
            }
        }
}
