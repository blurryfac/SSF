package com.shanchuang.ssf.bean;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/10/31
 */
public class SubJectNumBean {
        /**
         * tihao : [{"st_no":1,"is_zq":-1},{"st_no":2,"is_zq":-1},{"st_no":3,"is_zq":-1},{"st_no":4,"is_zq":-1},{"st_no":5,"is_zq":-1},{"st_no":6,"is_zq":-1},{"st_no":7,"is_zq":-1},{"st_no":8,"is_zq":-1},{"st_no":9,"is_zq":-1},{"st_no":10,"is_zq":-1}]
         * shiti : null
         * is_zuo : 0
         */

        private ShiTiBean shiti;
        private int is_zuo;
        private List<TihaoBean> tihao;

        public ShiTiBean getShiti() {
            return shiti;
        }

        public void setShiti(ShiTiBean shiti) {
            this.shiti = shiti;
        }

        public int getIs_zuo() {
            return is_zuo;
        }

        public void setIs_zuo(int is_zuo) {
            this.is_zuo = is_zuo;
        }

        public List<TihaoBean> getTihao() {
            return tihao;
        }

        public void setTihao(List<TihaoBean> tihao) {
            this.tihao = tihao;
        }

        public static class TihaoBean {
            /**
             * st_no : 1
             * is_zq : -1
             */

            private String st_no;
            private int is_zq;
            private boolean isCheck;

            public boolean isCheck() {
                return isCheck;
            }

            public void setCheck(boolean check) {
                isCheck = check;
            }

            public String getSt_no() {
                return st_no;
            }

            public void setSt_no(String st_no) {
                this.st_no = st_no;
            }

            public int getIs_zq() {
                return is_zq;
            }

            public void setIs_zq(int is_zq) {
                this.is_zq = is_zq;
            }
        }
}
