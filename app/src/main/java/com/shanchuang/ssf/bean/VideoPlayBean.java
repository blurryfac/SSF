package com.shanchuang.ssf.bean;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/10/30
 */
public class VideoPlayBean {
        /**
         * is_coll : 0
         * is_buy : 0
         * course : {"id":1,"title":"初中英语语法 英语应用 考试重点课程全国同步教材 初一","price":39.89,"video":null,"old_price":45,"view":0,"zhujiang":"李文俊","createtime":1571208631,"jifen":0}
         * url : http://test.k12.com/k12/article/course/id/1.html
         */

        private int is_coll;
        private int is_buy;
        private CourseBean course;
        private String url;
        private String user_jifen;

    public String getUser_jifen() {
        return user_jifen;
    }

    public void setUser_jifen(String user_jifen) {
        this.user_jifen = user_jifen;
    }

    public int getIs_coll() {
            return is_coll;
        }

        public void setIs_coll(int is_coll) {
            this.is_coll = is_coll;
        }

        public int getIs_buy() {
            return is_buy;
        }

        public void setIs_buy(int is_buy) {
            this.is_buy = is_buy;
        }

        public CourseBean getCourse() {
            return course;
        }

        public void setCourse(CourseBean course) {
            this.course = course;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public static class CourseBean {
            /**
             * id : 1
             * title : 初中英语语法 英语应用 考试重点课程全国同步教材 初一
             * price : 39.89
             * video : null
             * old_price : 45
             * view : 0
             * zhujiang : 李文俊
             * createtime : 1571208631
             * jifen : 0
             */

            private String id;
            private String title;
            private String price;
            private String image;
            private String video;
            private String old_price;
            private String view;
            private String zhujiang;
            private String createtime;
            private String jifen;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
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

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getVideo() {
                return video;
            }

            public void setVideo(String video) {
                this.video = video;
            }

            public String getOld_price() {
                return old_price;
            }

            public void setOld_price(String old_price) {
                this.old_price = old_price;
            }

            public String getView() {
                return view;
            }

            public void setView(String view) {
                this.view = view;
            }

            public String getZhujiang() {
                return zhujiang;
            }

            public void setZhujiang(String zhujiang) {
                this.zhujiang = zhujiang;
            }

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }

            public String getJifen() {
                return jifen;
            }

            public void setJifen(String jifen) {
                this.jifen = jifen;
            }
        }
}
