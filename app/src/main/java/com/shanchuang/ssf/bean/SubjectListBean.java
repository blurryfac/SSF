package com.shanchuang.ssf.bean;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/10/24
 */
public class SubjectListBean {
        /**
         * xiti : [{"id":1,"title":"2015学年重庆江津区四校七年级上第三次月考试英语试卷","image":"http://test.k12.com/uploads/20191017/1cb8c0b5e3f6afe81dc4f45cd944fdfe.jpg","fenshu":120,"count":10,"defen":0}]
         * next : 0
         */

        private int next;
        private List<XitiBean> xiti;

        public int getNext() {
            return next;
        }

        public void setNext(int next) {
            this.next = next;
        }

        public List<XitiBean> getXiti() {
            return xiti;
        }

        public void setXiti(List<XitiBean> xiti) {
            this.xiti = xiti;
        }

        public static class XitiBean {
            /**
             * id : 1
             * title : 2015学年重庆江津区四校七年级上第三次月考试英语试卷
             * image : http://test.k12.com/uploads/20191017/1cb8c0b5e3f6afe81dc4f45cd944fdfe.jpg
             * fenshu : 120
             * count : 10
             * defen : 0
             */

            private String id;
            private String title;
            private String image;
            private String fenshu;
            private String count;
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

            public String getDefen() {
                return defen;
            }

            public void setDefen(String defen) {
                this.defen = defen;
            }
        }
}
