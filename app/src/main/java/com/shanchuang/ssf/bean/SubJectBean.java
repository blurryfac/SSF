package com.shanchuang.ssf.bean;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/10/31
 */
public class SubJectBean {
        /**
         * shiti : {"id":1,"title":"Is this a  ________ of your family.","title_img":null,"type":1,"daan_a":"photos","daan_b":"photo","daan_c":"your photo","daan_d":"photos","a_img":null,"b_img":null,"c_img":null,"d_img":null}
         * xiti : {"count":10,"fen":12}
         */
        private String my_daan;
        private String url;
        private ShitiBean shiti;
        private XitiBean xiti;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMy_daan() {
        return my_daan;
    }

    public void setMy_daan(String my_daan) {
        this.my_daan = my_daan;
    }

    public ShitiBean getShiti() {
            return shiti;
        }

        public void setShiti(ShitiBean shiti) {
            this.shiti = shiti;
        }

        public XitiBean getXiti() {
            return xiti;
        }

        public void setXiti(XitiBean xiti) {
            this.xiti = xiti;
        }

        public static class ShitiBean {
            /**
             * id : 1
             * title : Is this a  ________ of your family.
             * title_img : null
             * type : 1
             * daan_a : photos
             * daan_b : photo
             * daan_c : your photo
             * daan_d : photos
             * a_img : null
             * b_img : null
             * c_img : null
             * d_img : null
             */
            private String zq_daan;
            private String id;
            private String title;
            private String title_img;
            private int type;
            private String daan_a;
            private String daan_b;
            private String daan_c;
            private String daan_d;
            private String a_img;
            private String b_img;
            private String c_img;
            private String d_img;

            public String getZq_daan() {
                return zq_daan;
            }

            public void setZq_daan(String zq_daan) {
                this.zq_daan = zq_daan;
            }

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

            public String getTitle_img() {
                return title_img;
            }

            public void setTitle_img(String title_img) {
                this.title_img = title_img;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getDaan_a() {
                return daan_a;
            }

            public void setDaan_a(String daan_a) {
                this.daan_a = daan_a;
            }

            public String getDaan_b() {
                return daan_b;
            }

            public void setDaan_b(String daan_b) {
                this.daan_b = daan_b;
            }

            public String getDaan_c() {
                return daan_c;
            }

            public void setDaan_c(String daan_c) {
                this.daan_c = daan_c;
            }

            public String getDaan_d() {
                return daan_d;
            }

            public void setDaan_d(String daan_d) {
                this.daan_d = daan_d;
            }

            public String getA_img() {
                return a_img;
            }

            public void setA_img(String a_img) {
                this.a_img = a_img;
            }

            public String getB_img() {
                return b_img;
            }

            public void setB_img(String b_img) {
                this.b_img = b_img;
            }

            public String getC_img() {
                return c_img;
            }

            public void setC_img(String c_img) {
                this.c_img = c_img;
            }

            public String getD_img() {
                return d_img;
            }

            public void setD_img(String d_img) {
                this.d_img = d_img;
            }
        }

        public static class XitiBean {
            /**
             * count : 10
             * fen : 12
             */

            private int count;
            private int fen;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public int getFen() {
                return fen;
            }

            public void setFen(int fen) {
                this.fen = fen;
            }
        }
}
