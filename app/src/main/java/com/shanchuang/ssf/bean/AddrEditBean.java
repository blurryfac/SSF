package com.shanchuang.ssf.bean;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/11/12
 */
public class AddrEditBean {
        /**
         * addr : {"id":3,"name":"111","mobile":"18238212211","sheng":"河南省","shi":"郑州市","xian":"金水区","address":"111111111","is_default":1}
         */

        private AddrBean addr;

        public AddrBean getAddr() {
            return addr;
        }

        public void setAddr(AddrBean addr) {
            this.addr = addr;
        }

        public static class AddrBean {
            /**
             * id : 3
             * name : 111
             * mobile : 18238212211
             * sheng : 河南省
             * shi : 郑州市
             * xian : 金水区
             * address : 111111111
             * is_default : 1
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
