package com.shanchuang.ssf.bean;

import java.util.List;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/10/31
 */
public class ExercisesBean {
        /**
         * xiti : [{"id":2,"title":"初一数学下册 平行线与相交线（知识点快速复习）","image":"http://test.k12.com/uploads/20191016/98cfeb6b87880a036c2f89e04e0f2210.jpg","fenshu":"120","count":"50","defen":65}]
         * cate : {"cate1":"初一","cate2":"初一"}
         * next : 0
         */

        private CateBean cate;
        private int next;
        private List<XitiBean> xiti;

        public CateBean getCate() {
            return cate;
        }

        public void setCate(CateBean cate) {
            this.cate = cate;
        }

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

        public static class CateBean {
            /**
             * cate1 : 初一
             * cate2 : 初一
             */

            private String cate1;
            private String cate2;

            public String getCate1() {
                return cate1;
            }

            public void setCate1(String cate1) {
                this.cate1 = cate1;
            }

            public String getCate2() {
                return cate2;
            }

            public void setCate2(String cate2) {
                this.cate2 = cate2;
            }
        }

        public static class XitiBean {
            /**
             * id : 2
             * title : 初一数学下册 平行线与相交线（知识点快速复习）
             * image : http://test.k12.com/uploads/20191016/98cfeb6b87880a036c2f89e04e0f2210.jpg
             * fenshu : 120
             * count : 50
             * defen : 65
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
