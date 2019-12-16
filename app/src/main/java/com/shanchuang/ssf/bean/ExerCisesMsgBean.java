package com.shanchuang.ssf.bean;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/10/31
 */
public class ExerCisesMsgBean {
        /**
         * is_coll : 0
         * xiti : {"id":2,"title":"2015学年浙江省东阳七校七年级上学期期中考试语文试卷","image":"/uploads/20191017/757b90871e875dfa4300444c57266c1c.jpg","fenshu":100,"count":5,"jg_fenshu":60,"fujian":0,"defen":0}
         */

        private int is_coll;
        private XitiBean xiti;

        public int getIs_coll() {
            return is_coll;
        }

        public void setIs_coll(int is_coll) {
            this.is_coll = is_coll;
        }

        public XitiBean getXiti() {
            return xiti;
        }

        public void setXiti(XitiBean xiti) {
            this.xiti = xiti;
        }

        public static class XitiBean {
            /**
             * id : 2
             * title : 2015学年浙江省东阳七校七年级上学期期中考试语文试卷
             * image : /uploads/20191017/757b90871e875dfa4300444c57266c1c.jpg
             * fenshu : 100
             * count : 5
             * jg_fenshu : 60
             * fujian : 0
             * defen : 0
             */

            private String id;
            private String title;
            private String image;
            private String fenshu;
            private String count;
            private String jg_fenshu;
            private String fujian;
            private String defen;

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

            public String getFenshu() {
                return fenshu;
            }

            public void setFenshu(String fenshu) {
                this.fenshu = fenshu;
            }

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }

            public String getJg_fenshu() {
                return jg_fenshu;
            }

            public void setJg_fenshu(String jg_fenshu) {
                this.jg_fenshu = jg_fenshu;
            }

            public String getFujian() {
                return fujian;
            }

            public void setFujian(String fujian) {
                this.fujian = fujian;
            }

            public String getDefen() {
                return defen;
            }

            public void setDefen(String defen) {
                this.defen = defen;
            }
        }
}
