package com.shanchuang.ssf.bean;

/**
 * 描述：添加类的描述
 *
 * @author 金源
 * @time 2019/11/20
 */
public class MessageDetailsBean {
        /**
         * news : {"id":4,"title":"央视快评：让尊老敬老助老传统发扬光大","createtime":1570504638}
         * url : http://test.k12.com/k12/article/news/id/4.html
         */

        private NewsBean news;
        private String url;

        public NewsBean getNews() {
            return news;
        }

        public void setNews(NewsBean news) {
            this.news = news;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public static class NewsBean {
            /**
             * id : 4
             * title : 央视快评：让尊老敬老助老传统发扬光大
             * createtime : 1570504638
             */

            private String id;
            private String title;
            private String createtime;

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

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }
        }
}
